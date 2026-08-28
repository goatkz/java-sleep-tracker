package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SleepTrackerAppTest {

    @Test
    void shouldRunApplicationAndPrintAnalysisResults() throws Exception {
        Path tempFile = Files.createTempFile(
                "sleep-log",
                ".txt"
        );

        Files.writeString(
                tempFile,
                "01.10.25 23:15;02.10.25 07:30;GOOD\n"
                        + "02.10.25 23:50;03.10.25 06:40;NORMAL\n"
                        + "03.10.25 14:10;03.10.25 15:00;NORMAL\n"
        );

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));

            SleepTrackerApp.main(
                    new String[]{tempFile.toString()}
            );
        } finally {
            System.setOut(originalOutput);
            Files.deleteIfExists(tempFile);
        }

        String result = output.toString();

        assertTrue(
                result.contains("Количество сессий сна: 3")
        );

        assertTrue(
                result.contains(
                        "Минимальная продолжительность сна (минут): 50"
                )
        );

        assertTrue(
                result.contains(
                        "Максимальная продолжительность сна (минут): 495"
                )
        );

        assertTrue(
                result.contains(
                        "Количество сессий с плохим качеством сна: 0"
                )
        );

        assertTrue(
                result.contains(
                        "Хронотип пользователя: PIGEON"
                )
        );
    }

    @Test
    void shouldProcessDifferentSleepQuality() throws Exception {
        Path tempFile = Files.createTempFile(
                "sleep-log",
                ".txt"
        );

        Files.writeString(
                tempFile,
                "01.10.25 23:00;02.10.25 07:00;BAD\n"
                        + "02.10.25 23:00;03.10.25 07:00;GOOD\n"
        );

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));

            SleepTrackerApp.main(
                    new String[]{tempFile.toString()}
            );
        } finally {
            System.setOut(originalOutput);
            Files.deleteIfExists(tempFile);
        }

        String result = output.toString();

        assertTrue(
                result.contains("Количество сессий сна: 2")
        );

        assertTrue(
                result.contains(
                        "Количество сессий с плохим качеством сна: 1"
                )
        );
    }
}