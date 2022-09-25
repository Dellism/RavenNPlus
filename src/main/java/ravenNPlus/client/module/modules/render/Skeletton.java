package ravenNPlus.client.module.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ravenNPlus.client.module.Module;

public class Skeletton extends Module {

    public Skeletton() {
        super("Skelettons", ModuleCategory.render, "aa");
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {



    }

}