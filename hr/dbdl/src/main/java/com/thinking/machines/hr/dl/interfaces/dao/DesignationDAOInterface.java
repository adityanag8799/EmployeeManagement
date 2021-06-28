package com.thinking.machines.hr.dl.interfaces.dao;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException;
public void update(DesignationDTOInterface designationDTO) throws DAOException;
public void delete(int code) throws DAOException;
public Set<DesignationDTOInterface> getAll() throws DAOException;
public DesignationDTOInterface getByCode(int code)throws DAOException;
public DesignationDTOInterface getByTitle(String name) throws DAOException;
public int getCount() throws DAOException;
public boolean codeExists(int code) throws DAOException;
public boolean titleExists(String title) throws DAOException;
}