package org.globallogic.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDto {
    private List<ErrorDto> errors;
}