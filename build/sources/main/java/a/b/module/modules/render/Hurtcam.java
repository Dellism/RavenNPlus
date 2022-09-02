package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Hurtcam extends Module {

    public static SliderSetting mode, hurtTime, yaw;

    public Hurtcam() {
        super("HurtCam", ModuleCategory.render);
        this.registerSetting(mode = new SliderSetting("Mode", 1D, 1D, 6D, 1D));
        this.registerSetting(hurtTime = new SliderSetting("HurtTime", 1D, -10D, 10D, 1D));
        this.registerSetting(yaw = new SliderSetting("HurtYaw", 1D, -180D, 180D, 1D));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(Utils.Player.isPlayerInGame()) {
            if(mc.thePlayer.hurtTime > hurtTime.getInput()) {

                if(mode.getInput() == 1D) {
                    mc.thePlayer.performHurtAnimation();
                }

                if(mode.getInput() == 2D) {
                    mc.thePlayer.hurtTime = mc.thePlayer.maxHurtTime = 0;
                    mc.thePlayer.attackedAtYaw = 0.0F;
                }

                if(mode.getInput() == 3D) {
                    mc.thePlayer.hurtTime = mc.thePlayer.maxHurtTime = (int) hurtTime.getInput();
                    mc.thePlayer.attackedAtYaw = (float) yaw.getInput();
                }

                if(mode.getInput() == 4D) {
                    mc.thePlayer.hurtTime = (int) hurtTime.getInput();
                    mc.thePlayer.attackedAtYaw = (float) yaw.getInput();
                }

                if(mode.getInput() == 5D) {
                    mc.thePlayer.hurtTime = (int) hurtTime.getInput();
                }

                if(mode.getInput() == 6D) {
                    mc.thePlayer.attackedAtYaw = (float) yaw.getInput();
                }

                if(mode.getInput()  > 7D) {
                    mode.setValue(6);
                }

            }
        }

    }


}