import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Canvas;

// ------------------------------------------------------------------------------------------------
public class CComponent
{
    protected int x, y, width, height;
    protected Color fillColor, borderColor, fontColor;
    protected String text;
    protected Canvas canvas;
    protected CComponent parent;

    // --------------------------------------------------------------------------------------------
    public CComponent (int x, int y, int width, int height, String text)
    {
        initCComponent (null, canvas, x, y, width, height, text);
    }

    // --------------------------------------------------------------------------------------------
    public CComponent (CComponent parent, Canvas canvas, int x, int y, int width, int height, String text)
    {
        initCComponent (parent, canvas, x, y, width, height, text);
    }

    // --------------------------------------------------------------------------------------------
    private void initCComponent (CComponent parent, Canvas canvas, int x, int y, int width, int height, String text)
    {
        this.parent     = parent;
        this.canvas     = canvas;
        this.x          = x;
        this.y          = y;
        this.width      = width;
        this.height     = height;
        this.text       = text;
        fillColor       = Color.LIGHT_GRAY;
        borderColor     = Color.GRAY;
        fontColor       = Color.BLACK;
    }

    // --------------------------------------------------------------------------------------------
    public void mousePressed (MouseEvent mouseEvent){}
    public void mouseReleased (MouseEvent mouseEvent){}
    public void mouseDragged (MouseEvent mouseEvent){}
    public void mouseMotion (MouseEvent mouseEvent){}

    // --------------------------------------------------------------------------------------------
    public boolean isInRectangle (int xPointer, int yPointer)
    {
        return xPointer >= getRelativeX() && xPointer < getRelativeX() + width && yPointer >= getRelativeY() && yPointer < getRelativeY() + height;
    }

    // --------------------------------------------------------------------------------------------
    public void paint (Graphics g)
    {
        paint (g, 0, 0);
    }

    // --------------------------------------------------------------------------------------------
    protected void paint (Graphics g, int xOffset, int yOffset)
    {

        g.setColor (fillColor);
        g.fillRoundRect (getRelativeX() + xOffset, getRelativeY() + yOffset, width, height, 5, 5);

        g.setColor (borderColor);
        g.drawRoundRect (getRelativeX() + xOffset, getRelativeY() + yOffset, width, height, 5, 5);

        g.setColor (fontColor);
        g.drawString (text, getRelativeX() + xOffset + 15, getRelativeY() + yOffset + 20);
    }

    // --------------------------------------------------------------------------------------------
    public void setCanvas (Canvas canvas)
    {
        this.canvas = canvas;
    }

    // --------------------------------------------------------------------------------------------
    public void setParent (CComponent parent)
    {
        this.parent = parent;
    }

    // --------------------------------------------------------------------------------------------
    public int getRelativeX ()
    {
        if (parent != null)
        {
            return parent.x + x;
        }

        return x;
    }

    // --------------------------------------------------------------------------------------------
    public int getRelativeY ()
    {
        if (parent != null)
        {
            return parent.y + y;
        }

        return y;
    }

    // --------------------------------------------------------------------------------------------
    public void setBounds (int x, int y, int width, int height) throws CComponentBoundsException
    {
        this.x      = x;
        this.y      = y;
        if (width > 1)
        {
            this.width  = width;
        }
        else
        {
            throw new CComponentBoundsException ("Error al especificar el ancho");
        }

        if (height > 1)
        {
            this.height = height;
        }
        else
        {
            throw new CComponentBoundsException ("Error al especificar el ancho");
        }

    }
}