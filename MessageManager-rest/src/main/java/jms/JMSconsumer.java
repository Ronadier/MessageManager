package jms;

import config.ConfigHelper;
import db.InsertDeleteDB;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

public class JMSconsumer {
    private static String JMS_FACTORY;
    private static String TEST_QUEUE;

    static {
        try {
            JMS_FACTORY = ConfigHelper.getConfig().getJMS().getJNDIConnectionFactory();
            TEST_QUEUE = ConfigHelper.getConfig().getJMS().getJNDIQueque();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static String readFromJMS() {
        QueueConnectionFactory qconFactory;
        Queue queue;
        try {
            Context initialContext = new InitialContext();
            qconFactory = (QueueConnectionFactory) initialContext.lookup(JMS_FACTORY);
            queue = (Queue) initialContext.lookup(TEST_QUEUE);
        } catch (NamingException e) {
            return e.getMessage();
        }
        try {
            String returnJms = "";
            QueueConnection qcon = qconFactory.createQueueConnection();
            QueueSession qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver qreceiever = qsession.createReceiver(queue);
            TextMessage msg = qsession.createTextMessage();
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
