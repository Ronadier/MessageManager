package db;

import service.Message;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SoapProcess {
    private static List<Message> res = new ArrayList<>();

    public static List<Message> getMessagesBySender(String sender) {
        try (Connection connection = ConnectionToDB.createConnection();
             PreparedStatement statement = connection
                     .prepareStatement("select id, sender, send_time, content from messages where sender = ?")) {
            statement.setString(1, sender);
            ResultSet rs = statement.executeQuery();
            res = addMessagesToList(rs);
        } catch (SQLException | DatatypeConfigurationException | JAXBException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static List<Message> getMessagesByDate(XMLGregorianCalendar sendTime) {
        Date date = new Date(sendTime.toGregorianCalendar().getTimeInMillis());
        try (Connection connection = ConnectionToDB.createConnection();
             PreparedStatement statement = connection
                     .prepareStatement("select id, sender, send_time, content from messages WHERE DATE(send_time) = ?")) {
            statement.setDate(1, date);
            ResultSet rs = statement.executeQuery();
            res = addMessagesToList(rs);
        } catch (SQLException | DatatypeConfigurationException | JAXBException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    private static List<Message> addMessagesToList(ResultSet rs) throws SQLException, DatatypeConfigurationException {
        List<Message> res = new ArrayList<>();
        int i = 0;
        GregorianCalendar gc = new GregorianCalendar();
        while (rs.next()) {
            res.add(new Message());
            res.get(i).setId(rs.getInt(1));
            res.get(i).setSender(rs.getString(2));
            Timestamp ts = rs.getTimestamp(3);
            gc.setTime(new Date(ts.getTime()));
            XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            res.get(i).setSendTime(cal);
            res.get(i).setContent(rs.getString(4));
            i++;
        }
        return res;
    }
}
