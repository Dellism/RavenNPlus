package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;

public class VClip extends Module {
   
   public static SliderSetting distance;

   public VClip() {
      super("VClip", ModuleCategory.movement, "TP Up blocks");
      this.addSetting(distance = new SliderSetting("Distance", 2.0D, -10.0D, 10.0D, 0.5D));
   }

   public void onEnable() {
      if (distance.getValue() != 0.0D) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + distance.getValue(), mc.thePlayer.posZ);
      }

      this.disable();
   }
   
}
