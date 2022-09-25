package ravenNPlus.client.module.modules.movement;

import ravenNPlus.client.main.Client;
import ravenNPlus.client.module.setting.impl.DoubleSliderSetting;
import ravenNPlus.client.utils.Utils;
import ravenNPlus.client.utils.Timer;
import ravenNPlus.client.utils.InvUtils;
import ravenNPlus.client.module.Module;
import ravenNPlus.client.utils.event.EventPreMotion;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.modules.player.SafeWalk;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.awt.Color;

public class Scaffold extends Module {

    public static TickSetting head, camera, showBlockAmount, noSprint, onlyOnGround, onDisableSprint, lookDown, switchItem, swing;
    public static SliderSetting blockShowMode, delay, mode, offset;
    public static DoubleSliderSetting pitchRange;
    public static DescriptionSetting desc;

    public Scaffold() {
        super("Scaffold", ModuleCategory.movement, "Places blocks under you (faster then Telly Bridge)");
        this.addSetting(mode = new SliderSetting("Mode", 1, 1, 2, 1));
        this.addSetting(lookDown = new TickSetting("Only when looking down", true));
        this.addSetting(pitchRange = new DoubleSliderSetting("Pitch min range:", 70, 85, 0, 90, 1));
        this.addSetting(switchItem = new TickSetting("Switch Item", true));
        this.addSetting(head = new TickSetting("Rotate Head", true));
        this.addSetting(camera = new TickSetting("Rotate Camera", false));
        this.addSetting(offset = new SliderSetting("Camera Offset", 15, 5, 180, 1));
        this.addSetting(swing = new TickSetting("Swing", false));
        this.addSetting(noSprint = new TickSetting("No Sprint", true));
        this.addSetting(onlyOnGround = new TickSetting("Only On Ground", true));
        this.addSetting(onDisableSprint = new TickSetting("Sprint On Disable", true));
        this.addSetting(delay = new SliderSetting("Delay", 12, 0, 50, 1));
        this.addSetting(showBlockAmount = new TickSetting("Show amount of blocks", true));
        this.addSetting(blockShowMode = new SliderSetting("Block display info", 1, 1, 2, 1));
    }

    int lastItem;
    float lastPitch;
    float yaw, pitch = 96;

