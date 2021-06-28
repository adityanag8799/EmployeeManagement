import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeDeleteTestCase 
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAO employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("Employee with id :"+employeeId+" deleted ");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}