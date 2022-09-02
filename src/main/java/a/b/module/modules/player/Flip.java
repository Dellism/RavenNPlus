package a.b.module.modules.player;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flip extends Module {

    public static TickSetting direction;
    public static SliderSetting dir;

    public Flip() {
        super("Flip", ModuleCategory.player);
        this.registerSetting(direction = new TickSetting("Direction", false));
        this.registerSetting(dir = new SliderSetting("Yaw", 1D, -180D, 180D, 1D));
    }

    @SubscribeEvent
    public void r(PlayerEvent e) {
        if(direction.isToggled()) {
            mc.thePlayer.rotationYaw = (float) dir.getInput();
        } else if (!direction.isToggled()) {
            mc.playerController.flipPlayer(mc.thePlayer);
        }


    }

}