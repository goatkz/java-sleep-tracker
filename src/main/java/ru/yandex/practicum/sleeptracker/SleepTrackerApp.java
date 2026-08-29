package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final List<SleepAnalysisFunction> ANALYSES = Arrays.asList(
            new SessionCountAnalysis(),
            new MinSleepDurationAnalysis(),
            new MaxSleepDurationAnalysis(),
            new AverageSleepDurationAnalysis(),
            new BadQualityAnalysis(),
            new SleeplessNightsAnalysis(),
            new ChronotypeAnalysis()
    );

    public static void main(String[] args) throws IOException {

        Path logFilePath = Path.of(args[0]);

        List<SleepingSession> sleepSessions = Files.lines(logFilePath)
                .map(SleepTrackerApp::parseSession)
                .collect(Collectors.toList());

        ANALYSES.stream()
                .map(analysisFunction ->
                        analysisFunction.apply(sleepSessions))
                .forEach(result ->
                        System.out.println(
                                result.getDescription()
                                        + ": "
                                        + result.getValue()
                        )
                );
    }

    private static SleepingSession parseSession(String sessionLine) {

        String[] sessionParts = sessionLine.trim().split(";");

        LocalDateTime sleepStart =
                LocalDateTime.parse(sessionParts[0], FORMATTER);

        LocalDateTime sleepEnd =
                LocalDateTime.parse(sessionParts[1], FORMATTER);

        SleepQuality sleepQuality =
                SleepQuality.valueOf(sessionParts[2].trim());

        return new SleepingSession(
                sleepStart,
                sleepEnd,
                sleepQuality
        );
    }
}