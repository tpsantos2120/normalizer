package com.api.normalizer.controller;

import com.api.normalizer.dto.Normalize;
import com.api.normalizer.service.NormalizeService;
import com.api.normalizer.views.NormalizeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NormalizerController {

    private final NormalizeService service;

    /**
     * Normalizes a list of job titles.
     *
     * @param request The list of job titles to be normalized.
     * @return The list of normalized job titles.
     */
    @PostMapping("/normalize")
    @JsonView(NormalizeView.Response.class)
    public ResponseEntity<List<Normalize>> normalize(
            @Validated(NormalizeView.Request.class)
            @JsonView(NormalizeView.Request.class)
            @RequestBody List<Normalize> request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.normalize(request));
    }
}
