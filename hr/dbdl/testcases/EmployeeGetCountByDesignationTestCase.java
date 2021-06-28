import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class EmployeeGetCountByDesignationTestCase 
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
int result=0;
result=employeeDAO.getCountByDesignation(designationCode);
System.out.println(result);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

