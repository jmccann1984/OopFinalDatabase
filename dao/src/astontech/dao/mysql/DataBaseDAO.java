package astontech.dao.mysql;

import astontech.bo.Directory;
import astontech.bo.HDFile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua.McCann on 7/5/2017.
 */
public class DataBaseDAO extends MySQL {
    public static void ClearDB(){
        try {
            ConnOpen();
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(Procedures.RefreshTables);
            if (rs.next())
                logger.info("Tables Cleared!");
        } catch (SQLException sqlEx){
            logger.error("Tables Not Cleared!");
        }
    }

    public static Directory MostFiles(){
        try{
            ConnOpen();
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(Procedures.MostFiles);
            if (rs.next()){
                return (Directory) new DirectoryDAOImpl().getRecordById(rs.getInt(1));
            }
        } catch (SQLException sqlEx){
            logger.error("Unable to Retrieve Folder");
        }
        return null;
    }

    public static Directory LargestFolder(){
        try{
            ConnOpen();
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(Procedures.LargestFolder);
            if (rs.next()){
                return (Directory) new DirectoryDAOImpl().getRecordById(rs.getInt(1));
            }
        } catch (SQLException sqlEx){
            logger.error("Unable to Retrieve Folder");
        }
        return null;
    }

    public static List TopFiveFilesBySize(){
        List<HDFile> fileList = null;
        try{
            ConnOpen();
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(Procedures.TopFiveFilesBySize);
            fileList = new ArrayList<>();
            while(rs.next()){
                fileList.add((HDFile) new FileDAOImpl().getRecordById(rs.getInt(1)));
            }


        } catch (SQLException sqlEx){
            logger.error("Unable to retrieve files");
        }
        return fileList;
    }

    public static List AllFilesForType(int i){
        List<HDFile> fileList = null;
        try{
            ConnOpen();
            CallableStatement cstmnt = conn.prepareCall(Procedures.AllFilesForType);
            cstmnt.setInt(1, i);

            ResultSet rs = cstmnt.executeQuery();
            fileList = new ArrayList<>();
            while(rs.next()){
                fileList.add((HDFile) new FileDAOImpl().getRecordById(rs.getInt(1)));
            }
        } catch (SQLException sqlEx){
            logger.error("Unable to retrieve files");
        }
        return fileList;
    }
}
