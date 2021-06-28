import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class EmployeeGetCountTestCase 
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
int count;
count=employeeDAO.getCount();
System.out.println("Total count :"+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}