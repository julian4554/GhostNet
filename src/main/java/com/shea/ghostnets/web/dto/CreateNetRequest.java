package com.shea.ghostnets.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter @Setter
public class CreateNetRequest {
    private Double latitude;
    private Double longitude;
    private String estimatedSize;
    @JsonAlias("name")
    private String reporterName;
    @JsonAlias("phone")
    private String reporterPhone;
}
