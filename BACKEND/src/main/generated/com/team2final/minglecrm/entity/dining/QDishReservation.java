package com.team2final.minglecrm.entity.dining;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDishReservation is a Querydsl query type for DishReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDishReservation extends EntityPathBase<DishReservation> {

    private static final long serialVersionUID = -1898981942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDishReservation dishReservation = new QDishReservation("dishReservation");

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.team2final.minglecrm.entity.payment.QPayment payment;

    public final DateTimePath<java.time.LocalDateTime> reservationDate = createDateTime("reservationDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public QDishReservation(String variable) {
        this(DishReservation.class, forVariable(variable), INITS);
    }

    public QDishReservation(Path<? extends DishReservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDishReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDishReservation(PathMetadata metadata, PathInits inits) {
        this(DishReservation.class, metadata, inits);
    }

    public QDishReservation(Class<? extends DishReservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.payment = inits.isInitialized("payment") ? new com.team2final.minglecrm.entity.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
    }

}

