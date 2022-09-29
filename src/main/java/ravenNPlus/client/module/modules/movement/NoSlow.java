package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.InvUtils;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoSlow extends Module {

   public static DescriptionSetting a, c;
   public static SliderSetting b, m;

   public NoSlow() {
      super("NoSlow", ModuleCategory.movement, "Default is 80% motion reduction");
      this.addSetting(c = new DescriptionSetting("Hypixel max: 22%"));
      this.addSetting(m = new SliderSetting("Mode", 1, 1, 3, 1));
      this.addSetting(a = new DescriptionSetting(Utils.md+""));
      this.addSetting(b = new SliderSetting("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
   }

   @Override
   public void onDisable() {
      if (Utils.Client.getTimer().timerSpeed != 1.0F) {
         Utils.Client.resetTimer();
      }
   }

   @SubscribeEvent
   public void r(TickEvent.PlayerTickEvent e) {
      if (!Utils.Player.isPlayerInGame() || !Utils.Player.isMoving() || mc.thePlayer.getHeldItem() == null
         || mc.thePlayer.isOnLadder() || !mc.thePlayer.onGround || Utils.Player.isInLiquid()) return;

      if ((InvUtils.isPlayerHoldingSword() || InvUtils.isPlayerHoldingFood() || InvUtils.isPlayerHoldingBow()) && Utils.Player.isMoving() && mc.gameSettings.keyBindUseItem.isKeyDown()) {

         float val = (100.0F - (float) b.getValue()) / 100.0F;

         if (m.getValue() == 1) {
            mc.thePlayer.moveForward *= val;
            mc.thePlayer.moveStrafing *= val;
         }

         if (m.getValue() == 2) {
            mc.thePlayer.movementInput.moveForward *= val;
            mc.thePlayer.movementInput.moveStrafe *= val;
         }

         if (m.getValue() == 3) {
            if(b.getValue() > 5) b.setValue(5);
            Utils.Client.getTimer().timerSpeed = b.getValueToFloat();
         }

      } else if (Utils.Client.getTimer().timerSpeed != 1.0F) {
         Utils.Client.resetTimer();
      }
   }

   public void guiUpdate() {
      switch ((int) m.getValue()) {
         case 1:
            a.setDesc(Utils.md + "MoveInput");
            break;
         case 2:
            a.setDesc(Utils.md + "MovementInput");
            break;
         case 3:
            a.setDesc(Utils.md + "Timer");
            break;
      }
   }

}