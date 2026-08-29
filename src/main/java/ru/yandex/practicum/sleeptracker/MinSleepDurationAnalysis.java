package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinSleepDurationAnalysis implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        long minDuration = sleepSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0);

        return new SleepAnalysisResult(
                "Минимальная продолжительность сна (минут)",
                minDuration
        );
    }
}