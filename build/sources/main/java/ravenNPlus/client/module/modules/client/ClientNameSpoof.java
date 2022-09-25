package ravenNPlus.client.module.modules.client;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.utils.Utils;

public class ClientNameSpoof extends Module {
    public static DescriptionSetting desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", ModuleCategory.client, "Edit your F3 Client name");
        this.addSetting(desc = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": f3name [name]"));
    }
}
