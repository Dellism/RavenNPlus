package ravenNPlus.client.module.modules.combat;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.InvUtils;
import ravenNPlus.client.utils.MouseManager;
import ravenNPlus.client.utils.Timer;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class DoubleClicker extends Module {

    public static SliderSetting cps, chance, delay;
    public static TickSetting rightClick, leftClick;
    public static TickSetting onlySword, onlySprint;
    public static boolean hasClickedR = false, hasClickedL = false;


    public DoubleClicker() {
        super("DoubleClicker", ModuleCategory.combat, "");
        this.addSetting(cps = new SliderSetting("CPS", 2, 1, 5, 1));
        this.addSetting(chance =  new SliderSetting("Chance %", 100, 0, 100, 1));
        this.addSetting(delay =  new SliderSetting("Delay", 5, 1, 50, 1));
        this.addSetting(rightClick = new TickSetting("Right Click", false));
        this.addSetting(leftClick = new TickSetting("Left Click", true));
        this.addSetting(onlySword = new TickSetting("Only Sword", true));
        this.addSetting(onlySprint = new TickSetting("Only Sprinting", false));
    }

    @Override
    public void onDisable() {
        reset();
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        if(onlySword.isToggled())
            if(!InvUtils.isPlayerHoldingWeapon()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(rightClick.isToggled() && !hasClickedR) {
            if(Mouse.isButtonDown(0)) {

                if(!(chance.getValue() == 100 || Math.random() <= chance.getValue() / 100))
                    return;

                if(Timer.hasTimeElapsed((long) delay.getValue(), true))
                    MouseManager.addLeftClick();

              hasClickedR = true;
            }
        }

        if(leftClick.isToggled() && !hasClickedL) {
            if(Mouse.isButtonDown(1)) {

                if(!(chance.getValue() == 100 || Math.random() <= chance.getValue() / 100))
                    return;

                if(Timer.hasTimeElapsed((long) delay.getValue(), true))
                    MouseManager.addRightClick();

              hasClickedL = true;
            }
        }

        reset();

        // press mouse right or left witg cps settings
        // maybe add delay between multiple clicks

    }

    private void reset() {
        if(hasClickedL)
            hasClickedL = false;
        if(hasClickedR)
            hasClickedR = false;
    }

}