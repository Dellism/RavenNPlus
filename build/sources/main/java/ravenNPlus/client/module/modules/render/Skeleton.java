package ravenNPlus.client.module.modules.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ravenNPlus.client.event.imp.RenderEntityModelEvent;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.utils.CombatUtils;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Skeleton extends Module {

    private final Map<Object, Object> rotationList = new HashMap<>();

    public Skeleton() {
        super("Skeleton", ModuleCategory.render, "Draws a nice Skeleton.");
    }

    @SubscribeEvent
    public void r(RenderEntityModelEvent ev) {
        if (!Utils.Player.isPlayerInGame()) return;

        for (EntityPlayer player : mc.thePlayer.getEntityWorld().playerEntities) {
            if (player == null || player == mc.getRenderViewEntity() || !player.isEntityAlive() || player.isPlayerSleeping() || (player.isInvisible() || this.rotationList.get(player) == null || mc.thePlayer.getDistanceToEntity(player) >= 2500.0D))
                continue;

            ModelBiped biped = (ModelBiped)ev.modelBase;
            float[][] rotations = CombatUtils.getBipedRotations(biped);
            rotationList.put(player, rotations);

            RenderUtils.renderSkeleton(mc.thePlayer, (float[][]) rotationList.get(player), Color.green);
        }
    }

}