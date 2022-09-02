package a.b.module.modules.movement;

import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Utils;

public class Boost extends Module {

   public static DescriptionSetting c;
   public static SliderSetting a;
   public static SliderSetting b;
   private int i = 0;
   private boolean t = false;

   public Boost() {
      super("Boost", ModuleCategory.movement);
      this.registerSetting(c = new DescriptionSetting("1 Sec = 20 Ticks"));
      this.registerSetting(a = new SliderSetting("Multiplier", 2.0D, 1.0D, 3.0D, 0.05D));
      this.registerSetting(b = new SliderSetting("Time (ticks)", 15.0D, 1.0D, 80.0D, 1.0D));
   }

   public void onEnable() {
      Module timer = Otaku.moduleManager.getModuleByClazz(Timer.class);
      if (timer != null && timer.isEnabled()) {
         this.t = true;
         timer.disable();
      }
   }

   public void onDisable() {
      this.i = 0;
      if (Utils.Client.getTimer().timerSpeed != 1.0F) {
         Utils.Client.resetTimer();
      }

      if (this.t) {
         Module timer = Otaku.moduleManager.getModuleByClazz(Timer.class);
         if (timer != null) timer.enable();
      }

      this.t = false;
   }

   public void update() {
      if (this.i == 0) {
         this.i = mc.thePlayer.ticksExisted;
      }

      Utils.Client.getTimer().timerSpeed = (float)a.getInput();
      if ((double)this.i == (double)mc.thePlayer.ticksExisted - b.getInput()) {
         Utils.Client.resetTimer();
         this.disable();
      }
   }
}