package a.b.utils;

import a.b.utils.notifications.Notification;
import a.b.utils.notifications.Manager;
import a.b.utils.notifications.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.lang.reflect.Method;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {

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
        if(!Utils.Player.isPlayerInGame()) return;

        java.util.List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
        if(targets.isEmpty()) return;

        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(mc.getSession().getUsername() == target.getName()) return;
        if(target.getName().contains("Empty")) return;
        if(target.getName().contains(" ")) return;
        if(target.getName().contains(":")) return;
        if(target.getName().startsWith("CIT-")) return;
        float targetLookX;
        float targetLookY;

        int I = (int) en.height *12+35;
        int l = (int)  en.width *12;

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
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if(targets.isEmpty()) return;

        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(mc.getSession().getUsername() == target.getName()) return;
        if(target.getName().contains("Empty")) return;
        if(target.getName().contains(" ")) return;
        if(target.getName().contains(":")) return;
        String targetHealth = "" + (int) target.getHealth();
        if(target.getName().startsWith("CIT-")) return;

        int c = 0xFFFFFFFF;
        int heartColor;
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

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        Utils.HUD.fontRender.drawString("", 0, 0, c);

        if(background && !head)
            RoundedUtils.drawSmoothRoundedRect(x - 3, y - 3, x + 60 + fr.getStringWidth(target.getName())-12,y + 20, 3, Integer.MIN_VALUE);

        if(background && head)
            RoundedUtils.drawSmoothRoundedRect(x - 22, y - 3, x + 60 + fr.getStringWidth(target.getName())-12, y + 20, 3, Integer.MIN_VALUE);

        if(head)
            RenderUtils.drawHead(mc.thePlayer.getLocationSkin(), x-20, y-1, 18, 19);

        if(cl)
            Utils.HUD.fontRender.drawString("", 0, 0, 0xFFFFFFFF);

        fr.drawString("Target : ", x, y, c);
        fr.drawString(target.getName(), x + fr.getStringWidth("Target : ") + 2, y, Color.red.getRGB());

        fr.drawString("Health : ", x+1, y + 10, c);
        fr.drawString(" "+targetHealth,    x + fr.getStringWidth("Health : ") - 1, y + 10, heartColor);
    }

    public static void quickDrawHead(ResourceLocation skin, int x, int y, int width, int height) {
        mc.getTextureManager().bindTexture(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8F, 8F, 8, 8, width, height,
                64F, 64F);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40F, 8F, 8, 8, width, height,
                64F, 64F);
    }

    public static void drawHead(ResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8F, 8F, 8, 8, width, height,
                64F, 64F);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40F, 8F, 8, 8, width, height,
                64F, 64F);
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
        glColor4f(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f, c.getAlpha());
        mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        mc.getTextureManager().bindTexture(null);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    protected static boolean isHovered(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < width && mouseY < height;
    }

}

