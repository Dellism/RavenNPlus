package ravenNPlus.client.module.modules.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Utils;

public class CrossCircle extends Module {

    public static SliderSetting radius, end;

    public CrossCircle() {
        super("CrossCircle", ModuleCategory.render, "A better Crossair");
        this.addSetting(radius = new SliderSetting("radius", 57, 1, 100, 0.1));
        this.addSetting(end = new SliderSetting("end", 871, 1, 380, 0.1));
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame()) return;

        ScaledResolution x = new ScaledResolution(mc);

        RenderUtils.drawCircle(x.getScaledWidth()/2, x.getScaledHeight()/2, (float) radius.getValue(), 10, (int) end.getValue());

        // mode 2
        //  RoundedUtils.drawCircle((double) (RenderUtils.scHight/2), RenderUtils.scWidth/2, radius.getValue(), -1);
    }

}