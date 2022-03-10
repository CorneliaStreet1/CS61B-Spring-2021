package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.GITLET_DIR;

/**
 * The saved contents of files.
 * The class that represents files in current working directory
 * as CWD/Blobs/SHA-1 Hash of certain file
 */
public class Blob implements Serializable {
    public static final File Blobs = Utils.join(GITLET_DIR, "Blobs");
    /**
     * The saved contents of files.
     */
    File StoredFile;
    public void WriteBlob() {

    }
    public String getHashValue() {
        return Utils.sha1(StoredFile);
    }
}
