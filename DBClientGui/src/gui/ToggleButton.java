package gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JToggleButton;

// ------------------------------------------------------------------------------------------------
public class ToggleButton extends JToggleButton 
{
	private String toolTipOn;
	private String toolTipOff;
	private Icon iconOn;
	private Icon iconOff;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------------------------------
	public ToggleButton ()
	{
		addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				setSelected (isSelected ());
			}
		});
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setSelected (boolean selected)
	{
		super.setSelected(selected);
		
		setToolTipText (selected ? toolTipOn : toolTipOff);
		setIcon (selected ? iconOn : iconOff);
	}

	// --------------------------------------------------------------------------------------------
	public String getToolTipOn() {
		return toolTipOn;
	}

	public void setToolTipOn(String toolTipOn) {
		this.toolTipOn = toolTipOn;
	}

	public String getToolTipOff() {
		return toolTipOff;
	}

	public void setToolTipOff(String toolTipOff) {
		this.toolTipOff = toolTipOff;
	}

	public Icon getIconOn() {
		return iconOn;
	}

	public void setIconOn(Icon iconOn) {
		this.iconOn = iconOn;
	}

	public Icon getIconOff() {
		return iconOff;
	}

	public void setIconOff(Icon iconOff) {
		this.iconOff = iconOff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
