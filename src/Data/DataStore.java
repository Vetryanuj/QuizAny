package Data;

public class DataStore 
{
     private static DataStore data = new DataStore();
     private String ip;
     
     public static DataStore getInstance()
     {
    	 return data;
     }
     
     public void setIP(String ip)
     {
    	 this.ip = ip;
     }
     
     public String getIP()

     {
    	 return ip;
     }
}
