package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class HypixelFly extends Module {

    public static SliderSetting boost, boostDelay;
    static boolean flyOnHypixel = false;

    public HypixelFly() {
        super("HypixelFly", ModuleCategory.movement, "Not Working");
        this.addSetting(boost = new SliderSetting("Speed", 5D, 1D, 50D, 1D));
        this.addSetting(boostDelay = new SliderSetting("Boost Delay", 500D, 1D, 10000D, 50D));
    }

    @SubscribeEvent
    public void r(RenderPlayerEvent e) {
        double csp = Utils.Player.pythagorasMovement();

        if(Timer.hasTimeElapsed((long) boostDelay.getInput(), true)) {
            if (csp != 0.0D) {
                double val = boost.getInput() - (boost.getInput() - 1.0D) * 0.5D;
                Utils.Player.fixMovementSpeed(csp * val, true);
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5, mc.thePlayer.posZ);
                flyOnHypixel = true;
            }

            if(mc.gameSettings.keyBindJump.isPressed()) {
                mc.thePlayer.setJumping(false);
            }

            if(mc.thePlayer.stepHeight > 0.1F) {
                mc.thePlayer.stepHeight = 0F;
            }
        }
    }

    @SubscribeEvent
    public void s(BlockEvent event) {
        if(flyOnHypixel)
            AxisAlignedBB.fromBounds(event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.pos.getX() + 1.0, mc.thePlayer.posY, event.pos.getZ() + 1.0);

        flyOnHypixel = false;
    }

    @SubscribeEvent
    public void p(FMLNetworkEvent.ClientCustomPacketEvent event) {
        if(flyOnHypixel)
            mc.thePlayer.onGround = false;
    }

    @Override
    public void onDisable() {
        if(!Utils.Player.isPlayerInGame()) return;
        if(mc.thePlayer.stepHeight == 0.5F) return;

        mc.thePlayer.stepHeight = 0.5F;
    }

}