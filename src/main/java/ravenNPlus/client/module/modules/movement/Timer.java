package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.clickgui.RavenNPlus.ClickGui;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;

public class Timer extends Module {
   public static SliderSetting speed;
   public static TickSetting strafe;

   public Timer() {
      super("Timer", ModuleCategory.movement, "Modifies your world timer");
      this.addSetting(speed = new SliderSetting("Speed", 1.0D, 0.5D, 2.5D, 0.01D));
      this.addSetting(strafe = new TickSetting("Strafe only", false));
   }

   public void update() {
      if (!(mc.currentScreen instanceof ClickGui)) {
         if (strafe.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
            Utils.Client.resetTimer();
            return;
         }

         Utils.Client.getTimer().timerSpeed = (float) speed.getValue();
      } else {
         Utils.Client.resetTimer();
      }
   }

   public void onDisable() {
      Utils.Client.resetTimer();
   }

}