package softuni.carshop.utils.fileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileReaderUtil {
    String fileContent(String path) throws IOException;
}
