package ravenNPlus.b.module.modules.minigames;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.InvUtils;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoSlot extends Module {

    public static DescriptionSetting modeMode;
    public static SliderSetting mode, autoDisDistance, autoDisableHealth, delay, playerRange;
    public static TickSetting autoThrow, autoDis, onlyOnline;
    boolean d = false;

    public AutoSlot() {
        super("AutoSlot", ModuleCategory.minigame, "Auto slot before Game starts");
        this.addSetting(onlyOnline = new TickSetting("Only on Servers", true));
        this.addSetting(mode = new SliderSetting("Mode", 4, 1, 4, 1));
        this.addSetting(modeMode = new DescriptionSetting(Utils.md+""));
        this.addSetting(playerRange = new SliderSetting("Player Range", 4, 1, 50, 1));
        this.addSetting(autoDis = new TickSetting("Auto Disable", false));
        this.addSetting(autoDisDistance = new SliderSetting("Auto Disable Distance", 3D, 1D, 10D, 1D));
        this.addSetting(autoDisableHealth = new SliderSetting("Auto Disable Health", 10D, 1D, 10D, 1D));
        this.addSetting(autoThrow = new TickSetting("Auto Throw Kit", false));
        this.addSetting(delay = new SliderSetting("Auto Trow Delay", 1200D, 200D, 5000D, 100D));
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(mc.thePlayer.isEntityAlive() && mc.thePlayer.getHealth() > 0 && mc.thePlayer.onGround && !mc.thePlayer.isRiding()
                && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && mc.thePlayer.experienceLevel < 6) {

            if(onlyOnline.isToggled())
                if(mc.isSingleplayer()) return;

            if(mode.getInput() == 1) {
                if(mc.thePlayer.experienceLevel == 5) {
                    InvUtils.setCurrentPlayerSlot(4);
                } else if(mc.thePlayer.experienceLevel == 4) {
                    InvUtils.setCurrentPlayerSlot(3);
                } else if(mc.thePlayer.experienceLevel == 3) {
                    InvUtils.setCurrentPlayerSlot(2);
                } else if(mc.thePlayer.experienceLevel == 2) {
                    InvUtils.setCurrentPlayerSlot(1);
                } else if(mc.thePlayer.experienceLevel == 1) {
                    InvUtils.setCurrentPlayerSlot(0);
                    d = true;
                }

                if (autoDis.isToggled() && mc.thePlayer.getHealth() < autoDisableHealth.getInput() * 2
                        && mc.thePlayer.fallDistance < autoDisDistance.getInput() && mc.thePlayer.experienceLevel == 0) {
                    this.disable();
                }
            }

            if (mode.getInput() == 3) {

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

            if(mode.getInput() == 4) {

                List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
                targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int)playerRange.getInput() && entity
                != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
                targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
                if(targets.isEmpty()) return;
                EntityLivingBase target = (EntityLivingBase) targets.get(0);

                if (target.getDistanceToEntity(mc.thePlayer) > playerRange.getInput()) {
                   mc.thePlayer.inventory.currentItem = 0;
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

            if(onlyOnline.isToggled())
                if(mc.isSingleplayer()) return;

            if (mode.getInput() == 2) {
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

    public void guiUpdate() {
        switch((int) mode.getInput()) {
            case 1:
                modeMode.setDesc(Utils.md + "Experience Level");
                break;
            case 2:
                modeMode.setDesc(Utils.md + "Chat Message");
                break;
            case 3:
                modeMode.setDesc(Utils.md + "Display Name");
                break;
            case 4:
                modeMode.setDesc(Utils.md + "Player in Range");
                break;
        }
    }

}