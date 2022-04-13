import java.awt.Canvas;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

// ------------------------------------------------------------------------------------------------
public class CButtonOk extends CButton
{
    // --------------------------------------------------------------------------------------------
    public CButtonOk (Canvas canvas, int x, int y, int width, int height, String text)
    {
        super (canvas, x, y, width, height, text);
    }

    // --------------------------------------------------------------------------------------------
    public void onMouseClicked (MouseEvent mouseEvent)    
    {
        JOptionPane.showMessageDialog (null, "Ok!");
    }
}
