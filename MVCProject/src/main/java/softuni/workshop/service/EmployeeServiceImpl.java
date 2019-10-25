package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.imports.employees.EmployeeImportDto;
import softuni.workshop.domain.dtos.imports.employees.EmployeeImportRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeImportRootDto rootDto = this.xmlParser.importXml(PathImports.EMPLOYEES_PATH,EmployeeImportRootDto.class);

        for(EmployeeImportDto employeeDto : rootDto.getEmployees()){
            Employee employee = this.modelMapper.map(employeeDto, Employee.class);

            if(!this.validatorUtil.isValid(employee)){
                this.validatorUtil.violations(employee).forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Project project = this.projectRepository.findProjectByName(employee.getProject().getName());
            employee.setProject(project);

            this.employeeRepository.saveAndFlush(employee);
        }
    }


    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(PathImports.EMPLOYEES_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder sb = new StringBuilder();
        List<Employee> employees = this.employeeRepository.findAlByAgeGreaterThan(25);

        for (Employee employee : employees) {
            sb.append(String.format("Name: %s %s",employee.getFirstName(), employee.getLastName())).append(System.lineSeparator());
            sb.append(String.format("   Age: %s", employee.getAge())).append(System.lineSeparator());
            sb.append(String.format("   Project name: %s", employee.getProject().getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
