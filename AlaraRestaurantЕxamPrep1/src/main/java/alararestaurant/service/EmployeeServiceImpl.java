package alararestaurant.service;

import alararestaurant.domain.dtos.importDtos.employee.EmployeeDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.constants.ImportConstants;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(ImportConstants.EMPLOYEES_JSON);
    }

    @Override
    public String importEmployees(String employees) throws IOException {
        StringBuilder sb = new StringBuilder();

        String content = this.readEmployeesJsonFile();
        EmployeeDto[] employeeDtos = this.gson.fromJson(content, EmployeeDto[].class);

        for (EmployeeDto employeeDto : employeeDtos) {
            Employee employee = this.modelMapper.map(employeeDto,Employee.class);
            Position position = this.positionRepository.findPositionByName(employeeDto.getPosition()).orElse(null);

            if(position == null){
                position = new Position();
                position.setName(employeeDto.getPosition());

                if(!this.validationUtil.isValid(position)){
                    String message = new ArrayList<>(this.validationUtil.violations(position)).get(0).getMessage();
                    sb.append(message).append(System.lineSeparator());
                    continue;
                }
            }

            employee.setPosition(position);

            if(!this.validationUtil.isValid(employee)){
                String message = new ArrayList<>(this.validationUtil.violations(employee)).get(0).getMessage();
                sb.append(message).append(System.lineSeparator());
                continue;
            }

            this.employeeRepository.saveAndFlush(employee);
            sb.append(String.format("Record %s successfully imported.",employee.getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
