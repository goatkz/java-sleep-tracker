package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SessionCountAnalysis implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {
        return new SleepAnalysisResult(
                "Количество сессий сна",
                sleepSessions.size()
        );
    }
}