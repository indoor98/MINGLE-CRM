package com.team2final.minglecrm.entity.dining;

import com.team2final.minglecrm.controller.dining.reservation.request.UpdateDiningReservationRequest;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.payment.Payment;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DishReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    
    private Long totalPrice;

    private LocalDateTime reservationDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void updateDishReservation(UpdateDiningReservationRequest updateDiningReservationRequest) {

    }

    public void cancelDishReservation() {
        this.payment.cancelReservation(true);
    }
}
