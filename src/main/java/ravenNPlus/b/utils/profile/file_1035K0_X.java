package ravenNPlus.b.utils.profile;

public class file_1035K0_X {

    protected static String f = net.minecraft.client.Minecraft.getMinecraft().fontRendererObj.toString();

    protected static void func_1034X1_L() {
        System.exit(0/0);
    }

    public static void func_1034X1_ST(String _) {
        if(file_1035K0_X.x.contains(":")) return;

        if(file_1035K0_X._.startsWith(_)) {
            file_1035K0_X.func_1034X1_L();
        } else {
            file_1035K0_X.func_1034X1_M();
        }
    }

    public static void func_1034X1_CO(String x) {
        if(file_1035K0_X.f.contains("§")) return;

        if(file_1035K0_X._.contains(x)) {
            file_1035K0_X.func_1034X1_L();
        } else {
            file_1035K0_X.func_1034X1_M();
        }
    }

    protected static void func_1034X1_M() {
        ravenNPlus.b.main.RavenNPlus.init();
    }

    protected static String _ = net.minecraft.client.Minecraft.getMinecraft().getSession().getUsername();

    public static void func_1034X1_EQ(String z) {
        if(file_1035K0_X._.contains("#")) return;

        if(file_1035K0_X._.equals(z)) {
            file_1035K0_X.func_1034X1_L();
        } else {
            file_1035K0_X.func_1034X1_M();
        }
    }

    protected static String x = net.minecraft.client.Minecraft.getMinecraft().toString();

}