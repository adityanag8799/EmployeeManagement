import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerRemoveTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation with "+code+" removed .");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException()+" hero 1");
}
if(blException.hasExceptions())
{
List<String> properties=blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property)+"hero 2");
}
}
}
}
}