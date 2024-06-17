package com.team2final.minglecrm.entity.dining;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiningReview is a Querydsl query type for DiningReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiningReview extends EntityPathBase<DiningReview> {

    private static final long serialVersionUID = -61163911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiningReview diningReview = new QDiningReview("diningReview");

    public final NumberPath<Double> atmosphereRating = createNumber("atmosphereRating", Double.class);

    public final NumberPath<Double> cleanlinessRating = createNumber("cleanlinessRating", Double.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final QDishReservation dishReservation;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> kindnessRating = createNumber("kindnessRating", Double.class);

    public final StringPath review = createString("review");

    public final NumberPath<Double> tasteRating = createNumber("tasteRating", Double.class);

    public QDiningReview(String variable) {
        this(DiningReview.class, forVariable(variable), INITS);
    }

    public QDiningReview(Path<? extends DiningReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiningReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiningReview(PathMetadata metadata, PathInits inits) {
        this(DiningReview.class, metadata, inits);
    }

    public QDiningReview(Class<? extends DiningReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.dishReservation = inits.isInitialized("dishReservation") ? new QDishReservation(forProperty("dishReservation"), inits.get("dishReservation")) : null;
    }

}

