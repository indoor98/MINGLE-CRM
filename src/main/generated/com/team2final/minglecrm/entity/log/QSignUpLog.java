package com.team2final.minglecrm.entity.log;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSignUpLog is a Querydsl query type for SignUpLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSignUpLog extends EntityPathBase<SignUpLog> {

    private static final long serialVersionUID = -697201483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSignUpLog signUpLog = new QSignUpLog("signUpLog");

    public final StringPath authority = createString("authority");

    public final DateTimePath<java.time.LocalDateTime> authorityChangeDate = createDateTime("authorityChangeDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final com.team2final.minglecrm.entity.employee.QEmployee employee;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isApproved = createBoolean("isApproved");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> requestDate = createDateTime("requestDate", java.time.LocalDateTime.class);

    public QSignUpLog(String variable) {
        this(SignUpLog.class, forVariable(variable), INITS);
    }

    public QSignUpLog(Path<? extends SignUpLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSignUpLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSignUpLog(PathMetadata metadata, PathInits inits) {
        this(SignUpLog.class, metadata, inits);
    }

    public QSignUpLog(Class<? extends SignUpLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employee")) : null;
    }

}

