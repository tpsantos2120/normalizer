package com.api.normalizer.service;

import com.api.normalizer.dto.Normalize;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class NormalizeService {

    private final List<String> normalizedJobTitles;

    public NormalizeService() {
        this.normalizedJobTitles = Arrays.asList("Architect", "Software engineer", "Quantity surveyor", "Accountant");
    }

    /**
     * Normalizes a list of job titles.
     *
     * @param request The list of job titles to be normalized.
     * @return The list of normalized job titles.
     */
    public List<Normalize> normalize(List<Normalize> request) {
        if(Objects.isNull(request) || request.isEmpty()){
            throw new IllegalArgumentException("Request cannot be null or empty");
        }
        List<Normalize> normalizedList = new ArrayList<>();
        for (Normalize normalize : request) {
            if(StringUtils.isBlank(normalize.jobTitle()))
                continue;
            normalizedList.add(getNormalizedJobTitle(normalize.jobTitle()));
        }
        return normalizedList;
    }

    /**
     * Returns a normalized job title and its quality score based on the input job title.
     *
     * @param jobTitle The input job title to be normalized.
     * @return A Normalize object containing the normalized job title and its quality score.
     */
    private Normalize getNormalizedJobTitle(String jobTitle) {
        int smallestDistance = Integer.MAX_VALUE;
        String bestChoice = null;

        for (String normalizedTitle : normalizedJobTitles) {
            int distance = LevenshteinDistance.getDefaultInstance().apply(jobTitle, normalizedTitle);
            if (distance <= smallestDistance) {
                smallestDistance = distance;
                bestChoice = normalizedTitle;
            }
        }
        return Normalize.builder()
                .normalizedJobTitle(bestChoice)
                .qualityScore(smallestDistance)
                .build();
    }
}
