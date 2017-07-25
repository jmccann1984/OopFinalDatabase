package common.helpers;

import astontech.bo.Directory;
import astontech.bo.FileType;
import astontech.bo.HDFile;
import astontech.dao.mysql.DataBaseDAO;
import astontech.dao.mysql.FileTypeDAOImpl;
import astontech.dao.mysql.MySQL;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Joshua.McCann on 7/5/2017.
 */
public class DataBaseHelper {

    public static boolean PopulateDataBase(File dir) throws SQLException{
        if(dir.exists()) {
            DirectoryHelper.rootDirectory(dir);
            PopulateSubDirectory(dir);
            return true;
        }
        else return false;
    }

    public static void PopulateSubDirectory(File dir) throws SQLException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                DirectoryHelper.NewOrOldDirectory(file);
                if(file.list()!=null)
                    PopulateSubDirectory(file);
            }
            else{
                FileHelper.InsertFile(file);
            }
        }
    }

    public static void ExecClearDB(){
        DataBaseDAO db = new DataBaseDAO();
        db.ClearDB();
    }

    public static void ExecMostFiles(){
        Directory directory = DataBaseDAO.MostFiles();
        System.out.println("======================");
        System.out.println("Folder: "+ directory.getDirectoryName() + "\nFiles:  " + directory.getDirectoryFiles() + "\nLocated: " + directory.pathToString() + "\nSize: " + directory.sizeToString());
        System.out.println("======================");
    }

    public static void ExecLargestFolder(){
        Directory directory = DataBaseDAO.LargestFolder();
        System.out.println("======================");
        System.out.println("Folder: "+ directory.getDirectoryName() + "\nFiles:  " + directory.getDirectoryFiles() + "\nLocated: " + directory.pathToString() + "\nSize: " + directory.sizeToString());
        System.out.println("======================");
    }

    public static void ExecTopFiveFilesBySizes(){
        int i = 1;
        List<HDFile> fileList = DataBaseDAO.TopFiveFilesBySize();
        System.out.println("======================");
        for(HDFile file : fileList) {
            System.out.println(i + ".  Name: " + file.getFileName() + " - FileType: " + file.getFileTypeType().getFileTypeName() + " - Size: " + file.getFileSize() + " - Located: " + file.getFileDirectory().pathToString());
            i++;
        }
        System.out.println("======================");
    }

    public static void ExecAllFilesForType(){
        List<FileType> fileTypeList= new FileTypeDAOImpl().getAllRecords();
        Scanner scanner = new Scanner(System.in);
        int i = 1;

        System.out.println("======================");
        for(FileType fileType : fileTypeList){
            System.out.println(i + ". " + fileType.getFileTypeName());
            i++;
        }
        System.out.print("Select one: ");
        i = scanner.nextInt();

        List<HDFile> fileList = DataBaseDAO.AllFilesForType(i);
        i = 1;
        for(HDFile file : fileList) {
            System.out.println(i + ".  Name: " + file.getFileName() + " - Size: " + file.getFileSize() + "bytes - Located: " + file.getFileDirectory().pathToString());
            i++;
        }
        System.out.println("======================");
    }
}
