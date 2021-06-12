package impl;

import service.Message;
import service.MessageManagerService;
import ws.MessageManagerImpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;


@Stateless
public class MessagesBean implements MessageManagerService {

    @Inject
    MessageManagerImpl msg;

    @Override
    public List<Message> getMessagesBySender(String sender) {
        return msg.getMessagesBySender(sender);
    }

    @Override
    public List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        return msg.getMessagesByDate(sendTime);
    }
}
