package com.br.VibeUp.controller;

import com.br.VibeUp.dto.AthleticDTO;
import com.br.VibeUp.model.Athletic;
import com.br.VibeUp.service.AthleticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/athletics")
public class AthleticController {

    @Autowired
    private AthleticService athleticService;

    @GetMapping
    public List<AthleticDTO> getAllAthletics() {
        return athleticService.getAllAthletics();
    }

    @PostMapping("/create")
    public ResponseEntity<AthleticDTO> createAthletic(@RequestBody AthleticDTO athleticDTO) {
        AthleticDTO createdAthletic = athleticService.createAthletic(athleticDTO);
        return ResponseEntity.ok(createdAthletic);
    }

    @GetMapping("/ranked")
    public List<AthleticDTO> getRankedAthletics() {
        return athleticService.getRankedAthletics();
    }

    @DeleteMapping("/{id}/{title}")
    public void deleteAthletic(@PathVariable String id, @PathVariable String name) {
        athleticService.deleteAthletic(id, name);
    }

    @PutMapping("/{id}")
    public Athletic updateAthletic(@PathVariable String id, @RequestBody Athletic updatedAthletic) {
        return athleticService.updateAthletic(id, updatedAthletic);
    }
}