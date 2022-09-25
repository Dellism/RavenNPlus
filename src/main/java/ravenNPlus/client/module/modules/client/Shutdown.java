package ravenNPlus.client.module.modules.client;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.Timer;
import ravenNPlus.client.utils.notifications.Notification;
import ravenNPlus.client.utils.notifications.Manager;
import ravenNPlus.client.utils.notifications.Type;

public class Shutdown extends Module {

    static SliderSetting status, delay;

    public Shutdown() {
        super("Shutdown", ModuleCategory.client, "Fake a crash");
        this.addSetting(status = new SliderSetting("Status", 1D, 0D,10D, 1D));
        this.addSetting(delay = new SliderSetting("Delay", 1D, 0D,50D, 1D));
    }

    @Override
    public void onEnable() {
        double s = status.getValue();
        Manager.show(new Notification(Type.WARNING, "Shutdown", "Minecraft Shutdowns soon...", 10));

        if(Timer.hasTimeElapsed((long)delay.getValue() * 50000L, false))
            System.exit((int) s);
    }

}