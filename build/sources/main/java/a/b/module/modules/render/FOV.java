package a.b.module.modules.render;

public class FOV extends a.b.module.Module {

    public static a.b.module.setting.impl.SliderSetting fov;
    public static a.b.module.setting.impl.DescriptionSetting desc;

    public FOV() {
        super("Fov", ModuleCategory.render);
        this.registerSetting(desc = new a.b.module.setting.impl.DescriptionSetting("Modifies your Fov"));
        this.registerSetting(fov = new a.b.module.setting.impl.SliderSetting("FOV", 140D, 0D, 500D, 10D));
    }

    public void update() {
        mc.gameSettings.fovSetting = (float)fov.getInput();
    }

}