package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fullbright extends Module {

    private float defaultGamma;
    private final float clientGamma;

    public Fullbright() {
        super("Fullbright", ModuleCategory.render, "No more darkness");
        this.clientGamma = 10000;
    }

    @Override
    public void onEnable() {
        this.defaultGamma = mc.gameSettings.gammaSetting;
        super.onEnable();
    }

    @Override
    public void onDisable(){
        super.onEnable();
        mc.gameSettings.gammaSetting = this.defaultGamma;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Utils.Player.isPlayerInGame()) {
            onDisable();
            return;
        }

        if (mc.gameSettings.gammaSetting != clientGamma)
            mc.gameSettings.gammaSetting = clientGamma;
    }

}