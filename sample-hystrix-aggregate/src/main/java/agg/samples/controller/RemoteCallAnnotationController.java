package agg.samples.controller;

import agg.samples.commands.remote.RemoteMessageAnnotationClient;
import agg.samples.domain.Message;
import agg.samples.domain.MessageAcknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteCallAnnotationController {

    @Autowired
    private RemoteMessageAnnotationClient remoteServiceClient;

    @RequestMapping("/messageAnnotation")
    public MessageAcknowledgement sendMessage(Message message) {
        return remoteServiceClient.sendMessage(message);
    }
}
