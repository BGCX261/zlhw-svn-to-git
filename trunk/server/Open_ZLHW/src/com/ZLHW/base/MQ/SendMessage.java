package com.ZLHW.base.MQ;
import javax.jms.Connection;   
import javax.jms.Destination;   
import javax.jms.JMSException;   
import javax.jms.MessageProducer;   
import javax.jms.ObjectMessage;
import javax.jms.Session;   
import javax.jms.TextMessage;   
import org.apache.activemq.ActiveMQConnectionFactory;   

import com.ZLHW.common.model.Admin;

  
public class SendMessage {   
private static final String url ="tcp://localhost:61616";   
private static final String QUEUE_NAME ="choice.queue";   
protected String expectedBody = "<hello>world!</hello>";   
public void sendMessage() throws JMSException{   
  Connection connection =null;   
  try{   
   ActiveMQConnectionFactory connectionFactory =new ActiveMQConnectionFactory(url);   
   connection = (Connection)connectionFactory.createConnection();   
   connection.start();   
   Session session = (Session)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
   Destination destination = session.createQueue(QUEUE_NAME);   
   MessageProducer producer = session.createProducer(destination);
   Admin a=new Admin();
   a.setAccount("zb");
   a.setDbId(1);
   ObjectMessage message = session.createObjectMessage(a);   
   message.setStringProperty("headname", "remoteB");   
   producer.send(message);       
  }catch(Exception e){   
   e.printStackTrace();   
  }   
}   
    
public static void main(String[] args){   
  SendMessage sndMsg = new SendMessage();   
  try{   
   sndMsg.sendMessage();   
  }catch(Exception ex){   
   System.out.println(ex.toString());   
  }   
}   
}  