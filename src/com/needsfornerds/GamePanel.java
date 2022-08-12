package com.needsfornerds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    boolean left = false, right = true;
    boolean up = false, down = false;
    int[] x = new int[750];
    int[] y = new int[750];
    int length = 3;
    int moves = 0;

    ImageIcon leftImg = new ImageIcon("src\\img\\left.png");
    ImageIcon rightImg = new ImageIcon("src\\img\\right.png");
    ImageIcon upImg = new ImageIcon("src\\img\\up.png");
    ImageIcon downImg = new ImageIcon("src\\img\\down.png");
    ImageIcon bodyImg = new ImageIcon("src\\img\\body.png");
    ImageIcon foodImg = new ImageIcon("src\\img\\food.png");

    Timer timer;
    int delay = 200;

    int[] foodXs = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
            525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    int[] foodYs = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425,
            450, 475, 500, 525, 550, 575, 600, 625};
    Random random = new Random();

    int foodX, foodY;
    private int score = 0;
    boolean gameOver = false;

    public GamePanel() {
        setBackground(Color.DARK_GRAY);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();
        newFood();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);

        g.setColor(Color.decode("#05C7F2"));
        g.fillRect(25, 11, 850, 54);

        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        if (moves == 0) {

            x[0] = 100;
            x[1] = 75;
            x[2] = 50;

            y[0] = 100;
            y[1] = 100;
            y[2] = 100;
        }

        g.setFont(new Font("", Font.BOLD, 25));
        g.drawString("Snake Game", 350, 45);

        g.setFont(new Font("", Font.PLAIN, 18));
        g.drawString("Score: " + score, 750, 45);

        // set head images
        if (left) {
            leftImg.paintIcon(this, g, x[0], y[0]);
        }
        if (right) {
            rightImg.paintIcon(this, g, x[0], y[0]);
        }
        if (up) {
            upImg.paintIcon(this, g, x[0], y[0]);
        }
        if (down) {
            downImg.paintIcon(this, g, x[0], y[0]);
        }

        // set body image
        for (int i=1; i<length; i++) {
            bodyImg.paintIcon(this, g, x[i], y[i]);
        }
        foodImg.paintIcon(this, g, foodX, foodY);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("", Font.BOLD, 50));
            g.drawString("Game Over!", 300, 300);

            g.setFont(new Font("", Font.ITALIC, 20));
            g.drawString("Press SPACE to restart", 320, 350);

            g.setFont(new Font("", Font.BOLD, 20));
            g.drawString("Score: " + score, 340, 400);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i=length-1; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if (left) {
            x[0] -= 25;
        }
        if (right) {
            x[0] += 25;
        }
        if (up) {
            y[0] -= 25;
        }
        if (down) {
            y[0] += 25;
        }

        if (x[0] > 850) {
            x[0] = 25;
        }
        if (x[0] < 25) {
            x[0] = 850;
        }
        if (y[0] > 625) {
            y[0] = 75;
        }
        if (y[0] < 75) {
            y[0] = 625;
        }

        eatenFood();
        bodyCollision();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
            up = true;
            right = left = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
            down = true;
            right = left = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();
        }
    }

    public void newFood() {
        foodX = foodXs[random.nextInt(34)];
        foodY = foodYs[random.nextInt(23)];

        for (int i=length-1; i>=0; i--) {
            if (x[i] == foodX && y[i] == foodY) {
                newFood();
            }
        }
    }

    public void eatenFood() {
        if (x[0] == foodX && y[0] == foodY) {
            newFood();
            length++;
            score++;
        }
    }

    public void bodyCollision() {
        for (int i=length-1; i>0; i--) {
            if (x[i] == x[0] && y[i] == y[0]) {
                timer.stop();
                gameOver = true;
            }
        }
    }

    public void restart() {
        gameOver = false;
        moves = score = 0;
        right = true;
        left = up = down = false;
        length = 3;
        timer.start();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
