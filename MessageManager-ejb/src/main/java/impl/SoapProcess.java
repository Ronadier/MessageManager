package impl;

import service.Message;

import javax.ejb.EJB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SoapProcess {


    public static List<Message> getMessagesBySender(String sender) {
        List<Message> res = new ArrayList<>();
            try {
                Connection connection = DriverManager
                        .getConnection("jdbc:postgresql://192.168.70.181:7100/eip", "coder", "");
                Statement statement = connection.createStatement();
                ResultSet rs = statement
                        .executeQuery("select id, sender, send_time, content from messages where sender = " + "\'" + sender + "\'");
                int i = 0;
                while (rs.next()) {
                    res.add(new Message());
                    res.get(i).setId(rs.getInt(1));
                    res.get(i).setSender(rs.getString(2));
                    Timestamp ts = rs.getTimestamp(3);
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(new Date(ts.getTime()));
                    XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
                    res.get(i).setSendTime(cal);
                    res.get(i).setContent(rs.getString(4));
                    i++;
                }
            } catch (SQLException throwables) {
                res.add(new Message());
                res.get(0).setSender(throwables.getMessage());
                res.get(0).setContent(throwables.getSQLState());
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        return res;
    }

    public static List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        List<Message> res = new ArrayList<>();
        res.add(new Message());
        res.get(0).setSendTime(sendTime);
        return res;
    }
}
