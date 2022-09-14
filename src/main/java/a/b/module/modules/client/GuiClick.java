package a.b.module.modules.client;

import a.b.clickgui.otaku.components.CategoryComponent;
import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.ImageButton;
import a.b.utils.Utils;
import java.util.ArrayList;

public class GuiClick extends Module {

   public static final int bind = 54;
   public static SliderSetting guiTheme, backgroundOpacity, roundedPerc;
   public static DescriptionSetting guiThemeDesc;
   public static TickSetting categoryBackground, cleanUp, rounded;
   public static TickSetting showPlayer, blur, monkeee, backGround, time, notifications;

   public GuiClick() {
      super("ClickGUI", ModuleCategory.client);
      withKeycode(bind);

      this.registerSetting(guiTheme = new SliderSetting("Theme", 3.0D, 1.0D, 4.0D, 1.0D));
      this.registerSetting(guiThemeDesc = new DescriptionSetting(Utils.md + "b1.3"));
  //  this.registerSetting(sounds = new TickSetting("Sounds", true));
      this.registerSetting(cleanUp = new TickSetting("Clean", false)); //code by Kv
      this.registerSetting(categoryBackground = new TickSetting("Category Background", true));
      this.registerSetting(showPlayer = new TickSetting("Show Player", true));
      this.registerSetting(blur = new TickSetting("Blur", false));
      this.registerSetting(monkeee = new TickSetting("Discord Monke icon", false));
      this.registerSetting(backGround = new TickSetting("Background", true));
      this.registerSetting(time = new TickSetting("Clock", true));
      this.registerSetting(notifications = new TickSetting("Notifications", true));
      this.registerSetting(backgroundOpacity = new SliderSetting("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(rounded = new TickSetting("Rounded Corners", true));
      this.registerSetting(roundedPerc = new SliderSetting("Rounded Corners %", 5, 1, 100, 1));
   }

   @Override
   public void guiButtonToggled(TickSetting setting) {
      if(setting == cleanUp) {
         cleanUp.disable();
         for(CategoryComponent cc : Otaku.clickGui.getCategoryList()) {
            cc.setX((cc.getX()/50*50) + (cc.getX() % 50 > 25 ? 50:0 ));
            cc.setY((cc.getY()/50*50) + (cc.getY() % 50 > 25 ? 50:0 ));
         }
      }
   }

   public void onEnable() {
      if (Utils.Player.isPlayerInGame() && mc.currentScreen != Otaku.clickGui) {
         mc.displayGuiScreen(Otaku.clickGui);
            Otaku.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int) guiTheme.getInput()) {
      case 1:
         guiThemeDesc.setDesc(Utils.md + "b" + 1);
         break;
      case 2:
         guiThemeDesc.setDesc(Utils.md + "b" + 2);
         break;
      case 3:
         guiThemeDesc.setDesc(Utils.md + "b" + 3);
         break;
      case 4:
         guiThemeDesc.setDesc(Utils.md + "b+");
         break;
      }
   }

}