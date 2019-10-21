package challange.revolut.service.transfer;

import javax.validation.ValidationException;

public class DifferentCurrenciesException extends ValidationException {

    DifferentCurrenciesException(String message) {
        super(message);
    }
}
