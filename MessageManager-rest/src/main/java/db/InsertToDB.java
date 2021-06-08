package db;

import com.google.gson.Gson;
import config.ConfigHelper;

import javax.json.Json;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertToDB {
    private static Statement statement;
    private static String nameDs;
    static {
        try {
            nameDs = ConfigHelper.getConfig().getDataBase().getJNDI();
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup(nameDs);
            Connection dsConnection = ds.getConnection();
            statement = dsConnection.createStatement();
        } catch (NamingException | SQLException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Boolean insert (Gson message) {
        message.
        try {
            ResultSet rs = statement
                    .executeQuery("insert into ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
