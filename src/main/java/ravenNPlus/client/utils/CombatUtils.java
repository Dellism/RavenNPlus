package ravenNPlus.client.utils;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class CombatUtils {

    public static boolean canTarget(Entity entity, boolean idk) {
        if(entity != null && entity != Minecraft.getMinecraft().thePlayer) {
            EntityLivingBase entityLivingBase = null;

            if(entity instanceof EntityLivingBase) {
                entityLivingBase = (EntityLivingBase)entity;
            }

            boolean isTeam = isTeam(Minecraft.getMinecraft().thePlayer, entity);
            boolean isVisible = (!entity.isInvisible());

            return !(entity instanceof EntityArmorStand) && isVisible && (entity instanceof EntityPlayer && !isTeam && !idk || entity instanceof EntityAnimal || entity instanceof EntityMob || entity instanceof EntityLivingBase && entityLivingBase.isEntityAlive());
        } else {
            return false;
        }
    }

    public static boolean isTeam(EntityPlayer player, Entity entity) {
        if(entity instanceof EntityPlayer && ((EntityPlayer)entity).getTeam() != null && player.getTeam() != null) {
            Character entity_3 = entity.getDisplayName().getFormattedText().charAt(3);
            Character player_3 = player.getDisplayName().getFormattedText().charAt(3);
            Character entity_2 = entity.getDisplayName().getFormattedText().charAt(2);
            Character player_2 = player.getDisplayName().getFormattedText().charAt(2);
            boolean isTeam = false;
            if (entity_3.equals(player_3) && entity_2.equals(player_2)) {
                isTeam = true;
            } else {
                Character entity_1 = entity.getDisplayName().getFormattedText().charAt(1);
                Character player_1 = player.getDisplayName().getFormattedText().charAt(1);
                Character entity_0 = entity.getDisplayName().getFormattedText().charAt(0);
                Character player_0 = player.getDisplayName().getFormattedText().charAt(0);
                if(entity_1.equals(player_1) && Character.isDigit(0) && entity_0.equals(player_0)) {
                    isTeam = true;
                }
            }

            return isTeam;
        } else {
            return true;
        }
    }

    public static boolean canPlayerAttack(boolean allowBlock) {
        if(allowBlock)
            if(Minecraft.getMinecraft().thePlayer.isBlocking()) return true;

        return (!Utils.Player.isPlayerInContainer() || !Utils.Player.isPlayerInChat() || !Utils.Player.isPlayerInInv()) && !Minecraft.getMinecraft().thePlayer.isDead &&
                !Minecraft.getMinecraft().thePlayer.isPlayerSleeping() && !Minecraft.getMinecraft().thePlayer.isSpectator();
    }

    public static Vec3d getEyesPos() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        return new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
    }

    public static float[][] getBipedRotations(ModelBiped biped) {
        float[][] rotations = new float[5][];
        float[] headRotation = { biped.bipedHead.rotateAngleX, biped.bipedHead.rotateAngleY, biped.bipedHead.rotateAngleZ };
        rotations[0] = headRotation;
        float[] rightArmRotation = { biped.bipedRightArm.rotateAngleX, biped.bipedRightArm.rotateAngleY, biped.bipedRightArm.rotateAngleZ };
        rotations[1] = rightArmRotation;
        float[] leftArmRotation = { biped.bipedLeftArm.rotateAngleX, biped.bipedLeftArm.rotateAngleY, biped.bipedLeftArm.rotateAngleZ };
        rotations[2] = leftArmRotation;
        float[] rightLegRotation = { biped.bipedRightLeg.rotateAngleX, biped.bipedRightLeg.rotateAngleY, biped.bipedRightLeg.rotateAngleZ };
        rotations[3] = rightLegRotation;
        float[] leftLegRotation = { biped.bipedLeftLeg.rotateAngleX, biped.bipedLeftLeg.rotateAngleY, biped.bipedLeftLeg.rotateAngleZ };
        rotations[4] = leftLegRotation;
        return rotations;
    }

}