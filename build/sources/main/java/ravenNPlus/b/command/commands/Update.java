package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.utils.Utils;
import ravenNPlus.b.utils.version.Version;
import java.net.MalformedURLException;
import java.net.URL;

public class Update extends Command {

    public Update() {
        super("update", "Assists you in updating the client", 0, 0, new String[] {}, new String[] {"upgrade", "updating"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = RavenNPlus.versionManager.getClientVersion();
        Version latestVersion = RavenNPlus.versionManager.getLatestVersion();

        if (latestVersion.isNewerThan(clientVersion)) {
            CommandPrompt.print("Opening page...");
            URL url = null;
            try {
                url = new URL(RavenNPlus.sourceLocation);
                Utils.Client.openWebpage(url);
                Utils.Client.openWebpage(new URL(RavenNPlus.downloadLocation));
                CommandPrompt.print("Opened page successfully!");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                CommandPrompt.print("Failed to open page! Please report this bug in "+RavenNPlus.name+" discord!");

            }
        } else {
            CommandPrompt.print("No need to upgrade, You are on the latest build");
        }
    }

}