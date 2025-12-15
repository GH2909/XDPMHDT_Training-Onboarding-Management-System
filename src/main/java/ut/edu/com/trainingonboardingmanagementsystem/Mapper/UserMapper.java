package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileCreationRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
@Component
@Mapper(componentModel = "spring")
public class UserMapper {

    public User creatEmployeeProfile(EmployeeProfileCreationRequest request){
        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAvatar(request.getAvatar());
        return user;
    }

    public void updateEmployeeProfile(@MappingTarget User user, EmployeeProfileUpdateRequest request) {
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAvatar(request.getAvatar());
    }
}

