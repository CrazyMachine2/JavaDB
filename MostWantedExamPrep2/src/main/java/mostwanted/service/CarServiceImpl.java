package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.CARS_JSON_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder sb = new StringBuilder();
        CarImportDto[] carDtos = this.gson.fromJson(carsFileContent,CarImportDto[].class);

        for (CarImportDto carDto : carDtos) {
            Car car = this.modelMapper.map(carDto, Car.class);

            if(!this.validationUtil.isValid(car)){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Racer racer = this.racerRepository.findRacerByName(carDto.getRacerName()).orElse(null);

            if(racer == null){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            car.setRacer(racer);
            this.carRepository.saveAndFlush(car);

            String carInfo = String.format("%s %s @ %d",car.getBrand(),car.getModel(), car.getYearOfProduction());
            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, car.getClass().getSimpleName(), carInfo);
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
