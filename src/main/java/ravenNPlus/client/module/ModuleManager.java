package ravenNPlus.client.module;

import ravenNPlus.client.utils.Utils;
import ravenNPlus.client.module.modules.HUD;
import ravenNPlus.client.module.modules.client.*;
import ravenNPlus.client.module.modules.combat.*;
import ravenNPlus.client.module.modules.hotkey.*;
import ravenNPlus.client.module.modules.other.*;
import ravenNPlus.client.module.modules.player.*;
import ravenNPlus.client.module.modules.render.*;
import ravenNPlus.client.module.modules.movement.*;
import ravenNPlus.client.module.modules.minigames.*;
import ravenNPlus.client.module.modules.minigames.sumo.*;
import net.minecraft.client.gui.FontRenderer;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
   private final List<Module> modules = new ArrayList<>();
   public static boolean initialized = false;

   public ModuleManager() {
         if (initialized) return;

         addMod(new LeftClicker());
         addMod(new RightClicker());
         addMod(new DoubleClicker()); // (7 settings)
         addMod(new AimAssist());
         addMod(new BurstClicker());
         addMod(new ClickAssist());
         addMod(new DelayRemover());
         addMod(new HitBox());
         addMod(new Reach());
         addMod(new Velocity());
         addMod(new BHop());
         addMod(new Boost());
         addMod(new Fly());
         addMod(new InvMove());
         addMod(new KeepSprint());
         addMod(new NoSlow());
         addMod(new Speed());
         addMod(new Sprint());
         addMod(new StopMotion());
         addMod(new Timer());
         addMod(new VClip());
         addMod(new AutoJump());
         addMod(new AutoPlace());
         addMod(new BedAura());
         addMod(new FallSpeed());
         addMod(new FastPlace());
         addMod(new Freecam());
         addMod(new NoFall());
         addMod(new SafeWalk());
         addMod(new AntiBot());
         addMod(new AntiShuffle());
         addMod(new Chams());
         addMod(new ChestESP());
         addMod(new PlayerESP());
         addMod(new Tracers());
         addMod(new HUD());
         addMod(new Xray());
         addMod(new BridgeInfo());
         addMod(new DuelsStats());
         addMod(new MurderMystery());
         addMod(new SumoFences());
         addMod(new SumoBot());
         addMod(new SumoClicker());
         addMod(new SumoStats());
         addMod(new SlyPort());
         addMod(new ClientNameSpoof());
         addMod(new FakeChat());
         addMod(new NameHider());
         addMod(new StringEncrypt());
         addMod(new WaterBucket());
         addMod(new CommandPrompt());
         addMod(new GuiClick());
         addMod(new SelfDestruct());
         addMod(new ChatLogger());
         addMod(new BridgeAssist());
         addMod(new Fullbright());
         addMod(new UpdateCheck());
         addMod(new AutoHeader());
         addMod(new AutoTool());
         addMod(new Blocks());
         addMod(new Ladders());
         addMod(new Weapon());
         addMod(new Pearl());
         addMod(new Armour());
         addMod(new Healing());
         addMod(new Trajectories());
         addMod(new WTap());
         addMod(new BlockHit());
         addMod(new STap());
         addMod(new AutoWeapon());
         addMod(new BedwarsOverlay());
         addMod(new ShiftTap());
         addMod(new FPSSpoofer());
         addMod(new MouseSpoofer());
         addMod(new NameTags());
         addMod(new NameTagsV2());
         addMod(new AutoBlock());
         addMod(new MiddleClick());
// -----   Stuff added by SleepyFish   -----
         addMod(new Shutdown());   // (2 setting)
         addMod(new AntiVoid());     // (9 settings)
         addMod(new Scaffold());      // (4 settings)
         addMod(new EasyChat());    // (4 settings)
         addMod(new FOV());           // (1 settings)
         addMod(new AutoSlot());     // (8 settings)
         addMod(new ItemCount());   // (10 settings)
         addMod(new Step());           // (1 settings)
         addMod(new AutoGap());      // (5 settings)
         addMod(new AutoArmor());   // (2 settings)
         addMod(new Jesus());          // (1 settings)
         addMod(new Hurtcam());      // (3 settings)
         addMod(new ChestSteal());   // (4 settings)
         addMod(new Animations());  // (13 settings)
         addMod(new ViewModel());   // (12 settings)
         addMod(new BestSword());   // (1 settings)
         addMod(new Flip());            // (2 settings)
         addMod(new Criticals());      // (1 settings)
         addMod(new TargetHUD());   // (4 settings)
         addMod(new UserHud());       // (8 settings)
         addMod(new FastLadder());    // (4 settings)
         addMod(new KillAura());        // (12 settings)
         addMod(new AutoHome());     // (3 settings)
         addMod(new LegoGraphics()); // (0 settings)
         addMod(new InvMgr());          // (7 settings)
         addMod(new CategorySett());  // (40 settings)
         addMod(new CrossCircle());    // (3 settings)
         addMod(new ModSettings());  // (17 settings)
         addMod(new ReachCircle());  // (17 settings)
         //addMod(new Skeletons());      // (0 settings)
         //addMod(new ItemESP());      // (4 settings)
         //addMod(new Projectiles());   // (1 settings)
         //addMod(new Skeletons());   // (1 settings)

         initialized = true;
   }

    //TODO: Fix ItemCount, add autoPlay, fix itemESP, add Hypixel Fly, fix skeletons, add noHurtcam, add / fix Projectiles

   private void addMod(Module m) { modules.add(m); }

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

   public int numberOfModules() {
       return modules.size();
   }

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