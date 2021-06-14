package ejb;

import db.SoapProcess;
import service.Message;
import service.MessageManagerService;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;


@Stateless
@LocalBean
public class MessagesBean implements MessageManagerService {

    @Inject
    SoapProcess soap;

    @Override
    public List<Message> getMessagesBySender(String sender) {
        if (sender == null || sender.equals("")) {
            throw new IllegalArgumentException("Не заполнено поле sender!");
        }
        return soap.getMessagesBySender(sender);
    }

    @Override
    public List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        if (sendTime == null) {
            throw new IllegalArgumentException("Не заполнено поле sendTime!");
        }
        return soap.getMessagesByDate(sendTime);
    }
}
