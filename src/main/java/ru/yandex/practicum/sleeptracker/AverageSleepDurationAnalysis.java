package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AverageSleepDurationAnalysis implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        double averageDuration = sleepSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult(
                "Средняя продолжительность сна (минут)",
                averageDuration
        );
    }
}