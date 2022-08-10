package fivesenses.server.fivesenses.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileConvertUtils {

    public static File convertInputStreamToFile(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile(String.valueOf(inputStream.hashCode()), ".tmp");
        tempFile.deleteOnExit();

        copyInputStreamToFile(inputStream, tempFile);

        return tempFile;
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        int read;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }
}
