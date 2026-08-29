package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements SleepAnalysisFunction {

    private static final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_UP = LocalTime.of(9, 0);

    private static final LocalTime LARK_SLEEP_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_UP = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepSessions) {

        List<Chronotype> chronotypes = sleepSessions.stream()
                .filter(this::isNightSession)
                .map(this::determineChronotype)
                .collect(Collectors.toList());

        Chronotype userChronotype = determineUserChronotype(chronotypes);

        return new SleepAnalysisResult(
                "Хронотип пользователя",
                userChronotype
        );
    }

    private boolean isNightSession(SleepingSession session) {

        LocalTime sleepStart = session.getStart().toLocalTime();
        LocalTime sleepEnd = session.getEnd().toLocalTime();

        boolean startsInEvening =
                !sleepStart.isBefore(LocalTime.of(18, 0));

        boolean endsInMorning =
                sleepEnd.isBefore(LocalTime.of(12, 0));

        return startsInEvening && endsInMorning;
    }

    private Chronotype determineChronotype(SleepingSession session) {

        LocalTime sleepStart = session.getStart().toLocalTime();
        LocalTime wakeUp = session.getEnd().toLocalTime();

        if (sleepStart.isAfter(OWL_SLEEP_START)
                && wakeUp.isAfter(OWL_WAKE_UP)) {
            return Chronotype.OWL;
        }

        if (sleepStart.isBefore(LARK_SLEEP_START)
                && wakeUp.isBefore(LARK_WAKE_UP)) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }

    private Chronotype determineUserChronotype(
            List<Chronotype> chronotypes) {

        Map<Chronotype, Long> chronotypeCounts = chronotypes.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        long owlCount = chronotypeCounts.getOrDefault(
                Chronotype.OWL,
                0L
        );

        long larkCount = chronotypeCounts.getOrDefault(
                Chronotype.LARK,
                0L
        );

        long pigeonCount = chronotypeCounts.getOrDefault(
                Chronotype.PIGEON,
                0L
        );

        if (owlCount > larkCount && owlCount > pigeonCount) {
            return Chronotype.OWL;
        }

        if (larkCount > owlCount && larkCount > pigeonCount) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }
}