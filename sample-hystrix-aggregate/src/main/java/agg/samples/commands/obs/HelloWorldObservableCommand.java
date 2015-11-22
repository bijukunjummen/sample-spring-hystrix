package agg.samples.commands.obs;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class HelloWorldObservableCommand extends HystrixObservableCommand<String> {

    private String name;

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldObservableCommand.class);

    public HelloWorldObservableCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.name = name;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.just("Returning a Fallback");
    }

    @Override
    protected Observable<String> construct() {
        logger.info("HelloWorldObservableCommand invoked");
        return Observable.just("Hello " + this.name);
    }
}
