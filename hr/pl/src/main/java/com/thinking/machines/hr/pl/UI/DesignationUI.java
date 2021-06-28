package com.thinking.machines.hr.pl.UI;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private DesignationModel designationModel;
private DesignationPanel designationPanel;
private JTable designationTable;
private JScrollPane scrollPane;
private JLabel designationLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private Container container;
private enum MODE{VIEW,ADD,EDIT,DELETE,CANCEL,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon deleteIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon clearIcon;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
private void initComponents()
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
addIcon=new ImageIcon(this.getClass().getResource("/icons/add.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf.png"));
clearIcon=new ImageIcon(this.getClass().getResource("/icons/clear.png"));
setIconImage(logoIcon.getImage());
designationModel=new DesignationModel();
designationTable=new JTable(designationModel);
designationPanel=new DesignationPanel();
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
searchErrorLabel=new JLabel("");
clearSearchTextFieldButton=new JButton(clearIcon);
container=getContentPane();
}
private void setAppearance()
{

Font designationLabelFont=new Font("Verdana",Font.BOLD,16);
Font searchLabelFont=new Font("Verdana",Font.BOLD,14);
Font searchErrorLabelFont=new Font("Verdana",Font.BOLD,10);
Font searchTextFieldFont=new Font("Verdana",Font.PLAIN,14);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,12);
designationLabel.setFont(designationLabelFont);
searchLabel.setFont(searchLabelFont);
searchErrorLabel.setFont(searchErrorLabelFont);
searchTextField.setFont(searchTextFieldFont);
searchErrorLabel.setForeground(Color.RED);
JTableHeader columnHeader=designationTable.getTableHeader();
columnHeader.setFont(columnHeaderFont);
columnHeader.setReorderingAllowed(false);
columnHeader.setResizingAllowed(false);
designationTable.setRowHeight(25);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(30);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(390);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
container.setLayout(null);
int lm=0;
int tp=0;
designationLabel.setBounds(lm+10,tp+10,200,20);
searchLabel.setBounds(lm+10,tp+10+30+10-5,60,25);
searchErrorLabel.setBounds(lm+10+60+380-65,tp+10+20,60,20);
searchTextField.setBounds(lm+10+60+5,tp+10+30+10-5,370,25);
clearSearchTextFieldButton.setBounds(lm+10+60+370+5,tp+10+30+10-5,25,25);
scrollPane.setBounds(lm+10,tp+10+30+10+30-5,470,200);
designationPanel.setBounds(lm+10,tp+10+30+10+30+170+25,470,180);
container.add(designationLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=500;
int height=500;
setSize(width,height);
setLocation((d.width/2)-(width/2),(d.height/2)-(height/2));
setVisible(true);
}
public void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
public void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
public void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
public void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
public void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus(); 	
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
public void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
public void removeUpdate(DocumentEvent de)
{
searchDesignation();
}
public void changedUpdate(DocumentEvent de)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}

//Inner class
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JPanel buttonPanel;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JButton saveButton;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation : ");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton(clearIcon);
buttonPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
deleteButton=new JButton(deleteIcon);
cancelButton=new JButton(cancelIcon);
exportToPDFButton=new JButton(pdfIcon);
saveButton=new JButton(saveIcon);
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.titleLabel.setVisible(true);
this.titleLabel.setEnabled(true);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
} 
}
void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.titleTextField.setText("");
this.titleTextField.setEnabled(true);
this.clearTitleTextFieldButton.setVisible(true);
this.clearTitleTextFieldButton.setEnabled(true);
this.addButton.setIcon(saveIcon);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit.");
return;
}
DesignationUI.this.setEditMode();
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.titleTextField.setText(this.designation.getTitle());
this.clearTitleTextFieldButton.setVisible(true);
this.clearTitleTextFieldButton.setEnabled(true);
this.addButton.setEnabled(false);
this.editButton.setIcon(saveIcon);
this.deleteButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.exportToPDFButton.setEnabled(false);
}
void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete.");
return;
}
DesignationUI.this.setDeleteMode();
this.titleTextField.setEnabled(false);
this.clearTitleTextFieldButton.setEnabled(false);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
deleteDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
this.titleTextField.setEnabled(false);
this.clearTitleTextFieldButton.setEnabled(false);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
}
private void setAppearance()
{
Font titleCaptionLabelFont=new Font("Verdana",Font.BOLD,16);
Font titleLabelFont=new Font("Verdana",Font.PLAIN,16);
Font titleTextFieldFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(titleCaptionLabelFont);
titleLabel.setFont(titleLabelFont);
titleTextField.setFont(titleTextFieldFont);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
int lm,tp;
lm=0;
tp=0;
titleCaptionLabel.setBounds(lm+15,tp+15,150,20);
titleLabel.setBounds(lm+200,tp+15,200,20);
titleTextField.setBounds(lm+150,tp+15,270,25);
clearTitleTextFieldButton.setBounds(lm+420,tp+15,20,25);
buttonPanel.setBounds(lm+30,tp+60,430,100);
addButton.setBounds(50,20,40,40);
editButton.setBounds(50+50+20,20,40,40);
deleteButton.setBounds(50+50+20+50+20,20,40,40);
cancelButton.setBounds(50+50+20+50+20+50+20,20,40,40);
exportToPDFButton.setBounds(50+50+20+50+20+50+20+50+20,20,40,40);
buttonPanel.setLayout(null);
buttonPanel.add(addButton);
buttonPanel.add(editButton);
buttonPanel.add(deleteButton);
buttonPanel.add(cancelButton);
buttonPanel.add(exportToPDFButton);
setLayout(null);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
titleTextField.setVisible(false);
add(clearTitleTextFieldButton);
clearTitleTextFieldButton.setEnabled(false);
add(buttonPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required.");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
// do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasExceptions())
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required.");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}
private void deleteDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,this.designation.getTitle()+" ? ","Delete",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted .");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}
private void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation()) setViewMode();
else setAddMode();
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation())
{
setViewMode();
}
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});
this.clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jFileChooser=new JFileChooser();
jFileChooser.setAcceptAllFileFilterUsed(false);
jFileChooser.setCurrentDirectory(new File("."));
jFileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory()) return true;
if(file.getName().endsWith(".pdf")) return true;
return false;
}
public String getDescription()
{
return "pdf file";
}
});
int selectedOption=jFileChooser.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jFileChooser.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path"+parent.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
if(file.exists()) 
{
selectedOption=JOptionPane.showConfirmDialog(DesignationUI.this,file+"already exists ,Do you want to override ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
}
JOptionPane.showMessageDialog(DesignationUI.this,"File saved :"+parent.getAbsolutePath());
}
catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}catch(Exception e)
{
System.out.println(e);
}
}
}
});

} // add Listener ends.
} // inner class ends 
}