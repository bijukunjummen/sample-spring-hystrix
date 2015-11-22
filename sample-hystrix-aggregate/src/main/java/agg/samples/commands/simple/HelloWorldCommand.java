package agg.samples.commands.simple;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldCommand extends HystrixCommand<String> {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldCommand.class);

    private final String name;

    public HelloWorldCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        logger.info("HelloWorld Command Invoked");
        return "Hello " + name;
    }
}
