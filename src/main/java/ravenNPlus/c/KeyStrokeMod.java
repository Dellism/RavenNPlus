package ravenNPlus.c;

import ravenNPlus.b.main.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod( modid = "ravenNPlus", name = "KeystrokesMod", version = "SLK3", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true )

public class KeyStrokeMod {
    private static KeyStroke keyStroke;
    private static KeyStrokeRenderer keyStrokeRenderer = new KeyStrokeRenderer();
    private static boolean isKeyStrokeConfigGuiToggled = false;
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new KeyStrokeCommand());
        MinecraftForge.EVENT_BUS.register(new KeyStrokeRenderer());
        MinecraftForge.EVENT_BUS.register(this);
        ClientConfig.applyKeyStrokeSettingsFromConfigFile();
        ravenNPlus.b.utils.profile.file_1035K0_X.func_1034X1_ST("FDP");
    }

    public static KeyStroke getKeyStroke() {
        return keyStroke;
    }

    public static KeyStrokeRenderer getKeyStrokeRenderer() {
        return keyStrokeRenderer;
    }

    public static void toggleKeyStrokeConfigGui() {
        isKeyStrokeConfigGuiToggled = true;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e){
        if (isKeyStrokeConfigGuiToggled) {
            isKeyStrokeConfigGuiToggled = false;
            Minecraft.getMinecraft().displayGuiScreen(new KeyStrokeConfigGui());
        }
    }

}