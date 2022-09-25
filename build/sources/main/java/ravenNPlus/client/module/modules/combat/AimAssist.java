package ravenNPlus.client.module.modules.combat;

import ravenNPlus.client.main.Client;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.modules.player.RightClicker;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.InvUtils;
import ravenNPlus.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class AimAssist extends Module {
   public static SliderSetting speed, compliment, fov, distance;
   public static TickSetting clickAim, weaponOnly,aimInvis, breakBlocks, blatantMode, ignoreFriends;
   public static ArrayList<Entity> friends = new ArrayList<>();

   public AimAssist() {
      super("AimAssist", ModuleCategory.combat, "Making your aim very good");
      this.addSetting(speed = new SliderSetting("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));
      this.addSetting(compliment = new SliderSetting("Speed 2", 15.0D, 2D, 97.0D, 1.0D));
      this.addSetting(fov = new SliderSetting("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
      this.addSetting(distance = new SliderSetting("Distance", 4.5D, 1.0D, 10.0D, 0.5D));
      this.addSetting(clickAim = new TickSetting("Click aim", true));
      this.addSetting(breakBlocks = new TickSetting("Break blocks", true));
      this.addSetting(ignoreFriends = new TickSetting("Ignore Friends", true));
      this.addSetting(weaponOnly = new TickSetting("Weapon only", false));
      this.addSetting(aimInvis = new TickSetting("Aim invis", false));
      this.addSetting(blatantMode = new TickSetting("Aimlock", false));
   }

   public void update() {

      if(!Utils.Client.currentScreenMinecraft()){
         return;
      }
      if(!Utils.Player.isPlayerInGame()) return;

      if (breakBlocks.isToggled() && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null) {
            Block bl = mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof  Block) {
               return;
            }
         }
      }


      if (!weaponOnly.isToggled() || InvUtils.isPlayerHoldingWeapon()) {
         Module autoClicker = Client.moduleManager.getModuleByClazz(RightClicker.class);
         if ((clickAim.isToggled() && Utils.Client.autoClickerClicking()) || (Mouse.isButtonDown(0) && autoClicker != null && !autoClicker.isEnabled()) || !clickAim.isToggled()) {
            Entity en = this.getEnemy();
            if (en != null) {
               if (Client.debugger) {
                  Utils.Player.sendMessageToSelf(this.getName() + " &e" + en.getName());
               }

               if (blatantMode.isToggled()) {
                  Utils.Player.aim(en, 0.0F, false, false);
               } else {
                  double n = Utils.Player.fovFromEntity(en);
                  if (n > 1.0D || n < -1.0D) {
                     double complimentSpeed = n*(ThreadLocalRandom.current().nextDouble(compliment.getValue() - 1.47328, compliment.getValue() + 2.48293)/100);
                     double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble(speed.getValue() - 4.723847, speed.getValue());
                     float val = (float)(-(complimentSpeed + n / (101.0D - (float)ThreadLocalRandom.current().nextDouble(speed.getValue() - 4.723847, speed.getValue()))));
                     mc.thePlayer.rotationYaw += val;
                  }
               }
            }
         }
      }
   }

   public static boolean isAFriend(Entity entity) {
      if(entity == mc.thePlayer) return true;

      for (Entity wut : friends){
         if (wut.equals(entity))
            return true;
      } try {
         EntityPlayer bruhentity = (EntityPlayer) entity;
         if(Client.debugger){
            Utils.Player.sendMessageToSelf("unformatted / " + bruhentity.getDisplayName().getUnformattedText().replace("§", "%"));

            Utils.Player.sendMessageToSelf("susbstring entity / " + bruhentity.getDisplayName().getUnformattedText().substring(0, 2));
            Utils.Player.sendMessageToSelf("substring player / " + mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2));
         }
         if(mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) return true;
      } catch (Exception fhwhfhwe) {
         if(Client.debugger) {
            Utils.Player.sendMessageToSelf(fhwhfhwe.getMessage());
         }
      }

      return false;
   }

   public Entity getEnemy() {
      int fov = (int) AimAssist.fov.getValue();
      Iterator var2 = mc.theWorld.playerEntities.iterator();

      EntityPlayer en;
      do {
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var2.hasNext()) {
                              return null;
                           }
                           en = (EntityPlayer) var2.next();
                        } while (ignoreFriends.isToggled() && isAFriend(en));
                     } while(en == mc.thePlayer);
                  } while(en.isDead);
               } while(!aimInvis.isToggled() && en.isInvisible());
            } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getValue());
         } while(AntiBot.bot(en));
      } while(!blatantMode.isToggled() && !Utils.Player.fov(en, (float)fov));

      return en;
   }

   public static void addFriend(Entity entityPlayer) {
      friends.add(entityPlayer);
   }

   public static boolean addFriend(String name) {
      boolean found = false;
      for (Entity entity:mc.theWorld.getLoadedEntityList()) {
         if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
            if(!isAFriend(entity)) {
               addFriend(entity);
               found = true;
            }
         }
      }

      return found;
   }

   public static boolean removeFriend(String name) {
      boolean removed = false;
      boolean found = false;
      for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<>(mc.getNetHandler().getPlayerInfoMap())) {
         Entity entity = mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
         if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
            removed = removeFriend(entity);
            found = true;
         }
      }

      return found && removed;
   }

   public static boolean removeFriend(Entity entityPlayer) {
      try{
         friends.remove(entityPlayer);
      } catch (Exception eeeeee){
         eeeeee.printStackTrace();
         return false;
      }
      return true;
   }

   public static ArrayList<Entity> getFriends() {
      return friends;
   }
}