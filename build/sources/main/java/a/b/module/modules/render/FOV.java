package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;

public class FOV extends Module {

    public static SliderSetting fov;
    public static DescriptionSetting desc;

    public FOV() {
        super("Fov", ModuleCategory.render);
        this.registerSetting(desc = new DescriptionSetting("Modifies your Fov"));
        this.registerSetting(fov = new SliderSetting("FOV", 140D, 0D, 500D, 10D));
    }

    public void update() {
        mc.gameSettings.fovSetting = (float)fov.getInput();
    }

}