//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package a.c;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class KeyStrokeCommand extends CommandBase {
   public String getCommandName() {
      return "a";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      KeyStrokeMod.toggleKeyStrokeConfigGui();
   }

   public String getCommandUsage(ICommandSender sender) {
      return "/a";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }
}
