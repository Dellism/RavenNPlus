package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.utils.Utils;

public class AntiShuffle extends Module {
   public static DescriptionSetting a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", ModuleCategory.render, "");
      this.addSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
