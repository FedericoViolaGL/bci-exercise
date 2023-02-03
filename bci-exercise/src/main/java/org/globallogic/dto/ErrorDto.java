package org.globallogic.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String detail;
    private int code;
    private Timestamp timestamp;
}
