package softuni.carshop.utils.jsonparser;

import java.io.IOException;
import java.util.List;

public interface JsonParser {

    <O> O parseJson(String path, Class<O> targetClass) throws IOException;
    <O> void exportToJson(List<O> objects, String path) throws IOException;
}
