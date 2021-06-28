import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerGetDesignationsTestCase
{
public static void main(String gg[])
{
try
{
Set<DesignationInterface> dsDesignations=new TreeSet<>();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
dsDesignations=designationManager.getDesignations();
dsDesignations.forEach((designation)->{
System.out.println("Code : "+designation.getCode()+" Designation : "+designation.getTitle());
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