package a.b.module.modules.combat;

import a.b.module.Module;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.InvUtils;
import a.b.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoWeapon extends Module {
    public static TickSetting onlyWhenHoldingDown;
    public static TickSetting goBackToPrevSlot;
    private boolean onWeapon;
    private int prevSlot;

    public AutoWeapon(){
        super("AutoWeapon", ModuleCategory.combat);
        this.registerSetting(onlyWhenHoldingDown = new TickSetting("Only when holding LMB", true));
        this.registerSetting(goBackToPrevSlot = new TickSetting("Revert to old slot", true));
    }

    @SubscribeEvent
    public void datsDaSoundOfDaPolis(TickEvent.RenderTickEvent ev){
        if(!Utils.Player.isPlayerInGame() || mc.currentScreen != null) return;


        if(mc.objectMouseOver==null || mc.objectMouseOver.entityHit==null || (onlyWhenHoldingDown.isToggled() && !Mouse.isButtonDown(0))){
            if(onWeapon){
                onWeapon = false;
                if(goBackToPrevSlot.isToggled()){
                    mc.thePlayer.inventory.currentItem = prevSlot;
                }
            }
        } else{
            Entity target = mc.objectMouseOver.entityHit;
            if(onlyWhenHoldingDown.isToggled()){
                if(!Mouse.isButtonDown(0)) return;
            }
            if(!onWeapon){
                prevSlot = mc.thePlayer.inventory.currentItem;
                onWeapon = true;

                int maxDamageSlot = InvUtils.getMaxDamageSlot();

                if(maxDamageSlot > 0 && InvUtils.getSlotDamage(maxDamageSlot) > InvUtils.getSlotDamage(mc.thePlayer.inventory.currentItem)){
                    mc.thePlayer.inventory.currentItem = maxDamageSlot;
                }
            }
        }
    }
}
