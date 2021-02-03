package com.stefanovich.service;

import com.stefanovich.dto.GlobalSettingsDTO;
import com.stefanovich.model.GlobalSettings;
import com.stefanovich.repository.GlobalSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GlobalSettingsService {
    private final GlobalSettingsRepository globalSettingsRepository;

    public void setGlobalSettings(GlobalSettingsDTO globalSettingsDTO) {
        GlobalSettings globalSettings1 = new GlobalSettings();
        globalSettings1.setCode("MULTIUSER_MODE");
        globalSettings1.setName("Многопользовательский режим");
        if (globalSettingsDTO.getMultiuserMode()) {
            globalSettings1.setValue("YES");
        } else globalSettings1.setValue("NO");
        globalSettingsRepository.save(globalSettings1);

        GlobalSettings globalSettings2 = new GlobalSettings();
        globalSettings2.setCode("POST_PREMODERATION");
        globalSettings2.setName("Премодерация постов");
        if (globalSettingsDTO.getPostPremoderation()) {
            globalSettings2.setValue("YES");
        } else globalSettings2.setValue("NO");
        globalSettingsRepository.save(globalSettings2);

        GlobalSettings globalSettings3 = new GlobalSettings();
        globalSettings3.setCode("STATISTICS_IS_PUBLIC");
        globalSettings3.setName("Показывать всем статистику блога");
        if (globalSettingsDTO.getStatisticsIsPublic()) {
            globalSettings3.setValue("YES");
        } else globalSettings3.setValue("NO");
        globalSettingsRepository.save(globalSettings3);
    }

    public Map<String, Boolean> getGlobalSettings() {
        List<GlobalSettings> all = globalSettingsRepository.findAll();
        Map<String, Boolean> settings = new HashMap<>();
        all.forEach(
                e -> {
                    if (e.getValue().equals("YES")) {
                        settings.put(e.getCode(), true);
                    } else {
                        settings.put(e.getCode(), false);
                    }

                });
        return settings;
    }
}
