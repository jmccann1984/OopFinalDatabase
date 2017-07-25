package astontech.bo;

/**
 * Created by Joshua.McCann on 6/30/2017.
 */
public class FileType {
    private int FileTypeId;
    private String FileTypeName;

    public FileType() {}

    public FileType(int fileTypeId,String fileTypeName){
        this.setFileTypeId(fileTypeId);
        this.setFileTypeName(fileTypeName);
    }

    public int getFileTypeId() {
        return FileTypeId;
    }

    public void setFileTypeId(int fileTypeId) {
        this.FileTypeId = fileTypeId;
    }

    public String getFileTypeName() {
        return FileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.FileTypeName = fileTypeName;
    }
}
