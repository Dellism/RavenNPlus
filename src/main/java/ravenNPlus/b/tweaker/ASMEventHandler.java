package ravenNPlus.b.tweaker;

import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.modules.combat.LeftClicker;
import ravenNPlus.b.module.modules.combat.Reach;
import ravenNPlus.b.module.modules.movement.KeepSprint;
import ravenNPlus.b.module.modules.movement.NoSlow;
import ravenNPlus.b.module.modules.other.NameHider;
import ravenNPlus.b.module.modules.other.StringEncrypt;
import ravenNPlus.b.module.modules.player.SafeWalk;
import ravenNPlus.b.module.modules.render.AntiShuffle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class ASMEventHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();

   /**
    * called when Minecraft format text
    * ASM Modules : NameHider, AntiShuffle, StringEncrypt
    */
   public static String getUnformattedTextForChat(String s) {
      Module nameHider = RavenNPlus.moduleManager.getModuleByClazz(NameHider.class);
      if (nameHider != null && nameHider.isEnabled()) {
         s = NameHider.getUnformattedTextForChat(s);
      }

      Module antiShuffle = RavenNPlus.moduleManager.getModuleByClazz(StringEncrypt.class);
      if (antiShuffle != null && antiShuffle.isEnabled()) {
         s = AntiShuffle.getUnformattedTextForChat(s);
      }

      Module stringEncrypt = RavenNPlus.moduleManager.getModuleByClazz(StringEncrypt.class);
      if (stringEncrypt != null && stringEncrypt.isEnabled()) {
         s = StringEncrypt.getUnformattedTextForChat(s);
      }

      return s;
   }


   /**
    * called when an entity moves
    * ASM Modules : SafeWalk
    */
   public static boolean onEntityMove(Entity entity) {
      if (entity == mc.thePlayer && mc.thePlayer.onGround) {
         Module safeWalk = RavenNPlus.moduleManager.getModuleByClazz(SafeWalk.class);

         if (safeWalk != null && safeWalk.isEnabled() && !SafeWalk.doShift.isToggled()) {
            if (SafeWalk.blocksOnly.isToggled()) {
               ItemStack i = mc.thePlayer.getHeldItem();
               if (i == null || !(i.getItem() instanceof ItemBlock)) {
                  return mc.thePlayer.isSneaking();
               }
            }

            return true;
         } else {
            return mc.thePlayer.isSneaking();
         }
      } else {
         return false;
      }
   }

   /*public String getModName()
   {
      return "lunarclient:db2533c";
   }*/

   /**
    * called when a player is using an item (aka right-click)
    * ASM Modules : NoSlow
    */
   public static void onLivingUpdate() {
      Module noSlow = RavenNPlus.moduleManager.getModuleByClazz(NoSlow.class);
      if (noSlow != null && noSlow.isEnabled()) {
         //NoSlow.sr(null);
      } else {
         mc.thePlayer.movementInput.moveStrafe *= 0.2F;
         mc.thePlayer.movementInput.moveForward *= 0.2F;
      }
   }

   /**
    * called when a player is moving and hits another one
    * ASM Modules : KeepSprint
    */
   public static void onAttackTargetEntityWithCurrentItem(Entity en) {
      Module keepSprint = RavenNPlus.moduleManager.getModuleByClazz(KeepSprint.class);
      if (keepSprint != null && keepSprint.isEnabled()) {
         KeepSprint.sl(en);
      } else {
         mc.thePlayer.motionX *= 0.6D;
         mc.thePlayer.motionZ *= 0.6D;
      }
   }

   /**
    * called every tick
    * ASM Modules : AutoClicker, Reach
    */
   public static void onTick() {
      Module autoClicker = RavenNPlus.moduleManager.getModuleByClazz(LeftClicker.class);
      if (autoClicker == null || !autoClicker.isEnabled() || !Mouse.isButtonDown(0) || !Reach.call()) {
         mc.entityRenderer.getMouseOver(1.0F);
      }
   }
}
