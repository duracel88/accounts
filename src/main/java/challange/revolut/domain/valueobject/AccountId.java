package challange.revolut.domain.valueobject;

import java.util.UUID;

import lombok.Value;


@Value
public class AccountId {
    private final UUID id;
}
