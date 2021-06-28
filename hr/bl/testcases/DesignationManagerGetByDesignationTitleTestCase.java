import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerGetByDesignationTitleTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationInterface designation=new Designation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designation=designationManager.getByDesignationTitle(title.toUpperCase());
System.out.println("Code : "+designation.getCode()+" Designation : "+designation.getTitle());
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