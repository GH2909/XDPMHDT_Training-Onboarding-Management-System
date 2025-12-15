package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

public class EmployeeProfileCreationRequest {
    private String password;
    private String fullName;
    private String phone;
    private String avatar;

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
