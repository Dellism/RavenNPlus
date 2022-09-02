package a.b.module.modules.movement;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastLadder extends Module {

    public static SliderSetting speed, delay;
    public static TickSetting dlay, auto;

    public FastLadder() {
        super("FastLadder", ModuleCategory.movement);
        this.registerSetting(speed = new SliderSetting("Speed", 0D, 0D, 1D, 0.1D));
        this.registerSetting(auto = new TickSetting("Auto Ladder", true));
        this.registerSetting(dlay = new TickSetting("Delay", false));
        this.registerSetting(delay = new SliderSetting("Delay Time", 0D, 0D, 50D, 0.1D));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent e) {
        if(!dlay.isToggled()) {
            if(auto.isToggled()) {
                void_pl23F9(e);
            } else {
                if(mc.gameSettings.keyBindForward.isKeyDown()) {
                    void_pl23F9(e);
                }
            }
        } else if(dlay.isToggled() && Timer.hasTimeElapsed((long) delay.getInput() * 100L, true)) {
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