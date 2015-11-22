package org.bk.sample.service;

import org.bk.sample.domain.Message;
import org.bk.sample.domain.MessageAcknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.concurrent.TimeUnit;

@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerServiceImpl.class);

    @Value("${reply.message}")
    private String replyMessage;

    public Observable<MessageAcknowledgement> handleMessage(Message message) {
        logger.info("About to Acknowledge");
        return Observable.timer(message.getDelayBy(), TimeUnit.MILLISECONDS)
                .map(l -> message.isThrowException())
                .map(throwException -> {
                    if (throwException) {
                        throw new RuntimeException("Throwing an exception!");
                    }
                    return new MessageAcknowledgement(message.getId(), message.getPayload(), replyMessage);
                });
    }
}
