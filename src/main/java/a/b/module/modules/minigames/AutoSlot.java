package a.b.module.modules.minigames;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.InvUtils;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoSlot extends Module {
    public static DescriptionSetting desc;
    public static SliderSetting mode, autoDisDistance, autoDisableHealth, delay;
    public static TickSetting autoThrow, autoDis, onlyOnline;
    boolean d = false;

    public AutoSlot() {
        super("AutoSlot", ModuleCategory.minigames);
        this.registerSetting(onlyOnline = new TickSetting("Only on Servers", true));
        this.registerSetting(mode = new SliderSetting("Mode", 1D, 1D, 3D, 1D));
        this.registerSetting(desc = new DescriptionSetting("Mode 1 = Experience Level"));
        this.registerSetting(desc = new DescriptionSetting("Mode 2 = Chat Message"));
        this.registerSetting(desc = new DescriptionSetting("Mode 3 = Display Name"));
        this.registerSetting(autoDis = new TickSetting("Auto Disable", false));
        this.registerSetting(autoDisDistance = new SliderSetting("Auto Disable Distance", 3D, 1D, 10D, 1D));
        this.registerSetting(autoDisableHealth = new SliderSetting("Auto Disable Health", 10D, 1D, 10D, 1D));
        this.registerSetting(autoThrow = new TickSetting("Auto Throw Kit", false));
        this.registerSetting(delay = new SliderSetting("Auto Trow Delay", 1200D, 200D, 5000D, 100D));
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        if (mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0 && mc.thePlayer.onGround && !mc.thePlayer.isRiding()
                && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && mc.thePlayer.experienceLevel < 6) {

            //Experience
            if (mode.getInput() == 1D) {
                if (mc.thePlayer.experienceLevel == 5) {
                    InvUtils.setCurrentPlayerSlot(4);
                } else if (mc.thePlayer.experienceLevel == 4) {
                    InvUtils.setCurrentPlayerSlot(3);
                } else if (mc.thePlayer.experienceLevel == 3) {
                    InvUtils.setCurrentPlayerSlot(2);
                } else if (mc.thePlayer.experienceLevel == 2) {
                    InvUtils.setCurrentPlayerSlot(1);
                } else if (mc.thePlayer.experienceLevel == 1) {
                    InvUtils.setCurrentPlayerSlot(0);
                    d = true;
                }

                if (autoDis.isToggled() && mc.thePlayer.getHealth() < autoDisableHealth.getInput() * 2
                        && mc.thePlayer.fallDistance < autoDisDistance.getInput() && mc.thePlayer.experienceLevel == 0) {
                    this.disable();
                }
            }

            //Chat
            if (mode.getInput() == 2D) {
                //onChatMessageRecieved if mode equals 2D
                 int a = mc.displayHeight;
            }

            //Display Name
            if (mode.getInput() == 3D) {
                int currentItem = InvUtils.getCurrentPlayerSlot();

                if (mc.thePlayer.getDisplayNameString().equalsIgnoreCase("5")) {
                    InvUtils.setCurrentPlayerSlot(4);
                } else if (mc.thePlayer.getDisplayNameString().equalsIgnoreCase("4")) {
                    InvUtils.setCurrentPlayerSlot(3);
                } else if (mc.thePlayer.getDisplayNameString().equalsIgnoreCase("3")) {
                    InvUtils.setCurrentPlayerSlot(2);
                } else if (mc.thePlayer.getDisplayNameString().equalsIgnoreCase("3")) {
                    InvUtils.setCurrentPlayerSlot(1);
                } else if (mc.thePlayer.getDisplayNameString().equalsIgnoreCase("1")) {
                    InvUtils.setCurrentPlayerSlot(0);
                }

                if (autoDis.isToggled() && mc.thePlayer.getHealth() < autoDisableHealth.getInput() * 2
                        && mc.thePlayer.fallDistance < autoDisDistance.getInput() && mc.thePlayer.getDisplayNameString().equalsIgnoreCase("0")) {
                    this.disable();
                }
            }

            if (autoThrow.isToggled() && d && Timer.hasTimeElapsed((long) delay.getInput(), true)) {
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 1, 0, 1, mc.thePlayer);
                Timer.hasTimeElapsed(1000L, true);
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 1, mc.thePlayer);
                Timer.hasTimeElapsed(1000L, true);
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 3, 0, 1, mc.thePlayer);
                Timer.hasTimeElapsed(1000L, true);
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 4, 0, 1, mc.thePlayer);
                autoThrow.disable();
            }
        }
    }

    @SubscribeEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event) {
        if (mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0 && mc.thePlayer.onGround && !mc.thePlayer.isRiding()
                && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && mc.thePlayer.experienceLevel < 6) {
            if(!Utils.Player.isPlayerInGame()) return;

            if(onlyOnline.isToggled()) {
                if (mc.isSingleplayer()) return;
            } else

            if (mode.getInput() == 2D) {
                if (Utils.Java.str(event.message.getUnformattedText()).contains("5") || Utils.Java.str(event.message.getUnformattedText()).contains("Five")) {
                    mc.thePlayer.inventory.currentItem = 4;
                } else if (Utils.Java.str(event.message.getUnformattedText()).contains("4") || Utils.Java.str(event.message.getUnformattedText()).contains("Four")) {
                    mc.thePlayer.inventory.currentItem = 3;
                } else if (Utils.Java.str(event.message.getUnformattedText()).contains("3")  || Utils.Java.str(event.message.getUnformattedText()).contains("Three")) {
                    mc.thePlayer.inventory.currentItem = 2;
                } else if (Utils.Java.str(event.message.getUnformattedText()).contains("2") || Utils.Java.str(event.message.getUnformattedText()).contains("Two")) {
                    mc.thePlayer.inventory.currentItem = 1;
                } else if (Utils.Java.str(event.message.getUnformattedText()).contains("1") || Utils.Java.str(event.message.getUnformattedText()).contains("One")) {
                    mc.thePlayer.inventory.currentItem = 0;
                }
            }

            if (autoDis.isToggled() && mc.thePlayer.getHealth() < autoDisableHealth.getInput() * 2
                    && mc.thePlayer.fallDistance < autoDisDistance.getInput() && (Utils.Java.str(event.message.getUnformattedText()).contains("Start")
                    || Utils.Java.str(event.message.getUnformattedText()).contains("Go") || Utils.Java.str(event.message.getUnformattedText()).contains("0"))) {
                this.disable();
            }
        }
    }

}