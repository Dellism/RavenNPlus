package ravenNPlus.b.command.commands;

import ravenNPlus.b.command.Command;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.utils.version.Version;
import static ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt.print;

public class VersionCommand extends Command {

    public VersionCommand() {
        super("version", "tells you what build of "+RavenNPlus.name+" you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "which", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {
        Version clientVersion = RavenNPlus.versionManager.getClientVersion();
        Version latestVersion = RavenNPlus.versionManager.getLatestVersion();

        print("Your build: " + clientVersion);
        print("Latest version: " + latestVersion);
    }

}