package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.*;
import java.util.Iterator;

public class ChestESP extends Module {

   public static SliderSetting a;
   public static SliderSetting b;
   public static SliderSetting c;
   public static TickSetting d;

   public ChestESP() {
      super("ChestESP", ModuleCategory.render, "Draw a outline around Chests");
      this.addSetting(a = new SliderSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.addSetting(b = new SliderSetting("Green", 0.0D, 0.0D, 255.0D, 1.0D));
      this.addSetting(c = new SliderSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D));
      this.addSetting(d = new TickSetting("Rainbow", false));
   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent ev) {
      if (Utils.Player.isPlayerInGame()) {
         int rgb = d.isToggled() ? Utils.Client.rainbowDraw(2L, 0L) : (new Color((int)a.getValue(), (int)b.getValue(), (int)c.getValue())).getRGB();
         Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

         while(true) {
            TileEntity te;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               te = (TileEntity)var3.next();
            } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

            Utils.HUD.re(te.getPos(), rgb, true);
         }
      }
   }

}