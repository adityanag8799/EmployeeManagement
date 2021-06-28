package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
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
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
throw new DAOException("Invalid designation code :"+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to Male/Female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
throw new DAOException("basic salary is null");
}
if(basicSalary.signum()==-1) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Pan  number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of pan number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of aadhar card number is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") exists , aadhar card number ("+aadharCardNumber+") exists .");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number ( "+aadharCardNumber+" ) exists.");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,is_indian,gender,date_of_birth,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
preparedStatement.setBoolean(3,isIndian);
preparedStatement.setString(4,String.valueOf(gender)); // imp
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(5,sqlDateOfBirth);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(10000000+generatedEmployeeId));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee id. is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id. is zero");
int actualEmployeeId;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee id.");
}
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try 
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
throw new DAOException("Invalid code"+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to Male/Female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
throw new DAOException("basic salary is null");
}
if(basicSalary.signum()==-1) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Pan  number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of pan number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of aadhar card number is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") exists , aadhar card number ("+aadharCardNumber+") exists .");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number ( "+aadharCardNumber+" ) exists.");
}
preparedStatement=connection.prepareStatement("update employee set name=?,set designation_code=?,set is_indian=?,set gender=?,set date_of_birth=?,set basic_salary=?,set pan_number=?,set aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
preparedStatement.setBoolean(3,isIndian);
preparedStatement.setString(4,String.valueOf(gender)); // imp
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(5,sqlDateOfBirth);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id. is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id. is zero");
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try 
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
throw new DAOException("Invalid employee Id:"+employeeId);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
try
{
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee;");
EmployeeDTOInterface employeeDTO;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+String.valueOf(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);   // convert sql date into Util date
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;  
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;
resultSet.next();
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(String.valueOf(resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);   // convert sql date into Util date
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar card is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(String.valueOf(resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));   // convert sql date into Util date
if(resultSet.getString("gender").charAt(0)=='M') employeeDTO.setGender(GENDER.MALE);
if(resultSet.getString("gender").charAt(0)=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+String.valueOf(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);   // convert sql date into Util date
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
boolean found=resultSet.next();
preparedStatement.close();
resultSet.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int code) throws DAOException
{
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code :"+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;

java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+String.valueOf(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);   // convert sql date into Util date
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid employee id");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid aadhar card number");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as cnt from employee");
int count=resultSet.getInt(1);
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select count(*) from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : "+designationCode);
}
int count=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}