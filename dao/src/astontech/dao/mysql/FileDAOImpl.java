package astontech.dao.mysql;

import astontech.bo.Directory;
import astontech.bo.FileType;
import astontech.bo.HDFile;
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
public class FileDAOImpl extends MySQL implements GenericDAO {


    @Override
    public Object getRecordById(int recordId) {
        ConnOpen();
        HDFile HDFile = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFilePrep);
            cStmnt.setInt(1, getById);
            cStmnt.setInt(2, recordId);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                HDFile = new HDFile();
                HDFile = HydrateFile(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return HDFile;
    }

    @Override
    public Object getRecordByName(String recordName) {
        ConnOpen();
        HDFile HDFile = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFilePrep);
            cStmnt.setInt(1, getByName);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setString(3, recordName);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                HDFile = new HDFile();
                HDFile = HydrateFile(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return HDFile;
    }

    @Override
    public List getAllRecords() {
        ConnOpen();
        List<HDFile> HDFileList = new ArrayList<>();

        try {
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFilePrep);
            cStmnt.setInt(1, getCollection);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            while (rs.next()) {
                HDFileList.add(HydrateFile(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return HDFileList;
    }

    @Override
    public int insertRecord(Object object) {
        int id = 0;
        try{
            id = ModifyFile(insert, (HDFile) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id;
    }

    @Override
    public boolean updateRecord(Object object) {
        int id = 0;
        try{
            id = ModifyFile(update, (HDFile) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    @Override
    public boolean deleteRecord(int recordId) {
        int id = 0;
        HDFile HDFile = new HDFile();
        HDFile.setFileId(recordId);
        try{
            id = ModifyFile(delete, HDFile);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    private static HDFile HydrateFile(ResultSet rs) throws SQLException{
        return new HDFile(rs.getInt(1),
                rs.getString(2),
                (FileType) new FileTypeDAOImpl().getRecordById(rs.getInt(3)),
                rs.getLong(4),
                (Directory) new DirectoryDAOImpl().getRecordById(rs.getInt(5)));
    }

    private static int ModifyFile(int key, HDFile HDFile) throws SQLException {
        ConnOpen();

        CallableStatement cStmnt = conn.prepareCall(Procedures.ExecFilePrep);
        cStmnt.setInt(1, key);
        cStmnt.setInt(2, HDFile.getFileId());

        if(HDFile.getFileName() != null)
            cStmnt.setString(3, HDFile.getFileName());
        else
            cStmnt.setNull(3, Types.VARCHAR);

        if(HDFile.getFileTypeType().getFileTypeId()!=0)
            cStmnt.setInt(4, HDFile.getFileTypeType().getFileTypeId());
        else
            cStmnt.setNull(4, Types.INTEGER);

        if(HDFile.getFileSize()!=0)
            cStmnt.setLong(5, HDFile.getFileSize());
        else
            cStmnt.setNull(5, Types.BIGINT);

        if(HDFile.getFileDirectory().getDirectoryId() !=0)
            cStmnt.setInt(6, HDFile.getFileDirectory().getDirectoryId());
        else
            cStmnt.setNull(6, Types.INTEGER);

        ResultSet rs = cStmnt.executeQuery();

        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }
}

