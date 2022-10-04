package step.learning.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeService {
    public String getDate() {
        return LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getTime() {
        return LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}