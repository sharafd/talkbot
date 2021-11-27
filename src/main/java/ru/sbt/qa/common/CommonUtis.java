package ru.sbt.qa.common;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.slf4j.LoggerFactory.getLogger;

public class CommonUtis {
    /**
     * Логирование.
     */
    private static final Logger LOG = getLogger(CommonUtis.class);

    private CommonUtis() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Содержит ли файл BOM.
     * @see <a htrf='https://mkyong.com/java/java-how-to-add-and-remove-bom-from-utf-8-file'>Java – How to add and remove BOM from UTF-8 file</a>
     *
     * @param path путь к файлу
     * @return boolean
     * @throws IOException
     */
    private static boolean isContainBOM(Path path) throws IOException {

        if (Files.notExists(path)) {
            throw new IllegalArgumentException("Path: " + path + " does not exists!");
        }

        boolean result = false;

        byte[] bom = new byte[3];
        try (InputStream is = new FileInputStream(path.toFile())) {
            // read 3 bytes of a file.
            is.read(bom); //NOSONAR
            // BOM encoded as ef bb bf
            String content = new String(Hex.encodeHex(bom));
            if ("efbbbf".equalsIgnoreCase(content)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Убрать BOM из файла.
     * @see <a htrf='https://mkyong.com/java/java-how-to-add-and-remove-bom-from-utf-8-file'>Java – How to add and remove BOM from UTF-8 file</a>
     *
     * @param path путь к файлу
     * @throws IOException
     */
    public static void removeBom(Path path) throws IOException {

        if (isContainBOM(path)) {

            byte[] bytes = Files.readAllBytes(path);
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            byte[] bom = new byte[3];
            // get the first 3 bytes
            bb.get(bom, 0, bom.length);
            // remaining
            byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
            bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

            LOG.warn("Found BOM! Remove the first 3 bytes, and overwrite the file.");
            // override the same path
            Files.write(path, contentAfterFirst3Bytes);
        }
    }

}
