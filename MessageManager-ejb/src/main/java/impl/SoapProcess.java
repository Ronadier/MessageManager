package impl;

import service.Message;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class SoapProcess {
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
