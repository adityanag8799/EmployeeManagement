import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class EmployeeIsDesignationAllotedTestCase 
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
boolean result=false;
result=employeeDAO.isDesignationAlloted(designationCode);
System.out.println(result);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

