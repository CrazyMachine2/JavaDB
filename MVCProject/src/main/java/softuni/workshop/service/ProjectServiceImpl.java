package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.imports.projects.ProjectImportDto;
import softuni.workshop.domain.dtos.imports.projects.ProjectImportRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;
import softuni.workshop.util.constants.PathImports;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;

    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, FileUtil fileUtil) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
    }


    @Override
    public void importProjects() throws JAXBException {
        ProjectImportRootDto rootDto = this.xmlParser.importXml(PathImports.PROJECTS_PATH,ProjectImportRootDto.class);

        for (ProjectImportDto projectDto : rootDto.getProjects()){
            Project project = this.modelMapper.map(projectDto, Project.class);

            if(!this.validatorUtil.isValid(project)){
                this.validatorUtil.violations(project).forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Company company = this.companyRepository.findCompanyByName(project.getCompany().getName());
            project.setCompany(company);

            this.projectRepository.saveAndFlush(project);
        }
    }


    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
      return this.fileUtil.readFile(PathImports.PROJECTS_PATH);
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder sb = new StringBuilder();
        List<Project> projects = this.projectRepository.findAllByIsFinishedIsTrue();

        for (Project project : projects) {
            sb.append(String.format("Project Name: %s",project.getName())).append(System.lineSeparator());
            sb.append(String.format("   Description: %s", project.getDescription())).append(System.lineSeparator());
            sb.append(String.format("   Payment: %s", project.getPayment())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
