package com.team2final.minglecrm.entity.reward;

import com.team2final.minglecrm.entity.payment.Payment;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reward_id", nullable = true)
    private Reward reward;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "voucher_id", nullable = true)
    private Voucher voucher;

    private String type; // 적립인지 사용인지 바우처->리워드 전환인지

    private Long amount;

    private String reason;

    private LocalDateTime Date;

}
