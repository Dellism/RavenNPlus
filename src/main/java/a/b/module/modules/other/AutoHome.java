package a.b.module.modules.other;

import a.b.module.Module;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Timer;
import a.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoHome extends Module {

    public static SliderSetting range;
    public static SliderSetting mode;
    public static DescriptionSetting modeMode;
    public static SliderSetting delay;

    public AutoHome() {
        super("AutoHome", ModuleCategory.other);
        this.registerSetting(range = new SliderSetting("Range", 5, 1, 50, 1));
        this.registerSetting(mode = new SliderSetting("Mode", 1, 1, 5, 1));
        this.registerSetting(modeMode = new DescriptionSetting(Utils.md+""));
        this.registerSetting(delay = new SliderSetting("Delay", 0, 0, 100, 1));
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int)range.getInput() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
        if(targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);

        //home
        if(mode.getInput() == 1) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getInput()) {
                if(Timer.hasTimeElapsed((long) delay.getInput(), true))
                    mc.thePlayer.sendChatMessage("/home");
            }
        }

        //back
        if(mode.getInput() == 2) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getInput()) {
                if(Timer.hasTimeElapsed((long) delay.getInput(), true))
                    mc.thePlayer.sendChatMessage("/back");
            }
        }

        //is
        if(mode.getInput() == 3) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getInput()) {
                if(Timer.hasTimeElapsed((long) delay.getInput(), true))
                    mc.thePlayer.sendChatMessage("/is");
            }
        }

        //spawn
        if(mode.getInput() == 4) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getInput()) {
                if(Timer.hasTimeElapsed((long) delay.getInput(), true))
                    mc.thePlayer.sendChatMessage("/spawn");
            }
        }

        //hub
        if(mode.getInput() == 5) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getInput()) {
                if(Timer.hasTimeElapsed((long) delay.getInput(), true))
                    mc.thePlayer.sendChatMessage("/hub");
            }
        }

    }


    public void guiUpdate() {
        switch((int) mode.getInput()) {
            case 1:
                modeMode.setDesc(Utils.md + "/home");
                break;
            case 2:
                modeMode.setDesc(Utils.md + "/back");
                break;
            case 3:
                modeMode.setDesc(Utils.md + "/is");
                break;
            case 4:
                modeMode.setDesc(Utils.md + "/spawn");
                break;
            case 5:
                modeMode.setDesc(Utils.md + "/hub");
                break;
        }
    }

}