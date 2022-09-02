package a.b.utils;

import javax.sound.sampled.AudioInputStream;

public class SoundUtil {

    private static final java.util.HashMap<String, AudioInputStream> sounds = new java.util.HashMap<String, AudioInputStream>();

    static String ogg  =  ".ogg";
    static String wav  =  ".wav";
    static String mp3  =  ".mp3";
    static String m4a  =  ".m4a";
    static String pcm  =  ".pcm";
    static String dsd  =  ".dsd";
    static String opus = ".opus";
    static String flac = ".flac";
    static String alac = ".alac";
    static String aiff = ".aiff";

    public static void start() {
        add("enable"  + wav ); // module  enable sound
        add("disable" + wav ); // module disable sound
    }

    public static void add(String name) {
        try {
            sounds.put(name, (AudioInputStream) SoundUtil.class.getResourceAsStream("/assets/a/sounds/" + name ));
        } catch(Exception e) {
            System.out.println("Error with adding sound.");
            e.printStackTrace();
        }
    }

    public static void play(String name) {
        try {
            AudioInputStream audioInputStream = sounds.get(name);
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
            add(name);
        }
    }

}