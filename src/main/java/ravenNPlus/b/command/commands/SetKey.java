package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.utils.Utils;

public class SetKey extends Command {
    public SetKey() {
        super("setkey", "Sets Hypixel's API key. To get a new key, run `/api new`", 2, 2, new String[] {"key"}, new String[] {"apikey"});
    }

    @Override
    public void onCall(String[] args) {
        if(args.length == 0) {
            this.incorrectArgs();
            return;
        }

        CommandPrompt.print("Setting...");
        String n;
        n = args[0];
        RavenNPlus.getExecutor().execute(() -> {
            if (Utils.URLS.isHypixelKeyValid(n)) {
                Utils.URLS.hypixelApiKey = n;
                CommandPrompt.print("Success!");
                RavenNPlus.clientConfig.saveConfig();
            } else {
                CommandPrompt.print("Invalid key.");
            }

        });

    }

}