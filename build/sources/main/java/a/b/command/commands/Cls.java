package a.b.command.commands;

import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;

public class Cls extends Command {
    public Cls() {
        super("cls", "Clears the Command Prompt", 0,0, new String[] {}, new String[] {"clr", "cls"});
    }

    @Override
    public void onCall(String[] args) {
        CommandPrompt.CommandPromtClear();
    }
}