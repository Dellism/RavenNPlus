package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;
import ravenNPlus.b.main.RavenNPlus;

public class Debug extends Command {

    public Debug() {
        super("debug", "Toggles "+RavenNPlus.name+" debugger", 0, 0,  new String[] {},  new String[] {"dbg", "log"});
    }

    @Override
    public void onCall(String[] args) {
        RavenNPlus.debugger = !RavenNPlus.debugger;
        CommandPrompt.print((RavenNPlus.debugger ? "Enabled" : "Disabled") + " debugging.");
    }

}