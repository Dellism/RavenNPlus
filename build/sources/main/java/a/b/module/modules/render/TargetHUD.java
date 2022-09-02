package a.b.module.modules.render;

import a.b.module.Module;
import a.b.module.setting.impl.SliderSetting;
import a.b.utils.Utils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import java.util.List;
import java.util.stream.Collectors;

public class TargetHUD extends Module {

    public static SliderSetting dist, x2, y2;

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.render);
        this.registerSetting(dist = new SliderSetting("Distance", 5, 1 ,50, 1));
        this.registerSetting(x2 = new SliderSetting("X", 5, 0, mc.displayWidth+150, 1));
        this.registerSetting(y2 = new SliderSetting("Y", 50, 0 , mc.displayHeight+150, 1));
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent ev) {
        if (!Utils.Player.isPlayerInGame()) return;
        
        FontRenderer fr = mc.fontRendererObj;

        int count = 1;
        List<EntityLivingBase> target = (List) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        target = target.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < dist.getInput() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0).collect(Collectors.toList() );

        for(EntityLivingBase player : target) {
            int ScaledW = new ScaledResolution(mc).getScaledWidth();
            int ScaledH = new ScaledResolution(mc).getScaledHeight();
            int xPos = ScaledW+ (int)x2.getInput();
            int yPos = ScaledH+ (int)y2.getInput();

            if(count == 1) {
                EntityLivingBase targes = player;

                Gui.drawRect(xPos+(int)80, yPos+150, ScaledW+10080*2, ScaledH+48+150*2, 0x40000000);
                fr.drawStringWithShadow(" \u2710 : " +player.getName(), 							xPos+80, yPos+05+150, 0xFF0079);
                fr.drawStringWithShadow(" \u2907 : "+ (int)player.getDistanceToEntity(mc.thePlayer), 	xPos+80, yPos+15+150, 0xFF0079);
                fr.drawStringWithShadow(" \u2764 : "+ (int)player.getHealth()/2, 					xPos+80, yPos+25+150, 0xFF0079);
                fr.drawSplitString(" ", 0, 0, 0, -1);
                GuiInventory.drawEntityOnScreen(xPos+65+(int)80, yPos+30+150, 15, Mouse.getX(), Mouse.getY(), targes);

                count++;
            }
        }
    }

}