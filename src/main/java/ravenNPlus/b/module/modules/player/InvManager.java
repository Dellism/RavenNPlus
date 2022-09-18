package ravenNPlus.b.module.modules.player;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.InvUtils;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvManager extends Module {

    public static TickSetting OpenInv, trash, snowball, seeds, egg, xpbottle, bow;
    public static SliderSetting delay, weaponSlot, blockSlot, foodSlot, pearlSlot;
    public static DescriptionSetting desc;

    public InvManager() {
        super("InvManager", ModuleCategory.player, "Automatically manages the Inventory");
        this.addSetting(OpenInv = new TickSetting("Open Inv", true));
        this.addSetting(delay = new SliderSetting("Delay", 10, 1, 100, 1));
        this.addSetting(weaponSlot = new SliderSetting("Weapon", 1, 1, 9, 1));
        this.addSetting(blockSlot = new SliderSetting("Block", 2, 1, 9, 1));
        this.addSetting(foodSlot = new SliderSetting("Food", 3, 1, 9, 1));
        this.addSetting(pearlSlot = new SliderSetting("Pearl", 4, 1, 9, 1));
        this.addSetting(desc = new DescriptionSetting("Throw Trash Settings"));
        this.addSetting(trash = new TickSetting("Throw Trash", true));
        this.addSetting(snowball = new TickSetting("Snowball", true));
        this.addSetting(seeds = new TickSetting("Seed", true));
        this.addSetting(egg = new TickSetting("Egg", true));
        this.addSetting(xpbottle = new TickSetting("ExpBottle", true));
        this.addSetting(bow = new TickSetting("Bow", false));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        if(OpenInv.isToggled())
            if(InvUtils.isContainerOpen()) return;

        if(Timer.hasTimeElapsed((long) delay.getInput()*5))
            InvUtils.sortSword((int)weaponSlot.getInput());
        Timer.reset();
        if(Timer.hasTimeElapsed((long) delay.getInput()*5))
            InvUtils.sortBlock((int)blockSlot.getInput());
        Timer.reset();
        if(Timer.hasTimeElapsed((long) delay.getInput()*5))
            InvUtils.sortFood((int)foodSlot.getInput());
        Timer.reset();
        if(Timer.hasTimeElapsed((long) delay.getInput()*5))
            InvUtils.sortPearl((int)pearlSlot.getInput());
        Timer.reset();

        if(trash.isToggled())
            if(Timer.hasTimeElapsed((long) delay.getInput()*5, true))
                InvUtils.searchForTrash(snowball.isToggled(), seeds.isToggled(), egg.isToggled(), xpbottle.isToggled(), bow.isToggled());
    }

}