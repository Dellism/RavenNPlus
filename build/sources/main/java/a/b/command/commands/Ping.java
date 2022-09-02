package a.b.command.commands;

import a.b.command.Command;
import a.b.utils.ChatHelper;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] {"p", "connection", "lag"});
    }

    @Override
    public void onCall(String[] args) {
        ChatHelper.checkPing();
    }
}
