package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.ProjectJsonDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final String PROJECT_JSON_PATH = "src\\main\\resources\\files\\jsons\\projects.json";

    private final ProjectRepository projectRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, Gson gson, ModelMapper modelMapper, FileUtil fileUtil) {
        this.projectRepository = projectRepository;

        this.gson = gson;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importProjects() throws JAXBException {
        //TODO
    }


    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        //TODO READ FILE
        return "";
    }

    @Override
    public String exportFinishedProjects() {
        //TODO
        return "";
    }


    @Override
    public void exportProjectToJson() throws IOException {
        List<ProjectJsonDto> projects = this.projectRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProjectJsonDto.class))
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter(PROJECT_JSON_PATH);
        this.gson.toJson(projects, writer);
        writer.close();
    }

    @Override
    public String readProjectJsonFile() throws IOException {

        return this.fileUtil.readFile(PROJECT_JSON_PATH);
    }

    @Override
    public boolean areExported() throws IOException {
        return this.readProjectJsonFile().length() > 0;
    }

    @Override
    public String exportProjectsWithNameEnding() {
        List<Project> projects = this.projectRepository.findAllByNameEndingWith("A");

        StringBuilder sb = new StringBuilder();
        for (Project project : projects) {
            sb.append(String.format("Name: %s", project.getName())).append(System.lineSeparator());
            sb.append(String.format("   Description: %s", project.getDescription())).append(System.lineSeparator());
            sb.append(String.format("   Start Date: %s", project.getStartDate())).append(System.lineSeparator());
            sb.append(String.format("   Is Finished: %s", project.getFinished())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
