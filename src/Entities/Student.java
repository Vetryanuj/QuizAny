package Entities;

public class Student 
{
	private String Name;
	private String ID;
	private String Password;
	private String Email;
	private String Location;
	private String[] CourseID;
	
    public Student(String Name, String ID, String Password, String Email, String Location, String[] CourseID )
    {
    	this.Name = Name;
    	this.ID = ID;
    	this.Password = Password;
    	this.Email = Email;
    	this.Location = Location;
    	this.CourseID = CourseID;
    	
    }
	
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public void setID(String ID)
    {
    	this.ID = ID;
    }
    
    public void setPassword(String Password)
    {
    	this.Password = Password;
    }
    
    public void setEmail(String Email)
    {
    	this.Email = Email;
    }
    
    public void setLocation(String Location)
    {
    	this.Location = Location;
    }
    
	public void setCourse(String[] CourseID)
	{
		this.CourseID = CourseID;
	}
	
	public String[] getCourse()
	{
		return CourseID;
	}
    public String getLocation()
    {
    	return Location;
    }
    public String getEmail()
    {
    	return Email;
    }
    public String getPassword()
    {
    	return Password;
    }
    public String getID()
    {
    	return ID;
    }
    public String getName()
    {
    	return Name;
    }
}
