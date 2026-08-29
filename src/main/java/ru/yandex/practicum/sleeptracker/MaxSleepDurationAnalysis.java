package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxSleepDurationAnalysis implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        long maxDuration = sleepSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0);

        return new SleepAnalysisResult(
                "Максимальная продолжительность сна (минут)",
                maxDuration
        );
    }
}