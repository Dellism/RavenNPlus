package a.b.module.modules.movement;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AntiVoid extends Module {

   public static TickSetting x, y, z, noCreative;
   public static DescriptionSetting desc;
   public static SliderSetting xS, yS, zS, a;

   static boolean no  = !mc.isSingleplayer();

   public AntiVoid() {
      super("AntiVoid", ModuleCategory.movement);
      this.registerSetting(a = new SliderSetting("Distance", 4.0D, 2.0D, 10.0D, 1.0D));
      this.registerSetting(noCreative = new TickSetting("Cancel if in Creative", true));
      this.registerSetting(desc = new DescriptionSetting("  "));
      this.registerSetting(x = new TickSetting("Motion X", true));
      this.registerSetting(xS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.registerSetting(y = new TickSetting("Motion Y", false));
      this.registerSetting(yS = new SliderSetting("Y Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.registerSetting(z = new TickSetting("Motion Z", false));
      this.registerSetting(zS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if(noCreative.isToggled()) {
         no = true;
      } else {
         no = false;
      }

      if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus) {
         if(!mc.thePlayer.onGround && mc.thePlayer.fallDistance > a.getInput() && mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0) {
            EntityPlayerSP p = mc.thePlayer;

            if(noCreative.isToggled() && no)
               return;

            if(x.isToggled())
               mc.thePlayer.motionX = xS.getInput();

            if(y.isToggled())
               mc.thePlayer.motionY = yS.getInput();

            if(z.isToggled())
               mc.thePlayer.motionZ = zS.getInput();
         }
      }
   }

}