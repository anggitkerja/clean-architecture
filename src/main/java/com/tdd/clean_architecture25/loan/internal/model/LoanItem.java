package com.tdd.clean_architecture25.loan.internal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "loan_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    @ToString.Exclude
    private LoanOrder loanOrder;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(nullable = false)
    private Integer qty;
}
