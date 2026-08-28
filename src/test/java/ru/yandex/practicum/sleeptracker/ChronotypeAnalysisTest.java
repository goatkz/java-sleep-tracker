package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeAnalysisTest {

    private final ChronotypeAnalysis analysis =
            new ChronotypeAnalysis();

    @Test
    void shouldDetermineOwl() {

        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 9, 30),
                SleepQuality.GOOD
        );

        SleepAnalysisResult result =
                analysis.apply(List.of(session));

        assertEquals(
                Chronotype.OWL,
                result.getValue()
        );
    }

    @Test
    void shouldDetermineLark() {

        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 21, 30),
                LocalDateTime.of(2025, 10, 2, 6, 30),
                SleepQuality.GOOD
        );

        SleepAnalysisResult result =
                analysis.apply(List.of(session));

        assertEquals(
                Chronotype.LARK,
                result.getValue()
        );
    }

    @Test
    void shouldDeterminePigeon() {

        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 30),
                LocalDateTime.of(2025, 10, 2, 7, 30),
                SleepQuality.GOOD
        );

        SleepAnalysisResult result =
                analysis.apply(List.of(session));

        assertEquals(
                Chronotype.PIGEON,
                result.getValue()
        );
    }

    @Test
    void shouldIgnoreDaytimeSleep() {

        SleepingSession daytimeSession =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 1, 14, 0
                        ),
                        LocalDateTime.of(
                                2025, 10, 1, 15, 0
                        ),
                        SleepQuality.GOOD
                );

        SleepAnalysisResult result =
                analysis.apply(List.of(daytimeSession));

        assertEquals(
                Chronotype.PIGEON,
                result.getValue()
        );
    }

    @Test
    void shouldChoosePigeonWhenChronotypesAreEqual() {

        SleepingSession owlSession =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 1, 23, 30
                        ),
                        LocalDateTime.of(
                                2025, 10, 2, 9, 30
                        ),
                        SleepQuality.GOOD
                );

        SleepingSession larkSession =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 2, 21, 30
                        ),
                        LocalDateTime.of(
                                2025, 10, 3, 6, 30
                        ),
                        SleepQuality.GOOD
                );

        SleepAnalysisResult result =
                analysis.apply(
                        List.of(
                                owlSession,
                                larkSession
                        )
                );

        assertEquals(
                Chronotype.PIGEON,
                result.getValue()
        );
    }

    @Test
    void shouldDetermineChronotypeUsingMostFrequentType() {

        SleepingSession firstPigeon =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 1, 22, 30
                        ),
                        LocalDateTime.of(
                                2025, 10, 2, 7, 30
                        ),
                        SleepQuality.GOOD
                );

        SleepingSession secondPigeon =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 2, 22, 30
                        ),
                        LocalDateTime.of(
                                2025, 10, 3, 7, 30
                        ),
                        SleepQuality.GOOD
                );

        SleepingSession owl =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 3, 23, 30
                        ),
                        LocalDateTime.of(
                                2025, 10, 4, 9, 30
                        ),
                        SleepQuality.GOOD
                );

        SleepAnalysisResult result =
                analysis.apply(
                        List.of(
                                firstPigeon,
                                secondPigeon,
                                owl
                        )
                );

        assertEquals(
                Chronotype.PIGEON,
                result.getValue()
        );
    }

    @Test
    void shouldTreatBoundaryValuesAsPigeon() {

        SleepingSession session =
                new SleepingSession(
                        LocalDateTime.of(
                                2025, 10, 1, 23, 0
                        ),
                        LocalDateTime.of(
                                2025, 10, 2, 9, 0
                        ),
                        SleepQuality.GOOD
                );

        SleepAnalysisResult result =
                analysis.apply(List.of(session));

        assertEquals(
                Chronotype.PIGEON,
                result.getValue()
        );
    }
}