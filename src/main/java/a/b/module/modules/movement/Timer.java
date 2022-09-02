package a.b.module.modules.movement;

import a.b.clickgui.otaku.ClickGui;
import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;

public class Timer extends Module {
   public static SliderSetting speed;
   public static TickSetting strafe;

   public Timer() {
      super("Timer", ModuleCategory.movement);
      speed = new SliderSetting("Speed", 1.0D, 0.5D, 2.5D, 0.01D);
      strafe = new TickSetting("Strafe only", false);
      this.registerSetting(speed);
      this.registerSetting(strafe);
   }

   public void update() {
      if (!(mc.currentScreen instanceof ClickGui)) {
         if (strafe.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
            Utils.Client.resetTimer();
            return;
         }

         Utils.Client.getTimer().timerSpeed = (float) speed.getInput();
      } else {
         Utils.Client.resetTimer();
      }

   }

   public void onDisable() { Utils.Client.resetTimer(); }
}