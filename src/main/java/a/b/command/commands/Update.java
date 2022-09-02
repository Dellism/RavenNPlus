package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.main.Otaku;
import a.b.utils.Utils;
import a.b.utils.version.Version;

import java.net.MalformedURLException;
import java.net.URL;

public class Update extends Command {
    public Update() {
        super("update", "Assists you in updating the client", 0, 0, new String[] {}, new String[] {"upgrade"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = Otaku.versionManager.getClientVersion();
        Version latestVersion = Otaku.versionManager.getLatestVersion();

        if (latestVersion.isNewerThan(clientVersion)) {
            CommandPrompt.print("Opening page...");
            URL url = null;
            try {
                url = new URL(Otaku.sourceLocation);
                Utils.Client.openWebpage(url);
                Utils.Client.openWebpage(new URL(Otaku.downloadLocation));
                CommandPrompt.print("Opened page successfully!");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                CommandPrompt.print("Failed to open page! Please report this bug in Raven b+'s discord!");

            }
        } else {
            CommandPrompt.print("No need to upgrade, You are on the latest build");
        }
    }
}
