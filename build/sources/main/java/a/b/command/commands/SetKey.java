package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.main.Otaku;
import a.b.utils.Utils;

public class SetKey extends Command {
    public SetKey() {
        super("setkey", "Sets hypixel's API key. To get a new key, run `/api new`", 2, 2, new String[] {"key"}, new String[] {"apikey"});
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
        Otaku.getExecutor().execute(() -> {
            if (Utils.URLS.isHypixelKeyValid(n)) {
                Utils.URLS.hypixelApiKey = n;
                CommandPrompt.print("Success!");
                Otaku.clientConfig.saveConfig();
            } else {
                CommandPrompt.print("Invalid key.");
            }

        });

    }
}
