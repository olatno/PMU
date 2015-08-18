
package scr.protool.client.project;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
import java.text.*;
import java.io.*;
import java.math.BigDecimal;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;
/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/

 
class FinancialActivitiesLabel extends JLabel { 
	private ArrayList<BigDecimal> datas;
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu = new PaymentInUtilities();
	private Date date;
	private final int y = 20;
	private final int x = 20;
	private SimpleDateFormat dateFormat; 
 
    public FinancialActivitiesLabel(int projectId) {
		bean = new BeanStud();	
		dateFormat = new SimpleDateFormat("dd-MM-YYYY", Locale.UK);
		datas = pu.getArrayListBigDecimal(bean.connect().projectFinancialActivities(projectId));
        setPreferredSize(new Dimension(500, 380)); 
		date = new Date();
		setBorder(BorderFactory.createLineBorder(Color.black));

    } 
 
    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
		g.setFont(new Font(null, Font.BOLD, 14) );
		g.drawString("INCOMING", 120, 100);
		g.drawString("OURGOING", 120, 120 + y);
		
		g.drawString("Incoming Total", x + 200, 100+y);
		
		g.setColor(Color.BLUE);
		g.drawString(ul.getStringInCurrency(datas.get(0)), x+320, 100+y);
		
		g.setColor(Color.BLACK);
		g.drawString("Material Total", x  + 200, 140 + y);
		g.setColor(Color.BLUE);
		g.drawString(ul.getStringInCurrency(datas.get(1)), x + 320, 140 + y);
		
		g.setColor(Color.BLACK);
		g.drawString("Labour Total", x + 200, 160 + y);
		g.setColor(Color.BLUE);
		g.drawString(ul.getStringInCurrency(datas.get(2)),x + 320, 160 + y);
		
		g.setColor(Color.BLACK);
		g.drawString(String.format("%s %s", "Position as at", dateFormat.format(date)), 120, 200 + y);
		if(datas.get(3).signum() == -1){
			g.setColor(Color.RED);
			g.drawString(ul.getStringInCurrency(datas.get(3)), x + 320, 200 + y);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString(ul.getStringInCurrency(datas.get(3)), x + 320, 200 + y);
		}

    }
}	
