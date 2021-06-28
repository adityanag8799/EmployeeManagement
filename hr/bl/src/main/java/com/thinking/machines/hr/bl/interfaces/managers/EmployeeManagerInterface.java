package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void removeEmployee(String employeeId) throws BLException;
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getByAadharCardNumber(String aadharCardNumber) throws BLException;
public EmployeeInterface getByPANNumber(String panNumber) throws BLException;
public boolean employeeIdExists(String employeeId);
public boolean panNumberExists(String panNumber);
public boolean aadharCardNumberExists(String aadharCardNumber);
public int getEmployeeCount();
public Set<EmployeeInterface> getEmployees();
public Set<EmployeeInterface> getEmployeesByDesignationCode(int code) throws BLException;
public int getCountByDesignation(int code) throws BLException;
public boolean designationAlloted(int code) throws BLException;
}