    @Override
    public void onEnable() {
        lastItem = InvUtils.getCurrentPlayerSlot();
        lastPitch = mc.thePlayer.rotationPitch;

        if(switchItem.isToggled()) {
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                    boolean c = itemStack.getItem() instanceof ItemAnvilBlock;
                    boolean d = itemStack.getDisplayName().equals("Sand");
                    boolean e = itemStack.getDisplayName().equals("Red Sand");
                    boolean f = itemStack.getDisplayName().equals("Anvil");
                    boolean g = itemStack.getDisplayName().endsWith("Slab");
                    boolean h = itemStack.getDisplayName().startsWith("Lilly");

                    if(c || d || e || f || g || h) return;

                    InvUtils.setCurrentPlayerSlot(i);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if(switchItem.isToggled())
            InvUtils.setCurrentPlayerSlot(lastItem);

        if(camera.isToggled())
            mc.thePlayer.rotationPitch = lastPitch;

        mc.thePlayer.setSprinting(onDisableSprint.isToggled());
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent pre) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(!InvUtils.isPlayerHoldingBlock()) return;

        if (lookDown.isToggled())
            if (mc.thePlayer.rotationPitch < pitchRange.getInputMin() || mc.thePlayer.rotationPitch > pitchRange.getInputMax())
                return;

        if(mode.getValue() == 1) {

            BlockData data = null;
            double posY, yDif;

            for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
                BlockData newData = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
                if(newData != null) {
                    yDif = mc.thePlayer.posY - posY;
                    if(yDif <= 3.0D) {
                        data = newData;
                        break;
                    }
                }
            }

            if(data == null) {
                return;
            }

            if(noSprint.isToggled())
                if(mc.thePlayer.isSprinting() || mc.gameSettings.keyBindSprint.isKeyDown() || mc.gameSettings.keyBindSprint.isPressed())
                    mc.thePlayer.setSprinting(false);

            if(data.pos.equals(new BlockPos(0, -1, 0))) {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }

            if((mc.thePlayer.getHeldItem() != null) && (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
                if(Timer.hasTimeElapsed((long) delay.getValue() * 5, true)) {
                    if(Utils.Player.isMoving() || mc.thePlayer.onGround) {
                        if(onlyOnGround.isToggled())
                            if(!mc.thePlayer.onGround) return;

                    if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getDisplayName().equals("Gravel")) return;
                    if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getDisplayName().endsWith("Sand")) return;

                    mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.pos, data.face, new Vec3(data.pos.getX(), data.pos.getY(), data.pos.getZ()));
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());

                        if(swing.isToggled()) {
                            if(Utils.Player.isMoving()) {
                                if(Timer.hasTimeElapsed(400L, true))
                                    Utils.Player.swing();
                            }
                        }
                    }
                }
            }
        }

        if(mode.getValue() == 2) {

            BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);

            if(valid(bp.add(0, -2, 0)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.UP, true);
            else if(valid(bp.add(-1, -1, 0)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.EAST, true);
            else if(valid(bp.add(1, -1, 0)))
                Utils.Player.place(bp.add(0, -1, -1), EnumFacing.WEST, true);
            else if(valid(bp.add(0, -1, -1)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.SOUTH, true);
            else if(valid(bp.add(0, -1, 1)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.NORTH, true);
            else if(valid(bp.add(1, -1, 1))) {
                if(valid(bp.add(0, -1, 1)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.NORTH, true);
                Utils.Player.place(bp.add(1, -1, 1), EnumFacing.EAST, true);
            } else if(valid(bp.add(-1, -1, 1))) {
                if(valid(bp.add(-1, -1, 0)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.WEST, true);
                Utils.Player.place(bp.add(-1, -1, 1), EnumFacing.SOUTH, true);
            } else if(valid(bp.add(-1, -1, -1))) {
                if(valid(bp.add(0, -1, -1)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.SOUTH, true);
                Utils.Player.place(bp.add(-1, -1 , 1), EnumFacing.WEST, true);
            } else if(valid(bp.add(1, -1, -1))) {
                if(valid(bp.add(1, -1, 0)))
                    Utils.Player.place(bp.add(1, -1, 0), EnumFacing.EAST, true);
                Utils.Player.place(bp.add(1, -1, -1), EnumFacing.NORTH, true);
            }

            if(head.isToggled()) {
                if(onlyOnGround.isToggled())
                    if(onlyOnGround.isToggled())
                        if(!mc.thePlayer.onGround) return;

                mc.thePlayer.rotationYawHead = yaw;
            }

            if(camera.isToggled()) {
                if(onlyOnGround.isToggled())
                    if(!mc.thePlayer.onGround) return;

                mc.thePlayer.rotationPitch = (float) (pitch-offset.getValue());
            }
        }
    }

    boolean valid(BlockPos p) {
        Block b = mc.theWorld.getBlockState(p).getBlock();
        return !(b instanceof BlockLiquid) && b.getMaterial() != Material.air || b.getMaterial() != Material.sand || b.getMaterial() != Material.anvil;
    }

    public Scaffold.BlockData getBlockData(BlockPos pos) {
        if(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        } else if(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        } else if(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        } else if(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        } else if(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        } else {
            return null;
        }
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent post) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(!InvUtils.isPlayerHoldingBlock()) return;

        if(mode.getValue() == 1) {
            BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            BlockData data = null;
            double posY, yDif;
            for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
                BlockData newData = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
                if(newData != null) {
                    yDif = mc.thePlayer.posY - posY;
                    if(yDif <= 3.0D) {
                        data = newData;
                        break;
                    }
                }
            }

            if(data == null) {
                return;
            }

            if(data.pos == new BlockPos(0, -1, 0)) {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }

            if(data.face == EnumFacing.UP) {
                yaw = 90;
            } else if(data.face == EnumFacing.NORTH) {
                yaw = 360;
            } else if(data.face == EnumFacing.EAST) {
                yaw = 90;
            } else if(data.face == EnumFacing.SOUTH) {
                yaw = 180;
            } else if(data.face == EnumFacing.WEST) {
                yaw = 270;
            } else {
                yaw = 90;
            }

            EventPreMotion.setYaw(yaw);
            EventPreMotion.setPitch(pitch);

            if(head.isToggled()) {
                if(onlyOnGround.isToggled())
                    if(mc.thePlayer.onGround)

                mc.thePlayer.rotationYawHead = yaw;
            }

            if(camera.isToggled()) {
                if(onlyOnGround.isToggled())
                    if(mc.thePlayer.onGround)
                        mc.thePlayer.rotationPitch = (float) (pitch-offset.getValue());
            }
        }
    }

    public class BlockData {
        public final BlockPos pos;
        public final EnumFacing face;

        BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos; this.face = face;
        }
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(!InvUtils.isPlayerHoldingBlock()) return;
        if(!showBlockAmount.isToggled()) return;

        if(mode.getValue() == 1) {
            if(e.phase == TickEvent.Phase.END) {
                if(mc.currentScreen == null) {
                    ScaledResolution res = new ScaledResolution(mc);

                    int totalBlocks = 0;
                    if(SafeWalk.BlockAmountInfo.values()[(int) blockShowMode.getValue() - 1] == SafeWalk.BlockAmountInfo.BLOCKS_IN_TOTAL) {
                        totalBlocks = InvUtils.getBlockAmountInCurrentStack(mc.thePlayer.inventory.currentItem);
                    } else {
                        for (int slot = 0; slot < 36; slot++) {
                            if(totalBlocks != 0)
                                totalBlocks += InvUtils.getBlockAmountInCurrentStack(slot);
                        }
                    }

                    if(totalBlocks <= 0) {
                        return;
                    }

                    int rgb = 0;
                    if(totalBlocks < 3)
                        rgb = new Color(238,0,0).getRGB();
                    else if(totalBlocks < 6)
                        rgb = new Color(215,25,0).getRGB();
                    else if(totalBlocks < 9)
                        rgb = new Color(203,37,0).getRGB();
                    else if(totalBlocks < 12)
                        rgb = new Color(192,49,0).getRGB();
                    else if(totalBlocks < 15)
                        rgb = new Color(181,61,0).getRGB();
                    else if(totalBlocks <18)
                        rgb = new Color(170,74,0).getRGB();
                    else if(totalBlocks <21)
                        rgb = new Color(158,86,0).getRGB();
                    else if(totalBlocks <24)
                        rgb = new Color(147,98,0).getRGB();
                    else if(totalBlocks <27)
                        rgb = new Color(136,110,0).getRGB();
                    else if(totalBlocks <30)
                        rgb = new Color(124,122,0).getRGB();
                    else if(totalBlocks <33)
                        rgb = new Color(113,134,0).getRGB();
                    else if(totalBlocks <36)
                        rgb = new Color(102,146,0).getRGB();
                    else if(totalBlocks <39)
                        rgb = new Color(90,158,0).getRGB();
                    else if(totalBlocks <42)
                        rgb = new Color(79,170,0).getRGB();
                    else if(totalBlocks <45)
                        rgb = new Color(68,182,0).getRGB();
                    else if(totalBlocks <48)
                        rgb = new Color(56,194,0).getRGB();
                    else if(totalBlocks <51)
                        rgb = new Color(45,207,0).getRGB();
                    else if(totalBlocks <54)
                        rgb = new Color(34,219,0).getRGB();
                    else if(totalBlocks <57)
                        rgb = new Color(23,231,0).getRGB();
                    else if(totalBlocks <60)
                        rgb = new Color(11,243,0).getRGB();
                    else if(totalBlocks <65)
                        rgb = new Color(0,255, 0).getRGB();
                    else rgb = 0;

                    String t;
                    if(totalBlocks == 1) {
                        t = totalBlocks + " block";
                    } else {
                        t = totalBlocks + " blocks";
                    }

                    int x = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2, y;
                    if(Client.debugger) {
                        y = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
                    } else {
                        y = res.getScaledHeight() / 2 + 15;
                    }

                     mc.fontRendererObj.drawString(t, (float) x, (float) y, rgb, false);
                }
            }
        }
    }

}