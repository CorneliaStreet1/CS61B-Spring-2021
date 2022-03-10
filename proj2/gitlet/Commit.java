package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  does at a high level.
 *  We use Sha-1 Hash Value of a commit as its FileName
 *  @author JiangYiqing
 */
public class Commit implements Serializable{
    /** The message of this Commit. */
    private String message;
    /**The Date the commit was made*/
    private Date CommitDate;
    /**We only Support two branches*/
    private List<String> Parents;
    /**The map between filename and blob id*/
    private Map<String,String> FileToBlob;
    public Commit(String message, Date CommitDate) {
        this.message = message;
        this.CommitDate = CommitDate;
        Parents = new ArrayList<>();
        FileToBlob = new HashMap<>();
    }
    public String getMessage() {
        return this.message;
    }
    public List<String> getParent() {
        return this.Parents;
    }
    public Date getCommitDate() {
        return this.CommitDate;
    }
    public String getHashValue() {
        return Utils.sha1(this.CommitDate.toString(),this.message,this.Parents.toString());
    }

    /**
     * Take SHA-1 Hash Value of Parent commits as parameters
     * @param Parents a series of Parent Commit ID
     */
    public void setParents(String... Parents) {
        for (String s: Parents) {
            this.Parents.add(s);
        }
    }

    /**
     * add a pair of FileName-BlobHashValue map
     * @param FileName The name of the file
     * @param HashVal  The SHA-1 Hash value of corresponding blob
     */
    public void setMap(String FileName, String HashVal) {
        FileToBlob.put(FileName,HashVal);
    }

}
