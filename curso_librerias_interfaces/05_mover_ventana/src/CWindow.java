import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Canvas;

// ------------------------------------------------------------------------------------------------
public class CWindow
{
    private int x, y, width, height;
    private Color fillColor, borderColor, fontColor, titleBarColor;
    private int titleBarHeight;
    private String text;
    private Canvas canvas;
    private boolean pressed;
    private int pressedX, pressedY;

    // --------------------------------------------------------------------------------------------
    public CWindow (Canvas canvas, int x, int y, int width, int height, String text)
    {
        this.canvas     = canvas;
        this.x          = x;
        this.y          = y;
        this.width      = width;
        this.height     = height;
        this.text       = text;
        fillColor       = Color.LIGHT_GRAY;
        borderColor     = Color.GRAY;
        fontColor       = Color.BLACK;
        titleBarColor   = Color.ORANGE;
        titleBarHeight  = 30;
    }

    // --------------------------------------------------------------------------------------------
    public void mousePressed (MouseEvent mouseEvent)
    {
        if (isInRectangleBar(mouseEvent.getX (), mouseEvent.getY ()))
        {
            pressedX    = mouseEvent.getX ();
            pressedY    = mouseEvent.getY ();
            pressed     = true;
        }
    }

    // --------------------------------------------------------------------------------------------
    public void mouseReleased (MouseEvent mouseEvent)
    {
        pressed = false;
        canvas.repaint ();

    }
    // --------------------------------------------------------------------------------------------
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
        g.fillRoundRect (x, y, width, height, 5, 5);
        g.setColor (borderColor);
        g.drawRoundRect (x, y, width, height, 5, 5);

        g.setColor (titleBarColor);
        g.fillRoundRect(x, y, width, titleBarHeight, 5, 5);
        g.setColor (borderColor);
        g.drawRoundRect (x, y, width, height, 5, 5);

        g.setColor (borderColor);
        g.drawRoundRect (x, y, width, titleBarHeight, 5, 5);
        g.setColor (fontColor);
        g.drawString (text, x + 15, y + 20);    
    }

}