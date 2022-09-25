package ravenNPlus.client.module.modules.other;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.MouseManager;
import ravenNPlus.client.utils.Utils;
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

      if (mode.getValue() == 1) {
         MouseManager.addRightClick();
      }

      if(mode.getValue() == 2) {
         MouseManager.addLeftClick();
      }

      if(mode.getValue() == 3) {
         MouseManager.addRightClick();
         MouseManager.addLeftClick();
      }

   }

   public void guiUpdate() {
      switch ((int) mode.getValue()) {
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