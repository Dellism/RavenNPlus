package ravenNPlus.client.module.modules.render;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ravenNPlus.client.event.imp.EventUpdateModel;
import ravenNPlus.client.event.imp.Render3DEvent;
import ravenNPlus.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.lwjgl.opengl.GL11;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Utils;
import static org.lwjgl.opengl.GL11.*;

public class Skeletton extends Module {

    private final Map<EntityPlayer, float[][]> playerRotationMap = new WeakHashMap<>();
    private static TickSetting smooth;

    public Skeletton() {
        super("Skelettons", ModuleCategory.render, "aa");
        this.addSetting(smooth = new TickSetting("Smooth", true));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent event) {
        setupRender(true);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glDisable(2848);
        Map<EntityPlayer, float[][]> playerRotationMap = null;
        playerRotationMap = playerRotationMap;
        final List<EntityPlayer> worldPlayers = mc.theWorld.playerEntities;
        assert false;
        final Object[] players = playerRotationMap.keySet().toArray();
        final Minecraft mc = Minecraft.getMinecraft();
        if(players.length == 0) return;

        for (int i = 0, playersLength = players.length; i < playersLength; i++) {
            final EntityPlayer player = (EntityPlayer) players[i];
            float[][] entPos = playerRotationMap.get(player);
            if (entPos != null && player.getEntityId() != -1488 && player.isEntityAlive() && isInViewFrustrum(player) && !player.isDead && player != mc.thePlayer && !player.isPlayerSleeping() && !player.isInvisible()) {

                glPushMatrix();
                final float[][] modelRotations = playerRotationMap.get(player);
                glLineWidth(1.0f);
                glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final double x = RenderUtils.interpolate(player.posX, player.lastTickPosX, Utils.Client.getTimer().elapsedPartialTicks) - mc.getRenderManager().viewerPosX;
                final double y = RenderUtils.interpolate(player.posY, player.lastTickPosY, Utils.Client.getTimer().elapsedPartialTicks) - mc.getRenderManager().viewerPosY;
                final double z = RenderUtils.interpolate(player.posZ, player.lastTickPosZ, Utils.Client.getTimer().elapsedPartialTicks) - mc.getRenderManager().viewerPosZ;
                glTranslated(x, y, z);
                final float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * Utils.Client.getTimer().renderPartialTicks;
                glRotatef(-bodyYawOffset, 0.0f, 1.0f, 0.0f);
                glTranslated(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
                final float legHeight = player.isSneaking() ? 0.6f : 0.75f;
                final float rad = (float) (180.0 / Math.PI);
                glPushMatrix();
                glTranslated(-0.125, legHeight, 0.0);
                if (modelRotations[3][0] != 0.0f) {
                    glRotatef(modelRotations[3][0] * rad, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[3][1] != 0.0f) {
                    glRotatef(modelRotations[3][1] * rad, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[3][2] != 0.0f) {
                    glRotatef(modelRotations[3][2] * rad, 0.0f, 0.0f, 1.0f);
                }
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, -legHeight, 0.0);
                glEnd();
                glPopMatrix();
                glPushMatrix();
                glTranslated(0.125, legHeight, 0.0);
                if (modelRotations[4][0] != 0.0f) {
                    glRotatef(modelRotations[4][0] * rad, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[4][1] != 0.0f) {
                    glRotatef(modelRotations[4][1] * rad, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[4][2] != 0.0f) {
                    glRotatef(modelRotations[4][2] * rad, 0.0f, 0.0f, 1.0f);
                }
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, -legHeight, 0.0);
                glEnd();
                glPopMatrix();
                glTranslated(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
                glPushMatrix();
                glTranslated(0.0, player.isSneaking() ? -0.05 : 0.0, player.isSneaking() ? -0.01725 : 0.0);

                // Left arm
                glPushMatrix();
                glTranslated(-0.375, legHeight + 0.55D, 0.0);
                if (modelRotations[1][0] != 0.0f) {
                    glRotatef(modelRotations[1][0] * rad, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[1][1] != 0.0f) {
                    glRotatef(modelRotations[1][1] * rad, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[1][2] != 0.0f) {
                    glRotatef(-modelRotations[1][2] * rad, 0.0f, 0.0f, 1.0f);
                }
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, -0.5, 0.0);
                glEnd();
                glPopMatrix();

                // Right arm
                glPushMatrix();
                glTranslated(0.375, legHeight + 0.55D, 0.0);
                if (modelRotations[2][0] != 0.0f) {
                    glRotatef(modelRotations[2][0] * rad, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[2][1] != 0.0f) {
                    glRotatef(modelRotations[2][1] * rad, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[2][2] != 0.0f) {
                    glRotatef(-modelRotations[2][2] * rad, 0.0f, 0.0f, 1.0f);
                }
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, -0.5, 0.0);
                glEnd();
                glPopMatrix();
                glRotatef(bodyYawOffset - player.rotationYawHead, 0.0f, 1.0f, 0.0f);

                // Head
                glPushMatrix();
                glTranslated(0.0, legHeight + 0.55D, 0.0);
                if (modelRotations[0][0] != 0.0f) {
                    glRotatef(modelRotations[0][0] * rad, 1.0f, 0.0f, 0.0f);
                }
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, 0.3, 0.0);
                glEnd();
                glPopMatrix();

                glPopMatrix();
                glRotatef(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
                glTranslated(0.0, player.isSneaking() ? -0.16175 : 0.0, player.isSneaking() ? -0.48025 : 0.0);

                // Pelvis
                glPushMatrix();
                glTranslated(0.0, legHeight, 0.0);
                glBegin(3);
                glVertex3d(-0.125, 0.0, 0.0);
                glVertex3d(0.125, 0.0, 0.0);
                glEnd();
                glPopMatrix();

                // Body
                glPushMatrix();
                glTranslated(0.0, legHeight, 0.0);
                glBegin(3);
                glVertex3d(0.0, 0.0, 0.0);
                glVertex3d(0.0, 0.55, 0.0);
                glEnd();
                glPopMatrix();

                // Shoulder
                glPushMatrix();
                glTranslated(0.0, legHeight + 0.55D, 0.0);
                glBegin(3);
                glVertex3d(-0.375, 0.0, 0.0);
                glVertex3d(0.375, 0.0, 0.0);
                glEnd();
                glPopMatrix();

                glPopMatrix();
            }
        }
        setupRender(false);
    }


    public static boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    @SubscribeEvent
    public void s(EventUpdateModel event) {
        final ModelPlayer model = event.modelPlayer;
        playerRotationMap.put((EntityPlayer) event.entity, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ},
                {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ},
                {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ},
                {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ},
                {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    private final static Frustum frustrum = new Frustum();

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    private boolean contain(EntityPlayer var0) {
        return !mc.theWorld.playerEntities.contains(var0);
    }

    private void setupRender(boolean start) {
        final boolean ss = smooth.isToggled();

        if (start) {
            if (ss) {
                RenderUtils.startSmooth();
            } else {
                glDisable(GL_LINE_SMOOTH);
            }
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_TEXTURE_2D);
        } else {
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            if (ss) {
                RenderUtils.endSmooth();
            }
        }

        GL11.glDepthMask(!start);
    }

}