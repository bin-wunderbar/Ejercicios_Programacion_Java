import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Canvas;

// ------------------------------------------------------------------------------------------------
public class CButton
{
    private int x, y, width, height;
    private boolean pressed;
    private Color fillColor, borderColor, fontColor;
    private String text;
    private Canvas canvas;
    private IComponentAction componentAction;

    // --------------------------------------------------------------------------------------------
    public CButton (Canvas canvas, int x, int y, int width, int height, String text)
    {
        this.canvas     = canvas;
        this.x          = x;
        this.y          = y;
        this.width      = width;
        this.height     = height;
        this.text       = text;
        pressed         = false;
        fillColor       = Color.LIGHT_GRAY;
        borderColor     = Color.GRAY;
        fontColor       = Color.BLACK;
        componentAction = null;
    }

    // --------------------------------------------------------------------------------------------
    public void mousePressed (MouseEvent mouseEvent)
    {
        if (isInRectangle(mouseEvent.getX (), mouseEvent.getY ()))
        {
            onMousePressed (mouseEvent);
            pressed = true;
            canvas.repaint ();
        }
    }

    // --------------------------------------------------------------------------------------------
    public void mouseReleased (MouseEvent mouseEvent)
    {
        if (pressed && isInRectangle (mouseEvent.getX (), mouseEvent.getY ()))
        {
            onMouseReleased (mouseEvent);
            onMouseClicked (mouseEvent);
            if (componentAction != null)
            {
                componentAction.onComponentAction();
            }
        }

        pressed = false;
        canvas.repaint ();

    }

    // --------------------------------------------------------------------------------------------
    public void setComponentAction (IComponentAction componentAction)
    {
        this.componentAction = componentAction;
    }

    // --------------------------------------------------------------------------------------------
    public boolean isInRectangle (int xPointer, int yPointer)
    {
        return xPointer >= x && xPointer < x + width && yPointer >= y && yPointer < y + height;
    }

    // --------------------------------------------------------------------------------------------
    public void paint (Graphics g)
    {
        if (pressed)
        {
            paint (g, 2, 2);
        }
        else
        {
            paint (g, 0, 0);
        }
    }

    // --------------------------------------------------------------------------------------------
    private void paint (Graphics g, int xOffset, int yOffset)
    {
        g.setColor (fillColor);
        g.fillRoundRect (x + xOffset, y + yOffset, width, height, 5, 5);
        g.setColor (borderColor);
        g.drawRoundRect (x + xOffset, y + yOffset, width, height, 5, 5);
        g.setColor (fontColor);
        g.drawString (text, x + xOffset + 15, y + yOffset + 20);
    }

    // --------------------------------------------------------------------------------------------
    public void onMousePressed (MouseEvent mouseEvent) {}
    public void onMouseReleased (MouseEvent mouseEvent) {}
    public void onMouseClicked (MouseEvent mouseEvent) {}
}