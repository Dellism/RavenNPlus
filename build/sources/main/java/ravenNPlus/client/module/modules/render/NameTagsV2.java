package ravenNPlus.client.module.modules.render;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.CombatUtils;
import ravenNPlus.client.utils.RenderUtils;
import ravenNPlus.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class NameTagsV2 extends Module {

    boolean armor;
    boolean dura;
    float _x;
    float _y;
    float _z;
    private float scale;
    private String mode;

    private final SliderSetting modeSetting;
    private final SliderSetting scaleSetting;
    private final SliderSetting rangeSetting;
    private final TickSetting heartSetting;
    private final TickSetting armorSetting;
    private final TickSetting durabilitySetting;
    private final TickSetting distanceSetting;

    public NameTagsV2() {
        super("NameTagsV2", ModuleCategory.render, "Custom name tags");
        this.addSetting(heartSetting = new TickSetting("Hearts", true));
        this.addSetting(modeSetting = new SliderSetting("Mode (Hearts/Percentage)", 1.0D, 1.0D, 2.0D, 1.0D));
        mode = "Percentage"; // default value
        this.addSetting(scaleSetting = new SliderSetting("Scale", 5.0D, 0.1D, 10.0D, 0.1D));
        this.addSetting(rangeSetting = new SliderSetting("Range", 0.0D, 0.0D, 512.0D, 1.0D));
        this.addSetting(armorSetting = new TickSetting("Armor", true));
        this.addSetting(durabilitySetting = new TickSetting("Durability", false));
        this.addSetting(distanceSetting = new TickSetting("Distance", false));
    }

    @Override
    public void guiUpdate() {
        scale = (float) scaleSetting.getValue();
        armor = armorSetting.isToggled();
        dura = durabilitySetting.isToggled();
        mode = modeSetting.getValue() == 1.0D ? "Hearts" : "Percentage";
    }

    @SubscribeEvent
    public void nameTag(Pre<? extends EntityLivingBase> player) {
        boolean _0 = player.entity.getDisplayName().getFormattedText() != null;
        boolean _1 = !player.entity.getDisplayName().getFormattedText().equals("");
        boolean _2 = player.entity instanceof EntityPlayer && CombatUtils.canTarget(player.entity, true);
        boolean _3 = ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player.entity) <= rangeSetting.getValue() || rangeSetting.getValue() == 0.0D);
        if ( _0 && _1  && _2 && _3) {
            player.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void render3d(RenderWorldLastEvent renderWorldLastEvent) {
        ArrayList<EntityLivingBase> playersArr = new ArrayList<>();

        Iterator playerIterator = Minecraft.getMinecraft().theWorld.playerEntities.iterator();

        while (playerIterator.hasNext()) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) playerIterator.next();
            if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityLivingBase) > rangeSetting.getValue() && rangeSetting.getValue() != 0.0D) {
                playersArr.remove(entityLivingBase);
            } else if (entityLivingBase.getName().contains("[NPC]")) {
                playersArr.remove(entityLivingBase);
            } else if (entityLivingBase.isEntityAlive()) {
                if (entityLivingBase.isInvisible()) {
                    playersArr.remove(entityLivingBase);
                } else if (entityLivingBase == Minecraft.getMinecraft().thePlayer) {
                    playersArr.remove(entityLivingBase);
                } else {
                    if (playersArr.size() > 100) {
                        break;
                    }

                    if (!playersArr.contains(entityLivingBase)) {
                        playersArr.add(entityLivingBase);
                    }
                }
            } else playersArr.remove(entityLivingBase);
        }

        _x = 0.0F;
        _y = 0.0F;
        _z = 0.0F;
        playerIterator = playersArr.iterator();

        while (playerIterator.hasNext()) {
            EntityPlayer player = (EntityPlayer) playerIterator.next();
            if (CombatUtils.canTarget(player, true)) {
                player.setAlwaysRenderNameTag(false);
                _x = (float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX);
                _y = (float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY);
                _z = (float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
                renderNametag(player, _x, _y, _z);
            }
        }
    }

    private String getHealth(EntityPlayer player) {
        DecimalFormat decimalFormat = new DecimalFormat("0.#");

        if(heartSetting.isToggled())
            return mode.equalsIgnoreCase("Percentage") ? decimalFormat.format(player.getHealth() * 5.0F + player.getAbsorptionAmount() * 5.0F) : decimalFormat.format(player.getHealth() / 2.0F + player.getAbsorptionAmount() / 2.0F);
        else
            return null;
    }

    private void drawNames(EntityPlayer player) {
        float aaa = (float) getWidth(getPlayerName(player)) / 2.0F + 2.2F;
        float bbb;
        aaa = bbb = (float) ((double) aaa + (getWidth(" " + getHealth(player)) / 2) + 2.5D);
        float ccc = -aaa - 2.2F;
        float ddd = (float) (getWidth(getPlayerName(player)) + 4);
        if (mode.equalsIgnoreCase("Percentage")) {
            RenderUtils.drawBorderedRect(ccc, -3.0F, aaa, 10.0F, 1.0F, (new Color(20, 20, 20, 180)).getRGB(), (new Color(10, 10, 10, 200)).getRGB());
        } else {
            RenderUtils.drawBorderedRect(ccc + 5.0F, -3.0F, aaa, 10.0F, 1.0F, (new Color(20, 20, 20, 180)).getRGB(), (new Color(10, 10, 10, 200)).getRGB());
        }

        GlStateManager.disableDepth();
        if (mode.equalsIgnoreCase("Percentage")) {
            if(heartSetting.isToggled())
                ddd += (float) (getWidth(getHealth(player)) + getWidth(" %") - 1);
        } else {
            if(heartSetting.isToggled())
                ddd += (float) (getWidth(getHealth(player)) + getWidth(" ") - 1);
        }

        drawString(getPlayerName(player), bbb - ddd, 0.0F, 16777215);

        int blendColor;
        if (player.getHealth() > 10.0F) {
            blendColor = RenderUtils.blend(new Color(-16711936), new Color(-256), (1.0F / player.getHealth() / 2.0F * (player.getHealth() - 10.0F))).getRGB();
        } else {
            blendColor = RenderUtils.blend(new Color(-256), new Color(-65536), 0.1F * player.getHealth()).getRGB();
        }

        if (mode.equalsIgnoreCase("Percentage")) {
            if(heartSetting.isToggled())
                drawString(getHealth(player) + "%", bbb - (float) getWidth(getHealth(player) + " %"), 0.0F, blendColor);
        } else {
            if(heartSetting.isToggled())
                drawString(getHealth(player), bbb - (float) getWidth(getHealth(player) + " "), 0.0F, blendColor);
        }

        GlStateManager.enableDepth();
    }

    private void drawString(String string, float x, float y, int z) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, x, y, z);
    }

    private int getWidth(String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }

    private void startDrawing(float x, float y, float z, EntityPlayer player) {
        float rotateX = Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F;
        double scaleRatio = (double) (getSize(player) / 10.0F * scale) * 1.5D;
        GL11.glPushMatrix();
        RenderUtils.startDrawing();
        GL11.glTranslatef(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, rotateX, 0.0F, 0.0F);
        GL11.glScaled(-0.01666666753590107D * scaleRatio, -0.01666666753590107D * scaleRatio, 0.01666666753590107D * scaleRatio);
    }

    private void stopDrawing() {
        RenderUtils.stopDrawing();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderNametag(EntityPlayer player, float x, float y, float z) {
        y += (float) (1.55D + (player.isSneaking() ? 0.5D : 0.7D));
        startDrawing(x, y, z, player);
        drawNames(player);
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
        if (armor) {
            renderArmor(player);
        }

        stopDrawing();
    }

    private void renderArmor(EntityPlayer player) {
        ItemStack[] armor = player.inventory.armorInventory;
        int pos = 0;

        for (ItemStack is : armor) {
            if (is != null) {
                pos -= 8;
            }
        }

        if (player.getHeldItem() != null) {
            pos -= 8;
            ItemStack var10 = player.getHeldItem().copy();
            if (var10.hasEffect() && (var10.getItem() instanceof ItemTool || var10.getItem() instanceof ItemArmor)) {
                var10.stackSize = 1;
            }

            RenderUtils.renderItemStack(var10, pos, -20, dura);
            pos += 16;
        }

        armor = player.inventory.armorInventory;

        for (int i = 3; i >= 0; --i) {
            ItemStack var11 = armor[i];
            if (var11 != null) {
                RenderUtils.renderItemStack(var11, pos, -20, dura);
                pos += 16;
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private String getPlayerName(EntityPlayer player) {
        boolean isDistanceSettingToggled = distanceSetting.isToggled();
        return (isDistanceSettingToggled ? (new DecimalFormat("#.##")).format(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player)) + "m " : "") + player.getDisplayName().getFormattedText();
    }

    private float getSize(EntityPlayer player) {
        return Math.max(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player) / 4.0F, 2.0F);
    }

}