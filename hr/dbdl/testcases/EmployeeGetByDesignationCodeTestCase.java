import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByDesignationCodeTestCase 
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
Set<EmployeeDTOInterface> employees;
employees=new TreeSet<>();
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employees=employeeDAO.getByDesignationCode(code);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTOInterface employeeDTO : employees)
{
System.out.println("Employee id :"+employeeDTO.getEmployeeId());
System.out.println("Name :"+employeeDTO.getName());
System.out.println("Designation code :"+employeeDTO.getDesignationCode());
System.out.println("Date of birth :"+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender :"+employeeDTO.getGender());
System.out.println("Is Indian :"+employeeDTO.getIsIndian());
System.out.println("Basic salary :"+employeeDTO.getBasicSalary());
System.out.println("PAN Number :"+employeeDTO.getPANNumber());
System.out.println("Aadhar Card Number :"+employeeDTO.getAadharCardNumber());
System.out.println("-----------------------------------------------");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

