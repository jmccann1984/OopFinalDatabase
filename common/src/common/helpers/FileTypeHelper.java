package common.helpers;

import astontech.bo.FileType;
import astontech.dao.mysql.FileTypeDAOImpl;

import java.lang.String;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public class FileTypeHelper {
    public static int NewOrOldFileType(String fileName){
        FileType fileType = new FileType();
        FileTypeDAOImpl FileTypeDAO = new FileTypeDAOImpl();
        if (FileTypeDAO.getRecordByName(fileName)!=null){
            fileType = (FileType) FileTypeDAO.getRecordByName(fileName);
            return fileType.getFileTypeId();
        } else {
            fileType.setFileTypeName(fileName);
            return FileTypeDAO.insertRecord(fileType);
        }
    }
}