package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;
import ravenNPlus.b.main.RavenNPlus;
import ravenNPlus.b.utils.Utils;

public class Discord extends Command {
    public Discord() {
        super("discord", "Allows you to join the "+RavenNPlus.name+" discord", 0, 3, new String[] {"copy", "open", "print"}, new String[] {"dc", "chat"});
    }

    @Override
    public void onCall(String[] args) {
        boolean opened = false;
        boolean copied = false;
        boolean showed = false;
        int argCurrent = 0;
        if(args.length == 0) {
            CommandPrompt.print("§3Opening " + RavenNPlus.discord);
            Utils.Client.openWebpage(RavenNPlus.discord);
            opened = true;
            return;
        }

        for (String argument : args) {
            if(argument.equalsIgnoreCase("copy")){
                if (!copied) {
                    Utils.Client.copyToClipboard(RavenNPlus.discord);
                    copied = true;
                    CommandPrompt.print("Copied " + RavenNPlus.discord + " to clipboard!");
                }
            }
            else if(argument.equalsIgnoreCase("open")){
                if (!opened) {
                    Utils.Client.openWebpage(RavenNPlus.discord);
                    opened = true;
                    CommandPrompt.print("Opened invite link!");
                }
            }
            else if(argument.equalsIgnoreCase("print")){
                if (!showed){
                    CommandPrompt.print(RavenNPlus.discord);
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