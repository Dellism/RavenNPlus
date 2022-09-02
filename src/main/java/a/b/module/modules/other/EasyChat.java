package a.b.module.modules.other;

public class EasyChat extends a.b.module.Module {

    public static a.b.module.setting.impl.DescriptionSetting desc;
    public static a.b.module.setting.impl.SliderSetting mode;
    public static a.b.module.setting.impl.DescriptionSetting modDe;
    public static a.b.module.setting.impl.TickSetting autoDis;

    public EasyChat() {
        super("EasyChat", ModuleCategory.other);
    //  this.registerSetting(desc  = new a.b.module.setting.impl.DescriptionSetting("Message when you kill Someone"));
        this.registerSetting(desc  = new a.b.module.setting.impl.DescriptionSetting("Message on Hotkey"));
        this.registerSetting(mode  = new a.b.module.setting.impl.SliderSetting("Mode", 60, 0, 60, 1));
        this.registerSetting(modDe = new a.b.module.setting.impl.DescriptionSetting("Mode: "+mode.getName()));
        this.registerSetting(autoDis = new a.b.module.setting.impl.TickSetting("AutoDisable", false));
    }

    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    public void a(net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent e) {
        if(mode.getInput() == 0) {
            send("Get rekt");
        } else if(mode.getInput() == 1) {
            send("L");
        } else if(mode.getInput() == 2) {
            send("EZ");
        } else if(mode.getInput() == 3) {
            send("Easy");
        } else if(mode.getInput() == 4) {
            send("Bozo");
        } else if(mode.getInput() == 5) {
            send("LLL");
        } else if(mode.getInput() == 6) {
            send("Looser");
        } else if(mode.getInput() == 7) {
            send("haha");
        } else if(mode.getInput() == 8) {
            send("noob");
        } else if(mode.getInput() == 9) {
            send("bot");
        } else if(mode.getInput() == 10) {
            send("youre bad");
        } else if(mode.getInput() == 11) {
            send("I need bread");
        } else if(mode.getInput() == 12) {
            send("xd");
        } else if(mode.getInput() == 13) {
            send("You need to be better to kill me");
        } else if(mode.getInput() == 14) {
            send("You need to be better");
        } else if(mode.getInput() == 15) {
            send("My Aim is god like");
        } else if(mode.getInput() == 16) {
            send("You dont know me");
        } else if(mode.getInput() == 17) {
            send("Your aim is not that good as mine");
        } else if(mode.getInput() == 18) {
            send("Good Game");
        } else if(mode.getInput() == 19) {
            send("GG");
        } else if(mode.getInput() == 20) {
            send("Nice");
        } else if(mode.getInput() == 21) {
            send("Ok");
        } else if(mode.getInput() == 22) {
            send("Yes");
        } else if(mode.getInput() == 23) {
            send("No");
        } else if(mode.getInput() == 24) {
            send("Why");
        } else if(mode.getInput() == 25) {
            send("lol");
        } else if(mode.getInput() == 26) {
            send("and");
        } else if(mode.getInput() == 27) {
            send("add me");
        } else if(mode.getInput() == 28) {
            send("send me your money");
        } else if(mode.getInput() == 29) {
            send("money is cool");
        } else if(mode.getInput() == 30) {
            send("bedwars");
        } else if(mode.getInput() == 31) {
            send("skywars");
        } else if(mode.getInput() == 32) {
            send("good luck");
        } else if(mode.getInput() == 33) {
            send("i dont need that");
        } else if(mode.getInput() == 34) {
            send("no problem");
        } else if(mode.getInput() == 35) {
            send("np");
        } else if(mode.getInput() == 36) {
            send("thank you");
        } else if(mode.getInput() == 37) {
            send("thx");
        } else if(mode.getInput() == 38) {
            send("lets play");
        } else if(mode.getInput() == 39) {
            send("lets go");
        } else if(mode.getInput() == 40) {
            send("you are gay");
        } else if(mode.getInput() == 41) {
            send("rekt");
        } else if(mode.getInput() == 42) {
            send("haha rekt");
        } else if(mode.getInput() == 43) {
            send("you got destroyed");
        } else if(mode.getInput() == 44) {
            send("destroyed");
        } else if(mode.getInput() == 45) {
            send("bruh");
        } else if(mode.getInput() == 46) {
            send("you are bad");
        } else if(mode.getInput() == 47) {
            send("you have bad hacks");
        } else if(mode.getInput() == 48) {
            send("otaku best client");
        } else if(mode.getInput() == 49) {
            send("i dont hack");
        } else if(mode.getInput() == 50) {
            send("the best hack is otaku");
        } else if(mode.getInput() == 51) {
            send("i use Otaku client");
        } else if(mode.getInput() == 52) {
            send("otaku b1.3");
        } else if(mode.getInput() == 53) {
            send("otaku is the best client");
        } else if(mode.getInput() == 54) {
            send("otaku client");
        } else if(mode.getInput() == 55) {
            send("i use otaku");
        } else if(mode.getInput() == 56) {
            send("i dont need hacks");
        } else if(mode.getInput() == 57) {
            send("i dont use Otaku client");
        } else if(mode.getInput() == 58) {
            send("i dont use Otaku");
        } else if(mode.getInput() == 59) {
            send("otaku b1.3 is out");
        } else if(mode.getInput() == 60) {
            send("Otaku b1.3 = https://discord.gg/WBApubEaVv");
        } else { mode.setValue(60); }
    }

    public void send(String message) {
        if(mc.thePlayer.getHealth() > 0 && mc.thePlayer.isEntityAlive()  ) {
            mc.thePlayer.sendChatMessage(message);
        }

        if(autoDis.isToggled())
            this.disable();
    }


    public net.minecraft.entity.Entity getEnemy() {
        java.util.Iterator var2 = mc.theWorld.playerEntities.iterator();

        net.minecraft.entity.player.EntityPlayer en;
        do {
            do {
                do {
                    if (!var2.hasNext()) {
                        return null;
                    }
                    en = (net.minecraft.entity.player.EntityPlayer) var2.next();
                } while(en == mc.thePlayer);
            } while(en.isDead);
        } while(a.b.module.modules.combat.AntiBot.bot(en));

        return en;
    }
}