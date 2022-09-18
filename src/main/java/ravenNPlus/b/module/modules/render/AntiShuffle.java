package ravenNPlus.b.module.modules.render;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.utils.Utils;

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
