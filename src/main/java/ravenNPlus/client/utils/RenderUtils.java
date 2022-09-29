package ravenNPlus.client.utils;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3d;
import javafx.scene.Camera;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.util.glu.Disk;
import ravenNPlus.client.module.modules.combat.AntiBot;
import ravenNPlus.client.utils.animations.AnimationUtil;
import ravenNPlus.client.utils.notifications.Notification;
import ravenNPlus.client.utils.notifications.Manager;
import ravenNPlus.client.utils.notifications.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.lang.reflect.Method;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.Math.*;
import static org.lwjgl.Sys.getTime;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL13.GL_SAMPLE_ALPHA_TO_COVERAGE;

public class RenderUtils {

    // icons
    public static ResourceLocation icon = new ResourceLocation("ravenNPlus/icon.png");
    public static ResourceLocation icon_old = new ResourceLocation("ravenNPlus/old_icon.png");
    public static ResourceLocation confirm = new ResourceLocation("ravenNPlus/icon/true.png");
    public static ResourceLocation decline = new ResourceLocation("ravenNPlus/icon/false.png");
    public static ResourceLocation info = new ResourceLocation("ravenNPlus/icon/info.png");
    public static ResourceLocation lock = new ResourceLocation("ravenNPlus/icon/lock.png");
    public static ResourceLocation unlock = new ResourceLocation("ravenNPlus/icon/unlock.png");
    public static ResourceLocation menu = new ResourceLocation("ravenNPlus/icon/menu.png");
    public static ResourceLocation setting = new ResourceLocation("ravenNPlus/icon/settings.png");
    public static ResourceLocation speech = new ResourceLocation("ravenNPlus/icon/speech.png");
    public static ResourceLocation stringShadow = new ResourceLocation("ravenNPlus/shadow2.png");
    public static ResourceLocation circleShadow = new ResourceLocation("ravenNPlus/shadow1.png");
    public static ResourceLocation comfortaa = new ResourceLocation("ravenNPlus/fonts/comfortaa.ttf");
    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer font = mc.fontRendererObj;
    public static ScaledResolution sc = new ScaledResolution(mc);
    public static int scWidth = sc.getScaledWidth();
    public static int scHight = sc.getScaledHeight();

    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void startDrawing() {
        GL11.glEnable(GL_BLEND);
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        try {
            Method m = ReflectionHelper.findMethod(
                    EntityRenderer.class,
                    Minecraft.getMinecraft().entityRenderer,
                    new String[]{
                            "func_78479_a",
                            "setupCameraTransform"
                    },
                    float.class, int.class
            );

            m.setAccessible(true);
            m.invoke(Minecraft.getMinecraft().entityRenderer, Utils.Client.getTimer().renderPartialTicks, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Color blend(Color color, Color color1, double d0) {
        float f = (float) d0;
        float f1 = 1.0F - f;
        float[] afloat = new float[3];
        float[] afloat1 = new float[3];

        color.getColorComponents(afloat);
        color1.getColorComponents(afloat1);

        return new Color(afloat[0] * f + afloat1[0] * f1, afloat[1] * f + afloat1[1] * f1, afloat[2] * f + afloat1[2] * f1);
    }

    public static void drawAnimatedRect(int x, int y, int height, int from, int to, int color) {
        net.minecraft.client.gui.Gui.drawRect(x, y, (int) AnimationUtil.calculateCompensation(x+from, x+to, 50, 80L), height, color);
    }

    public static void drawRect(double d0, double d1, double d2, double d3, int i) {
        double d4;

        if (d0 < d2) {
            d4 = d0;
            d0 = d2;
            d2 = d4;
        }

        if (d1 < d3) {
            d4 = d1;
            d1 = d3;
            d3 = d4;
        }

        float f = (float) (i >> 24 & 255) / 255.0F;
        float f1 = (float) (i >> 16 & 255) / 255.0F;
        float f2 = (float) (i >> 8 & 255) / 255.0F;
        float f3 = (float) (i & 255) / 255.0F;
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(d0, d3, 0.0D).endVertex();
        wR.pos(d2, d3, 0.0D).endVertex();
        wR.pos(d2, d1, 0.0D).endVertex();
        wR.pos(d0, d1, 0.0D).endVertex();
        tess.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect(float f, float f1, float f2, float f3, float f4, int i, int j) {
        drawRect(f, f1, f2, f3, j);
        float f5 = (float) (i >> 24 & 255) / 255.0F;
        float f6 = (float) (i >> 16 & 255) / 255.0F;
        float f7 = (float) (i >> 8 & 255) / 255.0F;
        float f8 = (float) (i & 255) / 255.0F;

        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        glPushMatrix();
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glLineWidth(f4);
        GL11.glBegin(1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator tess = Tessellator.getInstance();
        final WorldRenderer wR = tess.getWorldRenderer();

        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        wR.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tess.draw();
    }

    public static void drawEntityHUD(Entity en, int entityX, int entityY, int backgroundX, int backgroundY, int enSize, int range, boolean cl, boolean bg, boolean mouse) {
        if (!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(AntiBot.isBot(target)) return;
        float targetLookX;
        float targetLookY;

        int I = (int) en.height *12+35;
        int l = (int) en.width *12;

        int y1 = 19;
        int y2 = 35+I;
        int x1 = 40;
        int x2 = 30+l;

        if(cl)
            Utils.HUD.fontRender.drawString("", 0, 0, 0xFFFFFFFF);

        if(mouse) {
            targetLookX = Mouse.getX();
            targetLookY = Mouse.getY();
        } else {
            targetLookX = 15;
            targetLookY = 20;
        }

        if(bg)
            RoundedUtils.drawSmoothRoundedRect(entityX - y1, entityY - y2, backgroundX - x2, backgroundY - x1, 3, Integer.MIN_VALUE);

        GuiInventory.drawEntityOnScreen(entityX, entityY, enSize, targetLookX, targetLookY, (EntityLivingBase) en);
    }

    public static void drawStringHUD(int x, int y, int range, boolean background, boolean cl, boolean head) {
        if (!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(AntiBot.isBot(target)) return;

        /*
        if (target.getHealth() < 5) {
            heartColor = Color.red.getRGB();
        } else if (target.getHealth() < 10) {
            heartColor = Color.orange.getRGB();
        } else if (target.getHealth() < 15) {
            heartColor = Color.yellow.getRGB();
        } else if (target.getHealth() < 20) {
            heartColor = Color.GREEN.getRGB();
        } else {
            heartColor = Color.GREEN.getRGB();
        }
        */

        int c = 0xFFFFFFFF;
        int heartColor;
        float targetHealth = target.getHealth();
        if(targetHealth < 1) {
            heartColor = new Color(238, 0, 0).getRGB();
        } else if (targetHealth < 2) {
            heartColor = new Color(215, 25, 0).getRGB();
        } else if (targetHealth < 3) {
            heartColor = new Color(203, 37, 0).getRGB();
        } else if (targetHealth < 4) {
            heartColor = new Color(192, 49, 0).getRGB();
        } else if (targetHealth < 5) {
            heartColor = new Color(181, 61, 0).getRGB();
        } else if (targetHealth < 6) {
            heartColor = new Color(170, 74, 0).getRGB();
        } else if (targetHealth < 7) {
            heartColor = new Color(158, 86, 0).getRGB();
        } else if (targetHealth < 8) {
            heartColor = new Color(147, 98, 0).getRGB();
        } else if (targetHealth < 9) {
            heartColor = new Color(136, 110, 0).getRGB();
        } else if (targetHealth < 10) {
            heartColor = new Color(124, 122, 0).getRGB();
        } else if (targetHealth < 11) {
            heartColor = new Color(113, 134, 0).getRGB();
        } else if (targetHealth < 12) {
            heartColor = new Color(102, 146, 0).getRGB();
        } else if (targetHealth < 13) {
            heartColor = new Color(90, 158, 0).getRGB();
        } else if (targetHealth < 14) {
            heartColor = new Color(79, 170, 0).getRGB();
        } else if (targetHealth < 15) {
            heartColor = new Color(68, 182, 0).getRGB();
        } else if (targetHealth < 16) {
            heartColor = new Color(56, 194, 0).getRGB();
        } else if (targetHealth < 17) {
            heartColor = new Color(24, 219, 0).getRGB();
        } else if (targetHealth < 18) {
            heartColor = new Color(23, 231, 0).getRGB();
        } else if (targetHealth < 19) {
            heartColor = new Color(11, 243, 0).getRGB();
        } else if (targetHealth < 20) {
            heartColor = new Color(0, 255, 0).getRGB();
        } else heartColor = 0;

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        Utils.HUD.fontRender.drawString("", 0, 0, c);

        if(background && !head)
            RoundedUtils.drawSmoothRoundedRect(x - 3, y - 3, x + 60 + fr.getStringWidth(target.getName())-12,y + 20, 3, Integer.MIN_VALUE);

        if(background && head)
            RoundedUtils.drawSmoothRoundedRect(x - 22, y - 3, x + 60 + fr.getStringWidth(target.getName())-12, y + 20, 3, Integer.MIN_VALUE);

        if(head)
            RenderUtils.drawHead(target.getName(), x-20, y-1, 18, 19);
        //RenderUtils.drawHead( ,x-20, y-1, 18, 19);

        if(cl)
            Utils.HUD.fontRender.drawString("", 0, 0, 0xFFFFFFFF);

        fr.drawString("Target : ", x, y, c);
        fr.drawString(target.getName(), x + fr.getStringWidth("Target : ") + 2, y, Color.red.getRGB());

        fr.drawString("Health : ", x + 1, y + 10, c);
        fr.drawString(" " + targetHealth, x + fr.getStringWidth("Health : ") - 1, y + 10, heartColor);
    }

    public static void quickDrawHead(ResourceLocation skin, int x, int y, int width, int height) {
        mc.getTextureManager().bindTexture(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8F, 8F, 8, 8, width, height, 64F, 64F);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40F, 8F, 8, 8, width, height, 64F, 64F);
    }

    public static void drawHead(String username, int x, int y, int width, int height) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        ResourceLocation r = new ResourceLocation("http://skins.minecraft.net/MinecraftSkins/%s.png", username);
        mc.getTextureManager().bindTexture(r);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8F, 8F, 8, 8, width, height, 64F, 64F);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40F, 8F, 8, 8, width, height, 64F, 64F);
    }

    public static void drawHead(ResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8F, 8F, 8, 8, width, height, 64F, 64F);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40F, 8F, 8, 8, width, height, 64F, 64F);
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();
        wR.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        wR.pos(x, y + height, 0.0D).tex(u * f, (v + (float) vHeight) * f1).endVertex();
        wR.pos(x + width, y + height, 0.0D).tex((u + (float) uWidth) * f, (v + (float) vHeight) * f1).endVertex();
        wR.pos(x + width, y, 0.0D).tex((u + (float) uWidth) * f, v * f1).endVertex();
        wR.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tess.draw();
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        float z = 0;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }

        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }

