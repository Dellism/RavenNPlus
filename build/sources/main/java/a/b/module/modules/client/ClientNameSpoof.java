package a.b.module.modules.client;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.utils.Utils;

public class ClientNameSpoof extends Module {
    public static DescriptionSetting desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", ModuleCategory.client);
        this.registerSetting(desc = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": f3name [name]"));
    }
}
