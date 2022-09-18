package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;
import ravenNPlus.b.module.modules.other.NameHider;

public class Cname extends Command {
    public Cname() {
        super("cname", "Hides your name client-side", 1, 1, new String[] {"New name"}, new String[] {"cn", "changename"});
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            this.incorrectArgs();
            return;
        }

        NameHider.n = args[0];
        CommandPrompt.print("Nick has been set to: " +  NameHider.n);
    }

}