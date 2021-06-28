import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class EmployeeIdExistsTestCase 
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
boolean result;
result=employeeDAO.employeeIdExists(employeeId);
System.out.println(result);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

