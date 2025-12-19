package ut.edu.com.trainingonboardingmanagementsystem.Schedular;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ut.edu.com.trainingonboardingmanagementsystem.Service.UserSheetSyncService;

@Component
@RequiredArgsConstructor
public class UserSheetScheduler {
    private final UserSheetSyncService syncService;

    @Scheduled(fixedDelay = 60000)
    public void sync() {
        System.out.println("Scheduler running...");
        syncService.syncUsersFromGGSheet();
    }
}
