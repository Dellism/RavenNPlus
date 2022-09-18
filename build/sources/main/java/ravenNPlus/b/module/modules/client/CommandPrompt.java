package ravenNPlus.b.module.modules.client;

import com.google.gson.JsonObject;
import ravenNPlus.b.clickgui.RavenNPlus.ClickGui;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.Setting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CommandPrompt extends Module {
   public static boolean visible = false, b = false;
   public static Timer animation;
   public static TickSetting animate;
   public static SliderSetting opacity;

   public CommandPrompt() {
      super("Command Prompt", ModuleCategory.client, "Command Manager");
      withEnabled(true);

      this.addSetting(opacity = new SliderSetting("CommandPromt background opacity", 60, 0, 255, 1));
   }

   public void onEnable() {
      RavenNPlus.clickGui.commandPrompt.show();
      //keystrokesmod.client.clickgui.raven.CommandLine.setccs();
      //visible = true;
      //b = false;
      (animation = new Timer(500.0F)).start();
   }

   @SubscribeEvent
   public void tick(TickEvent.PlayerTickEvent e){
      if(Utils.Player.isPlayerInGame() && enabled && mc.currentScreen instanceof ClickGui && RavenNPlus.clickGui.commandPrompt.hidden())
         RavenNPlus.clickGui.commandPrompt.show();
   }

   public void onDisable() {
      RavenNPlus.clickGui.commandPrompt.hide();
      if (animation != null) {
         animation.start();
      }
   }

   @Override
   public void applyConfigFromJson(JsonObject data){
      try {
         this.keycode = data.get("keycode").getAsInt();
         // no need to set this to disabled
         JsonObject settingsData = data.get("settings").getAsJsonObject();
         for (Setting setting : getSettings()) {
            if (settingsData.has(setting.getName())) {
               setting.applyConfigFromJson(
                       settingsData.get(setting.getName()).getAsJsonObject()
               );
            }
         }
      } catch (NullPointerException ignored){ }
   }

   @Override
   public void resetToDefaults() {
      this.keycode = defualtKeyCode;
      for(Setting setting : this.settings){
         setting.resetToDefaults();
      }
   }
}