package jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class JMSproducer {
    private static final String JMS_FACTORY = "TestConnectionFactory";
    private static final String TEST_QUEUE = "jms/TestQueue";


    private static QueueConnectionFactory qconFactory;
    private static QueueConnection qcon;
    private static QueueSession qsession;
    private static QueueSender qsender;
    private static Queue queue;
    private static TextMessage msg;
    private static Context initialContext;

    public static String addMessage(String message) {
        try {
            initialContext = new InitialContext();
            qconFactory = (QueueConnectionFactory) initialContext.lookup(JMS_FACTORY);
            queue = (Queue) initialContext.lookup(TEST_QUEUE);
        } catch (NamingException e) {
            return e.getMessage();
        }
        try {
            qcon = qconFactory.createQueueConnection();
            qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            qsender = qsession.createSender(queue);
            msg = qsession.createTextMessage();
            qcon.start();
            msg.setText(message);
            qsender.send(msg);
            return "Success";
        } catch (JMSException e) {
            return e.getMessage();
        }
    }
}
