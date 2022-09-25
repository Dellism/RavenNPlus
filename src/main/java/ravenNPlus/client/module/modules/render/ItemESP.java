package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.utils.Utils;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.utils.RenderUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.item.EntityItem;
import java.awt.*;

public class ItemESP extends Module {

    public ItemESP() {
        super("ItemESP", ModuleCategory.render, "Draw a outline around Items");
    }

    @SubscribeEvent
    public void s(TickEvent.RenderTickEvent ev) {
        if(!Utils.Player.isPlayerInGame()) return;

        for(Object o : mc.theWorld.loadedEntityList) {
            if(o instanceof EntityItem) {
                EntityItem e = (EntityItem) o;
                mc.getRenderManager(); double itemX = e.lastTickPosX + (e.posX - e.lastTickPosX) * Utils.Client.getTimer().renderPartialTicks - Utils.Client.getRenderPosX();
                mc.getRenderManager(); double itemY = e.lastTickPosY + (e.posY - e.lastTickPosY) * Utils.Client.getTimer().renderPartialTicks - Utils.Client.getRenderPosY();
                mc.getRenderManager(); double itemZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * Utils.Client.getTimer().renderPartialTicks - Utils.Client.getRenderPosZ();
                RenderUtils.drawOutlinedEntityESP(itemX, itemY, itemZ, e.width, 0.5F, Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue(), 0.75F);
            }
        }
    }

}