package a.b.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static int findEmptySlot() {
        for (int i = 0; i < 8; i++) {
            if (mc.thePlayer.inventory.mainInventory[i] == null)
                return i;
        }

        return mc.thePlayer.inventory.currentItem + (mc.thePlayer.inventory.getCurrentItem() == null ? 0 : ((mc.thePlayer.inventory.currentItem < 8) ? 4 : -1));
    }

    // TODO: AutoPot refill always put potions on slot 1, bugs here?
    public static int findEmptySlot(int priority) {
        if (mc.thePlayer.inventory.mainInventory[priority] == null)
            return priority;

        return findEmptySlot();
    }

    public static void swapShift(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
    }

    public static void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
    }

    public static boolean isFull() {
        return !Arrays.asList(mc.thePlayer.inventory.mainInventory).contains(null);
    }

    public static int armorSlotToNormalSlot(int armorSlot) {
        return 8 - armorSlot;
    }

    public static void block() {
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
    }

    public static ItemStack getCurrentItem() {
        return mc.thePlayer.getCurrentEquippedItem() == null ? new ItemStack(Blocks.air) : mc.thePlayer.getCurrentEquippedItem();
    }

    public static ItemStack getItemBySlot(int slot) {
        return mc.thePlayer.inventory.mainInventory[slot] == null ? new ItemStack(Blocks.air) : mc.thePlayer.inventory.mainInventory[slot];
    }

    public static List<ItemStack> getHotbarContent() {
        List<ItemStack> result = new ArrayList<>();
        result.addAll(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 9));
        return result;
    }

    public static List<ItemStack> getAllInventoryContent() {
        List<ItemStack> result = new ArrayList<>();
        result.addAll(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 35));
        for (int i = 0; i < 4; i++) {
            result.add(mc.thePlayer.inventory.armorItemInSlot(i));
        }

        return result;
    }

    public static List<ItemStack> getInventoryContent() {
        List<ItemStack> result = new ArrayList<>();
        result.addAll(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(9, 35));
        return result;
    }

    public static int getEmptySlotInHotbar() {
        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.mainInventory[i] == null)
                return i;
        }

        return -1;
    }

    public static float getArmorScore(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor))
            return -1;

        ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
        float score = 0;
        score += itemArmor.damageReduceAmount;
        if (EnchantmentHelper.getEnchantments(itemStack).size() <= 0) score -= 0.1;
        int protection = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
        score += protection * 0.2;

        return score;
    }

    public static boolean hasWeapon() {
        if (mc.thePlayer.inventory.getCurrentItem() != null)
            return false;

        return (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAxe) || (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword);
    }

    public static boolean isHeldingSword() {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    public static int pickaxeSlot = 37, axeSlot = 38, shovelSlot = 39;

    public static void getBestPickaxe() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {

                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (isBestPickaxe(is) && pickaxeSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                            swap(i, pickaxeSlot - 36);
                        } else if (!isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack())) {
                            swap(i, pickaxeSlot - 36);
                        }
                }
            }
        }
    }

    public static void getBestShovel() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestShovel(is) && shovelSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                            swap(i, shovelSlot - 36);
                        } else if (!isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())) {
                            swap(i, shovelSlot - 36);
                        }
                }
            }
        }
    }

    public static void getBestAxe() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestAxe(is) && axeSlot != i) {
                    if (!isBestWeapon(is)) {
                        if (!mc.thePlayer.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                            swap(i, axeSlot - 36);
                        } else if (!isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack())) {
                            swap(i, axeSlot - 36);
                        }
                    }
                }
            }
        }
    }

    public static boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe))
            return false;

        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }

            }
        }

        return true;
    }

    public static boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }

            }
        }

        return true;
    }

    public static boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool))
            return 0;
        String name = item.getUnlocalizedName();
        ItemTool tool = (ItemTool) item;
        float value = 1;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else
            return 1f;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100d;

        return value;
    }

    public static boolean isBestWeapon(ItemStack stack) {
        float damage = getDamage(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamage(is) > damage && (is.getItem() instanceof ItemSword))
                    return false;
            }
        }
        if ((stack.getItem() instanceof ItemSword)) {
            return true;
        } else {
            return false;
        }

    }

    public static double getScoreForSword(final ItemStack itemStack){
        if(!(itemStack.getItem() instanceof ItemSword))
            return 0;

        ItemSword itemSword = (ItemSword) itemStack.getItem();
        double result = 1.0;

        result += itemSword.getDamageVsEntity();
        result += 1.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
        result += 0.5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);

        return result;
    }

    public static float getDamage(ItemStack stack) {
        float damage = 0;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            ItemTool tool = (ItemTool) item;
            damage += tool.getMaxDamage();
        }
        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword) item;
            damage += sword.getDamageVsEntity();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f
                + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;

        return damage;
    }

    public static boolean isItemEmpty(Item item) {
        return item == null || Item.getIdFromItem(item) == 0;
    }

    public static int findAutoBlockBlock() {
        for(int i = 36; i < 45; i++) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                final Block block = itemBlock.getBlock();
            }
        }

        for(int i = 36; i < 45; i++) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                final Block block = itemBlock.getBlock();
            }
        }

        return -1;
    }

    public static int findInInventory(String itemOrBlock) {

        if(itemOrBlock == "gap") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemAppleGold)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "sword") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemSword)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "water") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemBucket)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "egg") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemEgg)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "snow") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemSnowball)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "potion") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemPotion)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "block") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemBlock)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "armor") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemArmor)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "bed") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemBed)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "pearl") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemEnderPearl)
                    return i;
            }

            return -1;
        }

        if(itemOrBlock == "soup") {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                        && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemSoup)
                    return i;
            }

            return -1;
        }

        return 0;
    }

    public static void hotkeyToSlot(int slot) {
        if (!Utils.Player.isPlayerInGame())
            return;

        mc.thePlayer.inventory.currentItem = slot;
    }

    public static int getCurrentPlayerSlot() {
        return mc.thePlayer.inventory.currentItem;
    }

    public static int setCurrentPlayerSlot(int slot) {
        return mc.thePlayer.inventory.currentItem = slot;
    }

    public static boolean isPlayerHoldingWeapon() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemSword || item instanceof ItemAxe;
        }
    }

    public static boolean isPlayerHoldingSword() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemSword;
        }
    }

    public static boolean isPlayerHoldingAxe() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemAxe;
        }
    }

    public static boolean isPlayerHoldingBlock() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemBlock;
        }
    }

    public static boolean isPlayerHoldingBow() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemBow;
        }
    }

    public static boolean isPlayerHolding(Item item) {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item != null;
        }
    }

    public static int getMaxDamageSlot() {
        int index = -1;
        double damage = -1;

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot == null)
                continue;
            for (AttributeModifier mooommHelp : itemInSlot.getAttributeModifiers().values()) {
                if (mooommHelp.getAmount() > damage) {
                    damage = mooommHelp.getAmount();
                    index = slot;
                }
            }
        }

        return index;
    }

    public static double getSlotDamage(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return -1;
        for (AttributeModifier mooommHelp : itemInSlot.getAttributeModifiers().values()) {
            return mooommHelp.getAmount();
        }

        return -1;
    }

    public static ArrayList<Integer> playerWearingArmor() {
        ArrayList<Integer> wearingArmor = new ArrayList<>();
        for (int armorPiece = 0; armorPiece < 4; armorPiece++) {
            if (mc.thePlayer.getCurrentArmor(armorPiece) != null) {
                if (armorPiece == 0) {
                    wearingArmor.add(3);
                } else if (armorPiece == 1) {
                    wearingArmor.add(2);
                } else if (armorPiece == 2) {
                    wearingArmor.add(1);
                } else if (armorPiece == 3) {
                    wearingArmor.add(0);
                }
            }
        }

        return wearingArmor;
    }

    public static int getBlockAmountInCurrentStack(int currentItem) {
        if (mc.thePlayer.inventory.getStackInSlot(currentItem) == null) {
            return 0;
        } else {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(currentItem);
            if (itemStack.getItem() instanceof ItemBlock) {
                return itemStack.stackSize;
            } else {
                return 0;
            }
        }
    }

}