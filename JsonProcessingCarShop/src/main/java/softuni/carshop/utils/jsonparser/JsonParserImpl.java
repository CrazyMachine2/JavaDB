package softuni.carshop.utils.jsonparser;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.carshop.utils.fileReader.FileReaderUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class JsonParserImpl implements JsonParser {
    private final Gson gson;
    private final FileReaderUtil fileReaderUtil;

    @Autowired
    public JsonParserImpl(Gson gson, FileReaderUtil fileReaderUtil) {
        this.gson = gson;
        this.fileReaderUtil = fileReaderUtil;
    }

    @Override
    public <O> O parseJson(String path, Class<O> targetClass) throws IOException {
        String content = this.fileReaderUtil.fileContent(path);
        return this.gson.fromJson(content, targetClass);
    }

    @Override
    public <O> void exportToJson(List<O> objects, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        this.gson.toJson(objects,writer);
        writer.close();
    }
}
