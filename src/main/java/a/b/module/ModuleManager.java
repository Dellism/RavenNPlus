package a.b.module;

import a.b.module.modules.minigames.sumo.SumoBot;
import a.b.module.modules.minigames.sumo.SumoClicker;
import a.b.module.modules.minigames.sumo.SumoStats;
import a.b.utils.Utils;
import a.b.module.modules.HUD;
import a.b.module.modules.client.*;
import a.b.module.modules.combat.*;
import a.b.module.modules.hotkey.*;
import a.b.module.modules.other.*;
import a.b.module.modules.player.*;
import a.b.module.modules.render.*;
import a.b.module.modules.movement.*;
import a.b.module.modules.minigames.*;
import a.b.utils.notifications.Notification;
import a.b.utils.notifications.NotificationManager;
import a.b.utils.notifications.NotificationType;
import net.minecraft.client.gui.FontRenderer;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
   private final List<Module> modules = new ArrayList<>();
   public static boolean initialized = false;

   public ModuleManager() {
         if (initialized) return;

         addModule(new LeftClicker());
         addModule(new RightClicker());
         addModule(new AimAssist());
         addModule(new BurstClicker());
         addModule(new ClickAssist());
         addModule(new DelayRemover());
         addModule(new HitBox());
         addModule(new Reach());
         addModule(new Velocity());
         addModule(new BHop());
         addModule(new Boost());
         addModule(new Fly());
         addModule(new InvMove());
         addModule(new KeepSprint());
         addModule(new NoSlow());
         addModule(new Speed());
         addModule(new Sprint());
         addModule(new StopMotion());
         addModule(new Timer());
         addModule(new VClip());
         addModule(new AutoJump());
         addModule(new AutoPlace());
         addModule(new BedAura());
         addModule(new FallSpeed());
         addModule(new FastPlace());
         addModule(new Freecam());
         addModule(new NoFall());
         addModule(new SafeWalk());
         addModule(new AntiBot());
         addModule(new AntiShuffle());
         addModule(new Chams());
         addModule(new ChestESP());
         addModule(new PlayerESP());
         addModule(new Tracers());
         addModule(new HUD());
         addModule(new Xray());
         addModule(new BridgeInfo());
         addModule(new DuelsStats());
         addModule(new MurderMystery());
         addModule(new SumoFences());
         addModule(new SumoBot());      // (code by Kv)
         addModule(new SumoClicker());  // (code by Kv)
         addModule(new SumoStats());    // (code by Kv)
         addModule(new SlyPort());
         addModule(new ClientNameSpoof());
         addModule(new FakeChat());
         addModule(new NameHider());
         addModule(new StringEncrypt());
         addModule(new WaterBucket());
         addModule(new CommandPrompt());
         addModule(new GuiModule());
         addModule(new SelfDestruct());
         addModule(new ChatLogger());
         addModule(new BridgeAssist());
         addModule(new Fullbright());
         addModule(new UpdateCheck());
         addModule(new AutoHeader());
         addModule(new AutoTool());
         addModule(new Blocks());
         addModule(new Ladders());
         addModule(new Weapon());
         addModule(new Pearl());
         addModule(new Armour());
         addModule(new Healing());
         addModule(new Trajectories());
         addModule(new WTap());
         addModule(new BlockHit());
         addModule(new STap());
         addModule(new AutoWeapon());
         addModule(new BedwarsOverlay());
         addModule(new ShiftTap());
         addModule(new FPSSpoofer());
         addModule(new MouseSpoofer());
         addModule(new NameTags());
         addModule(new NameTagsV2());
         addModule(new AutoBlock());
         addModule(new MiddleClick());

//   Stuff added by SleepyFish
//    | [:] Moved ChatLog From World -> Other
//    | [:] Moved AntiBot From World -> Combat
//    | [+] Code Optimization
//    | [+] Renamed BHop, added 4 settings,
//    | [+] Renamed AimAssist Blatant mode to AimLock
//    | [+] Renamed ExplicitNameTags, added 1 setting
//    | [+] Added Day command
//    | [+] Added Night command
//    | [+] Added gamemodeC command
//    | [+] Added gamemodeS command
//    | [+] Added clearChat command
//    | [+] Added color command
//    | [+] Added author command
//    | [:] Renamed clear terminal command to cls
//    | [:] Renamed terminal to Command prompt
//    | [:] Renamed Command prompt color
//    | [:] Edited the discord link
//    | [+] Added 9 HUD setting
//    | [+] Added 1 PlayerESP setting
//    | [+] Added AntiVoid with 8 settings
//    | [+] Added Scaffold with 5 setting
//    | [+] Added Shutdown with 1 setting and 50 modes
//    | [+] Added Step     with 1 setting
//    | [+] Added ViewModel with 3 setting
//    | [+] Added Animations with 7 setting
//    | [+] Added AutoSlot   with 4 setting
//    | [+] Added ItemCount  with 8 setting
//    | [+] Added EasyChat   with 2 setting and 60 modes
//    | [+] Added ChestSteal with 4 setting
//    | [+] Added BestSword  with 1 setting
//    | [+] Added AutoArmor  with 2 setting
//    | [+] Added Criticals  with 1 setting
//    | [+] Added DiscordRPC and icon and text
//    | [+] Added ClickGUI stuff
//    | [+] Added RGB to clickGUI animation and edited the string / position
//    | [+] Added BlurUtils and RoundedUtils to draw Rounded Rectangles and blur background
//    | [+] Added getServerIP and isServerIP and DelayTimer and InvUtils and SoundUtils
         addModule(new Shutdown());   // (2 setting)
         addModule(new AntiVoid());     // (9 settings)
         addModule(new Scaffold());     // (4 settings)
         addModule(new EasyChat());   // (4 settings)
         addModule(new FOV());          // (1 settings)
         addModule(new AutoSlot());    // (8 settings)
         addModule(new ItemCount());  // (10 settings)
         addModule(new Step());         // (1 settings)
         addModule(new AutoGap());    // (5 settings)
         addModule(new AutoArmor()); // (2 settings)
         addModule(new Jesus());        // (1 settings)
         addModule(new Hurtcam());    // (3 settings)
         addModule(new ChestSteal()); // (4 settings)
         addModule(new Animations()); // (13 settings)
         addModule(new ViewModel());  // (12 settings)
         addModule(new BestSword());  // (1 settings)
         addModule(new Flip());            // (2 settings)
         addModule(new Criticals());      // (1 settings)
         addModule(new TargetHUD());   // (4 settings)
         addModule(new UserHud());      // (8 settings)
         addModule(new FastLadder());   // (4 settings)
         addModule(new KillAura());       // (12 settings)
         addModule(new AutoHome());    // (4 settings)
//       addModule(new ItemESP());        // (1 settings)
         addModule(new LegoGraphics()); // (1 settings)
//       addModule(new HypixelFly()); // (2 settings)

         initialized = true;
        NotificationManager.show(new Notification(NotificationType.INFO, "Otaku", "All Modules loaded\nDiscord RPC loaded", 10));
   }

    //TODO: Fix ItemCount, scaffold sending to much Packets, add autoPlay

   private void addModule(Module m) { modules.add(m); }

   public Module getModuleByName(String name) {
      if (!initialized) return null;
      for (Module module : modules) {
         if (module.getName().equalsIgnoreCase(name))
            return module;
      }

      return null;
   }

   public Module getModuleByClazz(Class<? extends Module> c) {
      if (!initialized) return null;
      for (Module module : modules) {
         if (module.getClass().equals(c))
            return module;
      }

      return null;
   }

   public List<Module> getModules() {
      return modules;
   }

   public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
      ArrayList<Module> modulesOfCat = new ArrayList<>();

      for (Module mod : modules) {
         if (mod.moduleCategory().equals(categ)) {
            modulesOfCat.add(mod);
         }
      }

      return modulesOfCat;
   }

   public void sort() {
      if (HUD.alphabeticalSort.isToggled()) {
         modules.sort(Comparator.comparing(Module::getName));
      } else {
         modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
      }
   }

   public int numberOfModules() { return modules.size(); }

   public void sortLongShort() {
      modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
   }

   public void sortShortLong() {
      modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public int getLongestActiveModule(FontRenderer fr) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            if(fr.getStringWidth(mod.getName()) > length){
               length = fr.getStringWidth(mod.getName());
            }
         }
      }

      return length;
   }

   public int getBoxHeight(FontRenderer fr, int margin) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            length += fr.FONT_HEIGHT + margin;
         }
      }

      return length;
   }
}