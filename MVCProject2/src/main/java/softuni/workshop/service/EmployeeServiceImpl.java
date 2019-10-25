package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.EmployeeJsonDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.util.FileUtil;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final String EMPLOYEE_JSON_PATH = "src\\main\\resources\\files\\jsons\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Gson gson, ModelMapper modelMapper, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importEmployees() throws JAXBException {
        //TODO
    }


    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        //TODO READ FILE
        return "";
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        //TODO
        return  "";
    }

    @Override
    public void exportEmployees() throws JAXBException {
        //TODO
    }


    @Override
    public void exportEmployeesToJson() throws IOException {
        List<EmployeeJsonDto> employees = this.employeeRepository.findAll().stream()
                .map(e -> this.modelMapper.map(e, EmployeeJsonDto.class))
                .collect(Collectors.toList());

        FileWriter writer = new FileWriter(EMPLOYEE_JSON_PATH);
        this.gson.toJson(employees,writer);
        writer.close();
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {

        return this.fileUtil.readFile(EMPLOYEE_JSON_PATH);
    }

    @Override
    public boolean areExported() throws IOException {
       return  this.readEmployeesJsonFile().length() > 0;
    }

    @Override
    public String exportEmployeesWithGivenName() {
        List<Employee> employees = this.employeeRepository.findAllByFirstNameAndLastName("Stamat", "Petrov");

        StringBuilder sb = new StringBuilder();
        for (Employee employee : employees) {
            sb.append(String.format("Employee Id: %d", employee.getId())).append(System.lineSeparator());
            sb.append(String.format("Name: %s %s", employee.getFirstName(),employee.getLastName())).append(System.lineSeparator());
        }

        return  sb.toString().trim();
    }

    @Override
    public String exportEmployeesWithGivenProjectName() {
        List<Employee> employees = this.employeeRepository.findAllByProjectName("MMA");

        StringBuilder sb = new StringBuilder();

        for (Employee employee : employees) {
            sb.append(String.format("Project Name: %s", employee.getProject().getName())).append(System.lineSeparator());
            sb.append(String.format("   Employee Name: %s %s", employee.getFirstName(),employee.getLastName())).append(System.lineSeparator());
            sb.append(String.format("   Employee Age: %d", employee.getAge())).append(System.lineSeparator());
        }

        return  sb.toString().trim();
    }
}
