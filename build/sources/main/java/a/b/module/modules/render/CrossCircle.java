package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class CrossCircle extends Module {

    public static SliderSetting x1, y1;

    public CrossCircle() {
        super("CrossCircle", ModuleCategory.render);
        this.registerSetting(x1 = new SliderSetting("X", 1, 1, 999, 1));
        this.registerSetting(y1 = new SliderSetting("Y", 1, 1, 999, 1));
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        int x = (int) (mc.displayHeight + x1.getInput());
        int y = (int) (mc.displayWidth  + y1.getInput());

        Utils.HUD.drawBoxAroundEntity(mc.thePlayer, 6, 5, 1, Color.white.getRGB(), true);

    }

}