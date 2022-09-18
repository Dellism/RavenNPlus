package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class BHop extends Module {

   public static SliderSetting speed, yOff, xOff;
   public static  TickSetting  jump, sneak;

   public BHop() {
      super("BHop", ModuleCategory.movement, "");
      this.addSetting(speed = new SliderSetting("Speed", 2.0D, 0.5D, 20.0D, 0.5D));
      this.addSetting(yOff  = new SliderSetting("y Offset", 0.0D, 0.0D, 1.0D, 0.2D));
      this.addSetting(xOff  = new SliderSetting("x Offset", 0.0D, 0.0D, 1.0D, 0.2D));
      this.addSetting(jump  = new TickSetting("Jump", true));
      this.addSetting(sneak = new TickSetting("Sneak", false));
   }

   public void update() {
      Module fly = RavenNPlus.moduleManager.getModuleByClazz(Fly.class);
      if (fly != null && !fly.isEnabled() && Utils.Player.isMoving() && !mc.thePlayer.isInWater()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
         mc.thePlayer.noClip = true;

         if (mc.thePlayer.onGround && jump.isToggled()) {
            mc.thePlayer.jump();
         }

         if(!sneak.isToggled()) {
            mc.thePlayer.isSneaking();
         }

         mc.thePlayer.setSprinting(true);
         double spd = 0.0025D * speed.getInput();

         double motionX = mc.thePlayer.motionX * mc.thePlayer.motionX + yOff.getInput();
         double motionY = mc.thePlayer.motionZ * mc.thePlayer.motionZ + xOff.getInput();

         double m = (float)(Math.sqrt( motionX + motionY ) + spd);

         Utils.Player.bop(m);
      }
   }

}