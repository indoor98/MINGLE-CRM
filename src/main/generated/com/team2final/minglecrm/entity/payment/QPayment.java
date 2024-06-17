package com.team2final.minglecrm.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 1687131473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final NumberPath<Long> amountBeforeDiscount = createNumber("amountBeforeDiscount", Long.class);

    public final NumberPath<Long> createdReward = createNumber("createdReward", Long.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRefunded = createBoolean("isRefunded");

    public final NumberPath<Long> paymentAmount = createNumber("paymentAmount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> paymentDate = createDateTime("paymentDate", java.time.LocalDateTime.class);

    public final StringPath paymentSpot = createString("paymentSpot");

    public final DateTimePath<java.time.LocalDateTime> refundDate = createDateTime("refundDate", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

