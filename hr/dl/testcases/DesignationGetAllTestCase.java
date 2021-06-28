import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationGetAllTestCase 
{
public static void main(String gg[])
{
try
{
Set<DesignationDTOInterface> designations;
designations=new TreeSet<>();
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designations=designationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.printf("Code: %d , Designation : %s\n",designationDTO.getCode(),designationDTO.getTitle());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

