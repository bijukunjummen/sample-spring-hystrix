package agg.samples.controller;

import agg.samples.domain.Message;
import agg.samples.domain.MessageAcknowledgement;
import agg.samples.feign.RemoteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoHystrixController {

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @RequestMapping("/noHystrix")
    public MessageAcknowledgement sendMessage(Message message) {
        return this.remoteServiceClient.sendMessage(message);
    }
}
