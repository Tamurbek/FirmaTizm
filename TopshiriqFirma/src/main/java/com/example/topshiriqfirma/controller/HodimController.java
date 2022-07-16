package com.example.topshiriqfirma.controller;

import com.example.topshiriqfirma.Service.HodimService;
import com.example.topshiriqfirma.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class HodimController {
    @Autowired
    HodimService hodimService;
    @PostMapping("/directorTayinlash")
    public HttpEntity<?> createDirector(@RequestBody DirectorDto directorDTO){
        ApiResponse apiResponse =hodimService.Directortayinlash(directorDTO);
        return ResponseEntity.status(apiResponse.getTuri()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/manegerTayinlash")
    public HttpEntity<?> createManager(@RequestBody HodimDto hodimDto){
        ApiResponse apiResponse =hodimService.manegerTayinlash(hodimDto);
        return ResponseEntity.status(apiResponse.getTuri()?200:409).body(apiResponse.getMessage());
    }
    @GetMapping("/emailtasdiqlash")
    public HttpEntity<?> email(@RequestParam String userEmail, @RequestParam String userCode){
        ApiResponse apiResponse = hodimService.emailConfirmation(userEmail, userCode);
        return ResponseEntity.status(apiResponse.getTuri()?201:409).body(apiResponse.getMessage());
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDTO){
        ApiResponse apiResponse = hodimService.HodimLogin(loginDTO);
        return ResponseEntity.status(apiResponse.getTuri()?200:401).body(apiResponse);
    }
    @PostMapping("/profildanChiqish")
    public HttpEntity<?> loginOut(@RequestBody LoginDto loginDTO){
        ApiResponse apiResponse = hodimService.profildanChiqish(loginDTO);
        return ResponseEntity.status(apiResponse.getTuri()?200:401).body(apiResponse);
    }
    @PostMapping("/parolYangilash")
    public HttpEntity<?> updatePassword(@RequestBody LoginDto loginDTO){
        ApiResponse apiResponse=hodimService.ParolYangilash(loginDTO);
        return ResponseEntity.status(apiResponse.getTuri()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/hodimlarnikorish")
    public HttpEntity<?> readStaff(@RequestBody GetHodimDto getHodimDto){
        return ResponseEntity.ok(hodimService.hodimlarniKorish(getHodimDto));
    }
}
