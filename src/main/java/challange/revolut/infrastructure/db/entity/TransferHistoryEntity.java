package challange.revolut.infrastructure.db.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import challange.revolut.domain.valueobject.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "transfer_history")
@Entity
@NoArgsConstructor
@Getter
public class TransferHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "from_id")
    private UUID fromId;

    @Column(name = "to_id")
    private UUID toId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

    @Builder
    public TransferHistoryEntity(UUID sendFrom, UUID sendTo, BigDecimal amount, Currency currency, LocalDateTime timestamp) {
        this.fromId = sendFrom;
        this.toId = sendTo;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }
}
