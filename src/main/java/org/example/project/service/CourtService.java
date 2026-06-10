package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.CourtrRequest;
import org.example.project.dto.response.CourtResponse;
import org.example.project.entity.Court;
import org.example.project.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {
    private final CourtRepository courtRepository;
    public CourtResponse create(CourtrRequest request){
        Court court = Court.builder()
                .name(request.getName())
                .address(request.getAddress())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();
        courtRepository.save(court);
        return CourtResponse.fromEntity(court);
    }
    public List<CourtResponse> findAll(){
        return courtRepository.findAll().stream().map(CourtResponse::fromEntity).toList();
    }
}
