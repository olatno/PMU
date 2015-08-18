package scr.protool.client.utilities;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*; 

/**
 * Partition.java
 *
 *
 * Created: Fri Jun 11 22:03:22 2010
 *
 * @author <a href="mailto:"></a>
 * @version 1.0
 */
public class Partition implements Border{

    private final int radius;
    String patitionClass;

    public Partition(int radius, String patitionClass){
	this.radius = radius;
	this.patitionClass = patitionClass;


    }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

	    public boolean isBorderOpaque() {
            return true;
        }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
	Graphics2D g2d = (Graphics2D)g;
	g2d.setColor(Color.red);
	if( patitionClass.equals("Main"))
	    g2d.drawRoundRect(x+150, y+100, width-300, height-200, radius, radius);
	else if( patitionClass.equals("Tool"))
	   // g2d.drawRoundRect(x+5, y+10, width-10, height-10, radius, radius);
	       g2d.drawRoundRect(x+5, y+10, width-10, height-22, radius, radius);
	else if( patitionClass.equals("AddProject"))
	    g2d.drawRoundRect(x+8, y+10, width-20, height-20, radius, radius);			
	else if( patitionClass.equals("Ledger"))
	    g2d.drawRoundRect(x+8, y+10, width-20, height-20, radius, radius);
	else if( patitionClass.equals("AddPaymentout"))
	    g2d.drawRoundRect(x+8, y+10, width-20, height-20, radius, radius);
	else if( patitionClass.equals("SpecialBilling"))
	    g2d.drawRoundRect(x+8, y+10, width-20, height-25, radius, radius);
	else if( patitionClass.equals("ProjectDetail"))
	    g2d.drawRoundRect(x+8, y+10, width-20, height-30, radius, radius);

    }

}



