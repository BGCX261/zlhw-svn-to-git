package com.ZLHW.common.service;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.util.UUIDUtils;

@Service("MessageService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class MessageService {
	public static final String DEFAULT_MESSAGEB_BROKER_ID = "_messageBroker";
	public static final String MESSAGE_PUSH_DEST = "message-push-destination";
	public static MessageBroker msgBroker = MessageBroker.getMessageBroker(DEFAULT_MESSAGEB_BROKER_ID);

	public void sendMessage(String subTopic, Object message) {
		AsyncMessage msg = new AsyncMessage();
		msg.setDestination(MESSAGE_PUSH_DEST);
		msg.setHeader("DSSubtopic", subTopic);
		msg.setClientId(UUIDUtils.createUUID());
		msg.setMessageId(UUIDUtils.createUUID());
		msg.setTimestamp(System.currentTimeMillis());
		msg.setBody(message);
		if(msgBroker==null)
			msgBroker = MessageBroker.getMessageBroker(DEFAULT_MESSAGEB_BROKER_ID);
		msgBroker.routeMessageToService(msg, null);
	}
}
