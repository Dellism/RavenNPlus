package ravenNPlus.b.module.modules.client;

import ravenNPlus.b.clickgui.RavenNPlus.components.CategoryComponent;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;

public class GuiClick extends Module {

   public static final int bind = 54;
   public static SliderSetting guiTheme, backgroundOpacity, roundedPerc;
   public static DescriptionSetting guiThemeDesc;
   public static TickSetting categoryBackground, cleanUp, rounded, moduleDescriptions;
   public static TickSetting showPlayer, blur, monkeee, backGround, time, notifications, sounds;

   public GuiClick() {
      super("ClickGUI", ModuleCategory.client, "");
      withKeycode(bind);

      this.addSetting(guiTheme = new SliderSetting("Theme", 4, 1, 7, 1));
      this.addSetting(guiThemeDesc = new DescriptionSetting(Utils.th+""));
      this.addSetting(moduleDescriptions = new TickSetting("Module Descriptions", true));
      this.addSetting(sounds = new TickSetting("Sounds", true));
      this.addSetting(cleanUp = new TickSetting("Clean", false)); //code by Kv
      this.addSetting(categoryBackground = new TickSetting("Category Background", false));
      this.addSetting(showPlayer = new TickSetting("Show Player", true));
      this.addSetting(blur = new TickSetting("Blur", true));
      this.addSetting(monkeee = new TickSetting("Discord Monke icon", false));
      this.addSetting(backGround = new TickSetting("Background", false));
      this.addSetting(time = new TickSetting("Clock", true));
      this.addSetting(notifications = new TickSetting("Notifications", true));
      this.addSetting(backgroundOpacity = new SliderSetting("Background Opacity %", 45, 0, 100, 1));
      this.addSetting(rounded = new TickSetting("Rounded Corners", true));
      this.addSetting(roundedPerc = new SliderSetting("Rounded Corners %", 8, 1, 90, 1));
   }

   @Override
   public void guiButtonToggled(TickSetting setting) {
      if(setting == cleanUp) {
         cleanUp.disable();
         for(CategoryComponent cc : RavenNPlus.clickGui.getCategoryList()) {
            cc.setX((cc.getX()/50*50) + (cc.getX() % 50 > 25 ? 50:0 ));
            cc.setY((cc.getY()/50*50) + (cc.getY() % 50 > 25 ? 50:0 ));
         }
      }
   }

   public void onEnable() {
      if(Utils.Player.isPlayerInGame() && mc.currentScreen != RavenNPlus.clickGui) {
         mc.displayGuiScreen(RavenNPlus.clickGui);
            RavenNPlus.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int) guiTheme.getInput()) {
         case 1:
            guiThemeDesc.setDesc(Utils.th + "b1");
            break;
         case 2:
            guiThemeDesc.setDesc(Utils.th + "b2");
            break;
         case 3:
            guiThemeDesc.setDesc(Utils.th + "b3");
            break;
         case 4:
         guiThemeDesc.setDesc(Utils.th + "b+");
            break;
         case 5:
            guiThemeDesc.setDesc(Utils.th + "N+");
            break;
         case 6:
            guiThemeDesc.setDesc(Utils.th + "Vape");
            break;
         case 7:
            guiThemeDesc.setDesc(Utils.th + "NoModulesFake");
            break;
      }
   }

}