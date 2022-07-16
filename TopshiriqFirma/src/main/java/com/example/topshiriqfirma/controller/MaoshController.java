package com.example.topshiriqfirma.controller;

import com.example.topshiriqfirma.Service.MaoshService;
import com.example.topshiriqfirma.dto.ApiResponse;
import com.example.topshiriqfirma.dto.GetHodimDto;
import com.example.topshiriqfirma.dto.MaoshDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maosh")
public class MaoshController {
    @Autowired
    MaoshService maoshService;
    @PostMapping("/tolov")
    public HttpEntity<?> maoshBerish(@RequestBody MaoshDto maoshDto){
        ApiResponse apiResponse=maoshService.maoshBerish(maoshDto);
        return ResponseEntity.status(apiResponse.getTuri()?200:408).body(apiResponse.getMessage());
    }
    @PostMapping("/Tolovni_korish")
    public HttpEntity<?> readPay(@RequestBody GetHodimDto getHodimDto){
        return ResponseEntity.ok(maoshService.salaryList(getHodimDto));
    }
}
