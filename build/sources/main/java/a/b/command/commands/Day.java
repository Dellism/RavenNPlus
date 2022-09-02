package a.b.command.commands;

import a.b.command.Command;
import net.minecraft.client.Minecraft;

public class Day extends Command { public Day() { super("day", "Makes day", 0,0, new String[] {}, new String[] {"day"}); }

    @Override
    public void onCall(String[] args) {
        if(Minecraft.getMinecraft().isSingleplayer()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/time set day");
        } else {
            a.b.command.Command.addChatMessage("This is only available in Single-player");
        }
    }
}