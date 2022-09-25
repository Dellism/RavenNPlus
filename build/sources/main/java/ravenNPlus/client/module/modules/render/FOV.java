package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.utils.Utils;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import net.minecraft.client.settings.GameSettings;

public class FOV extends Module {

    public static SliderSetting fov;
    private float oldFov = 0F;

    public FOV() {
        super("Fov", ModuleCategory.render, "Modifies your Fov");
        this.addSetting(fov = new SliderSetting("FOV", 140D, 0D, 500D, 10D));
    }

    @Override
    public void onEnable() {
        if(!Utils.Player.isPlayerInGame()) return;

        oldFov = mc.gameSettings.getOptionFloatValue(GameSettings.Options.FOV);
        mc.gameSettings.fovSetting = (float)fov.getValue();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(!Utils.Player.isPlayerInGame()) return;

        mc.gameSettings.fovSetting = oldFov;
        super.onDisable();
    }

}