package astontech.bo;

import java.lang.String; /**
 * Created by Joshua.McCann on 6/30/2017.
 */
public class HDFile {
    private int FileId;
    private String FileName;
    private FileType FileTypeType;
    private long FileSize;
    private Directory FileDirectory;

    public HDFile() {
        this.FileTypeType = new FileType();
        this.FileDirectory = new Directory();
    }

    public HDFile(int fileId, String fileName, FileType fileTypeType, long fileSize, Directory fileDirectory){
        this.FileTypeType = new FileType();
        this.FileDirectory = new Directory();
        this.setFileName(fileName);
        this.setFileSize(fileSize);
        this.setFileDirectory(fileDirectory);
        this.setFileId(fileId);
        this.setFileTypeType(fileTypeType);
    }

    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        this.FileId = fileId;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        this.FileName = fileName;
    }

    public FileType getFileTypeType() {
        return FileTypeType;
    }

    public void setFileTypeType(FileType fileTypeType) {
        this.FileTypeType = fileTypeType;
    }

    public long getFileSize() {
        return FileSize;
    }

    public void setFileSize(long fileSize) {
        this.FileSize = fileSize;
    }

    public Directory getFileDirectory() {
        return FileDirectory;
    }

    public void setFileDirectory(Directory fileDirectory) {
        this.FileDirectory = fileDirectory;
    }
}
