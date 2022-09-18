package ravenNPlus.b.module.modules.render;

import ravenNPlus.b.utils.Utils;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.notifications.Notification;
import ravenNPlus.b.utils.notifications.Manager;
import ravenNPlus.b.utils.notifications.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Calendar;

public class UserHud extends Module {

    public static TickSetting fps, clock, ping, ip, coordinates, dontShowCoordsOnline;
    public static SliderSetting hudX, hudY;

    public UserHud() {
        super("UserHUD", ModuleCategory.render, "Draws a hud you can setup");
        this.addSetting(fps = new TickSetting("FPS", true));
        this.addSetting(clock = new TickSetting("Clock", true));
        this.addSetting(ping = new TickSetting("Ping", true));
        this.addSetting(ip = new TickSetting("IP", true));
        this.addSetting(coordinates = new TickSetting("Coordinates", true));
        this.addSetting(dontShowCoordsOnline = new TickSetting("Dont show Coordinates Online", true));
        this.addSetting(hudX = new SliderSetting("X", 5, -50, mc.displayWidth+150, 1));
        this.addSetting(hudY = new SliderSetting("Y", 50, -50 , mc.displayHeight+150, 1));
    }

    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        FontRenderer fr = mc.fontRendererObj;
        int y = 5+ (int)hudX.getInput(), x = 20+ (int)hudY.getInput(), color = 0xFF0079;

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
            if(dontShowCoordsOnline.isToggled() && !mc.isSingleplayer()) return;
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