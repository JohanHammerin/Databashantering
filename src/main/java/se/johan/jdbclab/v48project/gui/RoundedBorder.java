package se.johan.jdbclab.v48project.gui;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private int radius;



    //Constructor
    RoundedBorder(int radius) {
        setRadius(radius);
    }

    //Getter & Setter
    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


    //Methods
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(c.getForeground());
        g.drawRoundRect(x,y,width - 1, height - 1, getRadius(), getRadius());
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(getRadius() + 1, getRadius() + 1, getRadius() + 1, getRadius() + 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
