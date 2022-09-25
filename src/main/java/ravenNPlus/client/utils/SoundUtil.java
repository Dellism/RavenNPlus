package ravenNPlus.client.utils;

public class SoundUtil {

    public static String ogg  =  ".ogg", wav  =  ".wav", mp3  =  ".mp3", m4a  =  ".m4a", opus = ".opus";
    public static String chestOpen = "random.chestopen";
    public static String chestClose = "random.chestclosed";
    public static String endermenPortal = "mob.endermen.portal";
    public static String tntExplosion = "random.explode";
    public static String click0 = "random.click";
    public static String click1 = "gui.button.press";
    public static String bowhit  = "random.bowhit";
    public static String playerDie = "game.player.die";
    public static String playerHurt = "game.player.hurt";
    public static String musicCat = "records.cat";

    public static void play(String name, float volume, float pitch) {
        if(!Utils.Player.isPlayerInGame()) return;

        switch (name) {
            case "endermen_portal":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(endermenPortal, volume, pitch);
                break;
            case "random.explode":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(tntExplosion, volume, pitch);
                break;
            case "random.click":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(click0, volume, pitch);
                break;
            case "gui.button.press":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(click1, volume, pitch);
                break;
            case "random.bowhit":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(bowhit, volume, pitch);
                break;
            case "game.player.hurt":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(playerHurt, volume, pitch);
                break;
            case "game.player.die":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(playerDie, volume, pitch);
                break;
            case "records.cat":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(musicCat, volume, pitch);
                break;
            case "random.chestopen":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(chestOpen, volume, pitch);
                break;
            case "random.chestclosed":
                net.minecraft.client.Minecraft.getMinecraft().thePlayer.playSound(chestClose, volume, pitch);
                break;
        }
    }

}