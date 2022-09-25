package ravenNPlus.pane;

import net.minecraft.client.Minecraft;
import ravenNPlus.client.main.Client;
import ravenNPlus.client.utils.Utils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame implements ActionListener {

    JButton button;

    public Frame() {
        JButton button = new JButton();
        button.setBounds(200, 100, 100, 50);
        button.addActionListener(this);
        button.setText("Open ClickGui");
        button.setFocusable(false);
        button.setForeground(Color.MAGENTA);
        button.setBackground(Color.black);
        this.setLayout(null);
        this.setSize(100, 80);
        this.setVisible(true);
        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if(Utils.Player.isPlayerInGame() && Minecraft.getMinecraft().currentScreen != Client.clickGui) {
                Minecraft.getMinecraft().displayGuiScreen(Client.clickGui);
                Client.clickGui.initMain();
            }
        }
    }


    /*
    public Frame() {

        button = new JButton();
        button.addActionListener(this);
        button.setEnabled(Utils.Player.isPlayerInGame());
        button.setBounds(200, 100, 100, 50);
        button.setText("Open ClickGui");
        button.setFocusable(false);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setFont(new Font("Comic Sans", Font.BOLD, 20));
        button.setIconTextGap(-5);
        button.setForeground(Color.MAGENTA);
        button.setBackground(Color.black);
        button.setBorder(BorderFactory.createEtchedBorder());

        this.setTitle(ravenNPlus.client.main.RavenNPlus.name+" by SleepyFish");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(button);
        //this.add(check);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button.setEnabled(Utils.Player.isPlayerInGame());

        if (e.getSource() == button) {
            button.setEnabled(!(Minecraft.getMinecraft().currentScreen instanceof ravenNPlus.client.clickgui.RavenNPlus.ClickGui));

            if(Utils.Player.isPlayerInGame() && Minecraft.getMinecraft().currentScreen != RavenNPlus.clickGui) {
                Minecraft.getMinecraft().displayGuiScreen(RavenNPlus.clickGui);
                RavenNPlus.clickGui.initMain();
            }
        }
    }
     */
}