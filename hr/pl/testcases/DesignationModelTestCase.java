import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
class DesignationModelTestCase extends JFrame
{
private JScrollPane jsp;
private JTable table;
private Container container;
private DesignationModel designationModel;
DesignationModelTestCase()
{
this.designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new FlowLayout());
container.add(jsp);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=500;
int height=400;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}