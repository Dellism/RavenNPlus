package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.event.imp.EventUpdateModel;
import ravenNPlus.client.event.imp.Render3DEvent;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glRotatef;

public class Animations extends Module {

    public static SliderSetting mode, x, y, z, s, block_height, height, digSlow, digSpeed;
    public static TickSetting onlyGround, onlyServer, onlySprint;

    public Animations() {
        super("Animations", ModuleCategory.render, "Modifies your Hand Animation");
        this.addSetting(x = new SliderSetting("X", -1.8D, -2D, 2D, -0.1D));
        this.addSetting(y = new SliderSetting("Y", 0.8D, -2D, 2D, -0.1D));
        this.addSetting(z = new SliderSetting("Z", 2.0D, -2D, 2D, -0.1D));
        this.addSetting(s = new SliderSetting("Speed", 323.0D, 5D, 500D, 1D));
        this.addSetting(mode = new SliderSetting("Mode", 6D, 1D, 6D, 1D));
        this.addSetting(block_height = new SliderSetting("Block Height", 1D, 1D, 10D, 1D));
        this.addSetting(height = new SliderSetting("Height", 1D, 1D, 10D, 1D));
        this.addSetting(digSlow = new SliderSetting("DigSlowdown", 10D, 0D, 100D, 1D));
        this.addSetting(digSpeed = new SliderSetting("DigSpeed", 0D, 1D, 100D, 1D));
        this.addSetting(onlyGround = new TickSetting("Only onGround", false));
        this.addSetting(onlyServer = new TickSetting("Only Server", false));
        this.addSetting(onlySprint = new TickSetting("Only Sprint", false));
    }

    @SubscribeEvent
    public void onRenderArms(final RenderHandEvent e) {

        if(onlyGround.isToggled())
            if(!mc.thePlayer.onGround) return;

        if(onlyServer.isToggled())
            if(mc.isSingleplayer()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(mode.getValue() == 1 && e.partialTicks > 0) {
            final float angle = (float) (1f - e.partialTicks * s.getValue());
            glRotated(angle, x.getValue(), y.getValue(), z.getValue());
        }

        if(mode.getValue() == 2 && e.partialTicks > 0) {
            double a = height.getValue();
            float heightSet = (float) (20.0F - (mc.thePlayer.isSwingInProgress && mc.thePlayer.isBlocking() ? block_height.getValue() : mc.thePlayer.getHeldItem() == null ? 0.0F : height.getValue()));

            if (1 < heightSet) {
                a += 1.0F;
            } else if (a > heightSet) {
                a -= 1.0F;
            }
        }

        if(mode.getValue() == 3D ) {
            Utils.Player.SlowSwing(digSlow.getValue());
        }

        if(mode.getValue() == 4D ) {
            Utils.Player.FastSwing(digSpeed.getValue());
        }

        if(mode.getValue() == 5D) {
            if(e.renderPass > 0) {
                final float angle = (1f - Utils.Player.getArmSwingAnimationEnd(mc.thePlayer)) * 360F;

                glRotatef(angle, 0, 0, 1);
            }
        }

        if(mode.getValue() == 6D) {
            if(e.partialTicks > 0) {
                final float angle = (1f - e.partialTicks) * 360;

                glRotatef(angle, 0, 1.8f, 7f);
            }
        }
    }

}