package com.tdd.clean_architecture25.alert.internal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_alerts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock;

    @Column(name = "threshold", nullable = false)
    private Integer threshold;

    @Builder.Default
    @Column(length = 20)
    private String status = "NEW"; // NEW, PROCESSED, IGNORED

    @CreationTimestamp
    @Column(name = "triggered_at", updatable = false)
    private OffsetDateTime triggeredAt;
}
