package com.team2final.minglecrm.entity.reward;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRewardHistory is a Querydsl query type for RewardHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRewardHistory extends EntityPathBase<RewardHistory> {

    private static final long serialVersionUID = 1962160723L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRewardHistory rewardHistory = new QRewardHistory("rewardHistory");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> Date = createDateTime("Date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.team2final.minglecrm.entity.payment.QPayment payment;

    public final StringPath reason = createString("reason");

    public final QReward reward;

    public final StringPath type = createString("type");

    public final QVoucher voucher;

    public QRewardHistory(String variable) {
        this(RewardHistory.class, forVariable(variable), INITS);
    }

    public QRewardHistory(Path<? extends RewardHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRewardHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRewardHistory(PathMetadata metadata, PathInits inits) {
        this(RewardHistory.class, metadata, inits);
    }

    public QRewardHistory(Class<? extends RewardHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payment = inits.isInitialized("payment") ? new com.team2final.minglecrm.entity.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.reward = inits.isInitialized("reward") ? new QReward(forProperty("reward"), inits.get("reward")) : null;
        this.voucher = inits.isInitialized("voucher") ? new QVoucher(forProperty("voucher"), inits.get("voucher")) : null;
    }

}

