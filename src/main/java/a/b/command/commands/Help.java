package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.main.Otaku;

public class Help extends Command {
    public Help() {
        super("help", "Shows you different command usages", 0, 1, new String[] {"name of module"}, new String[] {"?", "wtf", "what"});
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            Otaku.commandManager.sort();

            CommandPrompt.print("Available commands:");
            int index = 1;
            for (Command command : Otaku.commandManager.getCommandList()) {
                if(command.getName().equalsIgnoreCase("help"))
                    continue;

                CommandPrompt.print(index + ") " + command.getName());
                index++;
            }

            CommandPrompt.print("Run \"help commandname\" for more information about the command");
        } else if (args.length == 1) {
            Command command = Otaku.commandManager.getCommandByName(args[0]);
            if (command == null) {
                CommandPrompt.print("Unable to find a command with the cname or alias with " + args[0]);
                return;
            }

            CommandPrompt.print(command.getName() + "'s info:");
            if(command.getAliases() != null || command.getAliases().length != 0) {
                CommandPrompt.print(command.getName() + "'s aliases:");
                for (String alias : command.getAliases()) {
                    CommandPrompt.print(alias);
                }
            }

            if(!command.getHelp().isEmpty()) {
                CommandPrompt.print(command.getName() + "'s description:");
                CommandPrompt.print(command.getHelp());
            }

            if(command.getArgs() != null) {
                CommandPrompt.print(command.getName() + "'s argument description:");
                CommandPrompt.print("Min args: " + command.getMinArgs() + ", max args: " + command.getMaxArgs());
                int argIndex = 1;
                int printLine;
                for (String argText : command.getArgs()){
                    CommandPrompt.print("Argument " + argIndex + ": " + argText);
                    argIndex++;
                }
            }

        }
    }
}
