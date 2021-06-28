import com.thinking.machines.enums.*;
public class Employee
{
private char gender;
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender='M';
else this.gender='F';
}
public char getGender()
{
return this.gender;
}
}
class genderTestCase
{
public static void main(String gg[])
{
String g=gg[0];
Employee e=new Employee();
if(g.charAt(0)=='M' || g.charAt(0)=='m') e.setGender(GENDER.MALE);
else if(g.charAt(0)=='F' || g.charAt(0)=='f') e.setGender(GENDER.FEMALE);
System.out.println(e.getGender());
}
}