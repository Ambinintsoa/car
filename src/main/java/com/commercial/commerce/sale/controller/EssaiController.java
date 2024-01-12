package com.commercial.commerce.sale.controller;
import com.commercial.commerce.sale.entity.EssaiEntity;
import com.commercial.commerce.sale.service.EssaiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/actu")
@RequiredArgsConstructor
public class EssaiController {
   
     private final EssaiService essaiService;
    @GetMapping("/test")
    public ResponseEntity<Object> register() {
        return ResponseEntity.ok(this.essaiService.getAll());
    }

}
