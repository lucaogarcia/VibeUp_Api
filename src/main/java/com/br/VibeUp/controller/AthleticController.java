package com.br.VibeUp.controller;

import com.br.VibeUp.dto.AthleticDTO;
import com.br.VibeUp.service.AthleticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletics")
@CrossOrigin
public class AthleticController {

    @Autowired
    private AthleticService athleticService;

    @GetMapping
    public List<AthleticDTO> getAllAthletics() {
        return athleticService.getAllAthletics();
    }

    @PostMapping("/create")
    public ResponseEntity<AthleticDTO> createAthletic(@RequestBody AthleticDTO athleticDTO) {
        AthleticDTO newAthletic = athleticService.createAthletic(athleticDTO);
        return ResponseEntity.ok(newAthletic);
    }

    @PutMapping("/incrementScore")
    public ResponseEntity<Void> incrementScore(@RequestParam String name) {
        athleticService.incrementScore(name);
        return ResponseEntity.ok().build();
    }
}
