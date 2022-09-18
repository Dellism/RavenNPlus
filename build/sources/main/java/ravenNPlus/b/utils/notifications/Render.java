package ravenNPlus.b.utils.notifications;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.modules.client.GuiClick;
import ravenNPlus.b.utils.RenderUtils;
import ravenNPlus.b.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Render {

    public static final Render notificationRenderer = new Render();

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if(GuiClick.notifications.isToggled()) Manager.render();
    }

    public static void change(Module m) {
        if(!GuiClick.notifications.isToggled() || Minecraft.getMinecraft().currentScreen != null || !Utils.Player.isPlayerInGame()) return;

        String s = m.isEnabled() ? "enabled" : "disabled";
        RenderUtils.Notify(Type.OTHER, m.getName(), s, 2);
    }

}