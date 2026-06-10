package org.example.project.dto.response;

import lombok.*;
import org.example.project.entity.Court;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourtResponse {
    private Long id;
    private String name;
    private String address;
    private BigDecimal price;
    private String imageUrl;
    public static CourtResponse fromEntity(Court court){
        return CourtResponse.builder()
                .id(court.getId())
                .name(court.getName())
                .address(court.getAddress())
                .price(court.getPrice())
                .imageUrl(court.getImageUrl())
                .build();
    }
}
