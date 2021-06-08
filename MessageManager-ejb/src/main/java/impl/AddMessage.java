package impl;

import service.Message;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class AddMessage {
    public static List<Message> addMessage(ResultSet rs) throws SQLException, DatatypeConfigurationException {
        List<Message> res = new ArrayList<>();
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
        return res;
    }
}
