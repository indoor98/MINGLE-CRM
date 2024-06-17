package com.team2final.minglecrm.entity.hotel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotelReview is a Querydsl query type for HotelReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotelReview extends EntityPathBase<HotelReview> {

    private static final long serialVersionUID = 593110885L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotelReview hotelReview = new QHotelReview("hotelReview");

    public final NumberPath<Double> cleanlinessRating = createNumber("cleanlinessRating", Double.class);

    public final StringPath comment = createString("comment");

    public final NumberPath<Double> convenienceRating = createNumber("convenienceRating", Double.class);

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> kindnessRating = createNumber("kindnessRating", Double.class);

    public final NumberPath<Double> locationRating = createNumber("locationRating", Double.class);

    public final QRoomReservation roomReservation;

    public QHotelReview(String variable) {
        this(HotelReview.class, forVariable(variable), INITS);
    }

    public QHotelReview(Path<? extends HotelReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotelReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotelReview(PathMetadata metadata, PathInits inits) {
        this(HotelReview.class, metadata, inits);
    }

    public QHotelReview(Class<? extends HotelReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.roomReservation = inits.isInitialized("roomReservation") ? new QRoomReservation(forProperty("roomReservation"), inits.get("roomReservation")) : null;
    }

}

