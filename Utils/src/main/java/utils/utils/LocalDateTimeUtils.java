package utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    private static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return FORMATTER.format(localDateTime);
    }

    public static String convertToTimeString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return FORMATTER_TIME.format(localDateTime);
    }

    public static String convertToDateString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return FORMATTER_DATE.format(localDateTime);
    }

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.of("Europe/Zagreb"));
    }
}
