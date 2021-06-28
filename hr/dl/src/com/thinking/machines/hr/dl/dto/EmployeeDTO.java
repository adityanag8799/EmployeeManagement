package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*;
import java.util.*;
import com.thinking.machines.enums.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
private String employeeId;
private String name;
private Date dateOfBirth;
private char gender;
private BigDecimal basicSalary;
private boolean isIndian;
private int designationCode;
private String panNumber;
private String aadharCardNumber;
public EmployeeDTO()
{
this.employeeId="";
this.name="";
this.dateOfBirth=null;
this.gender=' ';
this.basicSalary=null;
this.isIndian=false;
this.designationCode=0;
this.panNumber="";
this.aadharCardNumber="";
}
public void setEmployeeId(java.lang.String employeeId)
{
this.employeeId=employeeId;
}
public java.lang.String getEmployeeId()
{
return this.employeeId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender='M';
else this.gender='F';
}
public char getGender()
{
return this.gender;
}
public void setBasicSalary(java.math.BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public java.math.BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setPANNumber(java.lang.String panNumber)
{
this.panNumber=panNumber;
}
public java.lang.String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(java.lang.String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public java.lang.String getAadharCardNumber()
{
return this.aadharCardNumber;
}
public boolean equals(Object other)
{
if(!(other instanceof EmployeeDTOInterface)) return false;
EmployeeDTOInterface employeeDTO;
employeeDTO=(EmployeeDTOInterface)other;
return this.employeeId.equalsIgnoreCase(employeeDTO.getEmployeeId());
}
public int compareTo(EmployeeDTOInterface other)
{
return this.employeeId.compareToIgnoreCase(other.getEmployeeId());
}
public int hashCode()
{
return this.employeeId.toUpperCase().hashCode();
}
}