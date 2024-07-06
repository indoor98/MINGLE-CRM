package com.team2final.minglecrm.entity.log;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QViewLog is a Querydsl query type for ViewLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QViewLog extends EntityPathBase<ViewLog> {

    private static final long serialVersionUID = 573049448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QViewLog viewLog = new QViewLog("viewLog");

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final com.team2final.minglecrm.entity.employee.QEmployee employee;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath log = createString("log");

    public QViewLog(String variable) {
        this(ViewLog.class, forVariable(variable), INITS);
    }

    public QViewLog(Path<? extends ViewLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QViewLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QViewLog(PathMetadata metadata, PathInits inits) {
        this(ViewLog.class, metadata, inits);
    }

    public QViewLog(Class<? extends ViewLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.employee = inits.isInitialized("employee") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employee")) : null;
    }

}

