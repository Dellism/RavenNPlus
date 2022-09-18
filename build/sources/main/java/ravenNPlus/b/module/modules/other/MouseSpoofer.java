package ravenNPlus.b.module.modules.other;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.utils.MouseManager;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MouseSpoofer extends Module {

   public static SliderSetting mode;
   public static DescriptionSetting modeMode;

   public MouseSpoofer() {
      super("MouseSpoof", ModuleCategory.other, "Spoofs your CPS");
      this.addSetting(mode = new SliderSetting("Mode", 1, 1, 3, 1));
      this.addSetting(modeMode = new DescriptionSetting(Utils.md +""));
   }

   @SubscribeEvent
   public void ef(TickEvent.RenderTickEvent e) {

      if (mode.getInput() == 1) {
         MouseManager.addRightClick();
      }

      if(mode.getInput() == 2) {
         MouseManager.addLeftClick();
      }

      if(mode.getInput() == 3) {
         MouseManager.addRightClick();
         MouseManager.addLeftClick();
      }

   }

   public void guiUpdate() {
      switch ((int) mode.getInput()) {
         case 1:
            modeMode.setDesc(Utils.md + "Right");
            break;
         case 2:
            modeMode.setDesc(Utils.md + "Left");
            break;
         case 3:
            modeMode.setDesc(Utils.md + "Both");
            break;
      }
   }

}