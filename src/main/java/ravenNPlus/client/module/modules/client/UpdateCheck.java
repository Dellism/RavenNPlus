package ravenNPlus.client.module.modules.client;

import ravenNPlus.client.main.Client;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ravenNPlus.client.utils.version.Version;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UpdateCheck extends Module {

    public static DescriptionSetting howToUse;
    public static TickSetting copyToClipboard;
    public static TickSetting openLink;
    private final ExecutorService executor;
    private final Runnable task;

    private Future<?> f;

    public UpdateCheck() {
        super("Update", ModuleCategory.client, "Update your client");

        this.addSetting(howToUse = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": update"));
        this.addSetting(copyToClipboard = new TickSetting("Copy to clipboard", true));
        this.addSetting(openLink = new TickSetting("Open dl in browser", true));

        executor = Executors.newFixedThreadPool(1);
        task = () -> {
            Version latest = Client.versionManager.getLatestVersion();
            Version current = Client.versionManager.getClientVersion();
            if (latest.isNewerThan(current)) {
                Utils.Player.sendMessageToSelf("The current version or RavenNPlus is outdated. Visit https://discord.gg/WBApubEaVv to download the latest version.");
                Utils.Player.sendMessageToSelf("https://discord.gg/WBApubEaVv");
            }

            if (current.isNewerThan(latest)) {
                Utils.Player.sendMessageToSelf("You are on a beta build of raven");
                Utils.Player.sendMessageToSelf("https://discord.gg/WBApubEaVv");
            } else {
                Utils.Player.sendMessageToSelf("You are on the latest public version!");
            }

            if (copyToClipboard.isToggled())
                if (Utils.Client.copyToClipboard(Client.downloadLocation))
                    Utils.Player.sendMessageToSelf("Successfully copied download link to clipboard!");

            Utils.Player.sendMessageToSelf(Client.sourceLocation);

            if (openLink.isToggled()) {
                try {
                    URL url = new URL(Client.sourceLocation);
                    Utils.Client.openWebpage(url);
                    Utils.Client.openWebpage(new URL(Client.downloadLocation));
                } catch (MalformedURLException err) {
                    err.printStackTrace();
                    Utils.Player.sendMessageToSelf("&cFailed to open page! Please report this bug in RavenNPlus discord");
                }
            }

            this.disable();
        };
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (f == null) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        } else if (f.isDone()) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        }
    }

}