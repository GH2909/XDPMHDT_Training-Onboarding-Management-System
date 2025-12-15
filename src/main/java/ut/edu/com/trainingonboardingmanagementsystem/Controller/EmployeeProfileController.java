package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.com.trainingonboardingmanagementsystem.Service.EmployeeProfileService;

@RestController
@RequestMapping("/employeeProfile")
@RequiredArgsConstructor
public class EmployeeProfileController {

    @Autowired
    EmployeeProfileService employeeProfileService;
}
