package a.b.module.modules;

import a.b.main.Otaku;
import a.b.module.*;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HUD extends Module {

   public static TickSetting editPosition, dropShadow, alphabeticalSort;
   public static DescriptionSetting colourModeDesc;
   public static SliderSetting colourMode;
   public static Utils.HUD.PositionMode positionMode;
   public static TickSetting clientName, clientAuthor;
   private static int hudX = 5, hudY = 70;
   public static boolean showedError;
   public static final String HUDX_prefix = "HUDX~ ", HUDY_prefix = "HUDY~ ";

   public HUD() {
      super("HUD", ModuleCategory.render);
      this.registerSetting(editPosition = new TickSetting("Edit position", false));
      this.registerSetting(dropShadow = new TickSetting("Drop shadow", true));
      this.registerSetting(alphabeticalSort = new TickSetting("Alphabetical sort", false));
      this.registerSetting(clientName = new TickSetting("Client Name", true));          //added
      this.registerSetting(clientAuthor = new TickSetting("Client Author", false));     //added
      this.registerSetting(colourMode = new SliderSetting("Color Mode: ", 1, 1, 5, 1));
      this.registerSetting(colourModeDesc = new DescriptionSetting("Mode: "+colourMode.getName()));
      showedError = false;
   }

   public void guiUpdate() { colourModeDesc.setDesc( Utils.md + ColourModes.values()[(int) colourMode.getInput()-1]); }
   public void onEnable() { Otaku.moduleManager.sort(); }
   public void guiButtonToggled(TickSetting b) { if (b == editPosition) { editPosition.disable();
      mc.displayGuiScreen(new EditHudPositionScreen());
   } else if (b == alphabeticalSort) { Otaku.moduleManager.sort(); } }

   @SubscribeEvent
   public void a(RenderTickEvent ev) { if (ev.phase == Phase.END && Utils.Player.isPlayerInGame()) {
         if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) { return; }
         int margin = 2, y = hudY, del = 0;
         if (!alphabeticalSort.isToggled()){
            if (positionMode == Utils.HUD.PositionMode.UPLEFT || positionMode == Utils.HUD.PositionMode.UPRIGHT) { Otaku.moduleManager.sortShortLong(); }
            else if(positionMode == Utils.HUD.PositionMode.DOWNLEFT || positionMode == Utils.HUD.PositionMode.DOWNRIGHT) { Otaku.moduleManager.sortLongShort(); }
         }

       if(clientName.isToggled()) {
           mc.fontRendererObj.drawStringWithShadow("Otaku b1.3", 5, 5, Utils.Client.rainbowDraw(2L, 1500L));
       }

       if(clientAuthor.isToggled()) {
           GlStateManager.pushMatrix();
           GlStateManager.scale(0.8, 0.8, 0.8);
           mc.fontRendererObj.drawStringWithShadow("by SleepyFish", 13, 18, Utils.Client.rainbowDraw(2L, 1500L));
           GlStateManager.popMatrix();
       }

         List<Module> en = new ArrayList<>(Otaku.moduleManager.getModules()); if(en.isEmpty()) return;
         int textBoxWidth = Otaku.moduleManager.getLongestActiveModule(mc.fontRendererObj);
         int textBoxHeight = Otaku.moduleManager.getBoxHeight(mc.fontRendererObj, margin);
         if(hudX < 0) { hudX = margin; }
         if(hudY < 0) { { hudY = margin; } }
         if(hudX + textBoxWidth > mc.displayWidth/2) { hudX = mc.displayWidth/2 - textBoxWidth - margin; }
         if(hudY + textBoxHeight > mc.displayHeight/2) { hudY = mc.displayHeight/2 - textBoxHeight; }

         for (Module m : en) { if (m.isEnabled() && m != this) {

               if (HUD.positionMode == Utils.HUD.PositionMode.DOWNRIGHT || HUD.positionMode == Utils.HUD.PositionMode.UPRIGHT) {
                  if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVENB) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y,
                             Utils.Client.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVENB2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y,
                             Utils.Client.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 10;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y,
                             Utils.Client.astolfoColorsDraw(10, 14), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y,
                             Utils.Client.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120;
                  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO3) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y,
                             Utils.Client.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 10; }
               } else { if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVENB) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Utils.Client.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120; } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.RAVENB2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Utils.Client.rainbowDraw(2L, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 10;  } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Utils.Client.astolfoColorsDraw(10, 14), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120; } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO2) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Utils.Client.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 120; } else if (ColourModes.values()[(int) colourMode.getInput() - 1] == ColourModes.ASTOLFO3) {
                     mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Utils.Client.astolfoColorsDraw(10, del), dropShadow.isToggled());
                     y += mc.fontRendererObj.FONT_HEIGHT + margin; del -= 10;
                  }
               }
            }
         }
      }
   }

   static class EditHudPositionScreen extends GuiScreen {
      final String hudTextExample = "Example-HUD"; GuiButtonExt resetPosButton; ScaledResolution sr;
      boolean mouseDown = false; int textBoxStartX = 0, textBoxStartY = 0, textBoxEndX = 0, textBoxEndY = 0;
      int marginX = 5, marginY = 70, lastMousePosX = 0, lastMousePosY = 0, sessionMousePosX = 0, sessionMousePosY = 0;

      public void initGui() {
         super.initGui();
         this.buttonList.add(this.resetPosButton = new GuiButtonExt(1, this.width - 90, 5, 85, 20, "Reset Position"));
         this.marginX = HUD.hudX; this.marginY = HUD.hudY; sr = new ScaledResolution(mc);
         HUD.positionMode = Utils.HUD.getPostitionMode(marginX, marginY, sr.getScaledWidth(), sr.getScaledHeight());
      }

      public void drawScreen(int mX, int mY, float pt) {
         drawRect(0, 0, this.width, this.height, -1308622848);
         drawRect(0, this.height /2, this.width, this.height /2 + 1, 0x9936393f);
         drawRect(this.width /2, 0, this.width /2 + 1, this.height, 0x9936393f);
         int textBoxStartX = this.marginX; int textBoxStartY = this.marginY; int textBoxEndX = textBoxStartX + 50; int textBoxEndY = textBoxStartY + 32;
         this.drawArrayList(this.mc.fontRendererObj, this.hudTextExample); this.textBoxStartX = textBoxStartX; this.textBoxStartY = textBoxStartY;
         this.textBoxEndX = textBoxEndX; this.textBoxEndY = textBoxEndY; HUD.hudX = textBoxStartX; HUD.hudY = textBoxStartY;
         ScaledResolution res = new ScaledResolution(this.mc); int descriptionOffsetX = res.getScaledWidth() / 2 - 84;
         int descriptionOffsetY = res.getScaledHeight() / 2 - 20;
         Utils.HUD.drawColouredText("Edit the Position by Dragging.", '-', descriptionOffsetX, descriptionOffsetY, 2L, 0L, true, this.mc.fontRendererObj);
         try { this.handleInput(); } catch (IOException ignored) {  } super.drawScreen(mX, mY, pt);
      }

      private void drawArrayList(FontRenderer fr, String t) {
         int x = this.textBoxStartX, gap = this.textBoxEndX - this.textBoxStartX, y = this.textBoxStartY;
         double marginY = fr.FONT_HEIGHT + 2; String[] var4 = t.split("-");
         ArrayList<String> var5 = Utils.Java.toArrayList(var4);
         if (HUD.positionMode == Utils.HUD.PositionMode.UPLEFT || HUD.positionMode == Utils.HUD.PositionMode.UPRIGHT) {
            var5.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2) - Utils.mc.fontRendererObj.getStringWidth(o1));
         } else if(HUD.positionMode == Utils.HUD.PositionMode.DOWNLEFT || HUD.positionMode == Utils.HUD.PositionMode.DOWNRIGHT) {
            var5.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2)));
         } if(HUD.positionMode == Utils.HUD.PositionMode.DOWNRIGHT || HUD.positionMode == Utils.HUD.PositionMode.UPRIGHT) { for (String s : var5) {
               fr.drawString(s, (float) x + (gap - fr.getStringWidth(s)), (float) y, Color.white.getRGB(), HUD.dropShadow.isToggled()); y += marginY;
         } } else { for (String s : var5) { fr.drawString(s, (float) x, (float) y, Color.white.getRGB(), HUD.dropShadow.isToggled()); y += marginY; } } }

      protected void mouseClickMove(int mousePosX, int mousePosY, int clickedMouseButton, long timeSinceLastClick) {
         super.mouseClickMove(mousePosX, mousePosY, clickedMouseButton, timeSinceLastClick);
         if (clickedMouseButton == 0) { if (this.mouseDown) {
               this.marginX = this.lastMousePosX + (mousePosX - this.sessionMousePosX); this.marginY = this.lastMousePosY + (mousePosY - this.sessionMousePosY);
               sr = new ScaledResolution(mc); HUD.positionMode = Utils.HUD.getPostitionMode(marginX, marginY,sr.getScaledWidth(), sr.getScaledHeight());

               //in the else if statement, we check if the mouse is clicked AND inside the "text box"
            } else if (mousePosX > this.textBoxStartX && mousePosX < this.textBoxEndX && mousePosY > this.textBoxStartY && mousePosY < this.textBoxEndY) {
               this.mouseDown = true; this.sessionMousePosX = mousePosX; this.sessionMousePosY = mousePosY; this.lastMousePosX = this.marginX; this.lastMousePosY = this.marginY;
            }
         }
      }

      protected void mouseReleased(int mX, int mY, int state) { super.mouseReleased(mX, mY, state); if (state == 0) { this.mouseDown = false; } }
      public void actionPerformed(GuiButton b) { if (b == this.resetPosButton) { this.marginX = HUD.hudX = 5; this.marginY = HUD.hudY = 70; } }
      public boolean doesGuiPauseGame() { return false; }
   }

   public enum ColourModes { RAVENB, RAVENB2, ASTOLFO, ASTOLFO2, ASTOLFO3, KOPAMED; }
   public static int getHudX() { return hudX; }
   public static int getHudY() { return hudY; }
   public static void setHudX(int hudX) { HUD.hudX = hudX; }
   public static void setHudY(int hudY) { HUD.hudY = hudY; }
}