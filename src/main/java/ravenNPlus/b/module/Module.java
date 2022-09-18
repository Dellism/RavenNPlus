package ravenNPlus.b.module;

import ravenNPlus.b.module.modules.client.GuiClick;
import ravenNPlus.b.utils.SoundUtil;
import ravenNPlus.b.utils.notifications.Render;
import com.google.gson.JsonObject;
import ravenNPlus.b.module.setting.Setting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;

public class Module {

   protected ArrayList<Setting> settings;
   private final String moduleName;
   private final ModuleCategory moduleCategory;
   protected boolean enabled = false;
   protected boolean showInHud = true;
   protected boolean defaultEnabled = enabled;
   protected int keycode = 0;
   protected int defualtKeyCode = keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;
   private static String description = "";

   public Module(String name, ModuleCategory moduleCategory, String description) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      Module.description = description;
      this.settings = new ArrayList<>();
      mc = Minecraft.getMinecraft();
   }

   protected <E extends Module> E withKeycode(int i){
      this.keycode = i;
      this.defualtKeyCode = i;
      return (E) this;
   }

   protected  <E extends Module> E withEnabled(boolean i){
      this.enabled = i;
      this.defaultEnabled = i;
      try{
         setToggled(i);
      } catch (Exception e){}
      return (E) this;
   }

   public static String getDesc() {
      return description;
   }

   public static void setDesc(String desc) {
      description = desc;
   }

   public JsonObject getConfigAsJson(){
      JsonObject settings = new JsonObject();
      for(Setting setting : this.settings){
         JsonObject settingData = setting.getConfigAsJson();
         settings.add(setting.settingName, settingData);
      }

      JsonObject data = new JsonObject();
      data.addProperty("enabled", enabled);
      data.addProperty("keycode", keycode);
      data.addProperty("showInHud", this.showInHud);
      data.add("settings", settings);
      return data;
   }

   public void applyConfigFromJson(JsonObject data) {
      try {
         this.keycode = data.get("keycode").getAsInt();
         setToggled(data.get("enabled").getAsBoolean());
         JsonObject settingsData = data.get("settings").getAsJsonObject();
         for (Setting setting : getSettings()) {
            if (settingsData.has(setting.getName())) {
               setting.applyConfigFromJson(
                       settingsData.get(setting.getName()).getAsJsonObject()
               );
            }
         }

         this.showInHud = data.get("showInHud").getAsBoolean();
      } catch (NullPointerException ignored) {
      }
   }

   public void keybind() {
      if (this.keycode != 0 && this.canBeEnabled()) {
         if (!this.isToggled && Keyboard.isKeyDown(this.keycode)) {
            this.toggle();
            this.isToggled = true;
         } else if (!Keyboard.isKeyDown(this.keycode)) {
            this.isToggled = false;
         }
      }
   }

   public boolean showInHud() {
      return this.showInHud;
   }
   public void setVisableInHud(boolean vis) {
      this.showInHud = vis;
   }
   public boolean canBeEnabled() { return true; }

   public void enable() {
      boolean oldState = this.enabled;
      this.enabled = true;
      this.onEnable();
      MinecraftForge.EVENT_BUS.register(this);
      Render.change(this);

      if(GuiClick.sounds.isToggled()) {
         if(moduleName.equals("ClickGUI")) return;
         if(moduleName.equals("GuiClick")) return;

         SoundUtil.play(SoundUtil.click1, 0.5F, 0.8F);
      }
   }

   public void disable() {
      boolean oldState = this.enabled;
      this.enabled = false;
      this.onDisable();
      MinecraftForge.EVENT_BUS.unregister(this);
      Render.change(this);

      if(GuiClick.sounds.isToggled()) {
         if(moduleName.equals("ClickGUI")) return;
         if(moduleName.equals("GuiClick")) return;

         SoundUtil.play(SoundUtil.click1, 0.5F, 0.8F);
      }
   }

   public void setToggled(boolean enabled) {
      if(enabled){
         enable();
      } else{
         disable();
      }
   }

   public String getName() { return this.moduleName; }

   public ArrayList<Setting> getSettings() { return this.settings; }

   public Setting getSettingByName(String name) {
      for (Setting setting : this.settings) {
         if (setting.getName().equalsIgnoreCase(name))
            return setting;
      }
      return null;
   }

   public void addSetting(Setting Setting) { this.settings.add(Setting); }
   public ModuleCategory moduleCategory() { return this.moduleCategory; }
   public boolean isEnabled() { return this.enabled; }
   public void onEnable() { }
   public void onDisable() { }

   public void toggle() {
      if (this.enabled) {
         this.disable();
      } else {
         this.enable();
      }
   }

   public void update() { }
   public void guiUpdate() { }
   public void guiButtonToggled(TickSetting b) { }
   public int getKeycode() { return this.keycode; }
   public void setbind(int keybind) { this.keycode = keybind; }

   public void resetToDefaults() {
      this.keycode = defualtKeyCode;
      this.setToggled(defaultEnabled);
      for(Setting setting : this.settings){
         setting.resetToDefaults();
      }
   }

   public void onGuiClose() { }
   public String getBindAsString() { return keycode == 0 ? "None" : Keyboard.getKeyName(keycode); }
   public void clearBinds() { this.keycode = 0; }

   public enum ModuleCategory {
      combat, movement,
      player, render,
      minigame, other,
      client, hotkey
   }

}