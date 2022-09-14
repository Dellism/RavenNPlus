package a.b.module.modules.movement;

import a.b.main.Otaku;
import a.b.module.Module;
import a.b.module.modules.player.SafeWalk;
import a.b.module.setting.impl.DescriptionSetting;
import a.b.module.setting.impl.SliderSetting;
import a.b.module.setting.impl.TickSetting;
import a.b.utils.InvUtils;
import a.b.utils.Timer;
import a.b.utils.Utils;
import a.b.utils.event.EventPreMotion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.awt.*;

public class Scaffold extends Module {

    public static TickSetting head, camera, showBlockAmount, noSprint;
    public static SliderSetting blockShowMode, delay, mode;
    public static DescriptionSetting desc;

    public Scaffold() {
        super("Scaffold", ModuleCategory.movement);
        this.registerSetting(mode = new SliderSetting("Mode", 1, 1, 2, 1));
        this.registerSetting(head = new TickSetting ("Rotate Head", true));
        this.registerSetting(camera = new TickSetting ("Rotate Camera", false));
        this.registerSetting(noSprint = new TickSetting ("No Sprint", true));
        this.registerSetting(desc = new DescriptionSetting("Rotate the Head, not the Camera"));
        this.registerSetting(delay = new SliderSetting("Delay", 12, 0, 50, 1));
        this.registerSetting(showBlockAmount = new TickSetting("Show amount of blocks", true));
        this.registerSetting(blockShowMode = new SliderSetting("Block display info", 2, 1, 2, 1));
    }

    private boolean canPlaceBlock(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return !mc.theWorld.isAirBlock(pos) && !(block instanceof BlockLiquid);
    }

