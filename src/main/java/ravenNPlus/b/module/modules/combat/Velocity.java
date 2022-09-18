package ravenNPlus.b.module.modules.combat;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.ComboSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

   public static SliderSetting a;
   public static SliderSetting b;
   public static SliderSetting c;
   public static SliderSetting ap;
   public static SliderSetting bp;
   public static SliderSetting cp;
   public static SliderSetting dt;
   public static TickSetting d;
   public static TickSetting e;
   public static TickSetting f;
   public static ComboSetting g;
   public Mode mode;

   public Velocity() {
      super("Velocity", ModuleCategory.combat, "Modifies your Knockback");
      this.mode = Velocity.Mode.Distance;
      this.addSetting(a = new SliderSetting("Horizontal", 90.0, -100.0, 100.0, 1.0));
      this.addSetting(b = new SliderSetting("Vertical", 100.0, -100.0, 100.0, 1.0));
      this.addSetting(c = new SliderSetting("Chance", 100.0, 0.0, 100.0, 1.0));
      this.addSetting(d = new TickSetting("Only while targeting", false));
      this.addSetting(e = new TickSetting("Disable while holding S", false));
      this.addSetting(f = new TickSetting("Different Velocity for projectiles", false));
      this.addSetting(g = new ComboSetting("Projectiles Mode", this.mode));
      this.addSetting(ap = new SliderSetting("Horizontal projectiles", 90.0, -100.0, 100.0, 1.0));
      this.addSetting(bp = new SliderSetting("Vertical projectiles", 100.0, -100.0, 100.0, 1.0));
      this.addSetting(cp = new SliderSetting("Chance projectiles", 100.0, 0.0, 100.0, 1.0));
      this.addSetting(dt = new SliderSetting("Distance projectiles", 3.0, 0.0, 20.0, 0.1));
   }

   @SubscribeEvent
   public void onLivingUpdate(Event fe) {
      if(!(fe instanceof LivingEvent)) return;

      if(Utils.Player.isPlayerInGame() && mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
         if(d.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null))
            return;

         if(e.isToggled() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()))
            return;

         if(mc.thePlayer.getLastAttacker() instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer)mc.thePlayer.getLastAttacker();
            Item item = attacker.getCurrentEquippedItem() != null ? attacker.getCurrentEquippedItem().getItem() : null;
            if((item instanceof ItemEgg || item instanceof ItemBow || item instanceof ItemSnow || item instanceof ItemFishingRod) && this.mode == Mode.ItemHeld) {
               this.valorant();
               return;
            }

            if((double)attacker.getDistanceToEntity(mc.thePlayer) > dt.getInput()) {
               this.valorant();
               return;
            }
         }

         if (c.getInput() != 100.0) {
            double ch = Math.random();
            if (ch >= c.getInput() / 100.0) {
               return;
            }
         }

         EntityPlayerSP playerSP;
         if (a.getInput() != 100.0) {
            playerSP = mc.thePlayer;
            playerSP.motionX *= a.getInput() / 100.0;
            playerSP = mc.thePlayer;
            playerSP.motionZ *= a.getInput() / 100.0;
         }

         if (b.getInput() != 100.0) {
            playerSP = mc.thePlayer;
            playerSP.motionY *= b.getInput() / 100.0;
         }
      }
   }

   public void valorant() {
      if (cp.getInput() != 100.0) {
         double ch = Math.random();
         if (ch >= cp.getInput() / 100.0) {
            return;
         }
      }

      EntityPlayerSP playerSP;
      if (ap.getInput() != 100.0) {
         playerSP = mc.thePlayer;
         playerSP.motionX *= ap.getInput() / 100.0;
         playerSP = mc.thePlayer;
         playerSP.motionZ *= ap.getInput() / 100.0;
      }

      if (bp.getInput() != 100.0) {
         playerSP = mc.thePlayer;
         playerSP.motionY *= bp.getInput() / 100.0;
      }
   }

   public enum Mode { Distance, ItemHeld }

}