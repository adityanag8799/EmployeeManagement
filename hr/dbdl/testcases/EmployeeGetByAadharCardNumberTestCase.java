import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeGetByAadharCardNumberTestCase 
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
EmployeeDTOInterface employeeDTO;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDTO=employeeDAO.getByAadharCardNumber(aadharCardNumber);
String employeeId=employeeDTO.getEmployeeId();
String name=employeeDTO.getName();
int designationCode=employeeDTO.getDesignationCode();
char gender=employeeDTO.getGender();
Date d=new Date();
d=employeeDTO.getDateOfBirth();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirth=sdf.format(d);
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String PANNumber=employeeDTO.getPANNumber();
System.out.println("Employee id : "+employeeId+" \n name : "+name+" \n designationCode : "+designationCode+" \n gender : "+gender+"\n Date of birth :"+dateOfBirth+" \n isIndian : "+isIndian+"\n Basic salary : "+basicSalary+"\n PAN Number : "+PANNumber+"\n Aadhar card number : "+aadharCardNumber);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

