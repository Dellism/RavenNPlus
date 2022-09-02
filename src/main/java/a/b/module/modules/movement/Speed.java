package a.b.module.modules.movement;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
   public static DescriptionSetting desc;
   public static SliderSetting speed;
   public static TickSetting strafe;

   public Speed() {
      super("Speed", ModuleCategory.movement);
      this.registerSetting(desc = new DescriptionSetting("Hypixel max: 1.13"));
      this.registerSetting(speed = new SliderSetting("Speed", 1.2D, 1.0D, 2.5D, 0.01D));
      this.registerSetting(strafe = new TickSetting("Strafe only", false));
   }

   public void update() {
      double csp = Utils.Player.pythagorasMovement();
      if (csp != 0.0D) {
         if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
            if (!strafe.isToggled() || mc.thePlayer.moveStrafing != 0.0F) {
               if (mc.thePlayer.hurtTime != mc.thePlayer.maxHurtTime || mc.thePlayer.maxHurtTime <= 0) {
                  if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                     double val = speed.getInput() - (speed.getInput() - 1.0D) * 0.5D;
                     Utils.Player.fixMovementSpeed(csp * val, true);
                  }
               }
            }
         }
      }
   }
}