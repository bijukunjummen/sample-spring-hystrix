package agg.samples.commands.remote;

import agg.samples.domain.Message;
import agg.samples.domain.MessageAcknowledgement;
import agg.samples.feign.RemoteServiceClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteMessageAnnotationClient  {

    private final RemoteServiceClient remoteServiceClient;

    @Autowired
    public RemoteMessageAnnotationClient(RemoteServiceClient remoteServiceClient) {
        this.remoteServiceClient = remoteServiceClient;
    }

    @HystrixCommand(fallbackMethod = "defaultMessage", commandKey = "RemoteMessageAnnotationClient" )
    public MessageAcknowledgement sendMessage(Message message) {
        return this.remoteServiceClient.sendMessage(message);
    }

    public MessageAcknowledgement defaultMessage(Message message) {
        return new MessageAcknowledgement("-1", message.getPayload(), "Fallback Payload");
    }

}
