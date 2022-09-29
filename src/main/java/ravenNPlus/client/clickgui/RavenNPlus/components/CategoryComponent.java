package ravenNPlus.client.clickgui.RavenNPlus.components;

import ravenNPlus.client.clickgui.RavenNPlus.Component;
import ravenNPlus.client.main.Client;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.modules.client.CategorySett;
import ravenNPlus.client.module.modules.client.GuiClick;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.RoundedUtils;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CategoryComponent {

   public ArrayList<Component> modulesInCategory = new ArrayList<>();
   public Module.ModuleCategory categoryName;
   private boolean categoryOpened;
   private int width;
   private int y;
   private int x;
   private final int bh;
   public boolean inUse;
   public int xx;
   public int yy;
   public static int iconX;
   public static int iconY;
   public int scrollheight;
   private int categoryHeight;
   private boolean visable = true;
   public boolean n4m = false;
   public String pvp;
   public boolean pin = false;
   private final int chromaSpeed;
   private final double marginY, marginX;

   public CategoryComponent(Module.ModuleCategory category) {

      this.categoryName = category;
      this.width = 92;
      this.x = 5;
      this.y = 5;
      this.bh = 13;
      this.xx = 0;
      this.categoryOpened = false;
      this.inUse = false;
      this.chromaSpeed = 3;
      int tY = this.bh + 3;
      this.marginX = 80;
      this.marginY = 4.5;

      for(Iterator<Module> var3 = Client.moduleManager.getModulesInCategory(this.categoryName).iterator(); var3.hasNext(); tY += 16) {
         Module mod = var3.next();
         ModuleComponent b = new ModuleComponent(mod, this, tY, null);
         this.modulesInCategory.add(b);
      }
   }

   public ArrayList<Component> getModules() {
      return this.modulesInCategory;
   }

   public void setX(int n) {
      this.x = n;
      if(Client.clientConfig != null){
         Client.clientConfig.saveConfig();
      }
   }

   public void setY(int y) {
      this.y = y;
      if(Client.clientConfig != null){
         Client.clientConfig.saveConfig();
      }
   }

   public void mousePressed(boolean d) {
      this.inUse = d;
   }

   public boolean p() {
      return this.pin;
   }

   public void cv(boolean on) {
      this.pin = on;
   }

   public boolean isOpened() {
      return this.categoryOpened;
   }

   public void setOpened(boolean on) {
      this.categoryOpened = on;
      if(Client.clientConfig != null){
         Client.clientConfig.saveConfig();
      }
   }

   public void rf(FontRenderer renderer) {
      this.width = 92;
      if (!this.modulesInCategory.isEmpty() && this.categoryOpened) {
         categoryHeight = 0;
         Component moduleRenderManager;
         for (Iterator<Component> moduleInCategoryIterator = this.modulesInCategory.iterator(); moduleInCategoryIterator.hasNext(); categoryHeight += moduleRenderManager.getHeight()) {
            moduleRenderManager = moduleInCategoryIterator.next();
         }

         if (CategorySett.rounded.isToggled()) {
            if(GuiClick.guiTheme.getValue() == 6.0D)
               RoundedUtils.drawSmoothRoundedRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4,(float) CategorySett.roundedPerc.getValue(), (new Color(26,25,25, (int) (CategorySett.backgroundOpacity.getValue() / 100 * 255))).getRGB());
            else
               RoundedUtils.drawSmoothRoundedRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4,(float) CategorySett.roundedPerc.getValue(), (new Color(0, 0, 0, (int) (CategorySett.backgroundOpacity.getValue() / 100 * 255))).getRGB());
         } else {
            if(GuiClick.guiTheme.getValue() == 6.0D)
               net.minecraft.client.gui.Gui.drawRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4, (new Color(26,25,25, (int) (CategorySett.backgroundOpacity.getValue() / 100 * 255))).getRGB());
            else
               net.minecraft.client.gui.Gui.drawRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4, (new Color(0, 0, 0, (int) (CategorySett.backgroundOpacity.getValue() / 100 * 255))).getRGB());
         }
      }

      if(CategorySett.categoryBackground.isToggled())
         TickComponent.renderMain((float)(this.x - 2), (float)this.y, (float)(this.x + this.width + 2), (float)(this.y + this.bh + 3), -1);

      Color c = new Color(92, 92, 92, 255);
