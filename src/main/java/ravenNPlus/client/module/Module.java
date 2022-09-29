package ravenNPlus.client.module;

import ravenNPlus.client.module.modules.client.CategorySett;
import ravenNPlus.client.utils.SoundUtil;
import ravenNPlus.client.utils.notifications.Render;
import com.google.gson.JsonObject;
import ravenNPlus.client.module.setting.Setting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;

public abstract class Module {

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

   private boolean playSound() {
      String i = moduleName;

      return
              i.equals("Update") || i.equals("Module Settings") || i.equals("Category Settings")
              || i.equals("BurstClicker") || i.equals("Fake Chat") || i.equals("Trajectories")
              || i.equals("VClip") || i.equals("Stop Motion") || i.equals("Self Destruct")
              || i.equals("Weapon") || i.equals("Ladders") || i.equals("SlyPort")
              || i.equals("Healing") || i.equals("Armour") || i.equals("Pearl")
              || i.equals("Blocks") || i.equals("ClickGUI")
      ;
   }

   public Module(String name, ModuleCategory moduleCategory, String description) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
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
      } catch (NullPointerException ignored) {  }
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

   public boolean canBeEnabled() {
      return true;
   }

   public void enable() {
      boolean oldState = this.enabled;
      this.enabled = true;
      this.onEnable();
      MinecraftForge.EVENT_BUS.register(this);
      Render.change(this);

      if(CategorySett.sounds.isToggled()) {
         if(playSound()) return;

         if(CategorySett.enable_mode.getValue() == 1)
            SoundUtil.play(SoundUtil.click1, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 2)
            SoundUtil.play(SoundUtil.bowhit, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 3)
            SoundUtil.play(SoundUtil.playerHurt, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 4)
            SoundUtil.play(SoundUtil.playerDie, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 5)
            SoundUtil.play(SoundUtil.chestOpen, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 6)
            SoundUtil.play(SoundUtil.chestClose, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());

         if(CategorySett.enable_mode.getValue() == 7)
            SoundUtil.play(SoundUtil.tntExplosion, (float) CategorySett.enable_volume.getValue(), (float) CategorySett.enable_pitch.getValue());
      }
   }

   public void disable() {
      boolean oldState = this.enabled;
      this.enabled = false;
      this.onDisable();
      MinecraftForge.EVENT_BUS.unregister(this);
      Render.change(this);

      if(CategorySett.sounds.isToggled()) {
         if(playSound()) return;

         if(CategorySett.disable_mode.getValue() == 1)
            SoundUtil.play(SoundUtil.click1, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 2)
            SoundUtil.play(SoundUtil.bowhit, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 3)
            SoundUtil.play(SoundUtil.playerHurt, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 4)
            SoundUtil.play(SoundUtil.playerDie, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 5)
            SoundUtil.play(SoundUtil.chestOpen, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 6)
            SoundUtil.play(SoundUtil.chestClose, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());

         if(CategorySett.disable_mode.getValue() == 7)
            SoundUtil.play(SoundUtil.tntExplosion, (float) CategorySett.disable_volume.getValue(), (float) CategorySett.disable_pitch.getValue());
      }
   }

   public void setToggled(boolean enabled) {
      if(enabled){
         enable();
      } else{
         disable();
      }
   }

   public String getName() {
      return this.moduleName;
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

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

   public void update() {  }
   public void guiUpdate() {  }
   public void guiButtonToggled(TickSetting b) {  }

   public int getKeycode() {
      return this.keycode;
   }

   public void setbind(int keybind) {
      this.keycode = keybind;
   }

   public void resetToDefaults() {
      this.keycode = defualtKeyCode;
      this.setToggled(defaultEnabled);
      for(Setting setting : this.settings){
         setting.resetToDefaults();
      }
   }

   public void onGuiClose() {  }
   public String getBindAsString() { return keycode == 0 ? "None" : Keyboard.getKeyName(keycode); }
   public void clearBinds() { this.keycode = 0; }

    public enum ModuleCategory {
      combat, movement,
      player, render,
      minigame, other,
      client, hotkey
   }

}