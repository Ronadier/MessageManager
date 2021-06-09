package jms;

import config.ConfigHelper;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;


public class JMSproducer {
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
            return JMSconsumer.addMessage();
        } catch (JMSException e) {
            return e.getMessage();
        }
    }

    public static String deleteMessage(Integer id) {
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
            msg.setText("del: id =" + id);
            qsender.send(msg);
            return JMSconsumer.deleteMessage();
        } catch (JMSException e) {
            return e.getMessage();
        }
    }
}
