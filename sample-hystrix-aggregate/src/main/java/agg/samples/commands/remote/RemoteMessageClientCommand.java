package agg.samples.commands.remote;

import agg.samples.domain.Message;
import agg.samples.domain.MessageAcknowledgement;
import agg.samples.feign.RemoteServiceClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteMessageClientCommand extends HystrixCommand<MessageAcknowledgement> {
    private static final String COMMAND_GROUP = "demo";
    private static final Logger logger = LoggerFactory.getLogger(RemoteMessageClientCommand.class);

    private final RemoteServiceClient remoteServiceClient;
    private final Message message;

    public RemoteMessageClientCommand(RemoteServiceClient remoteServiceClient, Message message) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
        this.remoteServiceClient = remoteServiceClient;
        this.message = message;
    }

    @Override
    protected MessageAcknowledgement run() throws Exception {
        logger.info("About to make Remote Call");
        return this.remoteServiceClient.sendMessage(this.message);
    }

    @Override
    protected MessageAcknowledgement getFallback() {
        return new MessageAcknowledgement(message.getId(), message.getPayload(), "Fallback message");
    }
}
