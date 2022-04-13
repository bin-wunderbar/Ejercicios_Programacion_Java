package gui;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;

// ------------------------------------------------------------------------------------------------
public class AreaDibujo extends Canvas 
{
	double grados;
	double x, y;

	// --------------------------------------------------------------------------------------------
	public AreaDibujo ()
	{
		setBackground (Color.BLACK);
		grados = 0.0;
		x = 100.0;
		y = 100.0;
	}
	
	// --------------------------------------------------------------------------------------------
	public void paint (Graphics g)
	{
		reset ();

		g.setColor (Color.WHITE);

		avance (g, 100);
		giraDerecha (130);
		avance (g, 100);
		giraDerecha (115);
		avance (g, 10);
	}
	
	// --------------------------------------------------------------------------------------------
	public void dibujaPoligono (Graphics g, int lados)
	{
		for (int i = 0; i < lados; i++)
		{
			avance (g, 100);
			giraDerecha (360 / lados);
		}
	}

	// --------------------------------------------------------------------------------------------
	public void avance (Graphics g, int longitud)
	{
		g.drawLine (
				(int)x, 
				(int)y,
				(int)(x += longitud * Math.cos ((grados * Math.PI / 180))),
				(int)(y -= longitud * Math.sin ((grados * Math.PI / 180)))
			);
		
	}
	
	// --------------------------------------------------------------------------------------------
	public void reset ()
	{
		grados = 0;
		x = getWidth () / 2;
		y = getHeight () / 2;
	}
	
	// --------------------------------------------------------------------------------------------
	public void giraDerecha (double grados)
	{
		this.grados -= grados;
	}

	// --------------------------------------------------------------------------------------------
	public void rota (double grados)
	{
		this.grados = grados;
	}
	
	
}
