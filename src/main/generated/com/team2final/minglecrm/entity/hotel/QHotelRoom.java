package com.team2final.minglecrm.entity.hotel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHotelRoom is a Querydsl query type for HotelRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotelRoom extends EntityPathBase<HotelRoom> {

    private static final long serialVersionUID = 1685540904L;

    public static final QHotelRoom hotelRoom = new QHotelRoom("hotelRoom");

    public final StringPath address = createString("address");

    public final StringPath hotel = createString("hotel");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> roomNumber = createNumber("roomNumber", Integer.class);

    public final StringPath roomState = createString("roomState");

    public final EnumPath<com.team2final.minglecrm.entity.hotel.type.RoomType> roomType = createEnum("roomType", com.team2final.minglecrm.entity.hotel.type.RoomType.class);

    public QHotelRoom(String variable) {
        super(HotelRoom.class, forVariable(variable));
    }

    public QHotelRoom(Path<? extends HotelRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHotelRoom(PathMetadata metadata) {
        super(HotelRoom.class, metadata);
    }

}

