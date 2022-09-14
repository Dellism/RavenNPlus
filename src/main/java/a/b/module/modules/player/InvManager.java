package a.b.module.modules.player;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.ImageButton;
import a.b.utils.InvUtils;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;

public class InvManager extends Module {

    public static TickSetting OpenInv, trash, snowball, seeds, egg, xpbottle, bow;
    public static DescriptionSetting desc;
    public static SliderSetting delay, weaponSlot, blockSlot, foodSlot, pearlSlot;
    protected ArrayList<ImageButton> imageButtons = new ArrayList<ImageButton>();

    public InvManager() {
        super("InvManager", ModuleCategory.player);
        this.registerSetting(OpenInv = new TickSetting("Open Inv", true));
        this.registerSetting(delay = new SliderSetting("Delay", 10, 1, 100, 1));
        this.registerSetting(weaponSlot = new SliderSetting("Weapon", 1, 1, 9, 1));
        this.registerSetting(blockSlot = new SliderSetting("Block", 2, 1, 9, 1));
        this.registerSetting(foodSlot = new SliderSetting("Food", 3, 1, 9, 1));
        this.registerSetting(pearlSlot = new SliderSetting("Pearl", 4, 1, 9, 1));
        this.registerSetting(desc = new DescriptionSetting("Throw Trash Settings"));
        this.registerSetting(trash = new TickSetting("Throw Trash", true));
        this.registerSetting(snowball = new TickSetting("Snowball", true));
        this.registerSetting(seeds = new TickSetting("Seed", true));
        this.registerSetting(egg = new TickSetting("Egg", true));
        this.registerSetting(xpbottle = new TickSetting("ExpBottle", true));
        this.registerSetting(bow = new TickSetting("Bow", false));
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