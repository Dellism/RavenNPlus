package ravenNPlus.b.module.modules.player;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flip extends Module {

    public static TickSetting direction;
    public static SliderSetting dir;

    public Flip() {
        super("Flip", ModuleCategory.player, "Flips your camera");
        this.addSetting(direction = new TickSetting("Direction", false));
        this.addSetting(dir = new SliderSetting("Yaw", 1D, -180D, 180D, 1D));
    }

    @SubscribeEvent
    public void r(PlayerEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        if(direction.isToggled()) {
            mc.thePlayer.rotationYaw = (float) dir.getInput();
        } else {
            mc.playerController.flipPlayer(mc.thePlayer);
        }
    }

}