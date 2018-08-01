package arouter.cuiqiang.com.baselib.utils.file;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {
    private static String TAG = "Compress";
    private static final int BUFFER = 2048;

    private File[] mFiles;
    private String mZipFile;

    public Compress(File[] files, String zipFile) {
        mFiles = files;
        mZipFile = zipFile;
    }

    public void zip() {
        try {
            FileOutputStream dest = new FileOutputStream(mZipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];
            for (int i = 0; i < mFiles.length; i++) {
                FileInputStream fi = new FileInputStream(mFiles[i]);
                BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(mFiles[i].getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
