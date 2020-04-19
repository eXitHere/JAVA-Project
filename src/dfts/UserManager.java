package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> listUser = new ArrayList<>();
    private List<String> listPhone = new ArrayList<>();
    
    public UserManager() {
        LoadUserFromDB();
    }
    
    
    private void LoadUserFromDB(){
        this.listPhone = new ArrayList<>(); // Clear
        this.listUser = new ArrayList<>();  // Clear
        
        File file = new File("src/resources/data/");
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".user")){
                    return true;
                } else {
                    return false;
                }
            }
        });
        for(File f:files){
            //System.out.println(f.getName());
            String[] name = ((String)f.getName()).split(".user");
            //System.out.println(name[0]);
            //try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", name[0]))); ObjectInputStream oi = new ObjectInputStream(fi);){
            //    User user = (User)oi.readObject();
                //this.listUser.add(user);
                this.listPhone.add(name[0]);
                //System.out.println(user);
            //}
            //catch(Exception e){e.printStackTrace();};
        }
    }
    
    public void RemoveUser(String phone){
        try{
            this.listPhone.remove(phone);
            File file = new File(String.format("src/resources/data/%s.user", phone));  //creates a file instance  
            file.deleteOnExit();
        }
        catch(Exception e){e.printStackTrace();}
    }
    
    public User getInfoUser(String phone){
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", phone))); ObjectInputStream oi = new ObjectInputStream(fi);){
            User user = (User)oi.readObject();
            return user;
        }
        catch(Exception e){ System.out.println("Error!");e.printStackTrace();};
        return null;
    }
    
    public List<String> getPhoneAll(){
        //System.out.println(this.listPhone.size());
        return this.listPhone;
    }
}
