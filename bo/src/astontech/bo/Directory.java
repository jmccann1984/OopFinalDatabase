package astontech.bo;

import java.lang.Integer;import java.lang.String;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Set;

/**
 * Created by Joshua.McCann on 6/30/2017.
 */
public class Directory extends BaseBo {
    private int DirectoryId;
    private String DirectoryName;
    private int DirectoryFiles;
    private long DirectorySize;
    private Hashtable<Integer, Directory> DirectoryPath;

    public Directory() {
        this.DirectoryPath = new Hashtable<>();
    }

    public Directory(int directoryId, String directoryName, int directoryFiles, long directorySize){
        this.DirectoryPath = new Hashtable<>();
        this.setDirectoryId(directoryId);
        this.setDirectoryName(directoryName);
        this.setDirectorySize(directorySize);
        this.setDirectoryFiles(directoryFiles);
    }


    public int getDirectoryId() {
        return DirectoryId;
    }

    public void setDirectoryId(int directoryId) {
        this.DirectoryId = directoryId;
    }

    public String getDirectoryName() {
        return DirectoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.DirectoryName = directoryName;
    }

    public long getDirectorySize() {
        return DirectorySize;
    }

    public void setDirectorySize(long directorySize) {
        this.DirectorySize = directorySize;
    }

    public Hashtable getDirectoryPath() {
        return DirectoryPath;
    }

    public void setDirectoryPath(Hashtable<Integer, Directory> directoryPath){
        this.DirectoryPath = directoryPath;
    }

    public int getDirectoryFiles() {
        return DirectoryFiles;
    }

    public void setDirectoryFiles(int directoryFiles) {
        DirectoryFiles = directoryFiles;
    }

    public void addDirectoryPath(Directory directoryPath) {
        this.DirectoryPath.put(DirectoryPath.size(), directoryPath);
    }

    public String pathToString(){
        Set<Integer> keys = this.getDirectoryPath().keySet();
        StringBuilder PathName = new StringBuilder();
        for(int key : keys){
            Directory directory = (Directory) this.getDirectoryPath().get(key);
            PathName.append(directory.getDirectoryName() + "\\");
        }
        return PathName.toString();
    }

    public String sizeToString(){
        DecimalFormat df = new DecimalFormat("#.##");
        if (this.getDirectorySize()<1024){
            return this.getDirectorySize() + " bytes";
        }
        else if (this.getDirectorySize()<(1024*1024))
            return df.format(this.getDirectorySize()/1024.0) + " KB";
        else
            return df.format(this.getDirectorySize()/1024.0/1024.0) + " MB";

    }
}

