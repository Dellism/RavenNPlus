package a.b.module.modules.other;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.modules.minigames.DuelsStats;
import a.b.utils.Utils;

public class NameHider extends Module {
   public static DescriptionSetting a;
   public static String n = "ravenb+";

   public NameHider() {
      super("Name Hider", ModuleCategory.other);
      this.registerSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": cname [name]"));
   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.thePlayer != null) {
         s = DuelsStats.playerNick.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(DuelsStats.playerNick, n);
      }

      return s;
   }
}
