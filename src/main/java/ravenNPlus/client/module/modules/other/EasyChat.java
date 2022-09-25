package ravenNPlus.client.module.modules.other;

import ravenNPlus.client.module.Module;
import ravenNPlus.client.module.setting.impl.DescriptionSetting;
import ravenNPlus.client.module.setting.impl.SliderSetting;
import ravenNPlus.client.module.setting.impl.TickSetting;
import ravenNPlus.client.utils.Utils;

public class EasyChat extends Module {

    public static TickSetting autoDis;
    public static SliderSetting mode;
    public static DescriptionSetting modDe;
    public static DescriptionSetting desc;

    public EasyChat() {
        super("EasyChat", ModuleCategory.other, "Message on Hotkey");
        this.addSetting(mode  = new SliderSetting("Mode", 60, 0, 60, 1));
        this.addSetting(modDe = new DescriptionSetting(Utils.md+mode.getName()));
        this.addSetting(desc = new DescriptionSetting(Utils.md+""));
        this.addSetting(autoDis = new TickSetting("AutoDisable", false));
    }

    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    public void a(net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent e) {
        if(mode.getValue() == 0) {
            send("Get rekt");
        } else if(mode.getValue() == 1) {
            send("L");
        } else if(mode.getValue() == 2) {
            send("EZ");
        } else if(mode.getValue() == 3) {
            send("Easy");
        } else if(mode.getValue() == 4) {
            send("Bozo");
        } else if(mode.getValue() == 5) {
            send("LLL");
        } else if(mode.getValue() == 6) {
            send("Looser");
        } else if(mode.getValue() == 7) {
            send("haha");
        } else if(mode.getValue() == 8) {
            send("noob");
        } else if(mode.getValue() == 9) {
            send("bot");
        } else if(mode.getValue() == 10) {
            send("youre bad");
        } else if(mode.getValue() == 11) {
            send("I need bread");
        } else if(mode.getValue() == 12) {
            send("xd");
        } else if(mode.getValue() == 13) {
            send("You need to be better to kill me");
        } else if(mode.getValue() == 14) {
            send("You need to be better");
        } else if(mode.getValue() == 15) {
            send("My Aim is god like");
        } else if(mode.getValue() == 16) {
            send("You dont know me");
        } else if(mode.getValue() == 17) {
            send("Your aim is not that good as mine");
        } else if(mode.getValue() == 18) {
            send("Good Game");
        } else if(mode.getValue() == 19) {
            send("GG");
        } else if(mode.getValue() == 20) {
            send("Nice");
        } else if(mode.getValue() == 21) {
            send("Ok");
        } else if(mode.getValue() == 22) {
            send("Yes");
        } else if(mode.getValue() == 23) {
            send("No");
        } else if(mode.getValue() == 24) {
            send("Why");
        } else if(mode.getValue() == 25) {
            send("lol");
        } else if(mode.getValue() == 26) {
            send("and");
        } else if(mode.getValue() == 27) {
            send("add me");
        } else if(mode.getValue() == 28) {
            send("send me your money");
        } else if(mode.getValue() == 29) {
            send("money is cool");
        } else if(mode.getValue() == 30) {
            send("bedwars");
        } else if(mode.getValue() == 31) {
            send("skywars");
        } else if(mode.getValue() == 32) {
            send("good luck");
        } else if(mode.getValue() == 33) {
            send("i dont need that");
        } else if(mode.getValue() == 34) {
            send("no problem");
        } else if(mode.getValue() == 35) {
            send("np");
        } else if(mode.getValue() == 36) {
            send("thank you");
        } else if(mode.getValue() == 37) {
            send("thx");
        } else if(mode.getValue() == 38) {
            send("lets play");
        } else if(mode.getValue() == 39) {
            send("lets go");
        } else if(mode.getValue() == 40) {
            send("you are gay");
        } else if(mode.getValue() == 41) {
            send("rekt");
        } else if(mode.getValue() == 42) {
            send("haha rekt");
        } else if(mode.getValue() == 43) {
            send("you got destroyed");
        } else if(mode.getValue() == 44) {
            send("destroyed");
        } else if(mode.getValue() == 45) {
            send("bruh");
        } else if(mode.getValue() == 46) {
            send("you are bad");
        } else if(mode.getValue() == 47) {
            send("you have bad hacks");
        } else if(mode.getValue() == 48) {
            send("RavenNPlus best client");
        } else if(mode.getValue() == 49) {
            send("i dont hack");
        } else if(mode.getValue() == 50) {
            send("the best hack is RavenNPlus");
        } else if(mode.getValue() == 51) {
            send("i use RavenNPlus client");
        } else if(mode.getValue() == 52) {
            send("RavenNPlus b1.3");
        } else if(mode.getValue() == 53) {
            send("RavenNPlus is the best client");
        } else if(mode.getValue() == 54) {
            send("RavenNPlus client");
        } else if(mode.getValue() == 55) {
            send("i use RavenNPlus");
        } else if(mode.getValue() == 56) {
            send("i dont need hacks");
        } else if(mode.getValue() == 57) {
            send("i dont use RavenNPlus client");
        } else if(mode.getValue() == 58) {
            send("i dont use RavenNPlus");
        } else if(mode.getValue() == 59) {
            send("RavenNPlus b1.3 is out");
        } else if(mode.getValue() == 60) {
            send("RavenNPlus b1.3 = https://discord.gg/WBApubEaVv");
        } else { mode.setValue(60); }
    }

