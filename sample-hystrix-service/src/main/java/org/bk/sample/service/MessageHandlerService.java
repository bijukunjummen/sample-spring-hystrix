package org.bk.sample.service;

import org.bk.sample.domain.Message;
import org.bk.sample.domain.MessageAcknowledgement;
import rx.Observable;

public interface MessageHandlerService {
    Observable<MessageAcknowledgement> handleMessage(Message message);
}
