package challange.revolut.domain.valueobject;

import java.math.BigDecimal;

import lombok.NonNull;
import lombok.Value;

@Value
public class Amount implements Comparable<Amount>{
    private final BigDecimal value;

    private Amount(BigDecimal value){
        this.value = value;
    }

    public Amount add(@NonNull Amount amount) {
        return new Amount(value.add(amount.getValue()));
    }

    public Amount subtract(@NonNull Amount amount) {
        return new Amount(value.subtract(amount.getValue()));
    }

    public boolean isPositive() {
        return value.signum() == 1;
    }

    public static Amount empty() {
        return new Amount(BigDecimal.ZERO);
    }

    public static Amount of(BigDecimal value) {
        return new Amount(value);
    }

    public static Amount of(double value) {
        return new Amount(BigDecimal.valueOf(value));
    }

    @Override
    public int compareTo(Amount o) {
        return value.compareTo(o.value);
    }
}
