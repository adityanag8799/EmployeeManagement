import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeManagerGetCountByDesignationTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
int count=employeeManager.getCountByDesignation(code);
System.out.println("Count : "+count);
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