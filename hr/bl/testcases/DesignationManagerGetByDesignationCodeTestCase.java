import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation=new Designation();
int code=Integer.parseInt(gg[0]);
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designation=designationManager.getByDesignationCode(code);
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