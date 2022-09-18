package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Step extends Module {

    public static SliderSetting hGt;

    public Step() {
        super("Step", ModuleCategory.movement, "");
        this.addSetting(hGt = new SliderSetting("Height", 3, 1, 50, 1));
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent e) {
        if(!GameSettings.isKeyDown(mc.gameSettings.keyBindJump) && mc.thePlayer.fallDistance < 0.1f) {
            mc.thePlayer.stepHeight = (float) hGt.getInput();
        } else {
            mc.thePlayer.stepHeight = 0.5F;
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.5F;
    }

}