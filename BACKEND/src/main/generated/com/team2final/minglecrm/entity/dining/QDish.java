package com.team2final.minglecrm.entity.dining;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDish is a Querydsl query type for Dish
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDish extends EntityPathBase<Dish> {

    private static final long serialVersionUID = -1357103966L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDish dish = new QDish("dish");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QDishReservation dishReservation;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public QDish(String variable) {
        this(Dish.class, forVariable(variable), INITS);
    }

    public QDish(Path<? extends Dish> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDish(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDish(PathMetadata metadata, PathInits inits) {
        this(Dish.class, metadata, inits);
    }

    public QDish(Class<? extends Dish> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dishReservation = inits.isInitialized("dishReservation") ? new QDishReservation(forProperty("dishReservation"), inits.get("dishReservation")) : null;
    }

}

