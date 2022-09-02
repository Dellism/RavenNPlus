package a.b.utils.notifications;

import a.b.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import java.awt.Color;

public class Notification {
    private NotificationType type;
    private final String title, messsage;
    private long start;
    private final long fadedIn, fadeOut, end;
    
    public Notification(NotificationType type, String title, String message, int length) {
        this.type = type;
        this.title = title;
        this.messsage = message;
        fadedIn = 200L * length;
        fadeOut = fadedIn + 500L * length;
        end = fadeOut + fadedIn;
    }
    
    public void show() {
        start = System.currentTimeMillis();
    }
    public boolean isShown() { return getTime() <= end; }
    private long getTime() { return System.currentTimeMillis() - start; }

    public void render() {
        double offset = 0;
        int width = 120;
        int height = 30;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        Color color = new Color(0, 0, 0, 220);
        Color color1;

        if (type == NotificationType.INFO)
            color1 = new Color(0, 26, 169);
        if (type == NotificationType.OTHER)
            color1 = new Color(185, 0, 185);
        else if (type == NotificationType.WARNING)
            color1 = new Color(204, 193, 0);
        else {
            color1 = new Color(204, 0, 18);
            int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
            color = new Color(i, 0, 0, 220);
        }

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());

        drawRect(Utils.HUD.scWidth - offset, Utils.HUD.scHeigt - 5 - height, Utils.HUD.scWidth, Utils.HUD.scHeigt - 5, color.getRGB());
        drawRect(Utils.HUD.scWidth - offset, Utils.HUD.scHeigt - 5 - height, Utils.HUD.scWidth - offset + 4, Utils.HUD.scHeigt - 5, color1.getRGB());

        fontRenderer.drawString(title, (int) (Utils.HUD.scWidth - offset + 8), Utils.HUD.scHeigt - 2 - height, -1);
        fontRenderer.drawString(messsage, (int) (Utils.HUD.scWidth - offset + 8), Utils.HUD.scHeigt - 15, -1);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
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

    public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
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
        float f  = (float) (color >> 16 & 255) / 255.0F;
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
}