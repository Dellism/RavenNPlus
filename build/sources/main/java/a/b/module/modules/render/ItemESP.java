package a.b.module.modules.render;

import a.b.module.Module;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ItemESP extends Module {

    public ItemESP() {
        super("ItemESP", ModuleCategory.render);
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        //soon...

    }

}