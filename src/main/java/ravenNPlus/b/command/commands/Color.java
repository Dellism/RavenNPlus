package ravenNPlus.b.command.commands;

import ravenNPlus.b.clickgui.RavenNPlus.CommandPrompt;
import ravenNPlus.b.command.Command;

public class Color extends Command {
    public Color() {
        super("color", "change color of the Command Prompt", 1,1, new String[] {}, new String[] {"title", ""});
    }

    @Override
    public void onCall(String[] args) {
        CommandPrompt.titleColor = 0xff088000;
    }

}