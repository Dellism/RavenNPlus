package a.b.utils.notifications;

import a.b.module.Module;
import a.b.module.modules.client.GuiClick;
import a.b.utils.RenderUtils;
import a.b.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Render {

    public static final Render notificationRenderer = new Render();

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if(GuiClick.notifications.isToggled()) Manager.render();
    }

    public static void moduleStateChanged(Module m) {
        if(!GuiClick.notifications.isToggled() || Minecraft.getMinecraft().currentScreen != null || !Utils.Player.isPlayerInGame()) return;

        if(!m.getClass().equals(Gui.class)) {
            String s = m.isEnabled() ? "Enabled" : "Disabled";
            RenderUtils.Notify(Type.OTHER, m.getName(), s, 2);
        }
    }

}