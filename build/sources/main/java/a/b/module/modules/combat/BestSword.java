package a.b.module.modules.combat;

import a.b.module.Module;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.DelayTimer;
import a.b.utils.InvUtils;
import a.b.utils.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BestSword extends Module {

    public static TickSetting AutoSet;
    private DelayTimer timer = new DelayTimer();

    public BestSword() {
        super("BestSword", ModuleCategory.combat);
        this.registerSetting(AutoSet = new TickSetting("AutoSet", true));
    }

    @SubscribeEvent
    public void r(PlayerInteractEvent event){
        if (mc.currentScreen instanceof GuiContainer) return;

        if(!this.timer.hasPassed(200))
            return;

        if(AutoSet.isToggled()) {
            if(!Utils.Player.isPlayerInGame()) return;

            int slot = this.getBestSword(this.getScoreForSword(InvUtils.getItemBySlot(0)));

            if (slot == -1)
                return;

            this.swap(slot, 0);
        } else {
            if(!Utils.Player.isPlayerInGame()) return;

            if (!(InvUtils.getCurrentItem().getItem() instanceof ItemSword))
                return;

            int slot = this.getBestSword(this.getScoreForSword(InvUtils.getCurrentItem()));

            if (slot == -1)
                return;

            this.swap(slot, mc.thePlayer.inventory.currentItem);
        }

        this.timer.reset();
    }

    public int getBestSword(double minimum) {
        for (int i = 0; i < 36; ++i) {
            if (mc.thePlayer.inventory.currentItem == i)
                continue;

            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];

            if (itemStack == null)
                continue;

            if (!(itemStack.getItem() instanceof ItemSword))
                continue;

            if (minimum >= this.getScoreForSword(itemStack))
                continue;

            return i;
        }

        return -1;
    }

    public double getScoreForSword(final ItemStack itemStack){
        if(!(itemStack.getItem() instanceof ItemSword))
            return 0;

        ItemSword itemSword = (ItemSword) itemStack.getItem();

        double result = 1.0;

        result += itemSword.getDamageVsEntity();

        result += 1.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
        result += 0.5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);

        return result;
    }

    public void swap(int from, int to) {
        if(from <= 8){
            from = 36 + from;
        }

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, from, to, 2, mc.thePlayer);
    }
}