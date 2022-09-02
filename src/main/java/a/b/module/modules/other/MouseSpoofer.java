package a.b.module.modules.other;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.MouseManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MouseSpoofer extends Module {

   public static SliderSetting m;

   public MouseSpoofer() {
      super("MouseSpoof", ModuleCategory.other);
      this.registerSetting(m = new SliderSetting("Mode", 1, 1, 3, 1));
   }

   @SubscribeEvent
   public void ef(TickEvent.RenderTickEvent e) {
      if(m.getInput() == 1) {
         MouseManager.addRightClick();
      }
      if(m.getInput() == 2) {
         MouseManager.addLeftClick();
      }
      if(m.getInput() == 3) {
         MouseManager.addRightClick();
         MouseManager.addLeftClick();
      }
   }
}