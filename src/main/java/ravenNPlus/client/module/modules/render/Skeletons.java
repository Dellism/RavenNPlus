package ravenNPlus.client.module.modules.render;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ravenNPlus.client.event.imp.RenderEntityModelEvent;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.utils.CombatUtils;
import ravenNPlus.client.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

public class Skeletons extends Module {

    private final Map<EntityPlayer, float[][]> rotationList = (Map)new HashMap<>();

    public Skeletons() {
        super("Skeletons", ModuleCategory.render, "Draw Skeletons on Players");
    }

    @SubscribeEvent
    public void r(RenderEntityModelEvent ev) {
        if (!Utils.Player.isPlayerInGame()) return;

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == null || player == mc.getRenderViewEntity() || !player.isEntityAlive() || player.isPlayerSleeping() || this.rotationList.get(player) == null || mc.thePlayer.getDistanceToEntity((Entity)player) >= 2500.0D)
                continue;
            this.renderSkeletonTest(player, this.rotationList.get(player));
        }
    }

    @SubscribeEvent
    public void onRenderModel(RenderEntityModelEvent event) {
        if (event.getStage() == 0 && event.entity instanceof EntityPlayer && event.modelBase instanceof ModelBiped) {
            ModelBiped biped = (ModelBiped)event.modelBase;
            float[][] rotations = CombatUtils.getBipedRotations(biped);
            EntityPlayer player = (EntityPlayer)event.entity;
            this.rotationList.put(player, rotations);
        }
    }

    private void renderSkeletonTest(EntityPlayer player, float[][] rotations) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        Vec3d vec = Utils.Player.getInterpolatedRenderPos((Entity)player, Utils.Client.getTimer().renderPartialTicks);
        double pX = vec.x;
        double pY = vec.y;
        double pZ = vec.z;
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
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, -sneak, 0.0D);
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
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
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
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
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
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
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, -0.5D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak + 0.55D, 0.0D);
        if (rotations[0][0] != 0.0F)
            GlStateManager.rotate(rotations[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
        GL11.glBegin(3);
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
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
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
        GL11.glVertex3d(0.125D, 0.0D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak, 0.0D);
        GL11.glBegin(3);
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
        GL11.glVertex3d(0.0D, 0.55D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, sneak + 0.55D, 0.0D);
        GL11.glBegin(3);
        GlStateManager.color(Color.green.getRed() / 255.0F, Color.green.getGreen() / 255.0F, Color.green.getBlue() / 255.0F, Color.green.getAlpha() / 255.0F);
        GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
        GlStateManager.color(Color.red.getRed() / 255.0F, Color.red.getGreen() / 255.0F, Color.red.getBlue() / 255.0F, Color.red.getAlpha() / 255.0F);
        GL11.glVertex3d(0.375D, 0.0D, 0.0D);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

}