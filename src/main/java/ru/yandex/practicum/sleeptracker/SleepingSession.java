package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {

    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime sleepStart,
                           LocalDateTime sleepEnd,
                           SleepQuality sleepQuality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStart() {
        return sleepStart;
    }

    public LocalDateTime getEnd() {
        return sleepEnd;
    }

    public SleepQuality getQuality() {
        return sleepQuality;
    }

    public long getDurationMinutes() {
        return Duration.between(sleepStart, sleepEnd).toMinutes();
    }
}