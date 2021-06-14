package ws;

import ejb.MessagesBean;
import service.Message;
import service.MessageManagerService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@WebService(
        name = "MessageManagerService",
        endpointInterface = "service.MessageManagerService",
        targetNamespace = "http://service/")
@Stateless
public class MessageManagerImpl implements MessageManagerService {

    @EJB
    MessagesBean bean;

    @Override
    public List<Message> getMessagesBySender(String sender) {
        return bean.getMessagesBySender(sender);
    }

    @Override
    public List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        return bean.getMessagesByDate(sendTime);
    }
}
