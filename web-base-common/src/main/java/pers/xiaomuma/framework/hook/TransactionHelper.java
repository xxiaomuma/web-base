package pers.xiaomuma.framework.hook;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionHelper {

    /**
     * 事务提交之后执行
     */
    public static void executeAfterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive())
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
    }

    /**
     * 事务回滚之后执行
     */
    public static void executeAfterRollback(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive())
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    if (TransactionSynchronization.STATUS_ROLLED_BACK == status) {
                        runnable.run();
                    }
                }
            });
    }


}
