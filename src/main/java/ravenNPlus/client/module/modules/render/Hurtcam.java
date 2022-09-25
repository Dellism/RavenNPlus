package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Hurtcam extends Module {

    public static SliderSetting mode, hurtTime, yaw;

    public Hurtcam() {
        super("HurtCam", ModuleCategory.render, "");
        this.addSetting(mode = new SliderSetting("Mode", 1D, 1D, 6D, 1D));
        this.addSetting(hurtTime = new SliderSetting("HurtTime", 1D, -10D, 10D, 1D));
        this.addSetting(yaw = new SliderSetting("HurtYaw", 1D, -180D, 180D, 1D));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(Utils.Player.isPlayerInGame()) {
            if(mc.thePlayer.hurtTime > hurtTime.getValue()) {

                if(mode.getValue() == 1D) {
                    mc.thePlayer.performHurtAnimation();
                }

                if(mode.getValue() == 2D) {
                    mc.thePlayer.hurtTime = mc.thePlayer.maxHurtTime = 0;
                    mc.thePlayer.attackedAtYaw = 0.0F;
                }

                if(mode.getValue() == 3D) {
                    mc.thePlayer.hurtTime = mc.thePlayer.maxHurtTime = (int) hurtTime.getValue();
                    mc.thePlayer.attackedAtYaw = (float) yaw.getValue();
                }

                if(mode.getValue() == 4D) {
                    mc.thePlayer.hurtTime = (int) hurtTime.getValue();
                    mc.thePlayer.attackedAtYaw = (float) yaw.getValue();
                }

                if(mode.getValue() == 5D) {
                    mc.thePlayer.hurtTime = (int) hurtTime.getValue();
                }

                if(mode.getValue() == 6D) {
                    mc.thePlayer.attackedAtYaw = (float) yaw.getValue();
                }

                if(mode.getValue()  > 7D) {
                    mode.setValue(6);
                }

            }
        }
    }

}