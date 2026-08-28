package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualityAnalysis implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        long badQualityCount = sleepSessions.stream()
                .filter(session ->
                        session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult(
                "Количество сессий с плохим качеством сна",
                badQualityCount
        );
    }
}