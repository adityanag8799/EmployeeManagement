import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class DesignationTableModel extends AbstractTableModel
{
private Set<DesignationDTOInterface> designations;
private Iterator<DesignationDTOInterface> value;
private java.util.List<DesignationDTOInterface> designationsList;
private DesignationDTOInterface designation;
private String title[];
DesignationTableModel()
{
populateDataStructure();
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int r,int c)
{
if(c==0) return new Integer(r+1);
if(c==1) return designationsList.get(r).getCode();
if(c==2) return designationsList.get(r).getTitle();
return null;
}
public String getColumnName(int c)
{
return title[c];
}
public boolean isCellEditable(int r,int c)
{
return false;
}
public Class getColumnClass(int c)
{
Class cc=null;
try
{
if(c==0 || c==1) cc=Class.forName("java.lang.Integer");
if(c==2) cc=Class.forName("java.lang.String");
}catch(Exception e)
{
System.out.println(e);
}
return cc;
}
private void populateDataStructure()
{
try
{
title=new String[3];
title[0]="S.No.";
title[1]="Code";
title[2]="Title";
designations=new TreeSet<>();
designations=new DesignationDAO().getAll();
designationsList=new ArrayList<DesignationDTOInterface>();
value=designations.iterator();
while(value.hasNext()) 
{
designationsList.add(value.next());
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
class swing3 extends JFrame
{
private JTable table;
private JScrollPane jsp;
private DesignationTableModel dsm;
private Container container;
swing3()
{
dsm=new DesignationTableModel();
table=new JTable(dsm);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.add(jsp);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=500;
int height=400;
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setSize(width,height);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}
class swing3psp
{
public static void main(String gg[])
{
swing3 s=new swing3();
}
}
