package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.main.Otaku;

public class Debug extends Command {
    public Debug() {
        super("debug", "Toggles B+ debbugger", 0, 0,  new String[] {},  new String[] {"dbg", "log"});
    }

    @Override
    public void onCall(String[] args) {
        Otaku.debugger = !Otaku.debugger;
        CommandPrompt.print((Otaku.debugger ? "Enabled" : "Disabled") + " debugging.");
    }
}
