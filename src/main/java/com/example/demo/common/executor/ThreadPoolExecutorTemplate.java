package com.example.demo.common.executor;



import java.util.concurrent.*;

/**
 * 线程池创建模版
 *
 * @author wujl
 * @date 2020/12/6 11:29
 */
public class ThreadPoolExecutorTemplate extends ThreadPoolExecutor {

    public ThreadPoolExecutorTemplate(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue,
                                      ThreadFactory threadFactory,
                                      RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    private Exception clientTrace() {
        return new Exception("tread task root stack trace");
    }

    @Override
    public void execute(final Runnable command) {
        super.execute(wrap(command, clientTrace()));
    }

    @Override
    public Future<?> submit(final Runnable task) {
        return super.submit(wrap(task, clientTrace()));
    }

    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return super.submit(wrap(task, clientTrace()));
    }

    private Runnable wrap(final Runnable task, final Exception clientStack) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    //e.setStackTrace(ArrayUtils.addAll(clientStack.getStackTrace(), e.getStackTrace()));
                    throw e;
                }
            }
        };
    }

    private <T> Callable<T> wrap(final Callable<T> task, final Exception clientStack) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    return task.call();
                } catch (Exception e) {
                    //e.setStackTrace(ArrayUtils.addAll(clientStack.getStackTrace(), e.getStackTrace()));
                    throw e;
                }
            }
        };
    }
}
