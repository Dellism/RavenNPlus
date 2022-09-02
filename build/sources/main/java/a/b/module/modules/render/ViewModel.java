package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class ViewModel extends Module {

    public static SliderSetting Trandx, Trandy, Trandz, Tranfx, Tranfy, Tranfz;
    public static DescriptionSetting desc;
    public static TickSetting mode, onlyGround, onlyOnAir, onlyServer, onlySprint, onlySneak;

    public ViewModel() {
        super("ViewModel", ModuleCategory.render);
        this.registerSetting(desc = new DescriptionSetting("Modifies your Hand Model"));
        this.registerSetting(Trandx = new SliderSetting("TranslateD X", 1.0D, -2D, 2D, -0.1D));
        this.registerSetting(Trandy = new SliderSetting("TranslateD Y", -0.2D, -2D, 2D, -0.1D));
        this.registerSetting(Trandz = new SliderSetting("TranslateD Z", -0.9D, -2D, 2D, -0.1D));
        this.registerSetting(mode = new TickSetting("Translate D / Translate F", true));
        this.registerSetting(Tranfx = new SliderSetting("TranslateF X", 1.0D, -2D, 2D, -0.1D));
        this.registerSetting(Tranfy = new SliderSetting("TranslateF Y", -0.2D, -2D, 2D, -0.1D));
        this.registerSetting(Tranfz = new SliderSetting("TranslateF Z", -0.9D, -2D, 2D, -0.1D));
        this.registerSetting(onlyGround = new TickSetting("Only onGround", false));
        this.registerSetting(onlyOnAir  = new TickSetting("Only onAir", false));
        this.registerSetting(onlyServer = new TickSetting("Only Server", false));
        this.registerSetting(onlySprint = new TickSetting("Only Sprint", false));
        this.registerSetting(onlySneak  = new TickSetting("Only Sneak", false));
    }

    @SubscribeEvent
    public void onRenderArms(RenderHandEvent e) {
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

        if(mode.isToggled()) {

            glTranslated(Trandx.getInput(), Trandy.getInput(), Trandz.getInput());
        } else {

            glTranslatef((float) Tranfx.getInput(), (float) Tranfy.getInput(), (float) Tranfz.getInput());
        }
    }

}