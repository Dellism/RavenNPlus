package ravenNPlus.b.module.modules.hotkey;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraft.item.ItemStack;

public class Ladders extends Module {
    private final TickSetting preferSlot;
    private final SliderSetting hotbarSlotPreference;
    public Ladders() {
        super("Ladders", ModuleCategory.hotkey, "");

        this.addSetting(preferSlot = new TickSetting("Prefer a slot", false));
        this.addSetting(hotbarSlotPreference = new SliderSetting("Prefer wich slot", 8, 1, 9, 1));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            if(checkSlot(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }
        }

        for (int slot = 0; slot <= 8; slot++) {
            if(checkSlot(slot)) {
                if(mc.thePlayer.inventory.currentItem != slot){
                    mc.thePlayer.inventory.currentItem = slot;
                } else {
                    return;
                }
                this.disable();
                return;
            }
        }
        this.disable();
    }

    public static boolean checkSlot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        return itemInSlot != null && itemInSlot.getDisplayName().equalsIgnoreCase("ladder");
    }

}