package ravenNPlus.b.module.modules.player;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.InvUtils;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvMgr extends Module {

    public static SliderSetting delay, blockSlot, foodSlot, pearlSlot;
    public static TickSetting invOnly, sort, clean;

    public InvMgr() {
        super("InvManager", ModuleCategory.player, "Automatically manages the Inventory");
        this.addSetting(delay = new SliderSetting("Delay", 1, 0, 50, 1));
        this.addSetting(blockSlot = new SliderSetting("Block", 2, 1, 9, 1));
        this.addSetting(foodSlot  = new SliderSetting("Food", 3, 1, 9, 1));
        this.addSetting(pearlSlot = new SliderSetting("Pearl", 4, 1, 9, 1));
        this.addSetting(invOnly  = new TickSetting("Inv Only", true));
        this.addSetting(clean = new TickSetting("Clean", true));
        this.addSetting(sort  = new TickSetting("Sort", false));
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(InvUtils.isChatOpen() || InvUtils.isPauseMenuOpen())
            return;

        if(invOnly.isToggled())
            if(!InvUtils.isInvOpen())
                return;

        for(int i = 9; i<mc.thePlayer.inventoryContainer.getInventory().size(); i++) {
            if(!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                continue;

            ItemStack stackInSlot = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item itemInSlot = stackInSlot.getItem();

            long s = (long) (delay.getInput()*15);
            String getName = itemInSlot.getUnlocalizedName();

            if((itemInSlot instanceof ItemPotion && InvUtils.isBadPotion(stackInSlot)) ||
                    getName.startsWith("Raw")  || getName.equals("Gunpowder") ||
                    getName.contains("snow") || getName.contains("stick") ||
                    getName.contains("seeds") || getName.contains("bottle") ||
                    getName.contains("string") || getName.contains("bucket") ||
                    getName.contains("feather") || getName.contains("piston") ||
                    getName.contains("sugar") || getName.contains("flower") ||
                    getName.contains("wheat") || getName.contains("tnt") ||
                    getName.contains("boat") || getName.contains("torch") ||
                    getName.contains("web") || itemInSlot instanceof ItemEgg ||
                    itemInSlot instanceof ItemBow || itemInSlot instanceof ItemAxe ||
                    (itemInSlot instanceof ItemFood && !(itemInSlot instanceof ItemAppleGold)) ||
                    itemInSlot instanceof ItemPickaxe || itemInSlot instanceof ItemExpBottle
            ) {
                if(clean.isToggled())
                    if(Timer.hasTimeElapsed(s, true))
                        InvUtils.clean(i);

                if((itemInSlot instanceof ItemPotion && InvUtils.isBadPotion(stackInSlot)) ||
                        getName.startsWith("Raw")  || getName.equals("Gunpowder") ||
                        getName.contains("snow") || getName.contains("stick") ||
                        getName.contains("seeds") || getName.contains("bottle") ||
                        getName.contains("string") || getName.contains("bucket") ||
                        getName.contains("feather") || getName.contains("piston") ||
                        getName.contains("sugar") || getName.contains("flower") ||
                        getName.contains("wheat") || getName.contains("tnt") ||
                        getName.contains("boat") || getName.contains("torch") ||
                        getName.contains("web") || itemInSlot instanceof ItemEgg ||
                        itemInSlot instanceof ItemBow || itemInSlot instanceof ItemAxe ||
                        (itemInSlot instanceof ItemFood && !(itemInSlot instanceof ItemAppleGold)) ||
                        itemInSlot instanceof ItemPickaxe || itemInSlot instanceof ItemExpBottle
                )

                    if(clean.isToggled())
                        if(Timer.hasTimeElapsed(s+10, true))
                            InvUtils.swapShift(i);
            } else {
                if(itemInSlot instanceof ItemBlock && i != 36) {
                    if(getName.equals("Gravel") || getName.equals("Sand")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortBlock((int) blockSlot.getInput());
                }
                if(itemInSlot instanceof ItemFood && i != 36) {
                    if(getName.startsWith("Raw")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortFood((int) foodSlot.getInput());
                }
                if(itemInSlot instanceof ItemEnderPearl && i != 36) {
                    if(getName.contains("Corrupted")) return;

                    if(sort.isToggled())
                        if(Timer.hasTimeElapsed(s, true))
                            InvUtils.sortPearl((int) pearlSlot.getInput());
                }
            }
        }
    }

}