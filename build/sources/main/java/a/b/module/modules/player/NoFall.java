package a.b.module.modules.player;

import a.b.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
   public NoFall() {
      super("NoFall", ModuleCategory.player);
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance > 2.5D) {
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }
}
