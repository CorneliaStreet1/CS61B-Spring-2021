package gitlet;

import java.io.File;
import java.util.Date;

import static gitlet.Utils.*;
/**
 *  We use a lot of folders to store different type of objects
 *  Details seen in documents above each instance variable
 *  Below are structure:
 *  ————.gitlet
 *     ——Commits
 *     ——Blobs
 *     ——Stage
 *  @author JiangYiqing
 */
public class Repository {
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The folder that stores all commits,
     * There may be many sub-folders inside for speeding up retrieval
     * Each sub-folder will be named after the top two characters of the Commit Hash
     */
    public static final File Commits = Utils.join(GITLET_DIR, "Commits");
    /**
     * The folder that contains all the files we want to save as byte stream
     * Each file will be saved as objects
     * Each file will be named after its Sha-1 Hash value
     */
    public static final File Blobs = Blob.Blobs;
    /**
     * Staging area,used by add command and commit command
     * Staging area should be cleared after commit
     */
    public static final File Stage = StageArea.Stage;
    /**
     * HEAD points to current commit
     */
    public static String HEAD = null;
    /**
     * Set up all folders
     * Write the first commit on disk
     * @param FirstCommit  the First Commit
     */
    public static void SetRepository(Commit FirstCommit) {
        GITLET_DIR.mkdir();
        Commits.mkdir();
        Blobs.mkdir();
        Stage.mkdir();
        String CommitName = FirstCommit.getHashValue();
        HEAD = CommitName;
        File FC = Utils.join(Commits,CommitName);
        Utils.writeObject(FC,FirstCommit);//write the FirstCommit on CWD/Commits/CommitName,Use writeObject() instead of writeContent()
    }
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        else {
            Commit FirstCommit = new Commit("initial commit", new Date(0));
            Repository.SetRepository(FirstCommit);
        }
    }
    public static void add(String FileName) {
        File AddFile = Utils.join(CWD,FileName);
        if (!AddFile.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        else {
            if (StageArea.Has(AddFile, FileName)) {

            }
        }
    }
}
