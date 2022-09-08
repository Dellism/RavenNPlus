package a.b.module.modules.render;

import a.b.utils.Utils;
import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.notifications.Notification;
import a.b.utils.notifications.Manager;
import a.b.utils.notifications.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Calendar;

public class UserHud extends Module {

    public static TickSetting fps, clock, ping, ip, coordinates, dontShowCoordOnline;
    public static SliderSetting x2, y2;

    public UserHud() {
        super("UserHUD", ModuleCategory.render);
        this.registerSetting(fps = new TickSetting("FPS", true));
        this.registerSetting(clock = new TickSetting("Clock", true));
        this.registerSetting(ping = new TickSetting("Ping", true));
        this.registerSetting(ip = new TickSetting("IP", true));
        this.registerSetting(coordinates = new TickSetting("Coordinates", true));
        this.registerSetting(dontShowCoordOnline = new TickSetting("Dont show Coordinates Online", true));
        this.registerSetting(x2 = new SliderSetting("X", 5, -50, mc.displayWidth+150, 1));
        this.registerSetting(y2 = new SliderSetting("Y", 50, -50 , mc.displayHeight+150, 1));
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        FontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);
        int y = 5+ (int)y2.getInput(), x = 20+ (int)x2.getInput(), color = 0xFF0079;

        if(fps.isToggled()) {
            fr.drawStringWithShadow("FPS  : "+Minecraft.getDebugFPS(), x, y, color);
            y += fr.FONT_HEIGHT;
        }

        if(clock.isToggled()) {
            mc.fontRendererObj.drawStringWithShadow(Calendar.getInstance().getTime().getHours()
                    +":"+Calendar.getInstance().getTime().getMinutes() +":"+Calendar.getInstance().getTime().getSeconds(), x, y, 0xFF0079);
            y += fr.FONT_HEIGHT;
        }

        if(coordinates.isToggled()) {
            if(dontShowCoordOnline.isToggled() && !mc.isSingleplayer()) return;
            if(Utils.Client.isServerIP("2b2t.org")) {
                Notification msg = new Notification(Type.WARNING, "Disabled coords", "cause your in 2b2t", 10);
                Manager.show(msg);
                return;
            }

            fr.drawStringWithShadow("X:"+(int) mc.thePlayer.posX+" Y:"+(int) mc.thePlayer.posY+" Z:"+(int) mc.thePlayer.posZ, x, y, color);
            y += fr.FONT_HEIGHT;
        }

        if(ping.isToggled()) {
            if(mc.isSingleplayer()) return;
            fr.drawStringWithShadow("Ping  : " + mc.getCurrentServerData().pingToServer, x, y, color);
            y += fr.FONT_HEIGHT;
        }

        if(ip.isToggled()) {
            if(mc.isSingleplayer()) return;
            fr.drawStringWithShadow("IP  : " + mc.getCurrentServerData().serverIP, x, y, color);
            y += fr.FONT_HEIGHT;
        }

    }

}