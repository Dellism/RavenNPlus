package ravenNPlus.b.module.modules.render;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ItemESP extends Module {

    public static SliderSetting xStart, yStart, xEnd, yEnd;

    public ItemESP() {
        super("ItemESP", ModuleCategory.render, "Draw a outline around Items");
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        //soon...

    }

}