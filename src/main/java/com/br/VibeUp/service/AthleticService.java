package com.br.VibeUp.service;

import com.br.VibeUp.dto.AthleticDTO;
import com.br.VibeUp.model.Athletic;
import com.br.VibeUp.repositories.AthleticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        athletic.setRank(athleticDTO.rank());
        athletic.setScore(athleticDTO.score());
        athletic.setFileUrl(athleticDTO.fileUrl());

        athletic = athleticRepository.save(athletic);
        return convertToDTO(athletic);
    }


    public AthleticDTO convertToDTO(Athletic athletic) {
        return new AthleticDTO(
                athletic.getId(),
                athletic.getName(),
                athletic.getRank(),
                athletic.getScore(),
                athletic.getFileUrl()
        );
    }


    public AthleticDTO getAthleticWithImage(String athleticId) {
        Athletic athletic = athleticRepository.findById(athleticId)
                .orElseThrow(() -> new RuntimeException("Athletic not found"));
        return convertToDTO(athletic);
    }

    public List<AthleticDTO> getRankedAthletics() {
        List<Athletic> athletics = athleticRepository.findAll();
        athletics.sort((a1, a2) -> Integer.compare(a2.getScore(), a1.getScore()));

        for (int i = 0; i < athletics.size(); i++) {
            Athletic athletic = athletics.get(i);
            athletic.setRank(i + 1);
            athleticRepository.save(athletic);
        }

        return athletics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateAthleticRank() {
        List<Athletic> athletics = athleticRepository.findAll();
        athletics.sort((a1, a2) -> Integer.compare(a2.getScore(), a1.getScore()));

        for (int i = 0; i < athletics.size(); i++) {
            Athletic athletic = athletics.get(i);
            athletic.setRank(i + 1);
            athleticRepository.save(athletic);
        }
    }

    public void deleteAthletic(String id, String name) {
        Athletic athletic = (Athletic) athleticRepository.findByIdAndName(id, name)
                .orElseThrow(() -> new RuntimeException("Athletic not found"));
        athleticRepository.delete(athletic);
    }

    public Athletic updateAthletic(String id, Athletic updatedAthletic) {
        Athletic athletic = athleticRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athletic not found"));
        athletic.setName(updatedAthletic.getName());
        athletic.setFileUrl(updatedAthletic.getFileUrl());
        return athleticRepository.save(athletic);
    }
}

