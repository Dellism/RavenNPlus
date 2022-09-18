package ravenNPlus.b.clickgui.RavenNPlus.components;

import ravenNPlus.b.clickgui.RavenNPlus.Component;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.modules.client.GuiClick;
import ravenNPlus.b.utils.RenderUtils;
import ravenNPlus.b.utils.RoundedUtils;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import ravenNPlus.b.utils.notifications.Render;

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
      for(Iterator<Module> var3 = RavenNPlus.moduleManager.getModulesInCategory(this.categoryName).iterator(); var3.hasNext(); tY += 16) {
         Module mod = var3.next();
         ModuleComponent b = new ModuleComponent(mod, this, tY, Module.getDesc());
         this.modulesInCategory.add(b);
      }
   }

   public ArrayList<Component> getModules() {
      return this.modulesInCategory;
   }

   public void setX(int n) {
      this.x = n;
      if(RavenNPlus.clientConfig != null){
         RavenNPlus.clientConfig.saveConfig();
      }
   }

   public void setY(int y) {
      this.y = y;
      if(RavenNPlus.clientConfig != null){
         RavenNPlus.clientConfig.saveConfig();
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
      if(RavenNPlus.clientConfig != null){
         RavenNPlus.clientConfig.saveConfig();
      }
   }

   public void rf(FontRenderer renderer) {
      this.width = 92;
      if (!this.modulesInCategory.isEmpty() && this.categoryOpened) {
         categoryHeight = 0;
         Component moduleRenderManager;
         for (Iterator moduleInCategoryIterator = this.modulesInCategory.iterator(); moduleInCategoryIterator.hasNext(); categoryHeight += moduleRenderManager.getHeight()) {
            moduleRenderManager = (Component) moduleInCategoryIterator.next();
         }

         if (GuiClick.rounded.isToggled()) {
            if(GuiClick.guiTheme.getInput() == 6.0D)
               RoundedUtils.drawSmoothRoundedRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4,(float) GuiClick.roundedPerc.getInput(), (new Color(26,25,25, (int) (GuiClick.backgroundOpacity.getInput() / 100 * 255))).getRGB());
            else
               RoundedUtils.drawSmoothRoundedRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4,(float) GuiClick.roundedPerc.getInput(), (new Color(0, 0, 0, (int) (GuiClick.backgroundOpacity.getInput() / 100 * 255))).getRGB());
         } else {
            if(GuiClick.guiTheme.getInput() == 6.0D)
               net.minecraft.client.gui.Gui.drawRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4, (new Color(26,25,25, (int) (GuiClick.backgroundOpacity.getInput() / 100 * 255))).getRGB());
            else
               net.minecraft.client.gui.Gui.drawRect(this.x - 1, this.y, this.x + this.width + 1, this.y + this.bh + categoryHeight + 4, (new Color(0, 0, 0, (int) (GuiClick.backgroundOpacity.getInput() / 100 * 255))).getRGB());
         }
      }

      if(GuiClick.categoryBackground.isToggled())
         TickComponent.renderMain((float)(this.x - 2), (float)this.y, (float)(this.x + this.width + 2), (float)(this.y + this.bh + 3), -1);

      Color c = new Color(92, 92, 92, 255);
      int iconSize = 10;
      int iconOff = 2;

      if(categoryOpened) {
         if(categoryName.name().equals("client"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/client.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("movement"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/move.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("combat"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/combat.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("client"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/client.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("player"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/player.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("hotkey"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/hotkey.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("render"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/render.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("other"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/other.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);

         if(categoryName.name().equals("minigame"))
            RenderUtils.draw2DImageByString("ravenNPlus/icon/modules/mini.png", this.x + iconOff, this.y + iconOff, iconSize, iconSize, c);
      } else {
         RenderUtils.draw2DImage(RenderUtils.stringShadow, this.x+7, this.y, renderer.getStringWidth(categoryName.name())+12, 17, new Color(92, 92, 92, 255));
      }

      int categoryNameOffsett = 15;

      renderer.drawString(this.n4m ? this.pvp : this.categoryName.name(), (float)(this.x + categoryNameOffsett), (float)(this.y + 4), Color.getHSBColor((float)(System.currentTimeMillis() % (7500L / (long)this.chromaSpeed)) / (7500.0F / (float)this.chromaSpeed), 1.0F, 1.0F).getRGB(), false);
      if (!this.n4m) {
         GL11.glPushMatrix();
         //                                         -     + / x -
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
      for(Iterator var2 = this.modulesInCategory.iterator(); var2.hasNext(); o += c.getHeight()) {
         c = (Component)var2.next();
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
      return this.iconX;
   }

   public int getIconY() {
      return this.iconY;
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