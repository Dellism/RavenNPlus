package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.RenderUtils;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TargetHUD extends Module {

    public static SliderSetting range, x, y;
    public static TickSetting background, head;

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.render);
        this.registerSetting(range = new SliderSetting("Distance", 5, 1 ,50, 1));
        this.registerSetting(head = new TickSetting("Head", false));
        this.registerSetting(background = new TickSetting("Background", true));
        this.registerSetting(x = new SliderSetting("X", 5, 0, mc.displayWidth+150, 1));
        this.registerSetting(y = new SliderSetting("Y", 50, 0 , mc.displayHeight+150, 1));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        RenderUtils.drawStringHUD((int) x.getInput(), (int) y.getInput(), (int) range.getInput(), background.isToggled(), true, head.isToggled());
    }

}