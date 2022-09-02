package a.b.module.modules.combat;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.util.Objects;

public class Criticals extends Module {

    public static SliderSetting Mode;
    public static DescriptionSetting desc, desc2, desc3;
    private Utils.Java packets;

    public Criticals() {
        super("Criticals", ModuleCategory.combat);
        this.registerSetting(desc = new DescriptionSetting("Mode 1: Packets"));
        this.registerSetting(desc2 = new DescriptionSetting("Mode 2: MiniJump"));
        this.registerSetting(desc3 = new DescriptionSetting("Mode 3: Bypass"));
        this.registerSetting(Mode = new SliderSetting("Mode", 1, 1, 3, 1));
    }

    @SubscribeEvent
    public void onPacketSend(FMLNetworkEvent.ClientCustomPacketEvent eventPacket) {

        C02PacketUseEntity packet = new C02PacketUseEntity();

        if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && (packet.getEntityFromWorld((World)mc.theWorld) instanceof net.minecraft.entity.EntityLivingBase) && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
            if (Mode.getInput() == 1) {
                switch (((Integer)this.packets.getValue()).intValue()) {
                    case 1:
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.10000000149011612D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    case 2:
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625101D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.1E-5D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    case 3:
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625101D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0125D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    case 4:
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.05D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.03D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        break;
                    case 5:
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1625D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer());
                        mc.thePlayer.onCriticalHit(Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.theWorld)));
                        break;
                    }
                } else if (Mode.getInput() == 3) {
                    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.11D, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1100013579D, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1100013579D, mc.thePlayer.posZ, false));
                } else {
                    mc.thePlayer.jump();
                    if (Mode.getInput() == 2) {
                        mc.thePlayer.motionY /= 2.0D;
                }
            }
        }
    }

}