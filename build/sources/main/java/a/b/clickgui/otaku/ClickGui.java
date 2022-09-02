package a.b.clickgui.otaku;

import a.b.clickgui.otaku.components.CategoryComponent;
import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.modules.HUD;
import a.b.module.modules.client.GuiModule;
import a.b.utils.BlurUtil;
import a.b.utils.Timer;
import a.b.utils.Utils;
import a.b.utils.notifications.Notification;
import a.b.utils.notifications.NotificationManager;
import a.b.utils.notifications.NotificationType;
import a.b.utils.version.Version;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClickGui extends GuiScreen {

   private ScheduledFuture<?> sf;
   private Timer aT, aL, aE,  aR;
   private final  ArrayList<CategoryComponent> categoryList;
   public final CommandPrompt terminal;

   public ClickGui() {
      this.terminal = new CommandPrompt();
      this.categoryList = new ArrayList<>();
      int topOffset = 5;
      Module.ModuleCategory[] values;
      int categoryAmount = (values = Module.ModuleCategory.values()).length;

      for(int category = 0; category < categoryAmount; ++category) {
         Module.ModuleCategory moduleCategory = values[category];
         CategoryComponent currentModuleCategory = new CategoryComponent(moduleCategory);
         currentModuleCategory.setY(topOffset);
         categoryList.add(currentModuleCategory);
         topOffset += 20;
      }

      terminal.setLocation(5, topOffset);
      terminal.setSize((int) (92 * 1.5), (int) ((92 * 1.5) * 0.75));
   }

   public void initMain() {
      (this.aT = this.aE = this.aR = new Timer(500.0F)).start();
      this.sf = Otaku.getExecutor().schedule(() -> (
              this.aL = new Timer(650.0F)
      ).start(), 650L, TimeUnit.MILLISECONDS);
   }

   public void initGui() { super.initGui(); }

   public void drawScreen(int x, int y, float p) {
      Version clientVersion = Otaku.versionManager.getClientVersion();
      Version latestVersion = Otaku.versionManager.getLatestVersion();

      if(GuiModule.backGround.isToggled() && !GuiModule.blur.isToggled()) {
         drawRect(0, 0, this.width, this.height, (int)(this.aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);
      }

      int sH = this.height / 4;
      int sW = this.width / 2;
      int off = 5;
      int w_c = 30 - this.aT.getValueInt(0, 30, 3);
      this.drawCenteredString(this.fontRendererObj, "o", sW + 1 - w_c, sH - 23 + off , Utils.Client.rainbowDraw(2L, 1500L));
      this.drawCenteredString(this.fontRendererObj, "t", sW - w_c, sH - 13 + off , Utils.Client.rainbowDraw(2L, 1200L));
      this.drawCenteredString(this.fontRendererObj, "a", sW - w_c, sH - 4  + off , Utils.Client.rainbowDraw(2L, 900L));
      this.drawCenteredString(this.fontRendererObj, "k", sW - w_c, sH + 7  + off , Utils.Client.rainbowDraw(2L, 600L));
      this.drawCenteredString(this.fontRendererObj, "u", sW - w_c, sH + 17 + off , Utils.Client.rainbowDraw(2L, 300L));

      float speed = 4890;

      if(latestVersion.isNewerThan(clientVersion)){
         int margin = 2;
         int rows = 1;
         for (int i = Otaku.updateText.length-1; i >= 0; i--) {
            String up = Otaku.updateText[i];
            mc.fontRendererObj.drawString(up, sW - this.fontRendererObj.getStringWidth(up) / 2, this.height - this.fontRendererObj.FONT_HEIGHT * rows - margin, Utils.Client.astolfoColorsDraw(10, 28, speed));
            rows++;
            margin += 2;
         }
      } else {
         mc.fontRendererObj.drawStringWithShadow("["+"Otaku " + "b1.3" + " | Config: " + Otaku.configManager.getConfig().getName()+"]", 4, this.height - 3 - mc.fontRendererObj.FONT_HEIGHT, Utils.Client.astolfoColorsDraw(10, 14, speed));
      }

      this.drawVerticalLine(sW - 10 - w_c, sH - 30, sH + 43, Utils.Client.rainbowDraw(2L, 900L));
      this.drawVerticalLine(sW + 10 + w_c, sH - 30, sH + 43, Utils.Client.rainbowDraw(2L, 900L));

      int animationProggress;
      if (this.aL != null) {
         animationProggress = this.aL.getValueInt(0, 20, 2);
         this.drawHorizontalLine(sW - 10, sW - 10 + animationProggress, sH - 29, Utils.Client.rainbowDraw(2L, 900L));
         this.drawHorizontalLine(sW + 10, sW + 10 - animationProggress, sH + 42, Utils.Client.rainbowDraw(2L, 900L));
      }

      for (CategoryComponent category : categoryList) {
         category.rf(this.fontRendererObj);
         category.up(x, y);

         for (Component module : category.getModules()) {
            module.update(x, y);
         }
      }

      if(GuiModule.time.isToggled()) {
         if(!Utils.Player.isPlayerInGame()) return;

         mc.fontRendererObj.drawStringWithShadow(Calendar.getInstance().getTime().getHours()
                 + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds(), this.height - 10, this.width + 5, Utils.Client.rainbowDraw(2L, 900L));
      }

      if(GuiModule.time.isToggled())
            Utils.HUD.fontRender.drawString("", 0, 0, 0xFFFFFFFF);

      if(GuiModule.showPlayer.isToggled())
         GuiInventory.drawEntityOnScreen(this.width + 15 - this.aE.getValueInt(0, 40, 2), this.height - 19 - this.fontRendererObj.FONT_HEIGHT, 40, (float) (this.width - 25 - x), (float) (this.height - 50 - y), this.mc.thePlayer);

      terminal.update(x, y);
      terminal.draw();

      if(GuiModule.blur.isToggled()) {
         BlurUtil.blur(true);
      } else {
         BlurUtil.blur(false);
      }
   }

   public void mouseClicked(int x, int y, int mouseButton) throws IOException {
      Iterator<CategoryComponent> btnCat = categoryList.iterator();

      terminal.mouseDown(x, y, mouseButton);
      if(terminal.overPosition(x, y)) return;

      while(true) {
         CategoryComponent category;
         do {
            do {
               if (!btnCat.hasNext()) {
                  return;
               }

               category = btnCat.next();
               if (category.insideArea(x, y) && !category.i(x, y) && !category.mousePressed(x, y) && mouseButton == 0) {
                  category.mousePressed(true);
                  category.xx = x - category.getX();
                  category.yy = y - category.getY();
               }

               if (category.mousePressed(x, y) && mouseButton == 0) {
                  category.setOpened(!category.isOpened());
               }

               if (category.i(x, y) && mouseButton == 0) {
                  category.cv(!category.p());
               }
            } while(!category.isOpened());
         } while(category.getModules().isEmpty());

         for (Component c : category.getModules()) {
            c.mouseDown(x, y, mouseButton);
         }
      }
   }

   public void mouseReleased(int x, int y, int s) {
      terminal.mouseReleased(x, y, s);
      if(terminal.overPosition(x, y)) return;

      if (s == 0) {
         Iterator<CategoryComponent> btnCat = categoryList.iterator();

         CategoryComponent c4t;
         while(btnCat.hasNext()) {
            c4t = btnCat.next();
            c4t.mousePressed(false);
         }

         btnCat = categoryList.iterator();

         while(true) {
            do {
               do {
                  if (!btnCat.hasNext()) {
                     return;
                  }

                  c4t = btnCat.next();
               } while(!c4t.isOpened());
            } while(c4t.getModules().isEmpty());

            for (Component c : c4t.getModules()) {
               c.mouseReleased(x, y, s);
            }
         }
      }
      if(Otaku.clientConfig != null){
         Otaku.clientConfig.saveConfig();
      }
   }

   public void keyTyped(char t, int k) {
      terminal.keyTyped(t, k);
      if (k == 1) {
         this.mc.displayGuiScreen(null);
      } else {
         Iterator<CategoryComponent> btnCat = categoryList.iterator();

         while(true) {
            CategoryComponent cat;
            do {
               do {
                  if (!btnCat.hasNext()) {
                     return;
                  }

                  cat = btnCat.next();
               } while(!cat.isOpened());
            } while(cat.getModules().isEmpty());

            for (Component c : cat.getModules()) {
               c.keyTyped(t, k);
            }
         }
      }
   }

   public void onGuiClosed() {
      this.aL = null;

      if (this.sf != null) {
         this.sf.cancel(true);
         this.sf = null;
      }

      Otaku.configManager.save();
      Otaku.clientConfig.saveConfig();

      if(GuiModule.blur.isToggled())
         BlurUtil.blur(false);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public ArrayList<CategoryComponent> getCategoryList() {
      return categoryList;
   }
}