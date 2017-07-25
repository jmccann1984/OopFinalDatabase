package astontech.dao.mysql;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public abstract class Procedures {
    final static public String GetDirectoryPrep = "{ call GetDirectory(?,?,?) }";
    final static public String GetFilePrep = "{ call GetFile(?,?,?) }";
    final static public String GetFileTypePrep = "{ call GetFileType(?,?,?) }";

    final static public String ExecDirectoryPrep = "{ call ExecDirectory(?,?,?,?,?,?) }";
    final static public String ExecFilePrep = "{ call ExecFile(?,?,?,?,?,?) }";
    final static public String ExecFileTypePrep = "{ call ExecFileType(?,?,?) }";


    final static public String RefreshTables =  "{ call ExecAll }";
    final static public String MostFiles = "select DirectoryId from directory order by DirectoryFiles desc limit 1;";
    final static public String LargestFolder = "select DirectoryId from directory order by DirectorySize desc limit 1;";
    final static public String TopFiveFilesBySize = "select a.FileId from file as a order by a.FileSize desc limit 5;";
    final static public String AllFilesForType = "select a.FileId from file as a where a.FileTypeId = ?;";


}