    int lastItem;
    float yaw, pitch = 96;

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent pre) {
        if (!Utils.Player.isPlayerInGame()) return;
        if (!InvUtils.isPlayerHoldingBlock()) return;

        if(mode.getInput() == 1) {
            BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            BlockData data = null;
            double posY;
            double yDif = 1.0D;
            for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
                BlockData newData = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
                if (newData != null) {
                    yDif = mc.thePlayer.posY - posY;
                    if (yDif <= 3.0D) {
                        data = newData;
                        break;
                    }
                }
            }

            if (data == null) {
                return;
            }

            if (data.pos == new BlockPos(0, -1, 0)) {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }

            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
                if (itemStack != null) {
                    int stackSize = itemStack.stackSize;
                    if (itemStack.getItem() instanceof ItemBlock) {
                        mc.thePlayer.inventory.currentItem = i;
                    }
                    lastItem = mc.thePlayer.inventory.currentItem;
                }
            }

            if(noSprint.isToggled())
                if(mc.gameSettings.keyBindSprint.isKeyDown() || mc.gameSettings.keyBindSprint.isPressed() || mc.thePlayer.isSprinting())
                    mc.thePlayer.setSprinting(false);

            if ((mc.thePlayer.getHeldItem() != null) && (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {

                if (Timer.hasTimeElapsed((long) delay.getInput() * 5, true)) {
                    if(Utils.Player.isMoving() || mc.thePlayer.onGround) {
                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.pos, data.face, new Vec3(data.pos.getX(), data.pos.getY(), data.pos.getZ()));
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                }
            }

            mc.thePlayer.inventory.currentItem = lastItem;
        }

        if(mode.getInput() == 2) {
            BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);

            if(valid(bp.add(0, -2, 0)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.UP);
            else if (valid(bp.add(-1, -1, 0)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.EAST);
            else if (valid(bp.add(1, -1, 0)))
                Utils.Player.place(bp.add(0, -1, -1), EnumFacing.WEST);
            else if (valid(bp.add(0, -1, -1)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.SOUTH);
            else if (valid(bp.add(0, -1, 1)))
                Utils.Player.place(bp.add(0, -1, 0), EnumFacing.NORTH);
            else if (valid(bp.add(1, -1, 1))) {
                if(valid(bp.add(0, -1, 1)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.NORTH);
                Utils.Player.place(bp.add(1, -1, 1), EnumFacing.EAST);
            } else if (valid(bp.add(-1, -1, 1))) {
                if(valid(bp.add(-1, -1, 0)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.WEST);
                Utils.Player.place(bp.add(-1, -1, 1), EnumFacing.SOUTH);
            } else if (valid(bp.add(-1, -1, -1))) {
                if(valid(bp.add(0, -1, -1)))
                    Utils.Player.place(bp.add(0, -1, 1), EnumFacing.SOUTH);
                Utils.Player.place(bp.add(-1, -1 , 1), EnumFacing.WEST);
            } else if (valid(bp.add(1, -1, -1))) {
                if(valid(bp.add(1, -1, 0)))
                    Utils.Player.place(bp.add(1, -1, 0), EnumFacing.EAST);
                Utils.Player.place(bp.add(1, -1, -1), EnumFacing.NORTH);
            }

            if(head.isToggled()) {
                mc.thePlayer.setRotationYawHead(yaw);
            }
        }
    }

    boolean valid(BlockPos p) {
        Block b = mc.theWorld.getBlockState(p).getBlock();
        return !(b instanceof BlockLiquid) && b.getMaterial() != Material.air;
    }

    public Scaffold.BlockData getBlockData(BlockPos pos) {
        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new Scaffold.BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        } else {
            return null;
        }
    }

    @SubscribeEvent
    public void s(TickEvent.PlayerTickEvent post) {
        if (!Utils.Player.isPlayerInGame()) return;
        if (!InvUtils.isPlayerHoldingBlock()) return;

        if(mode.getInput() == 1) {
            BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            BlockData data = null;
            double posY, yDif = 1.0D;
            for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
                BlockData newData = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
                if (newData != null) {
                    yDif = mc.thePlayer.posY - posY;
                    if (yDif <= 3.0D) {
                        data = newData;
                        break;
                    }
                }
            }

            if (data == null) {
                return;
            }

            if (data.pos == new BlockPos(0, -1, 0)) {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }

            if (data.face == EnumFacing.UP) {
                yaw = 90;
            } else if (data.face == EnumFacing.NORTH) {
                yaw = 360;
            } else if (data.face == EnumFacing.EAST) {
                yaw = 90;
            } else if (data.face == EnumFacing.SOUTH) {
                yaw = 180;
            } else if (data.face == EnumFacing.WEST) {
                yaw = 270;
            } else {
                yaw = 90;
            }

            EventPreMotion.setYaw(yaw);
            EventPreMotion.setPitch(pitch);

            if(head.isToggled()) {
                mc.thePlayer.setRotationYawHead(yaw);
            }

            if(camera.isToggled()) {
                mc.thePlayer.cameraYaw = yaw;
            }

        }
    }

    public class BlockData {
        public final BlockPos pos; public final EnumFacing face;
        BlockData(BlockPos pos, EnumFacing face) { this.pos = pos; this.face = face; }
    }

    @SubscribeEvent
    public void r(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;
        if(!InvUtils.isPlayerHoldingBlock()) return;
        if (!showBlockAmount.isToggled()) return;

        if(mode.getInput() == 1) {

            if (e.phase == TickEvent.Phase.END) {
                if (mc.currentScreen == null) {
                    ScaledResolution res = new ScaledResolution(mc);

                    int totalBlocks = 0;
                    if (SafeWalk.BlockAmountInfo.values()[(int) blockShowMode.getInput() - 1] == SafeWalk.BlockAmountInfo.BLOCKS_IN_TOTAL) {
                        totalBlocks = InvUtils.getBlockAmountInCurrentStack(mc.thePlayer.inventory.currentItem);
                    } else {
                        for (int slot = 0; slot < 36; slot++) {
                            totalBlocks += InvUtils.getBlockAmountInCurrentStack(slot);
                        }
                    }

                    if (totalBlocks <= 0) {
                        return;
                    }
                    int rgb;
                    if (totalBlocks < 16.0D) {
                        rgb = Color.red.getRGB();
                    } else if (totalBlocks < 32.0D) {
                        rgb = Color.orange.getRGB();
                    } else if (totalBlocks < 128.0D) {
                        rgb = Color.yellow.getRGB();
                    } else if (totalBlocks > 128.0D) {
                        rgb = Color.green.getRGB();
                    } else {
                        rgb = Color.black.getRGB();
                    }

                    String t;
                    if (totalBlocks == 1) {
                        t = totalBlocks + " block";
                    } else {
                        t = totalBlocks + " blocks";
                    }

                    int x = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2, y;
                    if (Otaku.debugger) {
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