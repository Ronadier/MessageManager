package db;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;



public class InsertDeleteDB {
    private static Statement statement;
    static {
        try {
            statement = ConnectionToDB.createConnection();
        } catch (NamingException | SQLException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public static String insert (String readFromJSM) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(readFromJSM).getAsJsonObject();
        JsonObject jsonTime = json.get("sendTime").getAsJsonObject();
        String time = jsonTime.get("year").getAsString() + "-" + jsonTime.get("month").getAsString() + "-" + jsonTime.get("day").getAsString() + " "
                + jsonTime.get("hour").getAsString() + ":" + jsonTime.get("minute").getAsString() + ":" + jsonTime.get("second").getAsString() + "."
                + jsonTime.get("fractionalSecond").getAsString().substring(2, 4);
        Timestamp ts = Timestamp.valueOf(time);
        try {
            statement.executeUpdate("INSERT INTO messages (id, sender, send_time, content) VALUES("
                    + json.get("id").getAsInt() + ", \'" + json.get("sender").getAsString()
                + "\',\'"+ ts + "\',\'" + json.get("content").getAsString()+ "\');");
            return "SUCCESS";
        } catch (SQLException throwables) {
            return throwables.getMessage();
        }
    }

    public static String delete(String readFromJMS) {
        Integer id = Integer.parseInt(readFromJMS.split("=")[1]);

        try {
            statement.executeUpdate("DELETE FROM messages WHERE id = " + id);
            return "SUCCESS";
        } catch (SQLException throwables) {
            return throwables.getMessage();
        }
    }
}
