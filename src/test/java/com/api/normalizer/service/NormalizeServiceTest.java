package com.api.normalizer.service;

import com.api.normalizer.dto.Normalize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


class NormalizeServiceTest {

    private static List<Normalize> normalizeList;

    private NormalizeService normalizeService;

    @BeforeAll
    static void setUpBeforeAll() {
        normalizeList = List.of(
                Normalize.builder().jobTitle("Java engineer").build(),
                Normalize.builder().jobTitle("C# engineer").build(),
                Normalize.builder().jobTitle("Accountant").build(),
                Normalize.builder().jobTitle("Chief Accountant").build()
        );
    }

    @BeforeEach
    void setUpBeforeEach(){
       normalizeService = new NormalizeService();
    }

    @Test
    @DisplayName("Given a list of job titles, when normalized, it should return correct normalized job titles and quality scores")
    void givenJobTitlesList_whenNormalized_thenShouldReturnNormalizedJobTitlesAndScores() {
        List<Normalize> normalizedJobTitles = normalizeService.normalize(normalizeList);
        assertThat(normalizedJobTitles)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("jobTitle")
                .isEqualTo(List.of(
                        Normalize.builder().normalizedJobTitle("Software engineer").qualityScore(7).build(),
                        Normalize.builder().normalizedJobTitle("Software engineer").qualityScore(8).build(),
                        Normalize.builder().normalizedJobTitle("Accountant").qualityScore(0).build(),
                        Normalize.builder().normalizedJobTitle("Accountant").qualityScore(6).build()
                ));
    }

    @Test
    @DisplayName("Given a null list of job titles, when normalized, it should return error")
    void givenNullJobTitlesList_whenNormalized_thenShouldReturnError() {
        assertThatThrownBy(() -> normalizeService.normalize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Request cannot be null or empty");
    }

    @Test
    @DisplayName("Given an empty list of job titles, when normalized, it should return error")
    void givenEmptyJobTitlesList_whenNormalized_thenShouldReturnError() {
        assertThatThrownBy(() -> normalizeService.normalize(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Request cannot be null or empty");
    }

    @Test
    @DisplayName("Given a list of job titles with empty title, when normalized, it should ignore empty or null job title")
    void givenJobTitlesListWithEmptyTitle_whenNormalized_thenShouldIgnoreBadJobTitle() {
        List<Normalize> normalizedJobTitles = normalizeService.normalize(
                List.of(
                        Normalize.builder().jobTitle("").build(),
                        Normalize.builder().jobTitle("Java engineer").build()
                )
        );
        assertThat(normalizedJobTitles).hasSize(1)
                .containsExactly(
                        Normalize.builder().normalizedJobTitle("Software engineer").qualityScore(7).build()
                );
    }

    @Test
    @DisplayName("Given a list of job titles with null title, when normalized, it should ignore empty or null job title")
    void givenJobTitlesListWithNullTitle_whenNormalized_thenShouldIgnoreBadJobTitle() {
        List<Normalize> normalizedJobTitles = normalizeService.normalize(
                List.of(
                        Normalize.builder().jobTitle(null).build(),
                        Normalize.builder().jobTitle("Java engineer").build()
                )
        );
        assertThat(normalizedJobTitles).hasSize(1)
                .containsExactly(
                        Normalize.builder().normalizedJobTitle("Software engineer").qualityScore(7).build()
                );
    }
}