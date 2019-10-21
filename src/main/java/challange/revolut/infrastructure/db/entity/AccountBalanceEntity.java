package challange.revolut.infrastructure.db.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "account_balance")
@Entity
@Getter
@NoArgsConstructor
public class AccountBalanceEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;

    @Version
    private Integer version;

    @Builder
    public AccountBalanceEntity(UUID id, Currency currency, BigDecimal balance, Integer version) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.version = version;
    }


}
