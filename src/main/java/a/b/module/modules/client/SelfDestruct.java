package a.b.module.modules.client;

import a.b.main.Otaku;
import a.b.module.Module;
import a.b.utils.ChatHelper;
import a.b.utils.DebugInfoRenderer;
import a.b.utils.MouseManager;
import a.c.KeyStrokeRenderer;
import net.minecraftforge.common.MinecraftForge;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("Self Destruct", ModuleCategory.client);
   }

   public void onEnable() {
      this.disable();

      mc.displayGuiScreen(null);

      for (Module module : Otaku.moduleManager.getModules()) {
         if (module != this && module.isEnabled()) {
            module.disable();
         }
      }

      MinecraftForge.EVENT_BUS.unregister(new Otaku());
      MinecraftForge.EVENT_BUS.unregister(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.unregister(new MouseManager());
      MinecraftForge.EVENT_BUS.unregister(new KeyStrokeRenderer());
      MinecraftForge.EVENT_BUS.unregister(new ChatHelper());
   }
}