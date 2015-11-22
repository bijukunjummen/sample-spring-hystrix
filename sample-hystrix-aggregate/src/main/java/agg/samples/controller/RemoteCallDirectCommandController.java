package agg.samples.controller;

import agg.samples.commands.remote.RemoteMessageClientCommand;
import agg.samples.domain.Message;
import agg.samples.domain.MessageAcknowledgement;
import agg.samples.feign.RemoteServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteCallDirectCommandController {

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @RequestMapping("/messageDirectCommand")
    public MessageAcknowledgement sendMessage(Message message) {
        RemoteMessageClientCommand remoteCallCommand = new RemoteMessageClientCommand(remoteServiceClient, message);
        return remoteCallCommand.execute();
    }
}
