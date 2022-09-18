package ravenNPlus.b.module.modules.combat;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.DescriptionSetting;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.utils.InvUtils;
import ravenNPlus.b.utils.RenderUtils;
import ravenNPlus.b.utils.Timer;
import ravenNPlus.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public static TickSetting mouse, background, onlySprint, onlySword, swing, drawEntity, drawHUD, silent;
    public static SliderSetting range, entityX, entityY, mode, entitySize, delay;
    public static DescriptionSetting modeMode;
    public static boolean viewModified = false;

    public KillAura() {
        super("KillAura", ModuleCategory.combat, "");
        this.addSetting(mode = new SliderSetting("Mode", 2D, 1D, 3D, 1D));
        this.addSetting(silent = new TickSetting("Silent", true));
        this.addSetting(swing = new TickSetting("Swing", true));
        this.addSetting(modeMode = new DescriptionSetting(Utils.md +""));
        this.addSetting(range = new SliderSetting("Range", 3.7D, 2D, 8D, 0.1D));
        this.addSetting(delay = new SliderSetting("Delay", 5, 0, 100, 1));
        this.addSetting(drawEntity = new TickSetting("Draw Entity", false));
        this.addSetting(drawHUD = new TickSetting("Draw HUD", true));
        this.addSetting(entityX = new SliderSetting("HUD X", 80D, 20D, mc.displayWidth+50, 1D));
        this.addSetting(entityY = new SliderSetting("HUD Y", 90D, 20D, mc.displayHeight+50, 1D));
        this.addSetting(entitySize = new SliderSetting("Entity Size", 35D, 10D, 100D, 1D));
        this.addSetting(background = new TickSetting("Background", true));
        this.addSetting(mouse = new TickSetting("Mouse X and Y", false));
        this.addSetting(onlySword = new TickSetting("Only Sword", true));
        this.addSetting(onlySprint = new TickSetting("Only Sprint", false));
    }

    @SubscribeEvent
    public void r(TickEvent.PlayerTickEvent p) {
        if (!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int) range.getInput() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
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

        if(onlySword.isToggled())
            if(!InvUtils.isPlayerHoldingWeapon()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(mode.getInput() == 2D) {
            if(Timer.hasTimeElapsed((long) delay.getInput() * 5, true)) {
                Utils.Player.aim(target, 0.0F, false, silent.isToggled());

                if(swing.isToggled()) {
                    Utils.Player.swing();
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }

                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }

            String lastAttack = String.valueOf(target.getLastAttacker());
            if(Objects.equals(lastAttack, mc.thePlayer.getName())) {
            //  Utils.Player.swing();
            Utils.Player.SlowSwing(60);
            }
        }

        if(mode.getInput() == 4D) {
            if(target.getDistanceToEntity(mc.thePlayer) < (int) range.getInput()) {
                mc.setRenderViewEntity(target);
                viewModified = true;
            } else {
                mc.setRenderViewEntity(mc.thePlayer);
                viewModified = false;
            }
        }

    }

    @Override
    public void onDisable() {
        //  Utils.Player.swing();
        if(swing.isToggled())
            Utils.Player.SlowSwing(60);

        if(mode.getInput() == 4D && viewModified) {
            mc.setRenderViewEntity(mc.thePlayer);
        }
    }


    @SubscribeEvent
    public void p(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < (int)range.getInput() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
        if(targets.isEmpty()) return;

        EntityLivingBase target = (EntityLivingBase) targets.get(0);
        if(mc.getSession().getUsername() == target.getName()) return;
        if(target.getName().contains("Empty")) return;
        if(target.getName().contains(" ")) return;
        if(target.getName().startsWith(":")) return;

        int xxx  = (int) entityX.getInput();
        int yyy  = (int) entityY.getInput();
        int rang = (int) range.getInput();
        int size  = (int) entitySize.getInput();

        if(onlySword.isToggled())
            if(!InvUtils.isPlayerHoldingWeapon()) return;

        if(onlySprint.isToggled())
            if(!mc.thePlayer.isSprinting()) return;

        if(drawHUD.isToggled())
            RenderUtils.drawStringHUD(xxx, yyy, rang, background.isToggled(), true, false);

        if(drawEntity.isToggled())
            RenderUtils.drawEntityHUD(target, xxx, yyy, xxx + 50, yyy + 50, size, rang, true, background.isToggled(), mouse.isToggled());
    }

    public void guiUpdate() {
        switch((int) mode.getInput()) {
            case 1:
                modeMode.setDesc(Utils.md + "Legit");
                break;
            case 2:
                modeMode.setDesc(Utils.md + "Packets");
                break;
            case 3:
                modeMode.setDesc(Utils.md + "More soon...");
                break;
            case 4:
                modeMode.setDesc(Utils.md + "Test");
                break;
        }
    }

}