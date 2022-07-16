package com.example.topshiriqfirma.controller;

import com.example.topshiriqfirma.Repository.VazifaRepository;
import com.example.topshiriqfirma.Service.VazifaService;
import com.example.topshiriqfirma.dto.ApiResponse;
import com.example.topshiriqfirma.dto.VazifaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class VazifaController {
    @Autowired
    VazifaService vazifaService;
    @Autowired
    VazifaRepository vazifaRepository;

    @PostMapping("/vazifaberish")
    public HttpEntity<?> VazifaBerish(@RequestBody VazifaDto vazifaDto){
        ApiResponse apiResponse = vazifaService.vazifaBerish(vazifaDto);
        return ResponseEntity.status(apiResponse.getTuri()?200:409).body(apiResponse.getMessage());
    }

    @GetMapping("/vazifanikorish/{id}")
    public HttpEntity<?> VazifaniKorish(@PathVariable Integer id){
        ApiResponse apiResponse=vazifaService.readTask(id);
        return ResponseEntity.status(apiResponse.getTuri()?201:409).body(apiResponse.getMessage());
    }

    @GetMapping("/HammaVazifalar")
    public HttpEntity<?> readAllTask(){
        return ResponseEntity.ok(vazifaRepository.findAll());
    }

}
