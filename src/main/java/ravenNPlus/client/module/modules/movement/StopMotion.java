package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;

public class StopMotion extends Module {

   public static TickSetting xStop;
   public static TickSetting yStop;
   public static TickSetting zStop;

   public StopMotion() {
      super("Stop Motion", ModuleCategory.movement, "Stop your motion");
      this.addSetting(xStop = new TickSetting("Stop X", true));
      this.addSetting(yStop = new TickSetting("Stop Y", true));
      this.addSetting(zStop = new TickSetting("Stop Z", true));
   }

   public void onEnable() {
      if(!Utils.Player.isPlayerInGame()) {
         this.disable();
         return;
      }

      if(xStop.isToggled())
         mc.thePlayer.motionX = 0;

      if(yStop.isToggled())
         mc.thePlayer.motionY = 0;

      if(zStop.isToggled())
         mc.thePlayer.motionZ = 0;

      this.disable();
   }

}