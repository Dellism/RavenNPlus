package ravenNPlus.client.module.modules.player;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
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
            mc.thePlayer.rotationYaw = (float) dir.getValue();
        } else {
            mc.playerController.flipPlayer(mc.thePlayer);
        }
    }

}