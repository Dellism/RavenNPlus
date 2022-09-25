package ravenNPlus.client.module.setting;

import com.google.gson.JsonObject;
import ravenNPlus.client.clickgui.RavenNPlus.Component;
import ravenNPlus.client.clickgui.RavenNPlus.components.ModuleComponent;

public abstract class Setting {
   public String settingName;
   public Setting(String name) { this.settingName = name; }
   public String getName() { return this.settingName; }
   public abstract void resetToDefaults();
   public abstract JsonObject getConfigAsJson();
   public abstract String getSettingType();
   public abstract void applyConfigFromJson(JsonObject data);
   public abstract Component createComponent(ModuleComponent moduleComponent);
}