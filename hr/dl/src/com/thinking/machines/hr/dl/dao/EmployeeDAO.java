package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationValid=designationDAO.codeExists(designationCode);
if(isDesignationValid==false) throw new DAOException("Invalid designation code :"+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not set to Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan  number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is zero");
try
{
int lastGeneratedEmployeeId=10000000;
int recordCount=0;
String lastGeneratedEmployeeIdString="";
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPanNumber;

String fAadharCardNumber;
boolean panNumberExists,aadharCardNumberExists;
panNumberExists=false;
aadharCardNumberExists=false;
int i;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=7;i++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists) break;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Pan number ("+panNumber+") exists , aadhar card number ("+aadharCardNumber+") exists .");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("aadhar card number ( "+aadharCardNumber+" ) exists.");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("employee is null");
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationValid=designationDAO.codeExists(designationCode);
if(isDesignationValid==false) throw new DAOException("Invalid designation code :"+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not set to Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Pan  number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id :"+employeeId); 
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) throw new DAOException("Invalid employee id :"+employeeId);
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fPANNumber;
String fAadharCardNumber;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
int x;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=6;x++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false && fPANNumber.equalsIgnoreCase(employeeId))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}
if(employeeIdFound==false) 
{
randomAccessFile.close();
throw new DAOException("Invalid employee id :"+employeeId);
}
boolean panNumberExists=false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ( "+panNumber+" ) exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("aadhar card number ( "+aadharCardNumber+" ) exists");
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan Number ( "+panNumber+" ) and aadhar card number ( "+aadharCardNumber+" ) exists");
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employee is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id :"+employeeId); 
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) throw new DAOException("Invalid employee id :"+employeeId);
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
boolean employeeIdFound=false;
int x;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false) 
{
randomAccessFile.close();
throw new DAOException("Invalid employee id :"+employeeId);
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
recordCount--;
String recordCountString="";
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
Date dateOfBirth;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
}
gender=randomAccessFile.readLine().charAt(0);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("file not exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("no records");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
String fEmployeeId;
Date dob;
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
int i;
boolean found=false;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
System.out.println(fEmployeeId +","+employeeId);
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
found=true;
break;
}
for(i=1;i<=8;i++) randomAccessFile.readLine();
}
if(found==false) throw new DAOException("Invalid employeeId");
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
dob=sdf.parse(randomAccessFile.readLine());
employeeDTO.setDateOfBirth(dob);
gender=randomAccessFile.readLine().charAt(0);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
return employeeDTO;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar card is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("file not exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("no records");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
String fAadharCardNumber;
Date dob;
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
int i;
boolean found=false;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
dob=sdf.parse(randomAccessFile.readLine());
employeeDTO.setDateOfBirth(dob);
gender=randomAccessFile.readLine().charAt(0);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
fAadharCardNumber=randomAccessFile.readLine().trim();
employeeDTO.setAadharCardNumber(fAadharCardNumber);
if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
found=true;
break;
}
}
if(found==false) throw new DAOException("Invalid aadhar card number.");
return employeeDTO;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("file not exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("no records");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
Date dob;
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
int i;
boolean found=false;
String fPanNumber;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
dob=sdf.parse(randomAccessFile.readLine());
employeeDTO.setDateOfBirth(dob);
}catch(ParseException pe)
{
}

gender=randomAccessFile.readLine().charAt(0);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
fPanNumber=randomAccessFile.readLine();
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
if(panNumber.equalsIgnoreCase(fPanNumber))
{
found=true;
break;
}
}
if(found==false) throw new DAOException("Invalid PAN number.");
return employeeDTO;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
int i;
int fDesignationCode;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
for(i=1;i<=6;i++) randomAccessFile.readLine();
if(designationCode==fDesignationCode)
{
found=true;
break;
}
}
if(found==false) return false;
return true;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int code) throws DAOException
{
if(new DesignationDAO().codeExists(code)==false) throw new DAOException("Invalid code "+code);
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date dateOfBirth;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int x;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=code)
{
for(x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
dateOfBirth=sdf.parse(randomAccessFile.readLine());
employeeDTO.setDateOfBirth(dateOfBirth);
}catch(ParseException pe)
{
}
gender=randomAccessFile.readLine().charAt(0);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid employee id");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
int i;
String fEmployeeId;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
found=true;
break;
}
for(i=1;i<=8;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return found;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
int i;
String fPANNumber;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=7;i++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
if(panNumber.equalsIgnoreCase(fPANNumber))
{
found=true;
break;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return found;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid aadhar card number");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("file not exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
int i;
String fAadharCardNumber;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=8;i++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
found=true;
break;
}
}
if(found==false) return false;
return true;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
int count=0;
randomAccessFile.readLine();
count=Integer.parseInt(randomAccessFile.readLine().trim());
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code");
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("file not exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
int i;
int count=0;
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
for(i=1;i<=6;i++) randomAccessFile.readLine();
if(designationCode==fDesignationCode) count++;
}
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}