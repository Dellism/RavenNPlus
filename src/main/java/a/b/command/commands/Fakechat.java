package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.module.modules.other.FakeChat;
import a.b.utils.Utils;

public class Fakechat extends Command {
    public Fakechat() {
        super(FakeChat.command, "Sends a message in chat", 1, 600,  new String[] {"text"},  new String[] {"fkct"});
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            this.incorrectArgs();
            return;
        }

        String n;
        String c = Utils.Java.joinStringList(args, " ");
        n = c.replaceFirst(FakeChat.command, "").substring(1);
        if (n.isEmpty() || n.equals("\\n")) {
            CommandPrompt.print(FakeChat.c4);
            return;
        }

        FakeChat.msg = n;
        CommandPrompt.print("Message set!");
    }
}
