package ravenNPlus.client.module.modules.client;

import ravenNPlus.client.clickgui.RavenNPlus.components.CategoryComponent;
import ravenNPlus.client.main.Client;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;

public class GuiClick extends Module {

   public static final int bind = 54;
   public static TickSetting showPlayer, blur, monkeee, backGround, time, date, notifications, cleanUp;
   public static DescriptionSetting guiThemeDesc;
   public static SliderSetting guiTheme;

   public GuiClick() {
      super("ClickGUI", ModuleCategory.client, "");
      withKeycode(bind);

      this.addSetting(guiTheme = new SliderSetting("Theme", 4, 1, 7, 1));
      this.addSetting(cleanUp = new TickSetting("Clean", false));
      this.addSetting(guiThemeDesc = new DescriptionSetting(Utils.th+""));
      this.addSetting(showPlayer = new TickSetting("Show Player", true));
      this.addSetting(blur = new TickSetting("Blur", true));
      this.addSetting(monkeee = new TickSetting("Discord Monke icon", false));
      this.addSetting(backGround = new TickSetting("Background", false));
      this.addSetting(date = new TickSetting("Date", true));
      this.addSetting(time = new TickSetting("Clock", true));
      this.addSetting(notifications = new TickSetting("Notifications", true));
   }

   @Override
   public void guiButtonToggled(TickSetting setting) {
      if(setting == cleanUp) {
         cleanUp.disable();
         for(CategoryComponent cc : Client.clickGui.getCategoryList()) {
            cc.setX((cc.getX()/50*50) + (cc.getX() % 50 > 25 ? 50:0 ));
            cc.setY((cc.getY()/50*50) + (cc.getY() % 50 > 25 ? 50:0 ));
         }
      }
   }

   public void onEnable() {
      if(Utils.Player.isPlayerInGame() && mc.currentScreen != Client.clickGui) {
         mc.displayGuiScreen(Client.clickGui);
            Client.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int) guiTheme.getValue()) {
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