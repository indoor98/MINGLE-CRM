package com.team2final.minglecrm.entity.reward;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoucher is a Querydsl query type for Voucher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoucher extends EntityPathBase<Voucher> {

    private static final long serialVersionUID = -603766500L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoucher voucher = new QVoucher("voucher");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final com.team2final.minglecrm.entity.employee.QEmployee employee;

    public final DateTimePath<java.time.LocalDateTime> expiredDate = createDateTime("expiredDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath voucherCode = createString("voucherCode");

    public QVoucher(String variable) {
        this(Voucher.class, forVariable(variable), INITS);
    }

    public QVoucher(Path<? extends Voucher> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoucher(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoucher(PathMetadata metadata, PathInits inits) {
        this(Voucher.class, metadata, inits);
    }

    public QVoucher(Class<? extends Voucher> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.employee = inits.isInitialized("employee") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employee")) : null;
    }

}

