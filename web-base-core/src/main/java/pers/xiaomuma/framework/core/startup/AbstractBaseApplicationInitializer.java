package pers.xiaomuma.framework.core.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractBaseApplicationInitializer implements BaseApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseApplicationInitializer.class);
    private AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public void init() {
        if (initialized.compareAndSet(false, true)) {
            LOGGER.info("{} 类开始执行初始化方法", this.getClass().getSimpleName());
            try {
                doInit();
            } catch (Exception e) {
                if (isFatal()) {
                    throw e;
                } else {
                    LOGGER.error("", e);
                }
            } finally {
                LOGGER.info("{} 类初始化方法执行完成", this.getClass().getSimpleName());
            }
        }
    }

    /**
     * 是否是致命的错误
     * 如果是则停止启动
     */
    protected boolean isFatal() {
        return false;
    }

    /**
     * 执行初始化方法
     */
    protected abstract void doInit();
}
