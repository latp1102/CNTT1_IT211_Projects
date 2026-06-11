package org.example.project.dto.request;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourtRequest {
    private String name;
    private String address;
    private BigDecimal price;
    private String imageUrl;
}
