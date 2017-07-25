package astontech.dao.mysql;

import astontech.bo.Directory;
import astontech.dao.GenericDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public class DirectoryDAOImpl extends MySQL implements GenericDAO {

    @Override
    public Object getRecordById(int recordId) {
        ConnOpen();
        Directory directory = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetDirectoryPrep);
            cStmnt.setInt(1, getById);
            cStmnt.setInt(2, recordId);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                directory = new Directory();
                directory = HydrateDirectory(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return directory;
    }

    @Override
    public Object getRecordByName(String recordName) {
        ConnOpen();
        Directory directory = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetDirectoryPrep);
            cStmnt.setInt(1, getByName);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setString(3, recordName);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                directory = new Directory();
                directory = HydrateDirectory(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return directory;
    }

    @Override
    public List getAllRecords() {
        ConnOpen();
        List<Directory> directoryList = new ArrayList<>();

        try {
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetDirectoryPrep);
            cStmnt.setInt(1, getCollection);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            while (rs.next()) {
                directoryList.add(HydrateDirectory(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directoryList;
    }

    @Override
    public int insertRecord(Object object) {
        int id = 0;
        try{
            id = ModifyDirectory(insert, (Directory) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id;
    }

    @Override
    public boolean updateRecord(Object object) {
        int id = 0;
        try{
            id = ModifyDirectory(update, (Directory) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    @Override
    public boolean deleteRecord(int recordId) {
        int id = 0;
        Directory directory = new Directory();
        directory.setDirectoryId(recordId);

        try{
            id = ModifyDirectory(delete, directory);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    public static Directory HydrateDirectory(ResultSet rs) throws SQLException{
        int i = rs.getInt(5);
        Directory tempDirectory = null;
        Directory tempDirectoryRoot = null;
        Directory directory = new Directory(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getLong(4));
        while(i!=0){
            tempDirectory = (Directory) new DirectoryDAOImpl().getRecordById(i);
            directory.addDirectoryPath(tempDirectory);
            tempDirectoryRoot = new Directory();
            tempDirectoryRoot = (Directory) tempDirectory.getDirectoryPath().get(0);
            if(tempDirectoryRoot != null)
                i= tempDirectoryRoot.getDirectoryId();
            else
                i=0;
        }
        return directory;
    }

    public static int ModifyDirectory(int key, Directory directory) throws SQLException{
        ConnOpen();

        CallableStatement cStmnt = conn.prepareCall(Procedures.ExecDirectoryPrep);
        cStmnt.setInt(1, key);
        cStmnt.setInt(2, directory.getDirectoryId());
        Directory tempDirectory = (Directory) directory.getDirectoryPath().get(0);

        if(directory.getDirectoryName() != null)
            cStmnt.setString(3, directory.getDirectoryName());
        else
            cStmnt.setNull(3, Types.VARCHAR);

        if(directory.getDirectoryFiles() !=0)
            cStmnt.setInt(4, directory.getDirectoryFiles());
        else
            cStmnt.setNull(4, Types.INTEGER);

        if(directory.getDirectorySize()!=0)
            cStmnt.setLong(5, directory.getDirectorySize());
        else
            cStmnt.setNull(5, Types.BIGINT);

        if(tempDirectory!=null&&tempDirectory.getDirectoryId()!=0)
            cStmnt.setInt(6, tempDirectory.getDirectoryId());
        else
            cStmnt.setNull(6, Types.INTEGER);

        ResultSet rs = cStmnt.executeQuery();

        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }
}
