package a.b.main;

import a.b.module.modules.client.GuiModule;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import a.b.utils.version.VersionManager;
import a.b.clickgui.otaku.ClickGui;
import a.b.command.CommandManager;
import a.b.config.ConfigManager;
import a.b.module.Module;
import a.b.module.ModuleManager;
import a.b.module.modules.HUD;
import a.b.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static a.b.utils.Utils.mc;

public class Otaku {
   public static boolean debugger = false;
   public static final VersionManager versionManager  = new VersionManager();
   public static CommandManager commandManager;
   public static final String sourceLocation = " ";
   public static final String downloadLocation = " ";
   public static final String discord = "https://discord.gg/WBApubEaVv";
   public static String[] updateText = {"Your version of Otaku ( " + versionManager.getClientVersion().toString() + " ) is outdated!"};
   public static ConfigManager configManager;
   public static ClientConfig clientConfig;
   public static final ModuleManager moduleManager = new ModuleManager();
   public static ClickGui clickGui;
   private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);
   public static ResourceLocation mResourceLocation;
   public static final String osName, osArch;
   public static a.b.utils.font.CustomFontrenderer customFontRenderer;
   public static DiscordRichPresence pres = new DiscordRichPresence();
   static String status = "Menu";

    static {
      osName = System.getProperty("os.name").toLowerCase();
      osArch = System.getProperty("os.arch").toLowerCase();
   }

   public static void init() {

      pres.state = "Status: " + status;

      MinecraftForge.EVENT_BUS.register(new Otaku());
      MinecraftForge.EVENT_BUS.register(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.register(new MouseManager());
      MinecraftForge.EVENT_BUS.register(new ChatHelper());
      Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));
      SoundUtil.start();

      InputStream ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/a/otaku.png");
      BufferedImage bf;
      try {
         assert ravenLogoInputStream != null;
         bf = ImageIO.read(ravenLogoInputStream);
         mResourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("otaku", new DynamicTexture(bf));
      } catch (IOException | IllegalArgumentException | NullPointerException noway) {
         noway.printStackTrace();
         mResourceLocation = null;
      }

      commandManager = new CommandManager();
      clickGui = new ClickGui();
      configManager = new ConfigManager();
      clientConfig = new ClientConfig();
      clientConfig.applyConfig();

      ex.execute(() -> {
         try {
            LaunchTracker.registerLaunch();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });

      String applicationId = "1010880713551269988";
      String steamId = "";
      DiscordEventHandlers handlers = new DiscordEventHandlers();
      pres.state = "Status: " + status;
      handlers.ready = (user) -> System.out.println("Ready!");

      if(GuiModule.monkeee.isToggled()) {
         pres.largeImageKey = "monkeeee_-_kopie";
         DiscordRPC.discordUpdatePresence(pres);
      } else {
         pres.largeImageKey = "nicepng_gurren-lagann-png_1684110";
         DiscordRPC.discordUpdatePresence(pres);
      }

      pres.largeImageText = "discord.gg/KpgaCsZxSN";

      DiscordRPC.discordInitialize(applicationId, handlers, true, steamId);
      DiscordRPC.discordUpdatePresence(pres);
      pres.startTimestamp = System.currentTimeMillis() / 1000;
      new Thread(() -> {
         while (!Thread.currentThread().isInterrupted()) {
            DiscordRPC.discordRunCallbacks();
            try {
               Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
         }
      }, "RPC-Callback-Handler").start();
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame()) {
            for (int i = 0; i < moduleManager.numberOfModules(); i++) {
               Module module = moduleManager.getModules().get(i);
               if (Minecraft.getMinecraft().currentScreen == null) {
                  module.keybind();
               } else if (Minecraft.getMinecraft().currentScreen instanceof ClickGui) {
                  module.guiUpdate();
               }

               if (module.isEnabled()) module.update();
            }
         }
      }

      if(mc.theWorld == null) {
         status = "Menu";
         DiscordRPC.discordUpdatePresence(pres);
      }

      if(mc.isSingleplayer()) {
         status = "Singleplayer";
         DiscordRPC.discordUpdatePresence(pres);
      }

      if(!mc.isSingleplayer() && Utils.Player.isPlayerInGame() && mc.theWorld != null) {
         status = mc.getCurrentServerData().serverIP.toLowerCase();
         DiscordRPC.discordUpdatePresence(pres);
      }

      if(GuiModule.monkeee.isToggled()) {
         pres.largeImageKey = "monkeeee_-_kopie";
         DiscordRPC.discordUpdatePresence(pres);
      } else {
         pres.largeImageKey = "nicepng_gurren-lagann-png_1684110";
         DiscordRPC.discordUpdatePresence(pres);
      }
   }

   @SuppressWarnings("unused")
   @SubscribeEvent
   public void onChatMessageReceived(ClientChatReceivedEvent event) {
      if (Utils.Player.isPlayerInGame()) {
         String msg = event.message.getUnformattedText();

         if (msg.startsWith("Your new API key is")) {
            Utils.URLS.hypixelApiKey = msg.replace("Your new API key is ", "");
            Utils.Player.sendMessageToSelf("&aSet api key to " + Utils.URLS.hypixelApiKey + "!");
            clientConfig.saveConfig();
         }
      }
   }

   public static ScheduledExecutorService getExecutor() { return ex; }

}