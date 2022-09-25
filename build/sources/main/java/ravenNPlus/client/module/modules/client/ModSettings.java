package ravenNPlus.client.module.modules.client;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;

public class ModSettings extends Module {

    public static java.awt.Color slider_finalColor, slider_finalColor2;
    public static SliderSetting slider_color_green, slider_color_green_i, slider_color_blue_i, slider_color_blue, slider_color_red, text_xOff, text_yOff;
    public static SliderSetting slider_color_red_i, slider_mode, slider_name_Xoff, slider_name_Yoff, slider_widthRad, text_underline_off;
    public static TickSetting slider_color_c, text_color_c, text_underline;
    public static DescriptionSetting slider_mode_desc, desc;

    public ModSettings() {
        super("Module Settings", ModuleCategory.client, "Edit the UI of Settings in Modules");
        this.addSetting(desc = new DescriptionSetting("Module Settings"));
        this.addSetting(text_color_c = new TickSetting("Custom Text Color", false));
        this.addSetting(text_underline = new TickSetting("Text Underline", true));
        this.addSetting(text_underline_off = new SliderSetting("Underline expand", 1.4, -10, 10, 0.1));
        this.addSetting(text_xOff = new SliderSetting("Text x Offset", 0, -50, 50, 1));
        this.addSetting(text_yOff = new SliderSetting("Text y Offset", 0, -50, 50, 1));
        this.addSetting(desc = new DescriptionSetting("Module Setting Settings"));
        this.addSetting(slider_mode = new SliderSetting("Mode", 1, 2, 3, 1));
        this.addSetting(slider_mode_desc = new DescriptionSetting(Utils.md+""));
        this.addSetting(slider_color_c = new TickSetting("Custom Slider Color", false));
        this.addSetting(slider_color_red = new SliderSetting("Out Slider Red", 1, 1, 255, 1));
        this.addSetting(slider_color_green = new SliderSetting("Out Slider Green", 1, 1, 255, 1));
        this.addSetting(slider_color_blue = new SliderSetting("Out Slider Blue", 1, 1, 255, 1));
        this.addSetting(slider_color_red_i = new SliderSetting("In Slider Red", 1, 1, 255, 1));
        this.addSetting(slider_color_green_i = new SliderSetting("In Slider Green", 1, 1, 255, 1));
        this.addSetting(slider_color_blue_i = new SliderSetting("In Slider Blue", 1, 1, 255, 1));
        this.addSetting(slider_widthRad = new SliderSetting("Slider Round Range", 2, -10, 10, 1));
        this.addSetting(slider_name_Xoff = new SliderSetting("Slider Name x Offset", 0, -50, 50, 1));
        this.addSetting(slider_name_Yoff = new SliderSetting("Slider Name y Offset", 0, -50, 50, 1));
    }

    @Override
    public void onEnable() {
        this.disable();
    }

    public void guiUpdate() {
        if(Utils.Player.isPlayerInGame() && (slider_color_c.isToggled() || text_color_c.isToggled())) {
            slider_finalColor = new java.awt.Color((int) slider_color_red.getValue(), (int) slider_color_green.getValue(), (int) slider_color_blue.getValue());
            slider_finalColor2 = new java.awt.Color((int) slider_color_red_i.getValue(), (int) slider_color_green_i.getValue(), (int) slider_color_blue_i.getValue());
        }

        switch((int) slider_mode.getValue()) {
            case 1:
                slider_mode_desc.setDesc(Utils.md+"Normal");
                break;
            case 2:
                slider_mode_desc.setDesc(Utils.md+"Round");
                break;
            case 3:
                slider_mode_desc.setDesc(Utils.md+"Beautiful");
                break;
        }
    }

}