package common.helpers;

import astontech.bo.Directory;
import astontech.bo.FileType;
import astontech.bo.HDFile;
import astontech.dao.mysql.DirectoryDAOImpl;
import astontech.dao.mysql.FileDAOImpl;
import astontech.dao.mysql.FileTypeDAOImpl;

import java.io.File;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public class FileHelper {
    public static void InsertFile (File file){
        HDFile hdFile = new HDFile();
        hdFile.setFileSize(file.length());
        hdFile.setFileDirectory((Directory)new DirectoryDAOImpl().getRecordById(DirectoryHelper.NewOrOldDirectory(file.getParentFile())));
        if(file.getName().contains(".")){
            hdFile.setFileName(file.getName().substring(0, file.getName().lastIndexOf(".")));
            hdFile.setFileTypeType((FileType)new FileTypeDAOImpl().getRecordById(FileTypeHelper.NewOrOldFileType(file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length()))));
        } else {
            hdFile.setFileName(file.getName());
            hdFile.getFileTypeType().setFileTypeName(null);
        }
        new FileDAOImpl().insertRecord(hdFile);
    }
}
