package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;

import java.util.List;

@Service
@AllArgsConstructor
public class GGSheetService {
    @Value("$gg.sheet.id}")
    private String id;
    @Value ("$gg.sheet.range}")
    private String range;
    private final GGSheetService sheetService;

    public List<UserFromGGSheet> readUsers(){

    }
}
