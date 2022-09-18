package ravenNPlus.b.module.modules.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.TickSetting;

public class Icons extends Module {

    public static TickSetting Icon_client, Icon_move, Icon_comb, Icon_player;
    public static TickSetting Icon_hotkey, Icon_render, Icon_other, Icon_mini;

    public Icons() {
        super("Icons", ModuleCategory.client, "Shows Category Icons");
        this.addSetting(Icon_client = new TickSetting("Client", true));
        this.addSetting(Icon_move = new TickSetting("Movement", true));
        this.addSetting(Icon_comb = new TickSetting("Combat", true));
        this.addSetting(Icon_player = new TickSetting("Player", true));
        this.addSetting(Icon_hotkey = new TickSetting("Hotkey", true));
        this.addSetting(Icon_render = new TickSetting("Render", true));
        this.addSetting(Icon_other = new TickSetting("Other", true));
        this.addSetting(Icon_mini = new TickSetting("Minigame", true));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {



    }

}