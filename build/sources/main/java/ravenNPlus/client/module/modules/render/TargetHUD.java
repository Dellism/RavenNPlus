package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TargetHUD extends Module {

    public static SliderSetting range, x, y;
    public static TickSetting background, head;

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.render, "A Hud to see your enemys health and name");
        this.addSetting(range = new SliderSetting("Distance", 5, 1 ,50, 1));
        this.addSetting(head = new TickSetting("Head", false));
        this.addSetting(background = new TickSetting("Background", true));
        this.addSetting(x = new SliderSetting("X", 5, 5, mc.displayWidth-5, 1));
        this.addSetting(y = new SliderSetting("Y", 50, 5 , mc.displayHeight+15, 1));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        RenderUtils.drawStringHUD((int) x.getValue(), (int) y.getValue(), (int) range.getValue(), background.isToggled(), true, head.isToggled());
    }

}