package ravenNPlus.client.utils.font.fr;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import ravenNPlus.client.main.Client;

public class TTFFontRenderer {
    private final boolean antiAlias;
    private final Font font;
    private boolean fractionalMetrics;
    private TTFFontRenderer.CharacterData[] regularData;
    private TTFFontRenderer.CharacterData[] boldData;
    private TTFFontRenderer.CharacterData[] italicsData;
    private final int[] colorCodes;
    private static int RANDOM_OFFSET = 1;
    public static TTFFontRenderer instance;

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font) {
        this(executorService, textureQueue, font, 256);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, int characterCount) {
        this(executorService, textureQueue, font, characterCount, true);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, boolean antiAlias) {
        this(executorService, textureQueue, font, 256, antiAlias);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, int characterCount, boolean antiAlias) {
        this.fractionalMetrics = false;
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        int[] regularTexturesIds = new int[characterCount];
        int[] boldTexturesIds = new int[characterCount];
        int[] italicTexturesIds = new int[characterCount];

        for(int i = 0; i < characterCount; ++i) {
            regularTexturesIds[i] = GL11.glGenTextures();
            boldTexturesIds[i] = GL11.glGenTextures();
            italicTexturesIds[i] = GL11.glGenTextures();
        }

        executorService.execute(() -> {
            this.regularData = this.setup(new TTFFontRenderer.CharacterData[characterCount], regularTexturesIds, textureQueue, 0);
        });
        executorService.execute(() -> {
            this.boldData = this.setup(new TTFFontRenderer.CharacterData[characterCount], boldTexturesIds, textureQueue, 1);
        });
        executorService.execute(() -> {
            this.italicsData = this.setup(new TTFFontRenderer.CharacterData[characterCount], italicTexturesIds, textureQueue, 2);
        });
    }

    private TTFFontRenderer.CharacterData[] setup(TTFFontRenderer.CharacterData[] characterData, int[] texturesIds, ConcurrentLinkedQueue<TextureData> textureQueue, int type) {
        this.generateColors();
        Font font = this.font.deriveFont(type);
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
        utilityGraphics.setFont(font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();

        for(int index = 0; index < characterData.length; ++index) {
            char character = (char)index;
            Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
            float width = (float)characterBounds.getWidth() + 8.0F;
            float height = (float)characterBounds.getHeight();
            BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int((double)width), MathHelper.ceiling_double_int((double)height), 2);
            Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
            graphics.setFont(font);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
            graphics.setColor(Color.WHITE);
            if (this.antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }

            graphics.drawString(character + "", 4, fontMetrics.getAscent());
            int textureId = texturesIds[index];
            this.createTexture(textureId, characterImage, textureQueue);
            characterData[index] = new TTFFontRenderer.CharacterData(character, (float)characterImage.getWidth(), (float)characterImage.getHeight(), textureId);
        }

        return characterData;
    }

    private void createTexture(int textureId, BufferedImage image, ConcurrentLinkedQueue<TextureData> textureQueue) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for(int y = 0; y < image.getHeight(); ++y) {
            for(int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 255));
                buffer.put((byte)(pixel >> 8 & 255));
                buffer.put((byte)(pixel & 255));
                buffer.put((byte)(pixel >> 24 & 255));
            }
        }

        buffer.flip();
        textureQueue.add(new TextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }

    public void drawString(String text, float x, float y, int color) {
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        this.renderString(text, x, y, color, false);
    }

    public void drawString(String text, float x, float y, int color, boolean shadow) {
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        this.renderString(text, x, y, color, shadow);
    }

    public void drawStringWithColorCode(String text, float x, float y, int color) {
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        this.renderString(text, x, y, color, false, true);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        float width = this.getWidth(text) / 2.0F;
        this.renderString(text, x - width, y, color, false);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        GL11.glTranslated(0.5D, 0.5D, 0.0D);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated(-0.5D, -0.5D, 0.0D);
        this.renderString(text, x, y, color, false);
    }

    private int renderString(String text, float x, float y, int color, boolean shadow) {
        if (!text.equals("") && text.length() != 0) {
            x = (float)Math.round(x * 10.0F) / 10.0F;
            y = (float)Math.round(y * 10.0F) / 10.0F;
            Minecraft mc = Minecraft.getMinecraft();
            GL11.glPushMatrix();
            GlStateManager.scale(0.5D, 0.5D, 1.0D);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            x -= 2.0F;
            y -= 2.0F;
            x += 0.5F;
            y += 0.5F;
            x *= 2.0F;
            y *= 2.0F;
            TTFFontRenderer.CharacterData[] characterData = this.regularData;
            int length = text.length();
            double multiplier = 255.0D * (double)(shadow ? 4 : 1);
            Color c = new Color(color);
            GL11.glColor4d((double)c.getRed() / multiplier, (double)c.getGreen() / multiplier, (double)c.getBlue() / multiplier, (double)(color >> 24 & 255) / 255.0D);

            try {
                for(int i = 0; i < length; ++i) {
                    char character = text.charAt(i);
                    this.drawChar(character, characterData, x, y);
                    if (character < characterData.length) {
                        TTFFontRenderer.CharacterData charData = characterData[character];
                        x += charData.width - 8.0F;
                    }
                }
            } catch (StringIndexOutOfBoundsException var15) {
                System.out.println("[" + Client.name + "] Couldn't render text");
                var15.printStackTrace();
            }

            GL11.glPopMatrix();
            GlStateManager.disableBlend();
            GlStateManager.bindTexture(0);
            GlStateManager.resetColor();
            return (int)x;
        } else {
            return 0;
        }
    }

    private int renderString(String text, float x, float y, int color, boolean shadow, boolean nickga) {
        if (!text.equals("") && text.length() != 0) {
            x = (float)Math.round(x * 10.0F) / 10.0F;
            y = (float)Math.round(y * 10.0F) / 10.0F;
            GL11.glPushMatrix();
            GlStateManager.scale(0.5D, 0.5D, 1.0D);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            x -= 2.0F;
            y -= 2.0F;
            x += 0.5F;
            y += 0.5F;
            x *= 2.0F;
            y *= 2.0F;
            TTFFontRenderer.CharacterData[] characterData = this.regularData;
            boolean underlined = false;
            boolean strikethrough = false;
            boolean obfuscated = false;
            int length = text.length();
            double multiplier = 255.0D * (double)(shadow ? 4 : 1);
            Color c = new Color(color);
            GL11.glColor4d((double)c.getRed() / multiplier, (double)c.getGreen() / multiplier, (double)c.getBlue() / multiplier, (double)(color >> 24 & 255) / 255.0D);

            for(int i = 0; i < length; ++i) {
                char character = text.charAt(i);
                char previous = i > 0 ? text.charAt(i - 1) : 46;
                if (previous != '&') {
                    if (character == '&') {
                        try {
                            int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                            if (index < 16) {
                                obfuscated = false;
                                strikethrough = false;
                                underlined = false;
                                characterData = this.regularData;
                                if (index < 0) {
                                    index = 15;
                                }

                                if (shadow) {
                                    index += 16;
                                }

                                int textColor = this.colorCodes[index];
                                GL11.glColor4d((double)(textColor >> 16) / 255.0D, (double)(textColor >> 8 & 255) / 255.0D, (double)(textColor & 255) / 255.0D, (double)(color >> 24 & 255) / 255.0D);
                            } else if (index <= 20) {
                                switch(index) {
                                    case 16:
                                        obfuscated = true;
                                        break;
                                    case 17:
                                        characterData = this.boldData;
                                        break;
                                    case 18:
                                        strikethrough = true;
                                        break;
                                    case 19:
                                        underlined = true;
                                        break;
                                    case 20:
                                        characterData = this.italicsData;
                                }
                            } else {
                                obfuscated = false;
                                strikethrough = false;
                                underlined = false;
                                characterData = this.regularData;
                                GL11.glColor4d(shadow ? 0.25D : 1.0D, shadow ? 0.25D : 1.0D, shadow ? 0.25D : 1.0D, (double)(color >> 24 & 255) / 255.0D);
                            }
                        } catch (StringIndexOutOfBoundsException var20) {
                        }
                    } else if (character <= 255) {
                        if (obfuscated) {
                            character += (char)RANDOM_OFFSET;
                        }

                        this.drawChar(character, characterData, x, y);
                        TTFFontRenderer.CharacterData charData = characterData[character];
                        if (strikethrough) {
                            this.drawLine(new Vector2f(0.0F, charData.height / 2.0F), new Vector2f(charData.width, charData.height / 2.0F), 3.0F);
                        }

                        if (underlined) {
                            this.drawLine(new Vector2f(0.0F, charData.height - 15.0F), new Vector2f(charData.width, charData.height - 15.0F), 3.0F);
                        }

                        x += charData.width - 8.0F;
                    }
                }
            }

            GL11.glPopMatrix();
            GlStateManager.disableBlend();
            GlStateManager.bindTexture(0);
            GlStateManager.resetColor();
            return (int)x;
        } else {
            return 0;
        }
    }

    public float getWidth(String text) {
        float width = 0.0F;
        TTFFontRenderer.CharacterData[] characterData = this.regularData;

        try {
            int length = text.length();

            for(int i = 0; i < length; ++i) {
                char character = text.charAt(i);
                TTFFontRenderer.CharacterData charData = characterData[character];
                width += (charData.width - 8.0F) / 2.0F;
            }
        } catch (ArrayIndexOutOfBoundsException var8) {
            return this.getWidth("A");
        }

        return width + 2.0F;
    }

    public float getWidthProtect(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        float width = 0.0F;
        TTFFontRenderer.CharacterData[] characterData = this.regularData;
        int length = text.length();

        for(int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            TTFFontRenderer.CharacterData charData = characterData[character];
            width += (charData.width - 8.0F) / 2.0F;
        }

        return width + 2.0F;
    }

    public float getHeight(String text) {
        float height = 0.0F;
        TTFFontRenderer.CharacterData[] characterData = this.regularData;
        int length = text.length();

        for(int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            TTFFontRenderer.CharacterData charData = characterData[character];
            height = Math.max(height, charData.height);
        }

        return height / 2.0F - 2.0F;
    }

    public float getHeight() {
        return this.getHeight("I");
    }

    private void drawChar(char character, TTFFontRenderer.CharacterData[] characterData, float x, float y) {
        if (character < characterData.length) {
            TTFFontRenderer.CharacterData charData = characterData[character];
            charData.bind();
            GL11.glBegin(6);
            GL11.glTexCoord2f(0.0F, 0.0F);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glTexCoord2f(0.0F, 1.0F);
            GL11.glVertex2d((double)x, (double)(y + charData.height));
            GL11.glTexCoord2f(1.0F, 1.0F);
            GL11.glVertex2d((double)(x + charData.width), (double)(y + charData.height));
            GL11.glTexCoord2f(1.0F, 0.0F);
            GL11.glVertex2d((double)(x + charData.width), (double)y);
            GL11.glEnd();
        }
    }

    private void drawLine(Vector2f start, Vector2f end) {
        GL11.glDisable(3553);
        GL11.glLineWidth(3.0F);
        GL11.glBegin(1);
        GL11.glVertex2f(start.x, start.y);
        GL11.glVertex2f(end.x, end.y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void drawLine(Vector2f start, Vector2f end, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(start.x, start.y);
        GL11.glVertex2f(end.x, end.y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void generateColors() {
        for(int i = 0; i < 32; ++i) {
            int thingy = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + thingy;
            int green = (i >> 1 & 1) * 170 + thingy;
            int blue = (i & 1) * 170 + thingy;
            if (i == 6) {
                red += 85;
            }

            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

    }

    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for(int k = i; k >= 0 && k < text.length() && f < (float)width; k += j) {
            char c0 = text.charAt(k);
            float f1 = this.getWidth(String.valueOf(c0));
            if (flag) {
                flag = false;
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag1 = false;
                    }
                } else {
                    flag1 = true;
                }
            } else if (f1 < 0.0F) {
                flag = true;
            } else {
                f += f1;
                if (flag1) {
                    ++f;
                }
            }

            if (f > (float)width) {
                break;
            }

            if (reverse) {
                stringbuilder.insert(0, c0);
            } else {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    static class CharacterData {
        public char character;
        public float width;
        public float height;
        private final int textureId;

        public CharacterData(char character, float width, float height, int textureId) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture(3553, this.textureId);
        }
    }
}