package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoSlow extends Module {

   public static DescriptionSetting a, c;
   public static SliderSetting b;

   public NoSlow() {
      super("NoSlow", ModuleCategory.movement, "Default is 80% motion reduction");
      this.addSetting(c = new DescriptionSetting("Hypixel max: 22%"));
      this.addSetting(b = new SliderSetting("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   @SubscribeEvent
   public void sr(TickEvent.PlayerTickEvent e) {
      if(!Utils.Player.isPlayerInGame()) return;

      float val = (100.0F - (float) b.getValue()) / 100.0F;

      if(mc.thePlayer.getHeldItem() != null) {
         if((mc.thePlayer.inventory.getItemStack().getItem() instanceof ItemSword || mc.thePlayer.inventory.getItemStack().getItem() instanceof ItemFood) && mc.thePlayer.isUsingItem()) {
            if(Utils.Player.isInLiquid()) return;

            mc.thePlayer.movementInput.moveStrafe *= val;
            mc.thePlayer.movementInput.moveForward *= val;
         }
      }
   }

}