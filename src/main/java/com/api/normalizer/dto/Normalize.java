package com.api.normalizer.dto;

import com.api.normalizer.views.NormalizeView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


/**
 * Represents a job title normalization record.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Normalize(@NotEmpty(groups = NormalizeView.Request.class)
                        @JsonView(NormalizeView.Request.class)
                        String jobTitle,
                        @JsonView(NormalizeView.Response.class)
                        String normalizedJobTitle,
                        @JsonView(NormalizeView.Response.class)
                        int qualityScore) {
}
