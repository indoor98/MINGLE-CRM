package com.team2final.minglecrm.entity.reward;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoucherHistory is a Querydsl query type for VoucherHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoucherHistory extends EntityPathBase<VoucherHistory> {

    private static final long serialVersionUID = 1310393304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoucherHistory voucherHistory = new QVoucherHistory("voucherHistory");

    public final DateTimePath<java.time.LocalDateTime> authDate = createDateTime("authDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> conversionDate = createDateTime("conversionDate", java.time.LocalDateTime.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final com.team2final.minglecrm.entity.employee.QEmployee employeeManager;

    public final com.team2final.minglecrm.entity.employee.QEmployee employeeStaff;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAuth = createBoolean("isAuth");

    public final BooleanPath isConvertedYn = createBoolean("isConvertedYn");

    public final DateTimePath<java.time.LocalDateTime> requestDate = createDateTime("requestDate", java.time.LocalDateTime.class);

    public final QVoucher voucher;

    public QVoucherHistory(String variable) {
        this(VoucherHistory.class, forVariable(variable), INITS);
    }

    public QVoucherHistory(Path<? extends VoucherHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoucherHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoucherHistory(PathMetadata metadata, PathInits inits) {
        this(VoucherHistory.class, metadata, inits);
    }

    public QVoucherHistory(Class<? extends VoucherHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.employeeManager = inits.isInitialized("employeeManager") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employeeManager")) : null;
        this.employeeStaff = inits.isInitialized("employeeStaff") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employeeStaff")) : null;
        this.voucher = inits.isInitialized("voucher") ? new QVoucher(forProperty("voucher"), inits.get("voucher")) : null;
    }

}

