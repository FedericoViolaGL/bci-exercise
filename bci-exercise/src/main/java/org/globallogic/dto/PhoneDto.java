package org.globallogic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PhoneDto {
    private Long number;
    private Integer cityCode;
    private String countryCode;
}
