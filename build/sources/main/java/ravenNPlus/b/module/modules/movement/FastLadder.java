package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastLadder extends Module {

    public static SliderSetting speed, delay;
    public static TickSetting delayToggle, auto;

    public FastLadder() {
        super("FastLadder", ModuleCategory.movement, "Makes you faster on Ladders");
        this.addSetting(speed = new SliderSetting("Speed", 0, 0, 1, 0.1));
        this.addSetting(auto = new TickSetting("Auto Ladder", true));
        this.addSetting(delayToggle = new TickSetting("Delay", false));
        this.addSetting(delay = new SliderSetting("Delay Time", 0, 0, 50, 1));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(!delayToggle.isToggled()) {
            if(auto.isToggled()) {
                void_pl23F9(e);
            } else {
                if(mc.gameSettings.keyBindForward.isKeyDown()) {
                    void_pl23F9(e);
                }
            }
        } else if(delayToggle.isToggled() && Timer.hasTimeElapsed((long) delay.getInput() * 100L, true)) {
            if(auto.isToggled()) {
                void_pl23F9(e);
            } else {
                if(mc.gameSettings.keyBindForward.isKeyDown()) {
                    void_pl23F9(e);
                }
            }
        }
    }

    private void void_pl23F9(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(!mc.thePlayer.isOnLadder()) return;

        if(e.phase != TickEvent.Phase.END) {
            if(!mc.thePlayer.isRiding() && !mc.thePlayer.isDead && mc.thePlayer.isOnLadder()) {
                mc.thePlayer.motionY = 0.169 + speed.getInput();
            }
        }

    }


}