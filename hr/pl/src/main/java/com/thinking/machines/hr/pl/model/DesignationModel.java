package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.image.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private Set<DesignationInterface> blDesignations;
private String title[];
public DesignationModel()
{
this.populateDataStructures();
}
private void populateDataStructures()
{
title=new String[2];
title[0]="S.NO";
title[1]="Designations";
try
{
this.designationManager=DesignationManager.getDesignationManager();
this.blDesignations=new TreeSet<>();
this.blDesignations=designationManager.getDesignations();
}catch(BLException blException)
{
System.out.println(blException.getMessage());
}
this.designations=new LinkedList<>();
for(DesignationInterface designation : blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return title.length;
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
if(columnIndex==1) return String.class;
return null;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
else return designations.get(rowIndex).getTitle();
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged(); //vvvvvvvvimp
}
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
int index;
index=0;
for(DesignationInterface d : this.designations)
{
if(d.equals(designation)) return index;
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Designation : "+designation);
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
int index=0;
Iterator<DesignationInterface> designationsIterator=this.designations.iterator();
DesignationInterface d;
while(designationsIterator.hasNext())
{
d=designationsIterator.next();
if(partialLeftSearch) if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
else if(d.getTitle().equalsIgnoreCase(title)) return index;
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Designation title : "+title);
throw blException;
}
public void update(DesignationInterface designation) throws BLException
{
this.designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
this.designationManager.removeDesignation(code);
Iterator<DesignationInterface> designationIterator=this.designations.iterator();
int index=0;
while(designationIterator.hasNext())
{
if(designationIterator.next().getCode()==code) break;
index++;
}
if(index==this.designations.size()) 
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation code :"+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size()) 
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
DesignationInterface designation;
designation=this.designations.get(index);
return designation;
}
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);
Image companyLogo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoParagraph=new Paragraph();
logoParagraph.add(companyLogo);
Paragraph companyName=new Paragraph();
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyName.setFont(companyNameFont);
companyName.setFontSize(18);
companyName.add("Client Smile");
Paragraph reportTitlePara=new Paragraph();
reportTitlePara.add(" List of Designations ");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(16);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont titleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph title1=new Paragraph("S.NO.");
Paragraph title2=new Paragraph("Designation");
title1.setFont(titleFont);
title1.setFontSize(15);
title2.setFont(titleFont);
title2.setFontSize(15);
Paragraph pageNumberParagraph;
Paragraph dataParagraph;
Paragraph paragraph;
Table pageNumberTable;
int sno,x;
int pageSize=15;
int totalPages;
boolean newPage=true;
if(this.designations.size()%pageSize==0) totalPages=this.designations.size()/pageSize;
else totalPages=this.designations.size()/pageSize+1;
Table topTable;
Table dataTable=null;
Cell cell;
float topTableColumnWidth[]={1,5};
float dataTableColumnWidth[]={1,5};
int pageNumber=0;
DesignationInterface designation;
sno=0;
x=0;
while(x<this.designations.size())
{
if(newPage==true) //create header
{
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidth));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoParagraph);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyName);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);
paragraph=new Paragraph();
paragraph.add(new Paragraph("Designations"));
pageNumberParagraph=new Paragraph("Page : "+pageNumber+"/"+totalPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(14);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
document.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidth));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(title1);
dataTable.addHeaderCell(title2);
newPage=false;
}
designation=this.designations.get(x);
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);

x++;
if(sno%pageSize==0 || x==this.designations.size())
{
document.add(dataTable);
document.add(new Paragraph("Software Developed by : Aditya Nag"));
if(x<designations.size()) 
{
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
} // loop ends
document.close();
}catch(Exception e)
{
System.out.println("Exception aayaaa : "+e.getMessage());
}
}
}