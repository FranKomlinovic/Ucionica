package utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceCalculatorUtils {
    public static final double PRICE_PER_HOUR = 2.20;
    public static final double PRICE_PER_MINUTE = PRICE_PER_HOUR / 60;
    public static final double PERCENTAGE = 0.80;

    public static double getPercentageFrom(double number) {
        return number * PERCENTAGE;
    }

    private static Duration getDuration(LocalDateTime start, LocalDateTime end) {
        if (end == null) {
            return Duration.ZERO;
        }
        int hours = (int) start.until(end, ChronoUnit.HOURS);
        start = start.plusHours(hours);

        int minutes = (int) start.until(end, ChronoUnit.MINUTES);

        return Duration.ofHours(hours).plusMinutes(minutes);

    }

    public static BigDecimal getPrice(LocalDateTime start, LocalDateTime end) {
        double calculatedPrice = 0.00;
        double minutePrice = PRICE_PER_MINUTE;
        double hourPrice = PRICE_PER_HOUR;

        Duration localTime = getDuration(start, end);

        for (long i = 0; i < localTime.toHoursPart(); i++) {
            calculatedPrice = calculatedPrice + hourPrice;
            minutePrice = getPercentageFrom(minutePrice);
            hourPrice = getPercentageFrom(hourPrice);
        }

        calculatedPrice = calculatedPrice + (localTime.toMinutesPart() * minutePrice);

        return BigDecimal.valueOf(calculatedPrice).setScale(2, RoundingMode.UP);
    }
}
