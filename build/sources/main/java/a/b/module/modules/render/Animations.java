package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.Utils;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static org.lwjgl.opengl.GL11.glRotated;

public class Animations extends Module {

    public static DescriptionSetting desc;
    public static SliderSetting mode, x, y, z, s, block_height, height, digSlow, digSpeed;
    public static TickSetting onlyOnAir, onlyGround, onlyServer, onlySprint, onlySneak;

    public Animations() {
        super("Animations", ModuleCategory.render);
        this.registerSetting(desc = new DescriptionSetting("Modifies your Hand Animation"));
        this.registerSetting(x = new SliderSetting("X", -1.8D, -2D, 2D, -0.1D));
        this.registerSetting(y = new SliderSetting("Y", 0.8D, -2D, 2D, -0.1D));
        this.registerSetting(z = new SliderSetting("Z", 2.0D, -2D, 2D, -0.1D));
        this.registerSetting(s = new SliderSetting("Speed", 323.0D, 5D, 500D, 1D));
        this.registerSetting(mode = new SliderSetting("Mode", 2D, 1D, 4D, 1D));
        this.registerSetting(block_height = new SliderSetting("Block Height", 1D, 1D, 10D, 1D));
        this.registerSetting(height = new SliderSetting("Height", 1D, 1D, 10D, 1D));
        this.registerSetting(digSlow = new SliderSetting("DigSlowdown", 10D, 0D, 100D, 1D));
        this.registerSetting(digSpeed = new SliderSetting("DigSpeed", 0D, 1D, 100D, 1D));
        this.registerSetting(onlyGround = new TickSetting("Only onGround", false));
        this.registerSetting(onlyOnAir  = new TickSetting("Only onAir", false));
        this.registerSetting(onlyServer = new TickSetting("Only Server", false));
        this.registerSetting(onlySprint = new TickSetting("Only Sprint", false));
        this.registerSetting(onlySneak  = new TickSetting("Only Sneak", false));
    }

    @SubscribeEvent
    public void onRenderArms(final RenderHandEvent e) {

        if(onlyGround.isToggled())
            if(!mc.thePlayer.onGround) return;

        if(onlyOnAir.isToggled())
            if(mc.thePlayer.onGround) return;

        if(onlyServer.isToggled())
            if(mc.isSingleplayer()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(onlySneak.isToggled())
            if(!mc.thePlayer.isSneaking()) return;

        if(mode.getInput() == 1 && e.partialTicks > 0) {
            final float angle = (float) (1f - e.partialTicks * s.getInput());
            glRotated(angle, x.getInput(), y.getInput(), z.getInput());
        }

        if(mode.getInput() == 2 && e.partialTicks > 0) {

            double a = height.getInput();
            float heightSet = (float) (20.0F - (mc.thePlayer.isSwingInProgress && mc.thePlayer.isBlocking() ? block_height.getInput() : mc.thePlayer.getHeldItem() == null ? 0.0F : height.getInput()));

            if (1 < heightSet) {
                a += 1.0F;
            } else if (a > heightSet) {
                a -= 1.0F;
            }

        }

        if(mode.getInput() == 3D ) {
            Utils.Player.SlowSwing(digSlow.getInput());
        }

        if(mode.getInput() == 3D ) {
            Utils.Player.FastSwing(digSpeed.getInput());
        }

    }


}