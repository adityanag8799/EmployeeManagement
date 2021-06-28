import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class EmployeeGetAllTestCase 
{
public static void main(String gg[])
{
try
{
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employees=employeeDAO.getAll();
for(EmployeeDTOInterface employeeDTO:employees)
{
System.out.println("Employee id :"+employeeDTO.getEmployeeId());
System.out.println("Name :"+employeeDTO.getName());
System.out.println("Designation code :"+employeeDTO.getDesignationCode());
System.out.println("Gender :"+employeeDTO.getGender());
System.out.println("Date of Birth :"+employeeDTO.getDateOfBirth());
System.out.println("Basic Salary :"+employeeDTO.getBasicSalary());
System.out.println("Is Indian :"+employeeDTO.getIsIndian());
System.out.println("PAN Number :"+employeeDTO.getPANNumber());
System.out.println("Aadhar Card Number :"+employeeDTO.getAadharCardNumber());
System.out.println("-------------------------------------------");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

