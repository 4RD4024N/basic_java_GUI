import java.awt.Component;

public class UserProfile{
    private String name;
    private String email;
    private String bio;
    private String pass;

    public UserProfile(String name,String pass,String email,String bio){
        this.name=name;
        this.email=email;
        this.bio=bio;
        this.pass=pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public  String getBio() {
        return bio;
    }

    public String getPass() {
        return pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public boolean changePassword(String currentPassword, String newPassword) {
        if (this.pass.equals(currentPassword)) {
            this.pass = newPassword;
            return true; // Password changed successfully
        }
        return false;
}
}
