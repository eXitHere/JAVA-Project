package dfts;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;  

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDay;
    private Date dateCreated;
    private Integer Point;
    private String password;

    public User(String firstName, String lastName, String phoneNumber, String birthDay,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.password = password;
        this.dateCreated = new Date();
        this.Point = 0;
    }
    
    public void pointUp(int newPoint){
        if(newPoint>0){
            this.Point += newPoint;
        }
    }
    
    public void update(String firstname){
        this.firstName = firstname;
    }
    
    public void update(String firstname,String lastName){
        this.firstName = firstname;
        this.lastName = lastName;
    }
    
    public void update(String firstname,String lastName,String phoneNumber){
        this.firstName = firstname;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    
    public void update(String firstname,String lastName,String phoneNumber,String birthDay){
        this.firstName = firstname;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
    }
    
    public void update(String firstname,String lastName,String phoneNumber,String birthDay,String password){
        this.firstName = firstname;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.password = password;
    }
    
    public boolean passwordCheck(String pass){
        return this.password.equals(pass);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    @Override
    public String toString() {
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", birthDay=" + birthDay + ", dateCreated=" + date.format(dateCreated) + ", Point=" + Point + ", Password " + password +'}';
    }
    
}
