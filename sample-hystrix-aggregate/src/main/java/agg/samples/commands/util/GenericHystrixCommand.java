package agg.samples.commands.util;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import rx.Observable;

import java.util.function.Function;
import java.util.function.Supplier;

public class GenericHystrixCommand<T> extends HystrixCommand<T> {

    private Supplier<T> toRun;

    private Function<Throwable, T> fallback;


    public static <T> T execute(String groupKey, String commandkey, Supplier<T> toRun) {
        return execute(groupKey, commandkey, toRun, null);
    }

    public static <T> T execute(String groupKey, String commandkey, Supplier<T> toRun, Function<Throwable, T> fallback) {
        try {
            return new GenericHystrixCommand<>(groupKey, commandkey, toRun, fallback).execute();
        } catch (Exception e) {
            throw wrappedException(e);
        }
    }

    public static <T> Observable<T> executeObservable(String groupKey, String commandkey, Supplier<T> toRun) {
        return executeObservable(groupKey, commandkey, toRun, null);
    }

    public static <T> Observable<T> executeObservable(String groupKey, String commandkey, Supplier<T> toRun, Function<Throwable, T> fallback) {
        return new GenericHystrixCommand<>(groupKey, commandkey, toRun, fallback)
                .toObservable()
                .onErrorReturn(t -> {throw wrappedException(t);});
    }

    public GenericHystrixCommand(String groupKey, String commandkey, Supplier<T> toRun, Function<Throwable, T> fallback) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandkey)));
        this.toRun = toRun;
        this.fallback = fallback;
    }

    protected T run() throws Exception {
        return this.toRun.get();
    }

    @Override
    protected T getFallback() {
        return (this.fallback != null)
                ? this.fallback.apply(getExecutionException())
                : super.getFallback();
    }
    
    private static RuntimeException wrappedException(Throwable t) {
        if (t instanceof HystrixRuntimeException) {
            return new CustomSystemException(t.getCause());
        } else if (t instanceof HystrixBadRequestException) {
            return new CustomBusinessException(t.getMessage());
        }
        throw new RuntimeException(t);
    }
}
