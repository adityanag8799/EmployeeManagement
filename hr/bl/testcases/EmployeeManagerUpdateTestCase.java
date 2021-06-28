import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeId="A10000009";
String name="Meenali";
char gender='F';
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse("10/10/1997");
}catch(ParseException pe)
{
}
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("7000000");
String panNumber="YIU6879";
String aadharCardNumber=":UID09809808908";
try
{
EmployeeInterface employee=new Employee();
DesignationInterface designation=new Designation();
designation.setCode(8);
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
employeeManager.updateEmployee(employee);
System.out.println("Employee updated");
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