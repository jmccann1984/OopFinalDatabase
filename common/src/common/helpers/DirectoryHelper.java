package common.helpers;


import astontech.bo.Directory;
import astontech.dao.mysql.DirectoryDAOImpl;

import java.io.File;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public class DirectoryHelper {

    public static void rootDirectory(File file){

        Directory directory = new Directory();
        DirectoryDAOImpl directoryDAO = new DirectoryDAOImpl();
        if(directoryDAO.getRecordByName(file.getPath())==null) {
            directory.setDirectoryName(file.getPath());
            directoryDAO.insertRecord(directory);
        }
    }
    public static int NewOrOldDirectory(File file){
        Directory directory= new Directory();
        DirectoryDAOImpl directoryDAO = new DirectoryDAOImpl();
        if (directoryDAO.getRecordByName(file.getPath())!=null) {
            directory = (Directory) directoryDAO.getRecordByName(file.getPath());
            return directory.getDirectoryId();
        } else if (directoryDAO.getRecordByName(file.getName())!=null){
            directory = (Directory) directoryDAO.getRecordByName(file.getName());
            return directory.getDirectoryId();

        } else {
            directory.setDirectoryName(file.getName());
            if (directoryDAO.getRecordByName(file.getParent())!=null | directoryDAO.getRecordByName(file.getParent().substring(file.getParent().lastIndexOf("\\") + 1, file.getParent().length()))!=null){
                directory.addDirectoryPath((Directory)directoryDAO.getRecordById(NewOrOldDirectory(file.getParentFile())));
            }

            if(file.listFiles()!=null) {
                directory.setDirectorySize(folderSize(file));
                directory.setDirectoryFiles(file.listFiles().length);
            } else {
                directory.setDirectorySize(0);
                directory.setDirectoryFiles(0);
            }
            return directoryDAO.insertRecord(directory);
        }
    }

    private static long folderSize(File dir){
        long length = 0;
        for (File file : dir.listFiles()){
            if (file.isFile())
                length += file.length();
            else if (file.listFiles()!=null)
                length += folderSize(file);
        }
        return length;
    }
}