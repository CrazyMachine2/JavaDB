package softuni.carshop.configurations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.carshop.utils.fileReader.FileReaderUtil;
import softuni.carshop.utils.fileReader.FileReaderUtilImpl;
import softuni.carshop.utils.jsonparser.JsonParser;
import softuni.carshop.utils.jsonparser.JsonParserImpl;
import softuni.carshop.utils.xmlparser.XmlParser;
import softuni.carshop.utils.xmlparser.XmlParserImpl;

@Configuration
public class AppBeansConfiguration {

    @Bean
    public FileReaderUtil fileReaderUtil() {
        return new FileReaderUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

    @Bean
    public JsonParser jsonParser() {
        return new JsonParserImpl(this.gson(), this.fileReaderUtil());
    }
}
