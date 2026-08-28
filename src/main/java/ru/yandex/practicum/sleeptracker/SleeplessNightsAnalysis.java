package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SleeplessNightsAnalysis implements SleepAnalysisFunction {

    private static final LocalTime NOON = LocalTime.NOON;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        if (sleepSessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество бессонных ночей",
                    0
            );
        }

        LocalDate firstPotentialNight =
                getFirstPotentialNight(sleepSessions);

        LocalDate lastPotentialNight =
                getLastPotentialNight(sleepSessions);

        long totalNights = ChronoUnit.DAYS.between(
                firstPotentialNight,
                lastPotentialNight
        );

        long sleepingNights = sleepSessions.stream()
                .filter(this::isNightSleep)
                .map(session -> getNightDate(session))
                .distinct()
                .count();

        long sleeplessNights = totalNights - sleepingNights;

        return new SleepAnalysisResult(
                "Количество бессонных ночей",
                Math.max(sleeplessNights, 0)
        );
    }

    private LocalDate getFirstPotentialNight(
            List<SleepingSession> sleepSessions) {

        SleepingSession firstSession = sleepSessions.get(0);

        LocalDate firstSessionDate =
                firstSession.getStart().toLocalDate();

        if (firstSession.getStart().toLocalTime().isAfter(NOON)) {
            return firstSessionDate;
        }

        return firstSessionDate.minusDays(1);
    }

    private LocalDate getLastPotentialNight(
            List<SleepingSession> sleepSessions) {

        SleepingSession lastSession =
                sleepSessions.get(sleepSessions.size() - 1);

        return lastSession.getEnd().toLocalDate();
    }

    private boolean isNightSleep(SleepingSession session) {

        LocalDateTime sleepStart = session.getStart();
        LocalDateTime sleepEnd = session.getEnd();

        boolean startsBeforeMidnightAndEndsAfter =
                sleepStart.toLocalDate().isBefore(
                        sleepEnd.toLocalDate()
                );

        boolean startsBeforeSix =
                sleepStart.toLocalTime().isBefore(NIGHT_END);

        boolean endsBeforeSix =
                sleepEnd.toLocalTime().isBefore(NIGHT_END);

        return startsBeforeMidnightAndEndsAfter
                || startsBeforeSix
                || endsBeforeSix;
    }

    private LocalDate getNightDate(SleepingSession session) {

        LocalTime sleepStart = session.getStart().toLocalTime();

        if (sleepStart.isBefore(NIGHT_END)) {
            return session.getStart().toLocalDate().minusDays(1);
        }

        return session.getStart().toLocalDate();
    }
}