//      int iconSize = (int) CategorySett.iconSize.getValue();
//      int iconOffX = (int) CategorySett.xIconOffset.getValue();
//      int iconOffY = (int) CategorySett.yIconOffset.getValue();
      int categoryNameOffsett = (int) CategorySett.TextOffset.getValue();

      /*
      if(categoryOpened) {
         if (CategorySettings.Icon_client.isToggled())
            if (categoryName.name().equals("client"))
               if (CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.client_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/client.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.client_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/client.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if (CategorySettings.Icon_move.isToggled())
            if (categoryName.name().equals("movement"))
               if (CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.move_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/move.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.move_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/move.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if (CategorySettings.Icon_comb.isToggled())
            if (categoryName.name().equals("combat"))
               if (CategorySettings.costum_color.isToggled()) {
                 // renderer.drawString("", 0, 0, CategorySettings.comb_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/combat.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.comb_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/combat.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if (CategorySettings.Icon_player.isToggled())
            if (categoryName.name().equals("player"))
               if (CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.player_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/player.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.player_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/player.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if (CategorySettings.Icon_hotkey.isToggled())
            if (categoryName.name().equals("hotkey"))
               if (CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.hotkey_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/hotkey.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.hotkey_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/hotkey.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if (CategorySettings.Icon_render.isToggled())
            if (categoryName.name().equals("render"))
               if (CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.render_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/render.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.render_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/render.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if(CategorySettings.Icon_other.isToggled())
            if(categoryName.name().equals("other"))
               if(CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.other_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/other.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.other_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/other.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }

         if(CategorySettings.Icon_mini.isToggled())
            if(categoryName.name().equals("minigame"))
               if(CategorySettings.costum_color.isToggled()) {
                  //   renderer.drawString("", 0, 0, CategorySettings.mini_color.getRGB());
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/mini.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, CategorySettings.mini_color);
               } else {
                  RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/mini.png", this.x + iconOffX, this.y + iconOffY, iconSize, iconSize, c);
               }
      } else {

       */
         RenderUtils.draw2DImage(RenderUtils.stringShadow, this.x+categoryNameOffsett, this.y, renderer.getStringWidth(categoryName.name())+6, 17, new Color(92, 92, 92, 255));
      //}


      renderer.drawString(this.n4m ? this.pvp : this.categoryName.name(), (float)(this.x + categoryNameOffsett), (float)(this.y + 4), Color.getHSBColor((float)(System.currentTimeMillis() % (7500L / (long)this.chromaSpeed)) / (7500.0F / (float)this.chromaSpeed), 1.0F, 1.0F).getRGB(), false);


      if (!this.n4m) {
         GL11.glPushMatrix();
         //                                                       - +  |  x -
         renderer.drawString(this.categoryOpened ? "x" : "-", (float)(this.x + marginX), (float)((double)this.y + marginY), Color.white.getRGB(), false);
         GL11.glPopMatrix();
         if (this.categoryOpened && !this.modulesInCategory.isEmpty()) {
            Iterator var5 = this.modulesInCategory.iterator();
            while(var5.hasNext()) {
               Component c2 = (Component)var5.next();
               c2.draw();
            }
         }
      }
   }

   public void r3nd3r() {
      int o = this.bh + 3;
      Component c;
      for(Iterator<Component> var2 = this.modulesInCategory.iterator(); var2.hasNext(); o += c.getHeight()) {
         c = var2.next();
         c.setComponentStartAt(o);
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getIconX() {
      return iconX;
   }

   public int getIconY() {
      return iconY;
   }

   public int getWidth() {
      return this.width;
   }

   public void up(int x, int y) {
      if (this.inUse) {
         this.setX(x - this.xx);
         this.setY(y - this.yy);
      }
   }

   public boolean i(int x, int y) {
      return x >= this.x + 92 - 13 && x <= this.x + this.width && (float)y >= (float)this.y + 2.0F && y <= this.y + this.bh + 1;
   }


   public boolean mousePressed(int x, int y) {
      return x >= this.x + 77
        && x <= this.x + this.width - 6
        && (float) y >= (float) this.y + 2.0F
        && y <= this.y + this.bh + 1;
   }

   public boolean insideArea(int x, int y) {
      return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.bh;
   }

   public String getName() {
      return String.valueOf(modulesInCategory);
   }

   public void setLocation(int parseInt, int parseInt1) {
      this.x = parseInt;
      this.y = parseInt1;
   }

   public int getActualHeight() {
      int h = this.bh + 16 + categoryHeight;
      for(Component c : getModules()) {
         h += c.getHeight();
      }
      return h;
   }

   public boolean insideAllArea(int x, int y) {
      return x >= this.x
        && x <= this.x + this.width
        && y >= this.y
        && y <= this.y + this.getActualHeight() - this.scrollheight;
   }

   public int getHeight() {
      return this.bh;
   }

   public void scroll(float ss) {
      if(ss > 0 || (getActualHeight() + ss) > 100 ) {
         scrollheight += ss;
      }
      if(scrollheight <= 0) {
         r3nd3r();
      } else {
         scrollheight = 0;
      }
   }

   public void setVisable(boolean vis) {
      this.visable = vis;
   }

   public boolean isVisable() {
      return visable;
   }

}