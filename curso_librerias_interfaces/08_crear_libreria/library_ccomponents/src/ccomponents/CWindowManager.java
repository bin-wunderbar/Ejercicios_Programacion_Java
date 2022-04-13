package ccomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

// ------------------------------------------------------------------------------------------------
public class CWindowManager extends Canvas
{
    private ArrayList <CComponent> components;

    public CWindowManager ()
    {

        components = new ArrayList <> ();

        addMouseListener (new MouseAdapter (){
            @Override
            public void mousePressed (MouseEvent mouseEvent)
            {
                for (CComponent component : components)
                {
                    component.mousePressed(mouseEvent);
                }
            }

            @Override
            public void mouseReleased (MouseEvent mouseEvent)
            {
                for (CComponent component : components)
                {
                    component.mouseReleased (mouseEvent);
                }

            }        
        });

        addMouseMotionListener (new MouseMotionListener (){
            @Override
            public void mouseDragged (MouseEvent mouseEvent)
            {
                for (CComponent component : components)
                {
                    component.mouseDragged (mouseEvent);
                }

            }

            @Override
            public void mouseMoved (MouseEvent mouseEvent) {}
        });
    }
    
    // --------------------------------------------------------------------------------------------
    public void paint (Graphics g)
    {
        for (CComponent component : components)
        {
            component.paint (g);
        }

    }

    // --------------------------------------------------------------------------------------------
    public void addComponent (CComponent component)
    {
        component.setCanvas (this);
        components.add (component);
    }
}

