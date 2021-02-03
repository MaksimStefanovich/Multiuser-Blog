package com.stefanovich.controllers;

import com.stefanovich.dto.ProfileMyDto;
import com.stefanovich.service.ProfileMyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiProfileMyController {
    private final ProfileMyService profileMyService;

    @PostMapping(value = "/api/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> changePasswordAndPhoto(@RequestPart MultipartFile photo, String name, String email,
                                                      String password, Integer removePhoto) throws IOException {

        return profileMyService.changePhotoAndPassword(photo, name, email, password, removePhoto);
    }

    @PostMapping(value = "/api/profile/my")
    public Map<String, Object> changeNameAndPassword(@RequestBody ProfileMyDto profileMyDto) {

        if (profileMyDto.getPassword() == null) {
            return profileMyService.changeNameAndEmail(profileMyDto);
        }

        if (profileMyDto.getRemovePhoto() == 1) {
            return profileMyService.removePhoto();
        }
        return profileMyService.changeNameAndPasswordAndEmail(profileMyDto);
    }
}
