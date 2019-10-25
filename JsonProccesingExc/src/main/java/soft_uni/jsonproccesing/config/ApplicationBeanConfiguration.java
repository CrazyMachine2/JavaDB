package soft_uni.jsonproccesing.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soft_uni.jsonproccesing.util.FileUtil;
import soft_uni.jsonproccesing.util.FileUtilImpl;
import soft_uni.jsonproccesing.util.ValidatorUtil;
import soft_uni.jsonproccesing.util.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public FileUtilImpl fileUtil(){
        return new FileUtilImpl();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtilImpl validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
