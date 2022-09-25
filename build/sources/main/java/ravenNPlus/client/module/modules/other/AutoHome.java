package ravenNPlus.client.module.modules.other;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.utils.Timer;
import ravenNPlus.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoHome extends Module {

    public static SliderSetting range, mode, delay;
    public static DescriptionSetting modeMode;

    public AutoHome() {
        super("AutoHome", ModuleCategory.other, "Auto TPs home when a entity is in a range");
        this.addSetting(range = new SliderSetting("Range", 5, 1, 50, 1));
        this.addSetting(mode = new SliderSetting("Mode", 1, 1, 5, 1));
        this.addSetting(modeMode = new DescriptionSetting(Utils.md+""));
        this.addSetting(delay = new SliderSetting("Delay", 0, 0, 50, 1));
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int) range.getValue() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
        if (targets.isEmpty()) return;
        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(mc.getSession().getUsername() == target.getName()) return;
        if(target.getName().contains("Empty")) return;
        if(target.getName().contains(" ")) return;
        if(target.getName().contains(":")) return;
        if(target.getName().contains("-")) return;
        if(target.getName().contains("!")) return;
        if(target.getName().contains("BOT")) return;
        if(target.getName().contains("?")) return;
        if(target.getName().contains("=")) return;
        if(target.getName().contains("§")) return;
        if(target.getName().startsWith("CIT-")) return;
        if(target.getName().length() > 3) return;
        if(target.getName().isEmpty()) return;

        if(mode.getValue() == 1) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getValue()) {
                if(Timer.hasTimeElapsed((long) delay.getValue()*5, true))
                    mc.thePlayer.sendChatMessage("/home");
            }
        }

        if(mode.getValue() == 2) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getValue()) {
                if(Timer.hasTimeElapsed((long) delay.getValue()*5, true))
                    mc.thePlayer.sendChatMessage("/back");
            }
        }

        if(mode.getValue() == 3) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getValue()) {
                if(Timer.hasTimeElapsed((long) delay.getValue()*5, true))
                    mc.thePlayer.sendChatMessage("/is");
            }
        }

        if(mode.getValue() == 4) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getValue()) {
                if(Timer.hasTimeElapsed((long) delay.getValue()*5, true))
                    mc.thePlayer.sendChatMessage("/spawn");
            }
        }

        if(mode.getValue() == 5) {
            if(target.getDistanceToEntity(mc.thePlayer) < range.getValue()) {
                if(Timer.hasTimeElapsed((long) delay.getValue()*5, true))
                    mc.thePlayer.sendChatMessage("/hub");
            }
        }
    }

    public void guiUpdate() {
        switch((int) mode.getValue()) {
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