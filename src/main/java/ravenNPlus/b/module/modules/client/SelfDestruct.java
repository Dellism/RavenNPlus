package ravenNPlus.b.module.modules.client;

import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.utils.ChatHelper;
import ravenNPlus.b.utils.DebugInfoRenderer;
import ravenNPlus.b.utils.MouseManager;
import ravenNPlus.c.KeyStrokeRenderer;
import net.minecraftforge.common.MinecraftForge;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("Self Destruct", ModuleCategory.client, "Disable all Modules");
   }

   public void onEnable() {
      this.disable();

      mc.displayGuiScreen(null);

      for (Module module : RavenNPlus.moduleManager.getModules()) {
         if (module != this && module.isEnabled()) {
            module.disable();
         }
      }

      MinecraftForge.EVENT_BUS.unregister(new RavenNPlus());
      MinecraftForge.EVENT_BUS.unregister(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.unregister(new MouseManager());
      MinecraftForge.EVENT_BUS.unregister(new KeyStrokeRenderer());
      MinecraftForge.EVENT_BUS.unregister(new ChatHelper());
   }
}