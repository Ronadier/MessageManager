package jms;

import db.InsertDeleteDB;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSconsumer {
    private static final String JMS_FACTORY = "TestConnectionFactory";
    private static final String TEST_QUEUE = "jms/TestQueue";


    private static QueueConnectionFactory qconFactory;
    private static QueueConnection qcon;
    private static QueueSession qsession;
    private static QueueReceiver qreceiever;
    private static Queue queue;
    private static TextMessage msg;
    private static Context initialContext;

    public static String readFromJMS() {
        try {
            initialContext = new InitialContext();
            qconFactory = (QueueConnectionFactory) initialContext.lookup(JMS_FACTORY);
            queue = (Queue) initialContext.lookup(TEST_QUEUE);
        } catch (NamingException e) {
            return e.getMessage();
        }
        try {
            String returnJms = "";
            qcon = qconFactory.createQueueConnection();
            qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            qreceiever = qsession.createReceiver(queue);
            msg = qsession.createTextMessage();
            qcon.start();
            Message jmsMessage;
            do {
                jmsMessage = qreceiever.receive(1);
                if (jmsMessage != null) {
                    if (jmsMessage instanceof TextMessage) {
                        msg = (TextMessage) jmsMessage;
                        returnJms += msg.getText();
                    } else {
                        break;
                    }
                }
            } while (jmsMessage != null);
            return returnJms;
        } catch (JMSException e) {
            return  e.getMessage();
        }
    }

    public static String addMessage () {
        return InsertDeleteDB.insert(readFromJMS());
    }

    public static String deleteMessage () {
        return InsertDeleteDB.delete(readFromJMS());
    }
}
