package ravenNPlus.client.module.modules.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.modules.combat.AntiBot;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import java.awt.*;
import java.util.Iterator;

public class ReachCircle extends Module {

    public static SliderSetting red, green, blue, expand;
    public static TickSetting damage, invis;

    public ReachCircle() {
        super("ReachCircle", ModuleCategory.render, "Renders a circle around Players to see the reach");
        this.addSetting(red = new SliderSetting("Red", 150, 0, 255, 1));
        this.addSetting(green = new SliderSetting("Green", 10, 0, 255, 1));
        this.addSetting(blue = new SliderSetting("Blue", 250, 0, 255, 1));
        this.addSetting(invis = new TickSetting("Show invis", true));
        this.addSetting(damage = new TickSetting("Red on damage", true));
        this.addSetting(expand = new SliderSetting("Expand", -0.3, -0.5, 5, 0.1));
    }

    @Override
    public void onDisable() {
        Utils.HUD.ring_c = false;
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent e) {
        if (!Utils.Player.isPlayerInGame()) return;

        Iterator var3;
        var3 = mc.theWorld.playerEntities.iterator();
        while(true) {
            EntityPlayer en;
            do {
                do {
                    do {
                        if (!var3.hasNext()) { return; }
                        en = (EntityPlayer) var3.next();
                    } while (en == mc.thePlayer);
                } while (en.deathTime != 0);
            } while (!invis.isToggled() && en.isInvisible());

            if (!AntiBot.isBot(en)) {
                int E = new Color(getColor(en.getCurrentArmor(2))).getRGB();
                Utils.HUD.drawCircleAroundEntity(en,1, 1+expand.getValue(), 1.5F, 11, E, damage.isToggled());
            }
        }
    }

    public int getColor(ItemStack stack) {
        if(stack == null)
            return -1;
        NBTTagCompound e = stack.getTagCompound();
        if (e != null) {
            NBTTagCompound s = e.getCompoundTag("display");
            if (s != null && s.hasKey("color", 3)) {
                return s.getInteger("color");
            }
        }

        return -2;
    }

}