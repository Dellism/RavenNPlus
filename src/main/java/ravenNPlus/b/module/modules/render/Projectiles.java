package ravenNPlus.b.module.modules.render;

import ravenNPlus.b.utils.Utils;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import net.minecraft.util.Vec3;
import net.minecraft.util.Timer;
import net.minecraft.item.Item;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Projectiles extends Module {

    public static SliderSetting thiccness;

    public Projectiles() {
        super("Projectiles", ModuleCategory.render, "Not working (Fix soon)");
        this.addSetting(thiccness = new SliderSetting("Line Thickness", 2.0, 1, 10.0, 1));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame())
            return;

        EntityPlayerSP player = mc.thePlayer;
        if (mc.thePlayer.getCurrentEquippedItem() == null)
            return;

        Item stack = mc.thePlayer.getCurrentEquippedItem().getItem();
        if (stack == null)
            return;

            /*
            if (!(stack instanceof ItemBow)) {
                return;
            }
             */

        Timer timer = new Timer(3.0F);
        double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) timer.renderPartialTicks - Math.cos((float) Math.toRadians(player.rotationYaw)) * 0.1599999964237213;
        double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) timer.renderPartialTicks + (double) player.getEyeHeight() - 0.1;
        double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) timer.renderPartialTicks - Math.sin((float) Math.toRadians(player.rotationYaw)) * 0.1599999964237213;
        float arrowMotionFactor = 1.0F;
        float yaw = (float) Math.toRadians(player.rotationYaw);
        float pitch = (float) Math.toRadians(player.rotationPitch);
        float arrowMotionX = (float) (-Math.sin(yaw) * Math.cos((double) pitch) * (double) arrowMotionFactor);
        float arrowMotionY = (float) (-Math.sin(pitch) * (double) arrowMotionFactor);
        float arrowMotionZ = (float) (Math.cos(yaw) * Math.cos(pitch) * (double) arrowMotionFactor);
        double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX = (float) ((double) arrowMotionX / arrowMotion);
        arrowMotionY = (float) ((double) arrowMotionY / arrowMotion);
        arrowMotionZ = (float) ((double) arrowMotionZ / arrowMotion);
        float bowPower = (float) (72000 - player.getItemInUseCount()) / 20.0F;
        bowPower = (bowPower * bowPower + bowPower * 2.0F) / 3.0F;
        if (bowPower > 1.0F || bowPower <= 0.1F) {
            bowPower = 1.0F;
        }

        bowPower *= 3.0F;
        arrowMotionX *= bowPower;
        arrowMotionY *= bowPower;
        arrowMotionZ *= bowPower;
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        GL11.glLineWidth((float) ((int) thiccness.getInput()));
        RenderManager renderManager = mc.getRenderManager();
        double gravity = 0.05;
        Vec3 playerVector = new Vec3(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
        GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.75F);
        GL11.glBegin(3);

        for (int i = 0; i < 1000; ++i) {
            GL11.glVertex3d(arrowPosX - renderManager.viewerPosX, arrowPosY - renderManager.viewerPosY, arrowPosZ - renderManager.viewerPosZ);
            arrowPosX += (double) arrowMotionX * 0.1;
            arrowPosY += (double) arrowMotionY * 0.1;
            arrowPosZ += (double) arrowMotionZ * 0.1;
            arrowMotionX = (float) ((double) arrowMotionX * 0.999);
            arrowMotionY = (float) ((double) arrowMotionY * 0.999);
            arrowMotionZ = (float) ((double) arrowMotionZ * 0.999);
            arrowMotionY = (float) ((double) arrowMotionY - gravity * 0.1);
            if (mc.theWorld.rayTraceBlocks(playerVector, new Vec3(arrowPosX, arrowPosY, arrowPosZ)) != null) {
                break;
            }
        }

        GL11.glEnd();
        double renderX = arrowPosX - renderManager.viewerPosX;
        double renderY = arrowPosY - renderManager.viewerPosY;
        double renderZ = arrowPosZ - renderManager.viewerPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated(renderX - 0.5, renderY - 0.5, renderZ - 0.5);
        GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.25F);
        GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.75F);
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

}