package ravenNPlus.b.module.modules.movement;

import ravenNPlus.b.module.Module;
import ravenNPlus.b.module.setting.impl.SliderSetting;
import ravenNPlus.b.module.setting.impl.TickSetting;
import ravenNPlus.b.module.modules.combat.AntiBot;
import ravenNPlus.b.utils.SoundUtil;
import ravenNPlus.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import java.util.Iterator;

public class SlyPort extends Module {
    public static SliderSetting r;
    public static TickSetting b;
    public static TickSetting d;
    public static TickSetting e;
    private final boolean s = false;

    public SlyPort() {
        super("SlyPort", ModuleCategory.movement, "Teleport behind enemies");
        this.addSetting(r = new SliderSetting("Range", 6.0D, 2.0D, 15.0D, 1.0D));
        this.addSetting(e = new TickSetting("Aim", true));
        this.addSetting(b = new TickSetting("Play sound", true));
        this.addSetting(d = new TickSetting("Players only", true));
    }

    public void onEnable() {
        Entity en = this.ge();
        if (en != null) {
            this.tp(en);
        }

        this.disable();
    }

    private void tp(Entity en) {
        if (b.isToggled()) {
            SoundUtil.play(SoundUtil.endermenPortal, 1F, 1F);
        }

        Vec3 vec = en.getLookVec();
        double x = en.posX - vec.xCoord * 2.5D;
        double z = en.posZ - vec.zCoord * 2.5D;
        mc.thePlayer.setPosition(x, mc.thePlayer.posY, z);
        if (e.isToggled()) {
            Utils.Player.aim(en, 0.0F, false, false);
        }

    }

    private Entity ge() {
        Entity en = null;
        double r = Math.pow(SlyPort.r.getInput(), 2.0D);
        double dist = r + 1.0D;
        Iterator<Entity> var6 = mc.theWorld.loadedEntityList.iterator();

        while(true) {
            Entity ent;
            do {
                do {
                    do {
                        do {
                            if (!var6.hasNext()) {
                                return en;
                            }

                            ent = var6.next();
                        } while(ent == mc.thePlayer);
                    } while(!(ent instanceof EntityLivingBase));
                } while(((EntityLivingBase)ent).deathTime != 0);
            } while(SlyPort.d.isToggled() && !(ent instanceof EntityPlayer));

            if (!AntiBot.bot(ent)) {
                double d = mc.thePlayer.getDistanceSqToEntity(ent);
                if (!(d > r) && !(dist < d)) {
                    dist = d;
                    en = ent;
                }
            }
        }
    }

}