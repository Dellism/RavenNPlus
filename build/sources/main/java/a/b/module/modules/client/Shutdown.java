package a.b.module.modules.client;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Timer;
import a.b.utils.notifications.Notification;
import a.b.utils.notifications.NotificationManager;
import a.b.utils.notifications.NotificationType;

public class Shutdown extends Module {

    static SliderSetting status, delay;
    static DescriptionSetting desc;

    public Shutdown() {
        super("Shutdown", ModuleCategory.client);
        this.registerSetting(desc = new DescriptionSetting("Fake a crash"));
        this.registerSetting(status = new SliderSetting("Status", 1D, 0D,10D, 1D));
        this.registerSetting(delay = new SliderSetting("Delay", 1D, 0D,50D, 1D));
    }

    @Override
    public void onEnable() {
        double s = status.getInput();
        Notification not = new Notification(NotificationType.WARNING, "Shutdown", "Minecraft Shutwons soon...", 10);
        NotificationManager.show(not);
        if(Timer.hasTimeElapsed((long)delay.getInput() * 1000L, true))
            System.exit((int) s);
    }

}