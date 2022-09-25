package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AntiVoid extends Module {

   public static TickSetting x, y, z, noCreative;
   public static DescriptionSetting desc;
   public static DescriptionSetting modeMode;
   public static SliderSetting xS, yS, zS, distance, mode;
   boolean no = !mc.isSingleplayer();

   public AntiVoid() {
      super("AntiVoid", ModuleCategory.movement, "Dont fall in void, TP Back");
      this.addSetting(distance = new SliderSetting("Distance", 4.0D, 2.0D, 10.0D, 1.0D));
      this.addSetting(mode = new SliderSetting("Mode", 4D, 1D, 6D, 1D));
      this.addSetting(modeMode = new DescriptionSetting(Utils.md + ""));
      this.addSetting(noCreative = new TickSetting("Cancel if in Creative", true));
      this.addSetting(x = new TickSetting("Motion X", true));
      this.addSetting(xS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.addSetting(y = new TickSetting("Motion Y", false));
      this.addSetting(yS = new SliderSetting("Y Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.addSetting(z = new TickSetting("Motion Z", false));
      this.addSetting(zS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
   }

   private boolean flagged = false;
   private boolean tried = false;
   private double posX = 0.0;
   private double posY = 0.0;
   private double posZ = 0.0;
   private double lastRecY = 0.0;

   @Override
   public void onEnable() {
      if (mc.thePlayer != null) {
         lastRecY = mc.thePlayer.posY;
      } else {
         lastRecY = 0.0;
      }

      tried = false;
      flagged = false;

      if (mc.isSingleplayer()) {
         if (lastRecY == 0.0) {
            lastRecY = mc.thePlayer.posY;
         }
      }
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus) {
      no = noCreative.isToggled();

         if (mode.getValue() == 1) {
            if (mc.thePlayer.fallDistance > distance.getValue() && !tried) {
               mc.thePlayer.motionY += xS.getValue();
               mc.thePlayer.fallDistance = 0.0F;
               tried = true;
            } else
               tried = false;
         }

         if (mode.getValue() == 2) {
            if (mc.thePlayer.fallDistance > distance.getValue() && !tried) {
               mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + 1, mc.thePlayer.posY + 1, mc.thePlayer.posZ + 1, false));
               tried = true;
            } else
               tried = false;
         }

         if (mode.getValue() == 3) {
            if (mc.thePlayer.onGround && Utils.Player.playerOnBlock()) {
               posX = mc.thePlayer.prevPosX;
               posY = mc.thePlayer.prevPosY;
               posZ = mc.thePlayer.prevPosZ;
            }
            if (mc.thePlayer.fallDistance > distance.getValue() && !tried) {
               mc.thePlayer.setPositionAndUpdate(posX, posY, posZ);
               mc.thePlayer.fallDistance = 0F;
               mc.thePlayer.motionX = 0.0;
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ = 0.0;
               tried = true;
            } else
               tried = false;
         }

         if (mode.getValue() == 4) {
            if (mc.thePlayer.fallDistance > distance.getValue() && mc.thePlayer.posY < lastRecY + 0.01 && mc.thePlayer.motionY <= 0 && !mc.thePlayer.onGround && !flagged) {
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ *= 0.838;
               mc.thePlayer.motionX *= 0.838;
            }

            lastRecY = mc.thePlayer.posY;
         }

         if (mode.getValue() == 5) {
            if (mc.thePlayer.fallDistance > distance.getValue() && mc.thePlayer.posY < lastRecY + 0.01 && mc.thePlayer.motionY <= 0 && !mc.thePlayer.onGround && !flagged) {
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ = 0.0;
               mc.thePlayer.motionX = 0.0;
               mc.thePlayer.jumpMovementFactor = 0.00f;
               if (!tried) {
                  tried = true;
                  mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, (32000.0), mc.thePlayer.posZ, false));
               }
            } else
               tried = false;

            lastRecY = mc.thePlayer.posY;
         }

         if (mode.getValue() == 6) {
            if (!mc.thePlayer.onGround && mc.thePlayer.fallDistance > distance.getValue() && mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0) {
               EntityPlayerSP p = mc.thePlayer;

               if (noCreative.isToggled() && no)
                  return;

               if(x.isToggled())
                  mc.thePlayer.motionX = xS.getValue();

               if(y.isToggled())
                  mc.thePlayer.motionY = yS.getValue();

               if(z.isToggled())
                  mc.thePlayer.motionZ = zS.getValue();
            }
         }
      }
   }

   @Override
   public void onDisable() {
      tried = false;
      flagged = false;
   }

   public void guiUpdate() {
      switch((int) mode.getValue()) {
         case 1:
            modeMode.setDesc(Utils.md + "Motion Flag");
            break;
         case 2:
            modeMode.setDesc(Utils.md + "Packets");
            break;
         case 3:
            modeMode.setDesc(Utils.md + "TP Back");
            break;
         case 4:
            modeMode.setDesc(Utils.md + "Jartex");
            break;
         case 5:
            modeMode.setDesc(Utils.md + "Old Cubecraft");
            break;
         case 6:
            modeMode.setDesc(Utils.md + "Adv Motion");
            break;
      }
   }

}