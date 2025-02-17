package com.tulio.banksofkareactivo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ViewsMaterializerScheduler {

    private final ViewsMaterializationService viewsMaterializationService;

    public ViewsMaterializerScheduler(ViewsMaterializationService viewMaterializationService) {
        this.viewsMaterializationService = viewMaterializationService;
    }

    @Scheduled(fixedRate = 60000) // Cada 60 segundos
    public void updateViews() {
        viewsMaterializationService.generateTransactionViews().subscribe();
        viewsMaterializationService.generateUserViews().subscribe();
        viewsMaterializationService.generateAccountViews().subscribe();
    }
}
