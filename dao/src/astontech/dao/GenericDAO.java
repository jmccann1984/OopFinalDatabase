package astontech.dao;

import java.util.List;


/**
 * Created by Joshua.McCann on 6/30/2017.
 */
public interface GenericDAO {

    //region GET METHODS
    public Object getRecordById(int recordId);
    public Object getRecordByName(String recordName);
    public List getAllRecords();

    //endregion

    //region EXECUTE METHODS
    public int insertRecord(Object object);
    public boolean updateRecord(Object object);
    public boolean deleteRecord(int recordId);
    //endregion
}
