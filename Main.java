package com.muradn;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gamePlay=new GamePlay();

        obj.setBounds(10,10,700,600);
        obj.setTitle("Bouncing Ball");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);











        obj.setVisible(true);


    }
}
