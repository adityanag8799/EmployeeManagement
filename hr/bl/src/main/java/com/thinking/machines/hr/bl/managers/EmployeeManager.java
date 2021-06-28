package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeeMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeeMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeeMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
private Set<EmployeeInterface> employeesSet;
private static EmployeeManagerInterface employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}
public static final EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeeMap=new HashMap<>();
this.panNumberWiseEmployeeMap=new HashMap<>();
this.aadharCardNumberWiseEmployeeMap=new HashMap<>();
this.designationCodeWiseEmployeeMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
Set<EmployeeDTOInterface> dlEmployees=new EmployeeDAO().getAll();
String employeeId="";
String panNumber="";
String aadharCardNumber="";
char gender;
EmployeeInterface dsEmployee;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
int designationCode;
Set<EmployeeInterface> et;
for(EmployeeDTOInterface dlEmployee : dlEmployees)
{
dsEmployee=new Employee();
employeeId=dlEmployee.getEmployeeId();
panNumber=dlEmployee.getPANNumber();
aadharCardNumber=dlEmployee.getAadharCardNumber();
dsEmployee.setEmployeeId(employeeId);
dsEmployee.setName(dlEmployee.getName());
gender=dlEmployee.getGender();
if(gender=='M') dsEmployee.setGender(GENDER.MALE);
else if(gender=='F') dsEmployee.setGender(GENDER.FEMALE);
designationCode=dlEmployee.getDesignationCode();
designation=designationManager.getByDesignationCode(designationCode);
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
dsEmployee.setIsIndian(dlEmployee.getIsIndian());
dsEmployee.setBasicSalary(dlEmployee.getBasicSalary());
dsEmployee.setPANNumber(dlEmployee.getPANNumber());
dsEmployee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
et=designationCodeWiseEmployeeMap.get(designation.getCode());
if(et==null)
{
et=new TreeSet<>();
et.add(dsEmployee);
this.designationCodeWiseEmployeeMap.put(new Integer(designation.getCode()),et);
}
else
{
et.add(dsEmployee);
}
this.employeeIdWiseEmployeeMap.put(employeeId.toUpperCase(),dsEmployee);
this.panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
}
}catch(DAOException daoException)
{
BLException blException;
blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException;
blException=new BLException();
if(employee==null) 
{
blException.setGenericException("Employee required.");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=designation.getCode();
char gender=employee.getGender();
Date dateOfBirth=employee.getDateOfBirth();
BigDecimal basicSalary=employee.getBasicSalary();
boolean isIndian=employee.getIsIndian();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();

if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","employee id should be nil/empty");
}
}

if(name==null)
{
blException.addException("name","name required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","name required");
}
}

if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date of birth required.");
}
if(gender==' ')
{
blException.addException("gender","gender required.");
}
if(basicSalary==null)
{
blException.addException("basicSalary","basic salary required.");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","basic salary cannot be negative.");
}
}
if(panNumber==null)
{
blException.addException("panNumber","pan number required.");
}
else 
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","pan number required");
}
}

if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","aadhar card number required.");
}
else 
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","aadhar card number required.");
}
}

if(panNumber!=null && panNumber.length()>0)
{
if(this.panNumberWiseEmployeeMap.containsKey(panNumber))
{
blException.addException("panNumber","pan number already exists : "+panNumber);
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber))
{
blException.addException("aadharCardNumber","aadhar card Number already exists : "+aadharCardNumber);
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
new EmployeeDAO().add(employeeDTO);
employeeId=employeeDTO.getEmployeeId();
employee.setEmployeeId(employeeId);

EmployeeInterface dsEmployee=new Employee();
dsEmployee.setName(name);
dsEmployee.setDesignation(designation);
char dsGender=employee.getGender();
if(dsGender=='M') dsEmployee.setGender(GENDER.MALE);
else if(dsGender=='F') dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode()); // dsEmployee wala designation mein jo code hai woh lenge.
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeeMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
this.employeeIdWiseEmployeeMap.put(employeeId.toUpperCase(),dsEmployee);
this.panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException;
blException=new BLException();
if(employee==null) 
{
blException.setGenericException("Employee required.");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation=employee.getDesignation();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
int designationCode=designation.getCode();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();

if(employeeId==null)
{
blException.addException("employeeId","Employee Id. required");
}
else 
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","employee required.");
}
else
{
if(this.employeeIdWiseEmployeeMap.containsKey(employeeId)==false)
{
blException.addException("employeeId","Invalid Employee Id : "+employeeId);
throw blException;
}
}
}
if(name==null)
{
blException.addException("name","name required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","name required");
}
}
if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation");
}
}

if(gender==' ')
{
blException.addException("gender","gender required.");
}
if(basicSalary==null)
{
blException.addException("basicSalary","basic salary required.");
}
else
{
if(basicSalary.signum()<0)
{
blException.addException("basicSalary","basic salary cannot be negative.");
}
}
String employeeIdAgainstPANNumber="";
String employeeIdAgainstAadharCardNumber="";
EmployeeInterface pEmployee;
boolean panCardNumberFound=false;
boolean aadharCardNumberFound=false;
if(this.panNumberWiseEmployeeMap.containsKey(panNumber))
{
panCardNumberFound=true;
pEmployee=this.panNumberWiseEmployeeMap.get(panNumber);
employeeIdAgainstPANNumber=pEmployee.getEmployeeId();
}
if(this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber))
{
aadharCardNumberFound=true;
pEmployee=this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber);
employeeIdAgainstAadharCardNumber=pEmployee.getEmployeeId();
}

