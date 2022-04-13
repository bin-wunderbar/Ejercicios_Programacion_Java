import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Canvas;

// ------------------------------------------------------------------------------------------------
public class CButton extends CComponent
{
    private boolean pressed;
    private IComponentAction componentAction;

    // --------------------------------------------------------------------------------------------
    public CButton (int x, int y, int width, int height, String text)
    {
        super (x, y, width, height, text);
        pressed         = false;
        componentAction = null;
    }

    // --------------------------------------------------------------------------------------------
    @Override
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
    @Override
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
    @Override
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
    public void onMousePressed (MouseEvent mouseEvent) {}
    public void onMouseReleased (MouseEvent mouseEvent) {}
    public void onMouseClicked (MouseEvent mouseEvent) {}
}