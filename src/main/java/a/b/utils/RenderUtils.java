package a.b.utils;

import a.b.module.modules.player.SafeWalk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.lang.reflect.Method;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);
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
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(d0, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d1, 0.0D).endVertex();
        worldrenderer.pos(d0, d1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect(float f, float f1, float f2, float f3, float f4, int i, int j) {
        drawRect(f, f1, f2, f3, j);
        float f5 = (float) (i >> 24 & 255) / 255.0F;
        float f6 = (float) (i >> 16 & 255) / 255.0F;
        float f7 = (float) (i >> 8 & 255) / 255.0F;
        float f8 = (float) (i & 255) / 255.0F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
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
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawEntityHUD(Entity en, int entityX, int entityY, int backgroundX, int backgroundY, int enSize, int range, boolean cl, boolean bg, boolean mouse) {
        if(!Utils.Player.isPlayerInGame()) return;

        java.util.List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
        if(targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);

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

        //entity looking
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

    public static void drawStringHUD(int x, int y, int range, boolean background) {
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;

        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        String targetDotHealth = "" + (int) target.getHealth();

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

        if(background)
            RoundedUtils.drawSmoothRoundedRect(x - 3, y - 3, x + 60 + fr.getStringWidth(target.getName())-16, y + 20, 3, Integer.MIN_VALUE);

        fr.drawString("Target: ", x, y, c);
        fr.drawString(target.getName(), x + fr.getStringWidth("Target: ") + 2, y, Color.red.getRGB());

        fr.drawString("Health: ", x, y + 10, c);
        fr.drawString(targetDotHealth,    x + fr.getStringWidth("Health: ") + 2, y + 10, heartColor);

    }


}