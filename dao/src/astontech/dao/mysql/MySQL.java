package astontech.dao.mysql;

import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Created by Joshua.McCann on 6/30/2017.
 */
public abstract class MySQL {

    protected static String dbHost = "localhost";
    protected static String dbName = "oopfinal";
    protected static String dbUser = "consoleUser";
    protected static String dbPass = "qwe123$!";
    protected static String useSSL = "false";
    protected static String procBod = "true";

    protected static Connection conn = null;

    final static Logger logger = Logger.getLogger(MySQL.class);

    protected static final int getById = 10;
    protected static final int getByName = 20;
    protected static final int getCollection = 30;
    protected static final int insert = 10;
    protected static final int update = 20;
    protected static final int delete = 30;

    protected static void ConnOpen(){
        if(conn == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex){
                logger.error("Driver Not Found");
            }

            try{
                conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + dbName + "?useSSL=" + useSSL + "&noAccessToProcedureBodies=" + procBod, dbUser, dbPass);
            } catch (SQLException ex){
                logger.error("Connection Failed");
            }

            if(conn != null){
                logger.info("Successfully Connected");
            } else {
                logger.info("Connection Failed");
            }
        }
    }

    protected static void ConnClose(){
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ex){
            logger.info("Unable To Close Connection");
        }
    }


}

