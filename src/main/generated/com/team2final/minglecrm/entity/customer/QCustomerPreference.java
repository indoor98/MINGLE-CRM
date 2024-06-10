package com.team2final.minglecrm.entity.customer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerPreference is a Querydsl query type for CustomerPreference
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerPreference extends EntityPathBase<CustomerPreference> {

    private static final long serialVersionUID = -1267844516L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerPreference customerPreference = new QCustomerPreference("customerPreference");

    public final QCustomer customer;

    public final StringPath dietaryRestrictions = createString("dietaryRestrictions");

    public final StringPath funnel = createString("funnel");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath interest = createString("interest");

    public final BooleanPath isBreakfastPreferred = createBoolean("isBreakfastPreferred");

    public final BooleanPath isPet = createBoolean("isPet");

    public final BooleanPath isSmoking = createBoolean("isSmoking");

    public final DateTimePath<java.time.LocalDateTime> preferredCheckinTime = createDateTime("preferredCheckinTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> preferredCheckoutTime = createDateTime("preferredCheckoutTime", java.time.LocalDateTime.class);

    public final StringPath purpose = createString("purpose");

    public QCustomerPreference(String variable) {
        this(CustomerPreference.class, forVariable(variable), INITS);
    }

    public QCustomerPreference(Path<? extends CustomerPreference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerPreference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerPreference(PathMetadata metadata, PathInits inits) {
        this(CustomerPreference.class, metadata, inits);
    }

    public QCustomerPreference(Class<? extends CustomerPreference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

