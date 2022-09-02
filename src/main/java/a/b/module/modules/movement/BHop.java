package a.b.module.modules.movement;

import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class BHop extends Module {

   public static SliderSetting speed;
   public static SliderSetting yOff;
   public static SliderSetting xOff;
   public static  TickSetting  jump;
   public static  TickSetting  sneak;
   private final double bspd = 0.0025D;

   public BHop() {
      super("BHop", ModuleCategory.movement);
      this.registerSetting(speed = new SliderSetting("Speed", 2.0D, 0.5D, 20.0D, 0.5D));
      this.registerSetting(yOff  = new SliderSetting("y Offset", 0.0D, 0.0D, 10.0D, 0.2D));
      this.registerSetting(xOff  = new SliderSetting("x Offset", 0.0D, 0.0D, 10.0D, 0.2D));
      this.registerSetting(jump  = new TickSetting("Jump", true));
      this.registerSetting(sneak = new TickSetting("Sneak", false));
   }

   public void update() {
      Module fly = Otaku.moduleManager.getModuleByClazz(Fly.class);
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