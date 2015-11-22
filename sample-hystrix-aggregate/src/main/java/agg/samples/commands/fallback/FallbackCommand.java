package agg.samples.commands.fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallbackCommand extends HystrixCommand<String> {

    private static final String COMMAND_GROUP="default";
    private static final Logger logger = LoggerFactory.getLogger(FallbackCommand.class);


    public FallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("Always fail");
    }

    @Override
    protected String getFallback() {
        logger.info("About to fallback");
        return "Falling back";
    }
}
