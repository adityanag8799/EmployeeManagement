package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
public class Designation implements DesignationInterface
{
private int code;
private String title;
public Designation()
{
this.code=0;
this.title=null;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
public int hashCode()
{
return code;
}
public boolean equals(Object object)
{
if(!(object instanceof DesignationInterface)) return false;
DesignationInterface designation;
designation=(Designation)object;
return this.code==designation.getCode();
}
public int compareTo(DesignationInterface other)
{
return this.code-other.getCode();
}
}