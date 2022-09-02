package a.b.command.commands;

import a.b.command.Command;
import a.b.main.Otaku;
import a.b.utils.version.Version;

import static a.b.clickgui.otaku.CommandPrompt.print;

public class VersionCommand extends Command {
    public VersionCommand() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = Otaku.versionManager.getClientVersion();
        Version latestVersion = Otaku.versionManager.getLatestVersion();

        print("Your build: " + clientVersion);
        print("Latest version: " + latestVersion);

    }
}
