package impl;

import service.Message;

import javax.ejb.EJB;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class SoapProcess {

    public static List<Message> getMessagesBySender(String sender) {
        List<Message> res = new ArrayList<>();
        res.add(new Message());
        res.get(0).setSender(sender);
        return res;
    }

    public static List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        List<Message> res = new ArrayList<>();
        res.add(new Message());
        res.get(0).setSendTime(sendTime);
        return res;
    }
}
