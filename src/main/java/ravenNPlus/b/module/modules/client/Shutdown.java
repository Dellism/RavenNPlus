package ravenNPlus.b.module.modules.client;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.notifications.Notification;
import ravenNPlus.b.utils.notifications.Manager;
import ravenNPlus.b.utils.notifications.Type;

public class Shutdown extends Module {

    static SliderSetting status, delay;

    public Shutdown() {
        super("Shutdown", ModuleCategory.client, "Fake a crash");
        this.addSetting(status = new SliderSetting("Status", 1D, 0D,10D, 1D));
        this.addSetting(delay = new SliderSetting("Delay", 1D, 0D,50D, 1D));
    }

    @Override
    public void onEnable() {
        double s = status.getInput();
        Notification not = new Notification(Type.WARNING, "Shutdown", "Minecraft Shutwons soon...", 10);
        Manager.show(not);
        if(Timer.hasTimeElapsed((long)delay.getInput() * 1000L, true))
            System.exit((int) s);
    }

}