package utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EuroUtils {

    private static final BigDecimal CONVERSION_RATE = BigDecimal.valueOf(7.53450);

    public static BigDecimal convertToHrk(BigDecimal euro) {
        return euro.multiply(CONVERSION_RATE);
    }

    public static String convertBigDecimalToString(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        String amountInEUR = amount.setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "€";
        String amountInHRK = convertToHrk(amount).setScale(2, RoundingMode.HALF_EVEN).toPlainString() + "kn";

        return amountInEUR + " (" + amountInHRK + ")";
    }
}
