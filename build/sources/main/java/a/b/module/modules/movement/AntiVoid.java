package a.b.module.modules.movement;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
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
      super("AntiVoid", ModuleCategory.movement);
      this.registerSetting(distance = new SliderSetting("Distance", 4.0D, 2.0D, 10.0D, 1.0D));
      this.registerSetting(mode = new SliderSetting("Mode", 4D, 1D, 6D, 1D));
      this.registerSetting(modeMode = new DescriptionSetting(Utils.md + ""));
      this.registerSetting(noCreative = new TickSetting("Cancel if in Creative", true));
      this.registerSetting(x = new TickSetting("Motion X", true));
      this.registerSetting(xS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.registerSetting(y = new TickSetting("Motion Y", false));
      this.registerSetting(yS = new SliderSetting("Y Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
      this.registerSetting(z = new TickSetting("Motion Z", false));
      this.registerSetting(zS = new SliderSetting("Z Value: ", 1.0D, 1.0D, 10.0D, 1.0D));
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

         if (mode.getInput() == 1) {
            if (mc.thePlayer.fallDistance > distance.getInput() && !tried) {
               mc.thePlayer.motionY += xS.getInput();
               mc.thePlayer.fallDistance = 0.0F;
               tried = true;
            } else
               tried = false;
         }

         if (mode.getInput() == 2) {
            if (mc.thePlayer.fallDistance > distance.getInput() && !tried) {
               mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + 1, mc.thePlayer.posY + 1, mc.thePlayer.posZ + 1, false));
               tried = true;
            } else
               tried = false;
         }

         if (mode.getInput() == 3) {
            if (mc.thePlayer.onGround && Utils.Player.playerOnBlock()) {
               posX = mc.thePlayer.prevPosX;
               posY = mc.thePlayer.prevPosY;
               posZ = mc.thePlayer.prevPosZ;
            }
            if (mc.thePlayer.fallDistance > distance.getInput() && !tried) {
               mc.thePlayer.setPositionAndUpdate(posX, posY, posZ);
               mc.thePlayer.fallDistance = 0F;
               mc.thePlayer.motionX = 0.0;
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ = 0.0;
               tried = true;
            } else
               tried = false;
         }

         boolean canSpoof = false;
         if (mode.getInput() == 4) {
            if (mc.thePlayer.fallDistance > distance.getInput() && mc.thePlayer.posY < lastRecY + 0.01 && mc.thePlayer.motionY <= 0 && !mc.thePlayer.onGround && !flagged) {
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ *= 0.838;
               mc.thePlayer.motionX *= 0.838;
               canSpoof = true;
            }

            lastRecY = mc.thePlayer.posY;
         }

         if (mode.getInput() == 5) {
            if (mc.thePlayer.fallDistance > distance.getInput() && mc.thePlayer.posY < lastRecY + 0.01 && mc.thePlayer.motionY <= 0 && !mc.thePlayer.onGround && !flagged) {
               mc.thePlayer.motionY = 0.0;
               mc.thePlayer.motionZ = 0.0;
               mc.thePlayer.motionX = 0.0;
               mc.thePlayer.jumpMovementFactor = 0.00f;
               canSpoof = true;
               if (!tried) {
                  tried = true;
                  mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, (32000.0), mc.thePlayer.posZ, false));
               }
            } else
               tried = false;

            lastRecY = mc.thePlayer.posY;
         }

         if (mode.getInput() == 6) {
            if (!mc.thePlayer.onGround && mc.thePlayer.fallDistance > distance.getInput() && mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0) {
               EntityPlayerSP p = mc.thePlayer;

               if (noCreative.isToggled() && no)
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

   @Override
   public void onDisable() {
      tried = false;
      flagged = false;
   }

   public void guiUpdate() {
      switch((int) mode.getInput()) {
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