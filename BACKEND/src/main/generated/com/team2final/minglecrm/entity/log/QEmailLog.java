package com.team2final.minglecrm.entity.log;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmailLog is a Querydsl query type for EmailLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailLog extends EntityPathBase<EmailLog> {

    private static final long serialVersionUID = 150180639L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmailLog emailLog = new QEmailLog("emailLog");

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final com.team2final.minglecrm.entity.event.QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> isClickedLink = createDateTime("isClickedLink", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> isRead = createDateTime("isRead", java.time.LocalDateTime.class);

    public QEmailLog(String variable) {
        this(EmailLog.class, forVariable(variable), INITS);
    }

    public QEmailLog(Path<? extends EmailLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmailLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmailLog(PathMetadata metadata, PathInits inits) {
        this(EmailLog.class, metadata, inits);
    }

    public QEmailLog(Class<? extends EmailLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.event = inits.isInitialized("event") ? new com.team2final.minglecrm.entity.event.QEvent(forProperty("event"), inits.get("event")) : null;
    }

}

