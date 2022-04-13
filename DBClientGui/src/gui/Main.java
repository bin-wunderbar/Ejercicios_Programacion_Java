package gui;

import java.awt.EventQueue;

// ------------------------------------------------------------------------------------------------
public class Main 
{

	// --------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrameLogic frame = new MainFrameLogic ();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
