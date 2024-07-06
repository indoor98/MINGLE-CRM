package com.team2final.minglecrm.entity.hotel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomReservation is a Querydsl query type for RoomReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomReservation extends EntityPathBase<RoomReservation> {

    private static final long serialVersionUID = 1496070314L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomReservation roomReservation = new QRoomReservation("roomReservation");

    public final DateTimePath<java.time.LocalDateTime> checkinTime = createDateTime("checkinTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> checkoutTime = createDateTime("checkoutTime", java.time.LocalDateTime.class);

    public final com.team2final.minglecrm.entity.customer.QCustomer customer;

    public final QHotelRoom hotelRoom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.team2final.minglecrm.entity.payment.QPayment payment;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final DateTimePath<java.time.LocalDateTime> reservationDate = createDateTime("reservationDate", java.time.LocalDateTime.class);

    public QRoomReservation(String variable) {
        this(RoomReservation.class, forVariable(variable), INITS);
    }

    public QRoomReservation(Path<? extends RoomReservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomReservation(PathMetadata metadata, PathInits inits) {
        this(RoomReservation.class, metadata, inits);
    }

    public QRoomReservation(Class<? extends RoomReservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.team2final.minglecrm.entity.customer.QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.hotelRoom = inits.isInitialized("hotelRoom") ? new QHotelRoom(forProperty("hotelRoom")) : null;
        this.payment = inits.isInitialized("payment") ? new com.team2final.minglecrm.entity.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
    }

}

