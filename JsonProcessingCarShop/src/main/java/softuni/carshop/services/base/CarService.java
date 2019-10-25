package softuni.carshop.services.base;

import softuni.carshop.domain.dtos.jsondtos.CarSeedDto;
import softuni.carshop.domain.dtos.jsondtos.CarToyotaDto;
import softuni.carshop.domain.dtos.jsondtos.CarWithPartsDto;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);
    void seedCarsXml() throws JAXBException;

    List<CarToyotaDto> getToyotaCars();
    List<CarWithPartsDto> getCarWithParts();

    void getToyotaCarsXml() throws JAXBException;
    void getCarWithPartsXml() throws JAXBException;
}