    public void send(String message) {
        if(mc.thePlayer.getHealth() > 0 && mc.thePlayer.isEntityAlive()  ) {
            mc.thePlayer.sendChatMessage(message);
        }

        if(autoDis.isToggled())
            this.disable();
    }

    public void guiUpdate() {
        switch ((int) mode.getValue()) {
            case 0:
                desc.setDesc(Utils.md + "Get rekt");
                break;
            case 1:
                desc.setDesc(Utils.md + "L");
                break;
            case 2:
                desc.setDesc(Utils.md + "EZ");
                break;
            case 3:
                desc.setDesc(Utils.md + "Easy");
                break;
            case 4:
                desc.setDesc(Utils.md + "Bozo");
                break;
            case 5:
                desc.setDesc(Utils.md + "LLL");
                break;
            case 6:
                desc.setDesc(Utils.md + "Looser");
                break;
            case 7:
                desc.setDesc(Utils.md + "haha");
                break;
            case 8:
                desc.setDesc(Utils.md + "noob");
                break;
            case 9:
                desc.setDesc(Utils.md + "bot");
                break;
            case 10:
                desc.setDesc(Utils.md + "youre bad");
                break;
            case 11:
                desc.setDesc(Utils.md + "I need bread");
                break;
            case 12:
                desc.setDesc(Utils.md + "xd");
                break;
            case 13:
                desc.setDesc(Utils.md + "You need to be better to kill me");
                break;
            case 14:
                desc.setDesc(Utils.md + "You need to be better");
                break;
            case 15:
                desc.setDesc(Utils.md + "My Aim is god like");
                break;
            case 16:
                desc.setDesc(Utils.md + "You dont know me");
                break;
            case 17:
                desc.setDesc(Utils.md + "Your aim is not that good as mine");
                break;
            case 18:
                desc.setDesc(Utils.md + "Good Game");
                break;
            case 19:
                desc.setDesc(Utils.md + "GG");
                break;
            case 20:
                desc.setDesc(Utils.md + "Nice");
                break;
            case 21:
                desc.setDesc(Utils.md + "Ok");
                break;
            case 22:
                desc.setDesc(Utils.md + "Yes");
                break;
            case 23:
                desc.setDesc(Utils.md + "No");
                break;
            case 24:
                desc.setDesc(Utils.md + "Why");
                break;
            case 25:
                desc.setDesc(Utils.md + "lol");
                break;
            case 26:
                desc.setDesc(Utils.md + "and");
                break;
            case 27:
                desc.setDesc(Utils.md + "add me");
                break;
            case 28:
                desc.setDesc(Utils.md + "send me your money");
                break;
            case 29:
                desc.setDesc(Utils.md + "money is cool");
                break;
            case 30:
                desc.setDesc(Utils.md + "bedwars");
                break;
            case 31:
                desc.setDesc(Utils.md + "skywars");
                break;
            case 32:
                desc.setDesc(Utils.md + "good luck");
                break;
            case 33:
                desc.setDesc(Utils.md + "i dont need that");
                break;
            case 34:
                desc.setDesc(Utils.md + "no problem");
                break;
            case 35:
                desc.setDesc(Utils.md + "np");
                break;
            case 36:
                desc.setDesc(Utils.md + "thank you");
                break;
            case 37:
                desc.setDesc(Utils.md + "thx");
                break;
            case 60:
                desc.setDesc(Utils.md + "discord link");
                break;
            default:
                desc.setDesc(Utils.md+ "?");
                break;
        }
    }

}