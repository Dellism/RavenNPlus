package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.main.Otaku;
import a.b.utils.Utils;

public class Discord extends Command {
    public Discord() {
        super("discord", "Allows you to join the Raven B+ discord", 0, 3, new String[] {"copy", "open", "print"}, new String[] {"dc", "chat"});
    }

    @Override
    public void onCall(String[] args) {
        boolean opened = false;
        boolean copied = false;
        boolean showed = false;
        int argCurrent = 0;
        if(args.length == 0) {
            CommandPrompt.print("§3Opening " + Otaku.discord);
            Utils.Client.openWebpage(Otaku.discord);
            opened = true;
            return;
        }

        for (String argument : args) {
            if(argument.equalsIgnoreCase("copy")){
                if (!copied) {
                    Utils.Client.copyToClipboard(Otaku.discord);
                    copied = true;
                    CommandPrompt.print("Copied " + Otaku.discord + " to clipboard!");
                }
            }
            else if(argument.equalsIgnoreCase("open")){
                if (!opened) {
                    Utils.Client.openWebpage(Otaku.discord);
                    opened = true;
                    CommandPrompt.print("Opened invite link!");
                }
            }
            else if(argument.equalsIgnoreCase("print")){
                if (!showed){
                    CommandPrompt.print(Otaku.discord);
                    showed = true;
                }
            } else {
                if (argCurrent != 0)
                    this.incorrectArgs();
            }
            argCurrent++;
        }
    }
}
