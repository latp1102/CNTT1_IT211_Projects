package org.example.project.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
}
