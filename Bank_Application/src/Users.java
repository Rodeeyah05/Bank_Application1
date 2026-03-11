public class Users {
    private int Id;
    private String Name;
    private String Username;
    private String Password;
    private String Email;
    private String phoneNumber;
    private String createdAt;

    public Users(){

    }
    public Users( String Name, String Username, String Password, String Email, String phoneNumber, String createdAt){
        this.Name = Name;
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
    public Users(int Id, String Name, String Username, String Password, String Email, String phoneNumber, String createdAt){
        this.Id = Id;
        this.Name = Name;
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
