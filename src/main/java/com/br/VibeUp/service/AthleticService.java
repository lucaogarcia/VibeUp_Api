package com.br.VibeUp.service;

import com.br.VibeUp.dto.AthleticDTO;
import com.br.VibeUp.model.Athletic;
import com.br.VibeUp.repositories.AthleticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AthleticService {

    @Autowired
    private AthleticRepository athleticRepository;

    public List<AthleticDTO> getAllAthletics() {
        return athleticRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AthleticDTO createAthletic(AthleticDTO athleticDTO) {
        Athletic athletic = new Athletic();
        athletic.setName(athleticDTO.name());
        athletic.setScore(athleticDTO.score());

        athleticRepository.save(athletic);
        updateRanks(); // Atualiza os ranks após adicionar uma nova atlética

        return convertToDTO(athletic);
    }

    private AthleticDTO convertToDTO(Athletic athletic) {
        return new AthleticDTO(
                athletic.getId(),
                athletic.getName(),
                athletic.getScore(),
                athletic.getRank()
        );
    }

    public void updateRanks() {
        List<Athletic> athletics = athleticRepository.findAll();
        athletics.sort(Comparator.comparingInt(Athletic::getScore).reversed());

        for (int i = 0; i < athletics.size(); i++) {
            athletics.get(i).setRank(i + 1);
        }
        athleticRepository.saveAll(athletics);
    }

    public void incrementScore(String name) {
        Athletic athletic = athleticRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Athletic not found"));
        athletic.setScore(athletic.getScore() + 1);
        athleticRepository.save(athletic);
        updateRanks();
    }
}
