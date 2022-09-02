package a.b.command.commands;

import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.module.modules.minigames.DuelsStats;

public class Nick extends Command {
    public Nick() {
        super("nick", "Like nickhider mod", 1, 1,  new String[] {"the new name"},  new String[] {"nk", "nickhider"});
    }

    @Override
    public void onCall(String[] args){
        if (args.length == 0) {
            this.incorrectArgs();
            return;
        }

        DuelsStats.playerNick = args[0];
        CommandPrompt.print("&aNick has been set to: " + DuelsStats.playerNick);
    }
}