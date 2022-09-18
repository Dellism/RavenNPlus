package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;

public class Cls extends Command {
    public Cls() {
        super("cls", "Clears the Command Prompt", 0,0, new String[] {}, new String[] {"clr", "cls"});
    }

    @Override
    public void onCall(String[] args) {
        CommandPrompt.CommandPromtClear();
    }

}