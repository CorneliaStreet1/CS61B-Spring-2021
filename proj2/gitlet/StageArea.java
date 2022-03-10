package gitlet;

import java.io.File;
import java.util.List;

import static gitlet.Repository.CWD;
import static gitlet.Repository.GITLET_DIR;

public class StageArea {
    public static final File Stage = Utils.join(GITLET_DIR, "Stage");
    public static boolean Has(File F,String FileName) {
        if (!Stage.isDirectory() || F.isDirectory() || !F.exists()) {
            throw new IllegalArgumentException(FileName + "is neither a file nor exists");
        }
        else {
            List<String> FilesInCWD = Utils.plainFilenamesIn(CWD);
            if (FilesInCWD == null) {
                return false;
            }
            else {
                for (String str: FilesInCWD) {
                    if (str.equals(FileName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    public boolean hasChanged(File Added, File Existing) {
        return false;
    }
}
