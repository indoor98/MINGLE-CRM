package com.team2final.minglecrm.entity.inquiry;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryAction is a Querydsl query type for InquiryAction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryAction extends EntityPathBase<InquiryAction> {

    private static final long serialVersionUID = 778171369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryAction inquiryAction = new QInquiryAction("inquiryAction");

    public final StringPath actionContent = createString("actionContent");

    public final EnumPath<ActionStatus> actionStatus = createEnum("actionStatus", ActionStatus.class);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final com.team2final.minglecrm.entity.employee.QEmployee employee;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInquiry inquiry;

    public QInquiryAction(String variable) {
        this(InquiryAction.class, forVariable(variable), INITS);
    }

    public QInquiryAction(Path<? extends InquiryAction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryAction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryAction(PathMetadata metadata, PathInits inits) {
        this(InquiryAction.class, metadata, inits);
    }

    public QInquiryAction(Class<? extends InquiryAction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.team2final.minglecrm.entity.employee.QEmployee(forProperty("employee")) : null;
        this.inquiry = inits.isInitialized("inquiry") ? new QInquiry(forProperty("inquiry"), inits.get("inquiry")) : null;
    }

}

