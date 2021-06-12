package ws;

import db.SoapProcess;
import service.Message;
import service.MessageManagerService;

import javax.ejb.Remote;
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

    @Inject
    SoapProcess soap;

    @Override
    public List<Message> getMessagesBySender(String sender) {
        return soap.getMessagesBySender(sender);
    }

    @Override
    public List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        return soap.getMessagesByDate(sendTime);
    }
}
