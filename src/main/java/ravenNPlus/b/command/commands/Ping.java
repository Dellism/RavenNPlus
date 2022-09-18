package ravenNPlus.b.command.commands;

import ravenNPlus.b.command.Command;
import ravenNPlus.b.utils.ChatHelper;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] {"p", "connection", "lag"});
    }

    @Override
    public void onCall(String[] args) {
        ChatHelper.checkPing();
    }

}