package impl;

import service.Message;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetMessagesBy {
    private static Statement statement;
    private static List<Message> res = new ArrayList<>();
    static {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("testDS");
            Connection jc = ds.getConnection();
            statement = jc.createStatement();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Message> getMessagesBySender(String sender) {

            try {
                ResultSet rs = statement
                        .executeQuery("select id, sender, send_time, content from messages where sender = \'" + sender + "\'");
                res = AddMessage.addMessage(rs);
                statement.close();
                rs.close();
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
            statement.close();
            rs.close();
        } catch (SQLException | DatatypeConfigurationException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }
}
