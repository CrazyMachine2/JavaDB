package softuni.workshop.service;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.CompanyJsonDto;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final String JSON_EXPORT_PATH = "src\\main\\resources\\files\\jsons\\companies.json";


    private final CompanyRepository companyRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, Gson gson, ModelMapper modelMapper, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCompanies() throws JAXBException {
        //TODO
    }

    @Override
    public boolean areImported() {
       return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        //TODO READ FILE
        return "";
    }


    @Override
    public void exportJsonCompanies() throws IOException {
        List<CompanyJsonDto> companies = this.companyRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c,CompanyJsonDto.class))
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter(JSON_EXPORT_PATH);
        this.gson.toJson(companies,writer);
        writer.close();
    }

    @Override
    public String readCompaniesJsonFile() throws IOException {
        return this.fileUtil.readFile(JSON_EXPORT_PATH);
    }

    @Override
    public boolean areExported() throws IOException {
       return this.readCompaniesJsonFile().length() > 0;
    }
}
