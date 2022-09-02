package a.b.module.modules.minigames.sumo;

import java.io.IOException;

import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.modules.combat.AimAssist;
import a.b.module.modules.combat.STap;
import a.b.module.modules.render.AntiShuffle;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.CoolDown;
import a.b.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SumoBot extends Module{

    private State state = State.HUB;
    private STap sTap;
    private SumoClicker leftClicker;
    private AimAssist aimAssist;
    private CoolDown timer = new CoolDown(0), rcTimer = new CoolDown(0), slotTimer = new CoolDown(0);
    private SliderSetting autoClickerTrigger;
    private DescriptionSetting desc;

    public SumoBot() {
        super("Sumo Bot", ModuleCategory.minigames);
        this.registerSetting(desc = new DescriptionSetting("This code is from Kv"));
        this.registerSetting(autoClickerTrigger = new SliderSetting("Autoclicker Distance",4.5, 1, 5, 0.1));
    }

    public void onEnable() {
        state = State.HUB;
        sTap = (STap) Otaku.moduleManager.getModuleByName("STap");
        leftClicker = (SumoClicker) Otaku.moduleManager.getModuleByName("Sumo Clicker");
        aimAssist = (AimAssist) Otaku.moduleManager.getModuleByName("AimAssist");
        mc.gameSettings.pauseOnLostFocus = false;
    }

    private void matchStart() {
        state =  State.INGAME;
        sTap.setToggled(true);
        aimAssist.setToggled(true);
        mc.thePlayer.inventory.currentItem = 4;
        slotTimer.setCooldown(2300);
        slotTimer.start();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    }

    private void matchEnd() {
        timer.setCooldown(2300);
        timer.start();
        state = State.GAMEEND;
        sTap.setToggled(false);
        leftClicker.setToggled(false);
        aimAssist.setToggled(false);
        mc.thePlayer.inventory.currentItem = 4;
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
    }

    @SubscribeEvent
    public void event(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;
        //Utils.Player.sendMessageToSelf(state.toString());
        if(slotTimer.firstFinish()) {
            mc.thePlayer.inventory.currentItem = 4;
        }
        if(rcTimer.firstFinish() && state == State.GAMEEND) {
            KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            rcTimer.setCooldown(1900);
            rcTimer.start();
        }
        if(state == State.QUEUE) {
            for (String s : Utils.Client.getPlayersFromScoreboard()) {
                if(s.contains("Opponent")) {
                    matchStart();
                }
            }
        } else if(state == State.GAMEEND) {
            if(timer.hasFinished()) {
                state = State.QUEUE;
                rcTimer.setCooldown(1900);
                rcTimer.start();
                KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            }
        } else if(state == State.HUB) {
            if(timer.hasFinished()) {
                if (timer.firstFinish()) {
                    mc.thePlayer.sendChatMessage("/play duels_sumo_duel");
                    state = State.QUEUE;
                }
                timer.setCooldown(1500);
                timer.start();
            }
        }
    }

    @SubscribeEvent
    public void event2(TickEvent.RenderTickEvent e) {
        if(state == State.INGAME) {
            if(leftClicker.isEnabled()) {
                if(Utils.Player.getClosestPlayer(autoClickerTrigger.getInput()) == null) {
                    leftClicker.disable();
                }
            } else if (!leftClicker.isEnabled()) {
                if(Utils.Player.getClosestPlayer(autoClickerTrigger.getInput()) != null) {
                    leftClicker.enable();
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) throws IOException {
        if(AntiShuffle.getUnformattedTextForChat(e.message.getFormattedText()).contains("WINNER") || AntiShuffle.getUnformattedTextForChat(e.message.getFormattedText()).contains("DRAW")) {
            matchEnd();
        }
    }

    public void reQueue() {
        mc.thePlayer.sendChatMessage("/hub");
        state = State.HUB;
    }

    public enum State {
        INGAME, HUB, QUEUE, GAMEEND
    }

}