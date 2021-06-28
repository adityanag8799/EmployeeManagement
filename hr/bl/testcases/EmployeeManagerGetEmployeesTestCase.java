import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeManagerGetEmployeesTestCase
{
public static void main(String gg[])
{
try
{
Set<EmployeeInterface> dsEmployees=new TreeSet<>();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
dsEmployees=employeeManager.getEmployees();
dsEmployees.forEach((employee)->{
System.out.println("Employee Id : "+employee.getEmployeeId());
System.out.println("Name : "+employee.getName());
System.out.println("Designation code : "+employee.getDesignation().getCode());
System.out.println("Designation  : "+employee.getDesignation().getTitle());
System.out.println("Gender : "+employee.getGender());
System.out.println("Date of birth : "+sdf.format(employee.getDateOfBirth()));
System.out.println("Is Indian : "+employee.getIsIndian());
System.out.println("Basic Salary : "+employee.getBasicSalary());
System.out.println("PAN number : "+employee.getPANNumber());
System.out.println("Aadhar card number :"+employee.getAadharCardNumber());
System.out.println("-------------------------------------------------------------------");
});
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