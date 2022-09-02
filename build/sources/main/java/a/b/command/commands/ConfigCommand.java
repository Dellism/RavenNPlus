package a.b.command.commands;

import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.config.Config;
import a.b.main.Otaku;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "Manages configs", 0, 3, new String[] {"load,save,list,remove,clear", "config's name"}, new String[] {"cfg", "profiles"});
    }

    @Override
    public void onCall(String[] args){
        if(Otaku.clientConfig != null){
            Otaku.clientConfig.saveConfig();
            Otaku.configManager.save(); // as now configs only save upon exiting the gui, this is required
        }

        if (args.length == 0) {
            CommandPrompt.print("Current config: " + Otaku.configManager.getConfig().getName());
        }

        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                this.listConfigs();
            } else if(args[0].equalsIgnoreCase("clear")){
                CommandPrompt.print("Are you sure you want to reset the config " + Otaku.configManager.getConfig().getName() + "? If so, run \"config clear confirm\"");
            }
            else {
                this.incorrectArgs();
            }
        }

        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("list")) {
                this.listConfigs();
            }

            else if (args[0].equalsIgnoreCase("load")) {
                boolean found = false;
                for (Config config : Otaku.configManager.getConfigs()) {
                    if (config.getName().equalsIgnoreCase(args[1])) {
                        found = true;
                        CommandPrompt.print("Found config with the name " + args[1] + "!");
                        Otaku.configManager.setConfig(config);
                        CommandPrompt.print("Loaded config!");
                    }
                }

                if (!found) {
                    CommandPrompt.print("Unable to find a config with the name " + args[1]);
                }

            }
            else if (args[0].equalsIgnoreCase("save")) {
                CommandPrompt.print("Saving...");
                Otaku.configManager.copyConfig(Otaku.configManager.getConfig(), args[1] + ".bplus");
                CommandPrompt.print("Saved as \"" + args[1] + "\"! To load the config, run \"config load " + args[1] + "\"");

            }
            else if (args[0].equalsIgnoreCase("remove")) {
                boolean found = false;
                CommandPrompt.print("Removing " + args[1] + "...");
                for(Config config : Otaku.configManager.getConfigs()){
                    if(config.getName().equalsIgnoreCase(args[1])){
                        Otaku.configManager.deleteConfig(config);
                        found = true;
                        CommandPrompt.print("Removed " + args[1] + " successfully! Current config: " + Otaku.configManager.getConfig().getName());
                        break;
                    }
                }

                if(!found) {
                    CommandPrompt.print("Failed to delete " + args[1] + ". Unable to find a config with the name or an error occurred during removal");
                }

            } else if(args[0].equalsIgnoreCase("clear")) {
                if(args[1].equalsIgnoreCase("confirm")){
                    Otaku.configManager.resetConfig();
                    Otaku.configManager.save();
                    CommandPrompt.print("Cleared config!");
                } else {
                    CommandPrompt.print("It is confirm, not " + args[1]);
                }

            }else {
                this.incorrectArgs();
            }
        }
    }

    public void listConfigs() {
       CommandPrompt.print("Available configs: ");
        for (Config config : Otaku.configManager.getConfigs()) {
            if (Otaku.configManager.getConfig().getName().equals(config.getName()))
                CommandPrompt.print("Current config: " + config.getName());
            else
                CommandPrompt.print(config.getName());
        }
    }
}
