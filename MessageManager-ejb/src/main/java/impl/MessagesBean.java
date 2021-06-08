package impl;

import service.Message;
import service.MessageManagerService;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@WebService(
        name = "MessageManagerService",
        endpointInterface = "service.MessageManagerService",
        targetNamespace = "http://service/")
@Stateless
public class MessagesBean implements MessageManagerService {
    @Override
    public List<Message> getMessagesBySender(String sender) {
        return GetMessagesBy.getMessagesBySender(sender);
    }

    @Override
    public List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        return GetMessagesBy.getMessagesByDate(sendTime);
    }
}
