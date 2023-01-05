package ravenNPlus.client.utils.font.fr;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class FontManager {
    private final HashMap<String, TTFFontRenderer> fonts = new HashMap();
    private final TTFFontRenderer defaultFont;

    public TTFFontRenderer getFont(String key) {
        return (TTFFontRenderer)this.fonts.getOrDefault(key, this.defaultFont);
    }

    public FontManager() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue();
        this.defaultFont = new TTFFontRenderer(executorService, textureQueue, new Font("Comfortaa", 0, 18));

        try {
            int[] var3 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 32, 128};
            int var4 = var3.length;

            int var5;
            int i;
            InputStream istream;
            Font myFont;
            for(var5 = 0; var5 < var4; ++var5) {
                i = var3[var5];
                istream = this.getClass().getResourceAsStream("/assets/minecraft/ravenNPlus/fonts/comfortaa.ttf");

                assert istream != null;

                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, (float)i);
                this.fonts.put("Comfortaa " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }

            var3 = new int[]{16, 18, 20, 24};
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                i = var3[var5];
                istream = this.getClass().getResourceAsStream("/assets/minecraft/ravenNPlus/fonts/comfortaa.ttf");

                assert istream != null;

                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, (float)i);
                this.fonts.put("Comfortaa " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
        } catch (Exception ignored) {
        }

        executorService.shutdown();

        while(!executorService.isTerminated()) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException var9) {
                var9.printStackTrace();
            }

            while(!textureQueue.isEmpty()) {
                TextureData textureData = (TextureData)textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());
                GL11.glTexParameteri(3553, 10241, 9728);
                GL11.glTexParameteri(3553, 10240, 9728);
                GL11.glTexImage2D(3553, 0, 6408, textureData.getWidth(), textureData.getHeight(), 0, 6408, 5121, textureData.getBuffer());
            }
        }

    }
}