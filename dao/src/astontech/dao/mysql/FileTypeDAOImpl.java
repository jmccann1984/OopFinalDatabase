package astontech.dao.mysql;

import astontech.bo.FileType;
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
public class FileTypeDAOImpl extends MySQL implements GenericDAO{

    @Override
    public Object getRecordById(int recordId) {
        ConnOpen();
        FileType fileType = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFileTypePrep);
            cStmnt.setInt(1, getById);
            cStmnt.setInt(2, recordId);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                fileType = new FileType();
                fileType = HydrateFileType(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return fileType;
    }

    @Override
    public Object getRecordByName(String recordName) {
        ConnOpen();
        FileType fileType = null;
        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFileTypePrep);
            cStmnt.setInt(1, getByName);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setString(3, recordName);

            ResultSet rs = cStmnt.executeQuery();
            if(rs.next()){
                fileType = new FileType();
                fileType = HydrateFileType(rs);
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return fileType;
    }

    @Override
    public List getAllRecords() {
        ConnOpen();
        List<FileType> fileTypeList = new ArrayList<>();

        try{
            CallableStatement cStmnt = conn.prepareCall(Procedures.GetFileTypePrep);
            cStmnt.setInt(1, getCollection);
            cStmnt.setNull(2, Types.INTEGER);
            cStmnt.setNull(3, Types.VARCHAR);

            ResultSet rs = cStmnt.executeQuery();
            while(rs.next()){
                fileTypeList.add(HydrateFileType(rs));
            }


        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return fileTypeList;
    }

    @Override
    public int insertRecord(Object object) {
        int id = 0;
        try{
            id = ModifyFileType(insert, (FileType) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id;
    }

    @Override
    public boolean updateRecord(Object object) {
        int id = 0;
        try{
            id = ModifyFileType(update, (FileType) object);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    @Override
    public boolean deleteRecord(int recordId) {
        int id = 0;
        FileType fileType = new FileType();
        fileType.setFileTypeId(recordId);

        try{
            id = ModifyFileType(insert, fileType);
        } catch (SQLException sqlEx){
            logger.error(sqlEx);
        }

        return id>0;
    }

    private static FileType HydrateFileType(ResultSet rs) throws SQLException{
        return new FileType(rs.getInt(1),rs.getString(2));
    }

    private static int ModifyFileType(int key, FileType fileType) throws SQLException{

        ConnOpen();

        CallableStatement cStmnt = conn.prepareCall(Procedures.ExecFileTypePrep);
        cStmnt.setInt(1, key);
        cStmnt.setInt(2, fileType.getFileTypeId());

        if (fileType.getFileTypeName() != null)
            cStmnt.setString(3, fileType.getFileTypeName());
        else
            cStmnt.setNull(3, Types.VARCHAR);

        ResultSet rs = cStmnt.executeQuery();

        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }
}
