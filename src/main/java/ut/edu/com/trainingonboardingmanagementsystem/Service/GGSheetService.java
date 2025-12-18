package ut.edu.com.trainingonboardingmanagementsystem.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Config.GGSheetProperties;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;

import java.util.List;

@Service
@AllArgsConstructor
public class GGSheetService {
    private final GGSheetProperties properties;
    private final Sheets sheetService;

    public List<List<Object>> readUsers(){
        try {
            ValueRange response = sheetService.spreadsheets().values()
                    .get(properties.getId(), properties.getRange())
                    .execute();

            return response.getValues();
        } catch (Exception e) {
            throw new RuntimeException("Không đọc được Google Sheet", e);
        }
    }

    public List<UserFromGGSheet> mapToDTO(List<List<Object>> rows) {

        return rows.stream()
                .skip(1)
                .map(row -> {
                    UserFromGGSheet sheet = new UserFromGGSheet();
                    sheet.setUsername(row.get(0).toString());
                    sheet.setPassword(row.get(1).toString());
                    sheet.setEmail(row.get(2).toString());
                    sheet.setFullname(row.get(3).toString());
                    sheet.setPhone(row.get(4).toString());
                    sheet.setAvatar(row.get(5).toString());
                    sheet.setStatus(row.get(6).toString());
                    sheet.setRoleName(row.get(7).toString().toUpperCase());
                    return sheet;
                })
                .toList();
    }

}
