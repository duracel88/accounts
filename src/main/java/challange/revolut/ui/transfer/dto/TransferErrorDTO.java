package challange.revolut.ui.transfer.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import challange.revolut.domain.exception.InsufficientBalanceException;
import lombok.Getter;

@Getter
public class TransferErrorDTO {

    private String[] errors;

    public TransferErrorDTO(String ... errors) {
        this.errors = errors;
    }

    public static TransferErrorDTO insufficientBalance(InsufficientBalanceException e){
        return new TransferErrorDTO(e.getMessage());
    }

    public static TransferErrorDTO validationException(ValidationException e){
        return new TransferErrorDTO(e.getMessage());
    }

    public static TransferErrorDTO validationException(ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String[] errors = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .toArray(new String[violations.size()]);
        return new TransferErrorDTO(errors);

    }
}
