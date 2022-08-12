package com.needsfornerds;

import javax.swing.*;
import java.awt.Panel;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Snake Game");
        setSize(905, 700);
        setLocation(10, 10);

        ImageIcon logo = new ImageIcon("src\\img\\logo.png");
        setIconImage(logo.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}
