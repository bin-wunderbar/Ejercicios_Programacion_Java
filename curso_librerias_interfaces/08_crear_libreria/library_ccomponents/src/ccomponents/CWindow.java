package ccomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Canvas;
import java.util.ArrayList;

// ------------------------------------------------------------------------------------------------
public class CWindow extends CComponent
{
    protected Color titleBarColor;
    protected int titleBarHeight;
    protected int pressedX, pressedY;
    protected boolean pressed;
    protected ArrayList <CComponent> components;

    // --------------------------------------------------------------------------------------------
    public CWindow (int x, int y, int width, int height, String text)
    {
        super (x, y, width, height, text);
        titleBarColor   = Color.ORANGE;
        titleBarHeight  = 30;
        pressed         = false;
        components      = new ArrayList <> ();
    }

    // --------------------------------------------------------------------------------------------
    public void addComponent (CComponent component)
    {
        component.setCanvas (canvas);
        component.setParent (this);
        components.add (component);
    }

    // --------------------------------------------------------------------------------------------
    @Override
    public void mousePressed (MouseEvent mouseEvent)
    {
        if (isInRectangleBar(mouseEvent.getX (), mouseEvent.getY ()))
        {
            pressedX    = mouseEvent.getX ();
            pressedY    = mouseEvent.getY ();
            pressed     = true;
        }

        for (CComponent component : components)
        {
            component.mousePressed(mouseEvent);
        }
    }

    // --------------------------------------------------------------------------------------------
    @Override
    public void mouseReleased (MouseEvent mouseEvent)
    {
        pressed = false;
        canvas.repaint ();

        for (CComponent component : components)
        {
            component.mouseReleased (mouseEvent);
        }

    }
    // --------------------------------------------------------------------------------------------
    @Override
    public void mouseDragged (MouseEvent mouseEvent)
    {
        if (pressed)
        {

            int pointerX = mouseEvent.getX ();
            int pointerY = mouseEvent.getY ();
    
            if (pressedX != pointerX || pressedY != pointerY)
            {
                x += pointerX - pressedX;
                y += pointerY - pressedY;
    
                pressedX = pointerX;
                pressedY = pointerY;
                canvas.repaint ();
            }
        }

    }
    // --------------------------------------------------------------------------------------------
    public boolean isInRectangleBar (int xPointer, int yPointer)
    {
        return xPointer >= x && xPointer < x + width && yPointer >= y && yPointer < y + titleBarHeight;
    }

    // --------------------------------------------------------------------------------------------
    public void paint (Graphics g)
    {
        g.setColor (fillColor);
        g.fillRoundRect (getRelativeX(), getRelativeY(), width, height, 5, 5);
        g.setColor (borderColor);
        g.drawRoundRect (getRelativeX(), getRelativeY(), width, height, 5, 5);

        g.setColor (titleBarColor);
        g.fillRoundRect(getRelativeX(), getRelativeY(), width, titleBarHeight, 5, 5);
        g.setColor (borderColor);
        g.drawRoundRect (getRelativeX(), getRelativeY(), width, height, 5, 5);

        g.setColor (borderColor);
        g.drawRoundRect (getRelativeX(), getRelativeY(), width, titleBarHeight, 5, 5);
        g.setColor (fontColor);
        g.drawString (text, getRelativeX() + 15, getRelativeY() + 20);

        for (CComponent component : components)
        {
            component.paint (g);
        }

    }

}