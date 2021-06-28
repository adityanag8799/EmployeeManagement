import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerDesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationInterface designation=new Designation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
boolean result=designationManager.designationCodeExists(code);
System.out.println(result);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String p : properties)
{
System.out.println(blException.getException(p));
}
}
}
}