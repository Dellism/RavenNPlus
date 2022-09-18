package ravenNPlus.b.module.modules.other;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.utils.Utils;

public class NickName extends Module {

    public static DescriptionSetting a;
    public static String name = "Notch";
    public static String nick = "";

    public NickName() {
        super("NickName", ModuleCategory.other, "");

        this.addSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": nickname [name]"));
    }

    public static String format(String s) {
        if(mc.thePlayer == null) return null;

        s = nick.isEmpty() ? s.replace(mc.thePlayer.getName(), name) : s.replace(nick, name);

        return s;
    }

}