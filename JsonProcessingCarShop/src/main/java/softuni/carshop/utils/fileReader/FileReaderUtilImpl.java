package softuni.carshop.utils.fileReader;

import java.io.*;

public class FileReaderUtilImpl implements FileReaderUtil {
    @Override
    public String fileContent(String path) throws IOException {
        StringBuilder sb = new StringBuilder();

        File file = new File(path);
        BufferedReader bf = new BufferedReader(new FileReader(file));

        String line;

        while ((line = bf.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
