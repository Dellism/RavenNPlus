package ravenNPlus.b.module.modules.render;

import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.awt.Color;

public class ItemCount extends Module {

    public static  TickSetting  gap, water, egg, snow, arrow;
    public static  TickSetting  shadow;
    public static SliderSetting red, green, blue;
    public static DescriptionSetting desc;
    private int gapCount = 0, waterCount = 0, eggCount = 0;
    private int arrowCount = 0, snowCount = 0, rgb_c = 0;

    public ItemCount() {
        super("ItemCount", ModuleCategory.render, "!!DONT USE!! (Crash)");
        this.addSetting(red = new SliderSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
        this.addSetting(green = new SliderSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D));
        this.addSetting(blue = new SliderSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
        this.addSetting(shadow = new TickSetting("Shadow", true));
        this.addSetting(desc = new DescriptionSetting("Mode :"));
        this.addSetting(gap = new TickSetting("Gap", false));
        this.addSetting(water = new TickSetting("Water", false));
        this.addSetting(egg = new TickSetting("Egg", false));
        this.addSetting(snow = new TickSetting("Snow", false));
        this.addSetting(arrow = new TickSetting("Arrow", false));
    }

    public void guiUpdate() {
        this.rgb_c = (new Color((int) red.getInput(), (int) green.getInput(), (int) blue.getInput())).getRGB();
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent post) {
        for (int i = 0; i < 36; i++) {
            ItemStack iS = mc.thePlayer.inventory.getStackInSlot(i);

            if (gap.isToggled()) {
                if (iS.getItem() == Items.golden_apple) {
                    gapCount++;
                }
            }

            if(water.isToggled()) {
                if(iS.getItem() == Items.water_bucket) {
                    waterCount++;
                }
            }

            if(egg.isToggled()) {
                if(iS.getItem() == Items.egg) {
                    eggCount++;
                }
            }

            if(arrow.isToggled()) {
                if(iS.getItem() == Items.arrow) {
                    arrowCount++;
                }
            }

            if(snow.isToggled()) {
                if(iS.getItem() == Items.snowball) {
                    snowCount++;
                }
            }

        }
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame()) return;
        if (e.phase == TickEvent.Phase.END) {
            if (mc.currentScreen == null) {
                ScaledResolution res = new ScaledResolution(mc);

                String t = "gaps: " + gapCount;
                String n = "water: " + waterCount;
                String j = "eggs: " + eggCount;
                String k = "snow: " + snowCount;
                String l = "arrows: " + arrowCount;

                int x1 = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2, y1;
                int x2 = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(n) / 2, y2;
                int x3 = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(j) / 2, y3;
                int x4 = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(k) / 2, y4;
                int x5 = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(l) / 2, y5;

                if (RavenNPlus.debugger) {
                    y1 = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                    y2 = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                    y3 = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                    y4 = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                    y5 = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                } else {
                    y1 = res.getScaledHeight() / 2 + 15;
                    y2 = res.getScaledHeight() / 2 + 15;
                    y3 = res.getScaledHeight() / 2 + 15;
                    y4 = res.getScaledHeight() / 2 + 15;
                    y5 = res.getScaledHeight() / 2 + 15;
                }

                if (gap.isToggled() && gapCount > 0 && this.isEnabled()) {
                    mc.fontRendererObj.drawString(t, (float) x1, (float) y1, rgb_c, shadow.isToggled());
                }

                if (water.isToggled() && waterCount > 0 && this.isEnabled()) {
                    mc.fontRendererObj.drawString(n, (float) x2, (float) y2, rgb_c, shadow.isToggled());
                }

                if (egg.isToggled() && eggCount > 0 && this.isEnabled()) {
                    mc.fontRendererObj.drawString(j, (float) x3, (float) y3, rgb_c, shadow.isToggled());
                }

                if (snow.isToggled() && snowCount > 0 && this.isEnabled()) {
                    mc.fontRendererObj.drawString(k, (float) x4, (float) y4, rgb_c, shadow.isToggled());
                }

                if (arrow.isToggled() && arrowCount > 0 && this.isEnabled()) {
                    mc.fontRendererObj.drawString(l, (float) x5, (float) y5, rgb_c, shadow.isToggled());
                }

            }
        }
    }

}