package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManagerInterface designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation : dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{ 
blException.setGenericException("designation required."); 
throw blException; 
}
int code=designation.getCode(); 
String title=designation.getTitle();
if(code!=0) blException.addException("code","code should be zero");
if(title==null)
{
blException.addException("title","title required.");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","title required.");
}
if(title.length()>0 && this.titleWiseDesignationsMap.containsKey(title.toUpperCase())) blException.addException("title","Designation "+title+" exists.");
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code= designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{ 
blException.setGenericException("designation required."); 
throw blException; 
}
int code=designation.getCode(); 
String title=designation.getTitle();
if(code<=0) blException.addException("code","Invalid code : "+code);
if(code>0) 
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.setGenericException("Invalid code :"+code); 
throw blException; 
}
}
if(title==null)
{
blException.addException("title","Title required.");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title required.");
}
if(title.length()>0) 
{
DesignationInterface d;
d=titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code) blException.addException("title","Designation "+title+" exists.");
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(dlDesignation);

codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);

dsDesignation.setTitle(title);
codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException;
blException=new BLException();
if(code<=0)
{
blException.addException("code","Invalid code.");
throw blException;
}
if(code>0) 
{
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.setGenericException("Invalid code :"+code); 
throw blException; 
}
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public DesignationInterface getByDesignationCode(int code) throws BLException
{
DesignationInterface designation;
designation=this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException;
blException=new BLException();
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
public DesignationInterface getByDesignationTitle(String title) throws BLException
{
DesignationInterface designation;
designation=this.titleWiseDesignationsMap.get(title);
if(designation==null)
{
BLException blException;
blException=new BLException();
blException.addException("title","Invalid title : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation;
designation=this.codeWiseDesignationsMap.get(code);
return designation;
}
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> dsDesignations=new TreeSet<>();
Designation designation;
for(DesignationInterface dsDesignation : designationsSet)
{
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
dsDesignations.add(designation);
}
return dsDesignations;
}
public int getDesignationCount()
{
return this.codeWiseDesignationsMap.size();
}
public boolean designationCodeExists(int code)
{
return this.codeWiseDesignationsMap.containsKey(code);
}
public boolean designationTitleExists(String title)
{
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
}
