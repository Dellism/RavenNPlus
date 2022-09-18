package ravenNPlus.b.clickgui.RavenNPlus;

import ravenNPlus.b.clickgui.RavenNPlus.components.CategoryComponent;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.modules.client.GuiClick;
import ravenNPlus.b.utils.*;
import ravenNPlus.b.utils.version.Version;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.lwjgl.input.Mouse;

public class ClickGui extends GuiScreen {

   private ScheduledFuture<?> sf;
   private Timer aT, aL, aE,  aR;
   protected boolean allowBlur = false;
   private final  ArrayList<CategoryComponent> categoryList;
   public final CommandPrompt commandPrompt;
   private int mouseX, mouseY;

   public ClickGui() {
      this.commandPrompt = new CommandPrompt();
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

      commandPrompt.setLocation(5, topOffset);
      commandPrompt.setSize((int) (92 * 1.5), (int) ((92 * 1.5) * 0.75));
   }

   public void initMain() {
      (this.aT = this.aE = this.aR = new Timer(500.0F)).start();
      this.sf = RavenNPlus.getExecutor().schedule(() -> (
              this.aL = new Timer(650.0F)
      ).start(), 650L, TimeUnit.MILLISECONDS);
   }

   public void initGui() { super.initGui(); }

   public void drawScreen(int x, int y, float p) {
      mouseX = x; mouseY = y;
      Version clientVersion = RavenNPlus.versionManager.getClientVersion();
      Version latestVersion = RavenNPlus.versionManager.getLatestVersion();

      //background
      if(GuiClick.backGround.isToggled()) {
         drawRect(0, 0, this.width, this.height, (int)(this.aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);
      }

      int sH = this.height / 4;
      int sW = this.width / 2;
      int w_c = 30 - this.aT.getValueInt(0, 30, 3);

      /*
      this.drawCenteredString(this.fontRendererObj, "r", sW + 1 - w_c, sH - 23 + off , Utils.Client.rainbowDraw(2L, 900L+300L));
      this.drawCenteredString(this.fontRendererObj, "a", sW - w_c, sH - 13 + off , Utils.Client.rainbowDraw(2L, 900L+150L));
      this.drawCenteredString(this.fontRendererObj, "v", sW - w_c, sH - 4  + off , Utils.Client.rainbowDraw(2L, 900L));
      this.drawCenteredString(this.fontRendererObj, "e", sW - w_c, sH + 7  + off , Utils.Client.rainbowDraw(2L, 900L-150L));
      this.drawCenteredString(this.fontRendererObj, "n", sW - w_c, sH + 17 + off , Utils.Client.rainbowDraw(2L, 900L-300L));
      */
      this.drawCenteredString(this.fontRendererObj, "N+", sW +1- w_c, sH + 4 , Utils.Client.rainbowDraw(2L, 900L-300L));

      float speed = 4890;

      if(latestVersion.isNewerThan(clientVersion)) {
         int margin = 2;
         int rows = 1;
         for (int i = RavenNPlus.updateText.length-1; i >= 0; i--) {
            String up = RavenNPlus.updateText[i];
            mc.fontRendererObj.drawString(up, sW - this.fontRendererObj.getStringWidth(up) / 2, this.height - this.fontRendererObj.FONT_HEIGHT * rows - margin, Utils.Client.astolfoColorsDraw(10, 28, speed));
            rows++;
            margin += 2;
         }
      } else {
         mc.fontRendererObj.drawStringWithShadow("["+RavenNPlus.name+ " | Config: " + RavenNPlus.configManager.getConfig().getName()+"]", 4, this.height - 3 - mc.fontRendererObj.FONT_HEIGHT, Utils.Client.astolfoColorsDraw(10, 14, speed));
      }

      int a = 10;  //30
      int b = 10;  //10
      int c = 23;  //43

      this.drawVerticalLine(sW - b - w_c, sH - a, sH + c, Utils.Client.rainbowDraw(2L, 900L));
      this.drawVerticalLine(sW + b + w_c, sH - a, sH + c, Utils.Client.rainbowDraw(2L, 900L));

      int animationProggress;
      if (this.aL != null) {
         animationProggress = this.aL.getValueInt(0, 20, 2);

         this.drawHorizontalLine(sW - b, sW - b + animationProggress, sH - a-1, Utils.Client.rainbowDraw(2L, 900L));
         this.drawHorizontalLine(sW + b, sW + b - animationProggress, sH + c+1, Utils.Client.rainbowDraw(2L, 900L));
      }

      for (CategoryComponent category : categoryList) {
         category.rf(this.fontRendererObj);
         category.up(x, y);

         for (Component module : category.getModules()) {
            module.update(x, y);
         }
      }

      if(GuiClick.time.isToggled()) {
         if(!Utils.Player.isPlayerInGame()) return;

         mc.fontRendererObj.drawStringWithShadow(Calendar.getInstance().getTime().getHours()
                 + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds(), 10, 5, Utils.Client.rainbowDraw(2L, 900L));
      }

      //RoundedUtils.drawImage(RavenNPlus, this.mouseX-5, this.mouseY-10, 80, 60);
      ScaledResolution sr = new ScaledResolution(mc);
      //RenderUtils.draw2DImage(RenderUtils.icon, sr.getScaledWidth()-50, sr.getScaledHeight()+50, 60, 40, Color.white);

      if(GuiClick.time.isToggled())
            Utils.HUD.fontRender.drawString("", 0, 0, 0xFFFFFFFF);

      if(GuiClick.showPlayer.isToggled())
         GuiInventory.drawEntityOnScreen(this.width + 15 - this.aE.getValueInt(0, 40, 2), this.height - 19 - this.fontRendererObj.FONT_HEIGHT, 40, (float) (this.width - 25 - x), (float) (this.height - 50 - y), this.mc.thePlayer);

      commandPrompt.update(x, y);
      commandPrompt.draw();

      if(GuiClick.blur.isToggled() && allowBlur) {
         BlurUtil.blur(true);
         allowBlur = false;
      }
   }

   public void mouseClicked(int x, int y, int mouseButton) throws IOException {
      Iterator<CategoryComponent> btnCat = categoryList.iterator();

      commandPrompt.mouseDown(x, y, mouseButton);
      if(commandPrompt.overPosition(x, y)) return;

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
                  if(GuiClick.sounds.isToggled())
                     SoundUtil.play(SoundUtil.click1, 0.5F, 0.6F);
               }
            } while(!category.isOpened());
         } while(category.getModules().isEmpty());

         for (Component c : category.getModules()) {
            c.mouseDown(x, y, mouseButton);
         }
      }
   }

   public void mouseReleased(int x, int y, int s) {
      commandPrompt.mouseReleased(x, y, s);
      if(commandPrompt.overPosition(x, y)) return;

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
      if(RavenNPlus.clientConfig != null){
         RavenNPlus.clientConfig.saveConfig();
      }
   }

   public void keyTyped(char t, int k) {
      commandPrompt.keyTyped(t, k);
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

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      for (CategoryComponent c : visableCategoryList()) {
         if (c.insideAllArea(mouseX, mouseY)) {
            int i = Mouse.getEventDWheel();
            i = Integer.compare(i, 0);
            c.scroll(i * 5f);
         }
      }
   }

   public void onGuiClosed() {
      this.aL = null;

      if (this.sf != null) {
         this.sf.cancel(true);
         this.sf = null;
      }

      RavenNPlus.configManager.save();
      RavenNPlus.clientConfig.saveConfig();

      if(GuiClick.blur.isToggled() && !allowBlur) {
         BlurUtil.blur(false);
         allowBlur = true;
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public ArrayList<CategoryComponent> getCategoryList() {
      return categoryList;
   }

   public ArrayList<CategoryComponent> visableCategoryList() {
      ArrayList<CategoryComponent> newList = (ArrayList<CategoryComponent>) categoryList.clone();
      newList.removeIf(obj -> !obj.isVisable());
      return newList;
   }

}