package ravenNPlus.client.module.modules.combat;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.util.Objects;

public class Criticals extends Module {

    public static SliderSetting mode;
    public static DescriptionSetting desc, modeMode;
    public static TickSetting jump;

    public Criticals() {
        super("Criticals", ModuleCategory.combat, "");
        this.addSetting(mode = new SliderSetting("Mode", 1, 1, 5, 1));
        this.addSetting(jump = new TickSetting("More Legit Jump", false));
        this.addSetting(modeMode = new DescriptionSetting(Utils.md+""));
    }

    @SubscribeEvent
    public void onPacketSend(FMLNetworkEvent.ClientCustomPacketEvent eventPacket) {

        C02PacketUseEntity packet = new C02PacketUseEntity();

        if (mc.thePlayer.onGround && (packet.getEntityFromWorld(mc.theWorld) instanceof net.minecraft.entity.EntityLivingBase) && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {

            if (Utils.Player.canCrit()) {
                if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {

                    if (mode.getValue() == 1) {
                        Utils.Player.fakeJump(jump.isToggled());
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1625D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                        mc.thePlayer.onCriticalHit(Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World) mc.theWorld)));
                    }

                    if (mode.getValue() == 2) {
                        mc.thePlayer.motionY /= 2.0D;
                    }

                    if (mode.getValue() == 3) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.11D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1100013579D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1100013579D, mc.thePlayer.posZ, false));
                    }

                    if (mode.getValue() == 4) {
                        mc.thePlayer.jump();
                    }

                    if (mode.getValue() == 5) {
                        Utils.Player.fakeJump(jump.isToggled());
                    }

                }
            }
        }
    }

    public void guiUpdate() {
        switch((int) mode.getValue()) {
            case 1:
                modeMode.setDesc(Utils.md + "Packets");
                break;
            case 2:
                modeMode.setDesc(Utils.md + "Mini Jump");
                break;
            case 3:
                modeMode.setDesc(Utils.md + "Bypass");
                break;
            case 4:
                modeMode.setDesc(Utils.md + "Jump");
                break;
            case 5:
                modeMode.setDesc(Utils.md + "Fake Jump");
                break;
        }
    }

}