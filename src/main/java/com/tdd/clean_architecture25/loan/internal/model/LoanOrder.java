package com.tdd.clean_architecture25.loan.internal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loan_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "borrower_name", nullable = false, length = 100)
    private String borrowerName;

    @Builder.Default
    @Column(name = "loan_date", nullable = false)
    private OffsetDateTime loanDate = OffsetDateTime.now();

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, RETURNED

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "loanOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LoanItem> items = new ArrayList<>();

    public void addItem(LoanItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setLoanOrder(this);
    }
}
