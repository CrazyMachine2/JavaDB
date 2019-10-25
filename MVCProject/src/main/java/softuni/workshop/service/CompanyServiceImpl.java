package softuni.workshop.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.imports.companies.CompanyImportDto;
import softuni.workshop.domain.dtos.imports.companies.CompanyImportRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;
import softuni.workshop.util.constants.PathImports;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompanyImportRootDto rootDto = this.xmlParser.importXml(PathImports.COMPANIES_PATH,CompanyImportRootDto.class);

        for (CompanyImportDto companyDto : rootDto.getCompanies()){
            Company company = this.modelMapper.map(companyDto,Company.class);

            if(!this.validatorUtil.isValid(company)){
                this.validatorUtil.violations(company).forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            this.companyRepository.saveAndFlush(company);
        }

    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return this.fileUtil.readFile(PathImports.COMPANIES_PATH);
    }
}
