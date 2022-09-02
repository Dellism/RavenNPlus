package a.b.module.modules.client;

import a.b.module.Module;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class discrdpr extends Module {

    public discrdpr() {
        super("DiscordRPC", ModuleCategory.client);
    }

    DiscordRichPresence presence = new DiscordRichPresence();
    String status = "Chilling";

    @Override
    public void onEnable() {
        String applicationId = "1010880713551269988";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        DiscordRPC.discordInitialize(applicationId, handlers, true, steamId);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        DiscordRPC.discordUpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public void onTick() {
        if(mc.thePlayer == null && mc.theWorld == null) {
            DiscordRPC.discordShutdown();
        }

        if(Minecraft.getMinecraft().isSingleplayer()) {
            status = "Singleplayer";
        } else if (!Minecraft.getMinecraft().isSingleplayer()) {
            status = Minecraft.getMinecraft().getCurrentServerData().serverIP.toUpperCase();
        }

        presence.state = "Status: "+status;
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
    }

}