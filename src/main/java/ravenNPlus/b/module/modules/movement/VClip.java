package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;

public class VClip extends Module {
   
   public static SliderSetting distance;

   public VClip() {
      super("VClip", ModuleCategory.movement, "TP Up blocks");
      this.addSetting(distance = new SliderSetting("Distance", 2.0D, -10.0D, 10.0D, 0.5D));
   }

   public void onEnable() {
      if (distance.getInput() != 0.0D) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + distance.getInput(), mc.thePlayer.posZ);
      }

      this.disable();
   }
   
}
