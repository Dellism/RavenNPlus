package a.b.module.modules.other;

import a.b.module.Module;
import a.b.utils.RoundedUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.awt.*;

public class LegoGraphics extends Module {

    public LegoGraphics() {
        super("LegoGraphics", ModuleCategory.other);
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        RoundedUtils.drawCircle2(1, 1, 5, Color.white.getRGB());
    }

}