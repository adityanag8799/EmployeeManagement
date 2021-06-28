import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeId="";
String name="Hitesh";
char gender='M';
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse("11/03/1996");
}catch(ParseException pe)
{
}
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("7000000");
String panNumber="UIG2141";
String aadharCardNumber="UID787612798900";
try
{
DesignationInterface designation=new Designation();
designation.setCode(8);
EmployeeInterface employee=new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
if(gender=='M' || gender=='m') employee.setGender(GENDER.MALE);
else if(gender=='F' || gender=='f') employee.setGender(GENDER.FEMALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee added with code :"+employee.getEmployeeId());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
if(blException.hasExceptions())
{
List<String> properties=blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
}
}
}
}
}