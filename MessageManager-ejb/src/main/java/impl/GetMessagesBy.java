package impl;

import db.ConnectionToDB;
import service.Message;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetMessagesBy {
    private static Statement statement;
    private static List<Message> res = new ArrayList<>();
    static {
        try {
            statement = ConnectionToDB.createConnection();
        } catch (NamingException | SQLException | JAXBException e) {
            e.printStackTrace();
        }
    }
    public static List<Message> getMessagesBySender(String sender) {

            try {
                ResultSet rs = statement
                        .executeQuery("select id, sender, send_time, content from messages where sender = \'" + sender + "\'");
                res = AddMessage.addMessage(rs);
            } catch (SQLException | DatatypeConfigurationException throwables) {
                throwables.printStackTrace();
            }
        return res;
    }

    public static List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        Date date = new Date(sendTime.toGregorianCalendar().getTimeInMillis());
        try {
            ResultSet rs = statement
                    .executeQuery("select id, sender, send_time, content from messages WHERE DATE(send_time) = \'" + date + "\'");
            res = AddMessage.addMessage(rs);
        } catch (SQLException | DatatypeConfigurationException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }
}
