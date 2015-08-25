package com.ZLHW.base.MQ;
import javax.jms.Connection;   
import javax.jms.Destination;   
import javax.jms.Message;   
import javax.jms.MessageConsumer;   
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;   
import javax.jms.TextMessage;   
import org.apache.activemq.ActiveMQConnectionFactory;   

import com.ZLHW.common.model.Admin;

  
public class ReceiveMessage implements MessageListener{   
private static final String url = "tcp://localhost:61616";   
private static final String QUEUE_NAME = "choice.queue";   
public void receiveMessage() {
  Connection connection = null;   
  try {
   try {  
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);   
    connection = connectionFactory.createConnection();   
   } catch (Exception e) {   
                System.out.println(e.toString());   
   }   
   connection.start();   
   Session session = connection.createSession(false,   
     Session.AUTO_ACKNOWLEDGE);   
   Destination destination = session.createQueue(QUEUE_NAME);   
   MessageConsumer consumer = session.createConsumer(destination);
   consumer.setMessageListener(this);
  } catch (Exception e) {   
    System.out.println(e.toString());   
  }   
}   
  

  
public void onMessage(Message message) {   
  try {   
   if (message instanceof TextMessage) { 
    TextMessage txtMsg = (TextMessage) message;   
    String msg = txtMsg.getText();   
    System.out.println("Received: " + msg);   
   }else if(message instanceof ObjectMessage){
	   ObjectMessage objMsg=(ObjectMessage)message;
	   Admin a=(Admin) objMsg.getObject();
	   System.out.println(a.getAccount()+a.getDbId());
   }
  } catch (Exception e) {   
   e.printStackTrace();   
  }   
  
}   
  
public static void main(String args[]) {   
  ReceiveMessage rm = new ReceiveMessage();   
  rm.receiveMessage();   
}   
  
}  
