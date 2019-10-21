package challange.revolut.domain.valueobject;

import java.util.UUID;

import lombok.Value;

@Value
public class TransferId {
    private final UUID id;

    public static TransferId generate(){
        return new TransferId(UUID.randomUUID());
    }
}
