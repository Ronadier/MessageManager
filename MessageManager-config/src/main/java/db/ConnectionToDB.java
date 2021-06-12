package db;

import config.ConfigHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionToDB {
    private static String nameDs;

    public static Connection createConnection() throws JAXBException, NamingException, SQLException {
        nameDs = ConfigHelper.getConfig().getDataBase().getJNDI();
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup(nameDs);
        Connection dsConnection = ds.getConnection();
        return dsConnection;
    }
}
