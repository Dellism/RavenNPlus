package a.b.command.commands;

import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.module.modules.other.NameHider;

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
