package ravenNPlus.b.module.modules.movement;

import io.netty.util.internal.ThreadLocalRandom;
import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoHeader extends Module {
    public static TickSetting cancelDuringShift, onlyWhenHoldingSpacebar;
    public static SliderSetting pbs;
    private double startWait;

    public AutoHeader() {
        super("AutoHeadHitter", ModuleCategory.movement, "Spams spacebar when under blocks");
        this.addSetting(cancelDuringShift = new TickSetting("Cancel if snkeaing", true));
        this.addSetting(onlyWhenHoldingSpacebar = new TickSetting("Only when holding jump", true));
        this.addSetting(pbs = new SliderSetting("Jump Presses per second", 12, 1, 20, 1));

        boolean jumping = false;
    }

    @Override
    public void onEnable(){
        startWait = System.currentTimeMillis();
        super.onEnable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;
        if (cancelDuringShift.isToggled() && mc.thePlayer.isSneaking())
            return;

        if(onlyWhenHoldingSpacebar.isToggled()){
            if(!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())){
                return;
            }
        }


        if (Utils.Player.playerUnderBlock() && mc.thePlayer.onGround){
            if(startWait + (1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.543543, pbs.getInput() + 1.32748923)) < System.currentTimeMillis()){
                mc.thePlayer.jump();
                startWait = System.currentTimeMillis();
            }
        }

    }
}
