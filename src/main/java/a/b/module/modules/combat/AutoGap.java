package a.b.module.modules.combat;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.RenderUtils;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class AutoGap extends Module {

    public static SliderSetting startHealth, packetAmount, time;
    public static TickSetting showAmount, legitMode;

    protected int lastSlot = 0;
    protected boolean sendingPacket = false, switchedItem = false;
    static boolean thisMustBeOnToRunShowAmount = false;

    public AutoGap() {
        super("AutoGap", ModuleCategory.combat);
        this.registerSetting(startHealth  = new SliderSetting("Health", 8D, 1D, 10D, 1D));
        this.registerSetting(time         = new SliderSetting("Delay", 10, 10, 1000D, 1D));
        this.registerSetting(packetAmount = new SliderSetting("Packets", 1D, 1D, 50D, 1D));
        this.registerSetting(legitMode    = new TickSetting("Legit", true));
        this.registerSetting(showAmount   = new TickSetting("Show Amount", false));
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent e) throws LWJGLException {
        if(!Utils.Player.isPlayerInGame()) return;

        int gAppleSlot = findGoldenApple();
        if(gAppleSlot != -1) {
            if (mc.thePlayer.getHealth() < startHealth.getInput() * 2D) {

                if (mc.thePlayer.inventory.currentItem != gAppleSlot)
                    this.lastSlot = mc.thePlayer.inventory.currentItem;

                mc.thePlayer.onGround = true;
                mc.thePlayer.inventory.currentItem = gAppleSlot;

                if(!legitMode.isToggled()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos
                            (mc.thePlayer),255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                } else {
                    while (Timer.hasTimeElapsed((long) time.getInput(), true)) {
                        Mouse.isButtonDown(1);
                    }
                }

                if (mc.thePlayer.isEating())
                    if (this.sendingPacket && !legitMode.isToggled()) {
                        for (int i = 0; i < packetAmount.getInput(); i++)
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        this.sendingPacket = false;
                    } else {
                        this.sendingPacket = true;
                    }
            } else {
                if (this.switchedItem) {
                    this.switchedItem = false;
                }
            }
        }

        thisMustBeOnToRunShowAmount = showAmount.isToggled();
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        int gAppleSlot = findGoldenApple();
        if(thisMustBeOnToRunShowAmount && showAmount.isToggled()) {
            if(gAppleSlot != -1) {
                int apples = mc.thePlayer.inventory.getStackInSlot(gAppleSlot).stackSize;
                Gui.drawRect(RenderUtils.scWidth / 2+9, RenderUtils.scHight / 2-1,
                        RenderUtils.scWidth / 2+10+RenderUtils.font.getStringWidth(apples+""),
                        RenderUtils.scHight / 2+RenderUtils.font.FONT_HEIGHT-1, Integer.MIN_VALUE);
                RenderUtils.font.drawString(apples+"", RenderUtils.scWidth / 2+10,RenderUtils.scHight / 2, -1);
            }
        }
    }

    protected int findGoldenApple() {
        for(int i = 0; i < 9; i++) {
            if(mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
                && mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack().getItem() instanceof ItemAppleGold)
                    return i;

        }

        return -1;
    }

}