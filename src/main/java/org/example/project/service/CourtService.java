package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.CourtRequest;
import org.example.project.dto.response.CourtResponse;
import org.example.project.entity.Court;
import org.example.project.repository.CourtRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {
    private final CourtRepository courtRepository;
    public CourtResponse create(CourtRequest request){
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
    public CourtResponse findById(Long id){
        Court court = courtRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));
        return CourtResponse.fromEntity(court);
    }
    public CourtResponse update(Long id, CourtRequest request){
        Court court = courtRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));
        court.setName(request.getName());
        court.setAddress(request.getAddress());
        court.setPrice(request.getPrice());
        court.setImageUrl(request.getImageUrl());
        courtRepository.save(court);
        return CourtResponse.fromEntity(court);
    }
    public void delete(Long id){
        Court court = courtRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));
        courtRepository.delete(court);
    }
    public List<CourtResponse> search(String keyword){
        return courtRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(CourtResponse::fromEntity)
                .toList();
    }

}