/*Method -1 (By Aditya)

boolean panCardNumberExists=false;
boolean aadharCardNumberExists=false;
if(panCardNumberFound && employeeIdAgainstPANNumber.equalsIgnoreCase(employeeId)==false) panCardNumberExists=true;
if(aadharCardNumberFound && employeeIdAgainstAadharCardNumber.equalsIgnoreCase(employeeId)==false) aadharCardNumberExists=true;
if(panCardNumberExists) blException.addException("panNumber","pan number already exists"+panNumber);
if(aadharCardNumberExists) blException.addException("aadharCardNumber","aadhar card number already exists"+aadharCardNumber);
if(panCardNumberExists && aadharCardNumberExists)
{
blException.addException("panNumber","pan number already exists"+panNumber);
blException.addException("aadharCardNumber","aadhar card number already exists"+aadharCardNumber);
}
*/ 

// Method -2 (By Sir)

if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("panNumber","PAN Number "+panNumber+" exists .");
}
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("aadharCardNumber","Aadhar Card Number "+aadharCardNumber+" exists .");
}
}

if(blException.hasExceptions()) throw blException;
try
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeeMap.get(employeeId);
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();

EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(dsEmployee.getEmployeeId()); 
/*
ds mein jo employee id hai woh lenge , 
samne wala ajeebo gareeb uppercase lower case mei de sakta hai, 
isliye ds mei se proper data nikala aur set kiya.
*/
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
new EmployeeDAO().update(employeeDTO);
dsEmployee.setName(name);
dsEmployee.setDesignation(designation);
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

// Remove old data and update new data.

employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(oldPANNumber.toUpperCase());
aadharCardNumberWiseEmployeeMap.remove(oldAadharCardNumber.toUpperCase());
employeeIdWiseEmployeeMap.put(dsEmployee.getEmployeeId(),dsEmployee);
panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);

// agr designation code alg hai toh hi remove karke update karo
if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
{
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeeMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeeMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}
employeesSet.add(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blException;
blException=new BLException();
if(employeeId==null) 
{
blException.addException("employeeId","Employee required.");
throw blException;
}
if(employeeId.trim().length()==0)
{
blException.addException("employeeId","Length of employee id is zero.");
throw blException;
}
if(this.employeeIdWiseEmployeeMap.containsKey(employeeId)==false)
{
blException.addException("employeeId","Invalid Employee Id : "+employeeId);
throw blException;
}
try
{
EmployeeInterface employee=this.employeeIdWiseEmployeeMap.get(employeeId);
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
int designationCode=employee.getDesignation().getCode();
new EmployeeDAO().delete(employeeId);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(designationCode);
ets.remove(employee);
employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(panNumber.toUpperCase());
aadharCardNumberWiseEmployeeMap.remove(aadharCardNumber.toUpperCase());
employeesSet.remove(employee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException
{
EmployeeInterface employee;
employee=this.employeeIdWiseEmployeeMap.get(employeeId);
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("employeeId","Invalid employee id :"+employeeId);
throw blException;
}
EmployeeInterface e=new Employee();
DesignationInterface dsDesignation;
DesignationInterface designation;
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
dsDesignation=employee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getByAadharCardNumber(String aadharCardNumber) throws BLException
{
EmployeeInterface employee;
employee=this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber);
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("aadharCardNumber","Invalid aadhar card number"+aadharCardNumber);
throw blException;
}
DesignationInterface dsDesignation;
DesignationInterface designation;
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
dsDesignation=employee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getByPANNumber(String panNumber) throws BLException
{
EmployeeInterface employee;
employee=this.panNumberWiseEmployeeMap.get(panNumber);
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("panNumber","Invalid pan number :"+panNumber);
throw blException;
}
DesignationInterface dsDesignation;
DesignationInterface designation=new Designation();
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
dsDesignation=employee.getDesignation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public int getEmployeeCount()
{
return this.employeeIdWiseEmployeeMap.size();
}
public boolean employeeIdExists(String employeeId) 
{
return this.employeeIdWiseEmployeeMap.containsKey(employeeId);
}
public boolean panNumberExists(String panNumber)
{
return this.panNumberWiseEmployeeMap.containsKey(panNumber);
}
public boolean aadharCardNumberExists(String aadharCardNumber) 
{
return this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber);
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
Set<EmployeeInterface> dsEmployees=this.employeesSet;
EmployeeInterface employee;
DesignationInterface designation;
DesignationInterface dsDesignation;
for(EmployeeInterface dsEmployee : dsEmployees)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int code) throws BLException
{
if(code<=0) 
{
BLException blException;
blException=new BLException();
blException.addException("designation","Invalid designation .");
throw blException;
}
Set<EmployeeInterface> dsEmployees;
Set<EmployeeInterface> employees=new TreeSet<>();
dsEmployees=designationCodeWiseEmployeeMap.get(code);
EmployeeInterface e;
DesignationInterface d;
for(EmployeeInterface employee :dsEmployees)
{
e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
d=new Designation();
d.setCode(employee.getDesignation().getCode());
d.setTitle(employee.getDesignation().getTitle());
e.setDesignation(d);
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(e);
}
return employees;
}
public int getCountByDesignation(int code) throws BLException
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(code);
if(ets==null) return 0;
return ets.size();
}
public boolean designationAlloted(int code) throws BLException
{
return this.designationCodeWiseEmployeeMap.containsKey(code);
}
}