package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.utils.Utils;

public class AntiShuffle extends Module {
   public static DescriptionSetting a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", ModuleCategory.render);
      this.registerSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