        double x1 = (paramXStart + radius);
        double y1 = (paramYStart + radius);
        double x2 = (paramXEnd - radius);
        double y2 = (paramYEnd - radius);

        if (popPush) glPushMatrix();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(1);

        glColor4f(red, green, blue, alpha);
        glBegin(GL_POLYGON);

        double degree = PI / 180;
        for (double i = 0; i <= 90; i += 1)
            glVertex2d(x2 + sin(i * degree) * radius, y2 + cos(i * degree) * radius);
        for (double i = 90; i <= 180; i += 1)
            glVertex2d(x2 + sin(i * degree) * radius, y1 + cos(i * degree) * radius);
        for (double i = 180; i <= 270; i += 1)
            glVertex2d(x1 + sin(i * degree) * radius, y1 + cos(i * degree) * radius);
        for (double i = 270; i <= 360; i += 1)
            glVertex2d(x1 + sin(i * degree) * radius, y2 + cos(i * degree) * radius);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        if (popPush) glPopMatrix();
    }

    public static void drawCircle(float x, float y, float radius, int start, int end) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        GL11.glColor4d(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), Color.WHITE.getAlpha());

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2F);
        glBegin(GL_LINE_STRIP);
        for (float i = end; i >= start; i -= (360 / 90.0f))
            glVertex2f((float) (x + (cos(i * PI / 180) * (radius * 1.001F))), (float) (y + (sin(i * PI / 180) * (radius * 1.001F))));
        glEnd();
        glDisable(GL_LINE_SMOOTH);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 4; i++) {
            int rot = (int) ((System.nanoTime() / 5000000 * i) % 360);
            drawCircle(x, y, i * 10, rot - 180, rot);
        }
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();
        wR.begin(3, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tess.draw();
        wR.begin(3, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tess.draw();
        wR.begin(1, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tess.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tess.draw();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tess.draw();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tess.draw();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tess.draw();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tess.draw();
        wR.begin(7, DefaultVertexFormats.POSITION);
        wR.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        wR.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        wR.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        wR.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tess.draw();
    }

    public static void lineWidth(final float width) {
        GL11.glLineWidth(width);
    }

    public static void vertex(final double x, final double y) {
        GL11.glVertex2d(x, y);
    }

    public static void Notify(Type type, String title, String message, int length) {
        Manager.show(new Notification(type, title, message, length));
    }

    public static void draw2DImage(ResourceLocation image, int x, int y, int width, int height, Color c) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
        mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        //  mc.getTextureManager().bindTexture(null);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void draw2DImageByString(String image, int x, int y, int width, int height, Color c) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
        ResourceLocation img = new ResourceLocation(image);
        mc.renderEngine.bindTexture(img);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        //  mc.getTextureManager().bindTexture(null);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    protected static boolean isHovered(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < width && mouseY < height;
    }

    public static void renderItemStack(ItemStack is, int xPos, int yPos, boolean dura) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0F;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, xPos, yPos);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, is, xPos, yPos);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(is, xPos, yPos, dura);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.popMatrix();
    }

    public static void drawAnimatedRect(String text, int width, int height, int fadedIn, int fadeOut, int end) {
        // width = 45;  | 120
        // height = 30; |  30

        long time = getTime();
        double offset;
        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        drawRect(scHight - offset, scHight - 5 - height, scWidth, scHight - 5, -1);
        drawRect(scWidth - offset, scHight - 5 - height, scWidth - offset + 4, scHight - 5, -1);

        font.drawString(text, (int) (scWidth - offset + 8), scHight - 2 - height, -1);
        int xBegin = (int) (scWidth - offset + 8);
        int yBegin = scHight - 22;
        int xEnd = xBegin + scWidth;
        int yEnd = yBegin + 1;
        drawRect(xBegin, yBegin, xEnd, yEnd, new Color(-1).getRGB());
    }

    public static void renderEnchantText(ItemStack is, int xPos, int yPos, boolean dura) {
        int newYPos = yPos - 24;
        if (is.getEnchantmentTagList() != null && is.getEnchantmentTagList().tagCount() >= 6) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("god", (float) (xPos * 2), (float) newYPos, 16711680);
        } else {
            if (is.getItem() instanceof ItemArmor) {
                int protection = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is);
                int projectileProtection = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, is);
                int blastProtectionLvL = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, is);
                int fireProtection = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, is);
                int thornsLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                int remainingDurability = is.getMaxDamage() - is.getItemDamage();
                if (dura) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(String.valueOf(remainingDurability), (float) (xPos * 2), (float) yPos, 16777215);
                }

                if (protection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("prot" + protection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (projectileProtection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("proj" + projectileProtection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (blastProtectionLvL > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("bp" + blastProtectionLvL, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fireProtection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("frp" + fireProtection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (thornsLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("th" + thornsLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }
            }

            if (is.getItem() instanceof ItemBow) {
                int powerLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);
                int punchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);
                int flameLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                if (powerLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("pow" + powerLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (punchLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("pun" + punchLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (flameLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("flame" + flameLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }
            }

            if (is.getItem() instanceof ItemSword) {
                int sharpnessLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
                int knockbackLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, is);
                int fireAspectLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                if (sharpnessLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("sh" + sharpnessLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (knockbackLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("kb" + knockbackLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fireAspectLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("fire" + fireAspectLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                }
            }

            if (is.getItem() instanceof ItemTool) {
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                int efficiencyLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, is);
                int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, is);
                int silkTouchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, is);
                if (efficiencyLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("eff" + efficiencyLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fortuneLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("fo" + fortuneLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (silkTouchLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("silk" + silkTouchLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("ub" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                }
            }

            if (is.getItem() == Items.golden_apple && is.hasEffect()) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("god", (float) (xPos * 2), (float) newYPos, -1);
            }
        }
    }

    public static void drawAnimatedRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawAnimatedRect(int mode, double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glColor4f(red, green, blue, 0.20F);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void scissorBox(int x, int y, int xend, int yend) {
        int width = xend - x;
        int height = yend - y;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int factor = sr.getScaleFactor();
        int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
        glScissor(x * factor, bottomY * factor, width * factor, height * factor);
    }

    public static int interpolateColor(int rgba1, int rgba2, float percent) {
        int r1 = rgba1 & 0xFF, g1 = rgba1 >> 8 & 0xFF, b1 = rgba1 >> 16 & 0xFF, a1 = rgba1 >> 24 & 0xFF;
        int r2 = rgba2 & 0xFF, g2 = rgba2 >> 8 & 0xFF, b2 = rgba2 >> 16 & 0xFF, a2 = rgba2 >> 24 & 0xFF;

        int r = (int) (r1 < r2 ? r1 + (r2 - r1) * percent : r2 + (r1 - r2) * percent);
        int g = (int) (g1 < g2 ? g1 + (g2 - g1) * percent : g2 + (g1 - g2) * percent);
        int b = (int) (b1 < b2 ? b1 + (b2 - b1) * percent : b2 + (b1 - b2) * percent);
        int a = (int) (a1 < a2 ? a1 + (a2 - a1) * percent : a2 + (a1 - a2) * percent);

        return r | g << 8 | b << 16 | a << 24;
    }

    public static void setupLineSmooth() {
        glEnable(GL_BLEND);
        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_LINE_SMOOTH);
        glDisable(GL_TEXTURE_2D);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        glShadeModel(GL_SMOOTH);
    }

    public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, float thickness) {
        glPushMatrix();
        setupLineSmooth();
        glLineWidth(thickness);
        glBegin(GL_LINES);
        glVertex3d(startX, startY, startZ);
        glVertex3d(endX, endY, endZ);
        glEnd();
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_MULTISAMPLE);
        glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        glPopMatrix();
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 1f, 1f).getRGB();
    }

    public static List getEntitiesWithinAABB(AxisAlignedBB axisalignedBB) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor_double(((axisalignedBB.minX - 2.0) / 16.0));
        int chunkMaxX = MathHelper.floor_double(((axisalignedBB.maxX + 2.0) / 16.0));
        int chunkMinZ = MathHelper.floor_double(((axisalignedBB.minZ - 2.0) / 16.0));
        int chunkMaxZ = MathHelper.floor_double(((axisalignedBB.maxZ + 2.0) / 16.0));
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (!mc.theWorld.getChunkProvider().chunkExists(x, z)) {
                    continue;
                }
                mc.theWorld.getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity(mc.thePlayer, axisalignedBB, list, null);
            }
        }
        return list;
    }

    public static double interpolate(double posX, double lastTickPosX, float elapsedPartialTicks) {

        return 0;
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    private void renderPoint() {
        GL11.glBegin(1);
        GL11.glVertex3d(-0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, -0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glEnd();
        Cylinder c = new Cylinder();
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawMode(DrawMode.LINE);
        c.resizeRelocate(0.5f, 0.5f, 0.1f, 24);
    }

    public static void enableGL3D(float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        mc.entityRenderer.disableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }

    public static void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawClock(float x, float y, float radius, int slices, int loops, float lineWidth, boolean fill, Color color) {
        Disk disk = new Disk();
        int hourAngle = 180 + -(Calendar.getInstance().get(Calendar.HOUR) * 30 + Calendar.getInstance().get(Calendar.MINUTE) / 2);
        int minuteAngle = 180 + -(Calendar.getInstance().get(Calendar.MINUTE) * 6 + Calendar.getInstance().get(Calendar.SECOND) / 10);
        int secondAngle = 180 + -(Calendar.getInstance().get(Calendar.SECOND) * 6);
        if (fill) {
            GL11.glPushMatrix();
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(lineWidth);
            GL11.glDisable(3553);
            disk.setOrientation(100020);
            disk.setDrawStyle(100012);
            GL11.glTranslated(x, y, 0.0D);
            disk.draw(0.0F, radius, slices, loops);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
            GL11.glEnable(3042);
            GL11.glLineWidth(lineWidth);
            GL11.glDisable(3553);
            GL11.glBegin(3);
            ArrayList<Vec2f> hVectors = new ArrayList<>();
            float hue = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
            for (int i = 0; i <= 360; i++) {
                Vec2f vec = new Vec2f(x + (float)Math.sin(i * Math.PI / 180.0D) * radius, y + (float)Math.cos(i * Math.PI / 180.0D) * radius);
                hVectors.add(vec);
            }
            Color color1 = new Color(Color.HSBtoRGB(hue, 1.0F, 1.0F));
            for (int j = 0; j < hVectors.size() - 1; j++) {
                GL11.glColor4f(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, color1.getAlpha() / 255.0F);
                GL11.glVertex3d(((Vec2f)hVectors.get(j)).x, ((Vec2f)hVectors.get(j)).y, 0.0D);
                GL11.glVertex3d(((Vec2f)hVectors.get(j + 1)).x, ((Vec2f)hVectors.get(j + 1)).y, 0.0D);
                color1 = new Color(Color.HSBtoRGB(hue += 0.0027777778F, 1.0F, 1.0F));
            }
            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        drawLine(x, y, x + (float)Math.sin(hourAngle * Math.PI / 180.0D) * radius / 2.0F, y + (float)Math.cos(hourAngle * Math.PI / 180.0D) * radius / 2.0F, 1.0F, Color.WHITE.getRGB(), 1);
        drawLine(x, y, x + (float)Math.sin(minuteAngle * Math.PI / 180.0D) * (radius - radius / 10.0F), y + (float)Math.cos(minuteAngle * Math.PI / 180.0D) * (radius - radius / 10.0F), 1.0F, Color.WHITE.getRGB(), 1);
        drawLine(x, y, x + (float)Math.sin(secondAngle * Math.PI / 180.0D) * (radius - radius / 10.0F), y + (float)Math.cos(secondAngle * Math.PI / 180.0D) * (radius - radius / 10.0F), 1.0F, Color.RED.getRGB(), 1);
    }

    public static void renderSkeleton(EntityPlayer player, float[][] rotations, Color color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        Vec3d interp = Utils.Player.getInterpolatedRenderPos((Entity)player, Utils.Client.getTimer().renderPartialTicks);
        double pX = interp.x;
        double pY = interp.y;
        double pZ = interp.z;
        GlStateManager.translate(pX, pY, pZ);
        GlStateManager.rotate(-player.renderYawOffset, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0D, 0.0D, player.isSneaking() ? -0.235D : 0.0D);
        float sneak = player.isSneaking() ? 0.6F : 0.75F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.125D, sneak, 0.0D);
        if (rotations[3][0] != 0.0F)
            GlStateManager.rotate(rotations[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        if (rotations[3][1] != 0.0F)
            GlStateManager.rotate(rotations[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
        if (rotations[3][2] != 0.0F)
            GlStateManager.rotate(rotations[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, -sneak, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.125D, sneak, 0.0D);
        if (rotations[4][0] != 0.0F)
            GlStateManager.rotate(rotations[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        if (rotations[4][1] != 0.0F)
            GlStateManager.rotate(rotations[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
        if (rotations[4][2] != 0.0F)
            GlStateManager.rotate(rotations[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, -sneak, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.translate(0.0D, 0.0D, player.isSneaking() ? 0.25D : 0.0D);
        GlStateManager.pushMatrix();
        double sneakOffset = 0.0D;
        if (player.isSneaking())
            sneakOffset = -0.05D;
        GlStateManager.translate(0.0D, sneakOffset, player.isSneaking() ? -0.01725D : 0.0D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.375D, sneak + 0.55D, 0.0D);
        if (rotations[1][0] != 0.0F)
            GlStateManager.rotate(rotations[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        if (rotations[1][1] != 0.0F)
            GlStateManager.rotate(rotations[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
        if (rotations[1][2] != 0.0F)
            GlStateManager.rotate(-rotations[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, -0.5D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.375D, sneak + 0.55D, 0.0D);
        if (rotations[2][0] != 0.0F)
            GlStateManager.rotate(rotations[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        if (rotations[2][1] != 0.0F)
            GlStateManager.rotate(rotations[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
        if (rotations[2][2] != 0.0F)
            GlStateManager.rotate(-rotations[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, -0.5D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak + 0.55D, 0.0D);
        if (rotations[0][0] != 0.0F)
            GlStateManager.rotate(rotations[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, 0.3D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.rotate(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
        if (player.isSneaking())
            sneakOffset = -0.16175D;
        GlStateManager.translate(0.0D, sneakOffset, player.isSneaking() ? -0.48025D : 0.0D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak, 0.0D);
        GL11.glBegin(3);
        GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
        GL11.glVertex3d(0.125D, 0.0D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak, 0.0D);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glVertex3d(0.0D, 0.55D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak + 0.55D, 0.0D);
        GL11.glBegin(3);
        GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
        GL11.glVertex3d(0.375D, 0.0D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

}