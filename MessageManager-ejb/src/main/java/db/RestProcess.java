package db;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import java.sql.*;


public class RestProcess {

    public static String insert(String readFromJSM) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(readFromJSM).getAsJsonObject();
        JsonObject jsonTime = json.get("sendTime").getAsJsonObject();
        String time = jsonTime.get("year").getAsString() + "-" + jsonTime.get("month").getAsString() + "-" + jsonTime.get("day").getAsString() + " "
                + jsonTime.get("hour").getAsString() + ":" + jsonTime.get("minute").getAsString() + ":" + jsonTime.get("second").getAsString() + "."
                + jsonTime.get("fractionalSecond").getAsString().substring(2, 4);
        Timestamp ts = Timestamp.valueOf(time);
        try (Connection connection = ConnectionToDB.createConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO messages (id, sender, send_time, content) VALUES(?,?,?,?)")) {
            statement.setInt(1, json.get("id").getAsInt());
            statement.setString(2, json.get("sender").getAsString());
            statement.setTimestamp(3, ts);
            statement.setString(4, json.get("content").getAsString());
            statement.executeUpdate();
            return "SUCCESS";
        } catch (SQLException | JAXBException | NamingException throwables) {
            return throwables.getMessage();
        }
    }

    public static String delete(String readFromJMS) {
        Integer id = Integer.parseInt(readFromJMS.split("=")[1]);

        try (Connection connection = ConnectionToDB.createConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM messages WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return "SUCCESS";
        } catch (SQLException | JAXBException | NamingException throwables) {
            return throwables.getMessage();
        }
    }
}
