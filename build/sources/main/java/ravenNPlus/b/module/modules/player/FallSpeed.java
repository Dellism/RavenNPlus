package ravenNPlus.b.module.modules.player;

import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.*;
import ravenNPlus.b.module.modules.movement.Fly;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;

public class FallSpeed extends Module {

   public static DescriptionSetting desc;
   public static SliderSetting motion;
   public static TickSetting disXZMotion;

   public FallSpeed() {
      super("FallSpeed", ModuleCategory.player, "Fall faster down");
      this.addSetting(desc = new DescriptionSetting("Vanilla max: 3.92"));
      this.addSetting(motion = new SliderSetting("Motion", 5.0D, 0.0D, 8.0D, 0.1D));
      this.addSetting(disXZMotion = new TickSetting("Disable XZ motion", true));
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance >= 2.5D) {
         Module fly = RavenNPlus.moduleManager.getModuleByClazz(Fly.class);
         Module noFall = RavenNPlus.moduleManager.getModuleByClazz(NoFall.class);

         if ((fly != null && fly.isEnabled()) || (noFall != null && noFall.isEnabled())) {
            return;
         }

         if (mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.capabilities.isFlying) {
            return;
         }

         if (mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
            return;
         }

         mc.thePlayer.motionY = -motion.getInput();
         if (disXZMotion.isToggled()) {
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
         }
      }
   }

}