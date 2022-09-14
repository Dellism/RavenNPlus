package a.b.module.modules.movement;

import a.b.utils.Utils;
import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.DescriptionSetting;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Jesus extends Module {

    public static DescriptionSetting desc;
    public static SliderSetting mode;

    public Jesus() {
        super("Jesus", ModuleCategory.movement);
        this.registerSetting(mode = new SliderSetting("Mode: ", 1D, 1D, 8D, 1D));
        this.registerSetting(desc = new DescriptionSetting(Utils.md + ""));
    }

    @SubscribeEvent
    public void r(PlayerEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        if(mode.getInput() == 1D) {

            if(Utils.Player.isPlayerInWater() && mc.thePlayer.isInsideOfMaterial(Material.air)) {
                mc.thePlayer.motionY = 0.08;
            }

        } else if(mode.getInput() == 2D) {

            if(Utils.Player.isPlayerInWater() && mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 1;
            }

        } else if(mode.getInput() == 3D) {

            if(!mc.thePlayer.onGround && Utils.Player.isPlayerInWater() || mc.thePlayer.isInWater()) {

                if(!mc.thePlayer.isSprinting()) {

                    mc.thePlayer.motionX *= 0.99999;
                    mc.thePlayer.motionY *= 0.0;
                    mc.thePlayer.motionZ *= 0.99999;

                    if(mc.thePlayer.isCollidedHorizontally) {
                        mc.thePlayer.motionY = ((mc.thePlayer.posY - (mc.thePlayer.posY - 1) / 8f));
                    }

                } else {

                    mc.thePlayer.motionX *= 0.99999;
                    mc.thePlayer.motionY *= 0.0;
                    mc.thePlayer.motionZ *= 0.99999;

                    if(mc.thePlayer.isCollidedHorizontally) {
                        mc.thePlayer.motionY = ((mc.thePlayer.posY - (mc.thePlayer.posY - 1) / 8f));
                    }
                }

                if(mc.thePlayer.fallDistance >= 4) {
                    mc.thePlayer.motionY = -0.004;
                } else if(mc.thePlayer.isInWater()) mc.thePlayer.motionY = 0.09;
            }

            if(mc.thePlayer.hurtTime != 0) {
                mc.thePlayer.onGround = false;
            }

        } else if(mode.getInput() == 4D) {

            if(mc.thePlayer.isInWater()) {
                mc.gameSettings.keyBindJump.isPressed();

                if(mc.thePlayer.isCollidedHorizontally) {
                    mc.thePlayer.motionY = +0.09;
                    return;
                }

                mc.thePlayer.motionY = 0.0;
                mc.thePlayer.motionX *= 0.15;
                mc.thePlayer.motionZ *= 0.15;
            }

        } else if(mode.getInput() == 5D) {

            if(mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.motionY += 0.15;
                return;
            }

            mc.thePlayer.motionY = 0.0;
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionX *= 0.085;
            mc.thePlayer.motionZ *= 0.085;

        } else if(mode.getInput() == 6D) {

            if(mc.thePlayer.isInWater()) {
                mc.thePlayer.motionY += 0.03999999910593033;
            }

        } else if(mode.getInput() == 7D) {

            if(mc.thePlayer.isInWater()) {
                mc.thePlayer.motionX *= 0.04;
                mc.thePlayer.motionZ *= 0.04;
                Utils.Player.bop(2);
            }

        } else if(mode.getInput() == 8D) {

            if(mc.thePlayer.isInWater()) {
                mc.thePlayer.onGround = true;
            }

        }

    }

    public void guiUpdate() {
        switch ((int) mode.getInput()) {
            case 1:
                desc.setDesc(Utils.md + "NPC, Vulcan");
                break;
            case 2:
                desc.setDesc(Utils.md + "Jump");
                break;
            case 3:
                desc.setDesc(Utils.md + "AAC");
                break;
            case 4:
                desc.setDesc(Utils.md+ "Matrix");
                break;
            case 5:
                desc.setDesc(Utils.md+ "Spartan");
                break;
            case 6:
                desc.setDesc(Utils.md+ "Dolphin");
                break;
            case 7:
                desc.setDesc(Utils.md+ "Twilight");
                break;
            case 8:
                desc.setDesc(Utils.md+ "Frog");
                break;
        }
    }

}