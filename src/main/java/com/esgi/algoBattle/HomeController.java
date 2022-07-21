package com.esgi.algoBattle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping()
    public ResponseEntity<String> displayHome() {
        return ResponseEntity.ok("Home");
    }
}
