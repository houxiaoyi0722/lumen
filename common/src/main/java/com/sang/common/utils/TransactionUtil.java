package com.sang.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Supplier;

/**
 * spring手动事务工具类
 */
public final class TransactionUtil {

    private static PlatformTransactionManager PLATFORM_TRANSACTION_MANAGER = SpringUtil.getBean(PlatformTransactionManager.class);
    private static TransactionDefinition TRANSACTION_DEFINITION = SpringUtil.getBean(TransactionDefinition.class);

    private TransactionUtil() {
    }

    public static <T> T transaction(Supplier<T> supplier) {
        return transactionHandler(supplier, PLATFORM_TRANSACTION_MANAGER.getTransaction(TRANSACTION_DEFINITION));
    }

    public static <T> T transactionNew(Supplier<T> supplier) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        TransactionStatus transactionStatus = PLATFORM_TRANSACTION_MANAGER.getTransaction(defaultTransactionDefinition);
        return transactionHandler(supplier, transactionStatus);
    }

    private static <T> T transactionHandler(Supplier<T> supplier, TransactionStatus transactionStatus) {
        try {
            T t = supplier.get();
            PLATFORM_TRANSACTION_MANAGER.commit(transactionStatus);
            return t;
        } catch (Exception var3) {
            PLATFORM_TRANSACTION_MANAGER.rollback(transactionStatus);
            throw var3;
        }
    }
}