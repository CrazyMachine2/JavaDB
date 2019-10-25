package softuni.carshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.jsondtos.CarSeedDto;
import softuni.carshop.domain.dtos.jsondtos.CarToyotaDto;
import softuni.carshop.domain.dtos.jsondtos.CarWithPartsDto;
import softuni.carshop.domain.dtos.xmldtos.carwithparts.CarExportRootDto;
import softuni.carshop.domain.dtos.xmldtos.carwithparts.CarExportXmlDto;
import softuni.carshop.domain.dtos.xmldtos.carwithparts.PartExportRootDto;
import softuni.carshop.domain.dtos.xmldtos.carwithparts.PartExportXmlDto;
import softuni.carshop.domain.dtos.xmldtos.seed.CarRootDto;
import softuni.carshop.domain.dtos.xmldtos.toyotacars.CarToyotaExportDto;
import softuni.carshop.domain.dtos.xmldtos.toyotacars.CarToyotaExportRootDto;
import softuni.carshop.domain.entities.Car;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.repositories.CarRepository;
import softuni.carshop.repositories.PartRepository;
import softuni.carshop.services.base.CarService;
import softuni.carshop.utils.constants.PathConstantsExport;
import softuni.carshop.utils.constants.PathConstantsImport;
import softuni.carshop.utils.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private List<Integer> usedParts;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.usedParts = new ArrayList<>();
    }

    @Override
    @Transactional
    public List<CarWithPartsDto> getCarWithParts() {
        return this.carRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CarWithPartsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarToyotaDto> getToyotaCars() {
        return this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(c -> this.modelMapper.map(c, CarToyotaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedCars(CarSeedDto[] carSeedDtos) {

        for (CarSeedDto carSeedDto : carSeedDtos) {
            Car car = this.modelMapper.map(carSeedDto, Car.class);
            car.setParts(this.getRandomParts());

            this.carRepository.saveAndFlush(car);
            //TODO: seed parts if necessary
        }
    }

    private Part getRandomPart(){
        Random random = new Random();
        int id = random.nextInt((int) (this.partRepository.count() -1)) +1;
        return this.partRepository.getOne(id);
    }

    private Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();
        Random random = new Random();

        while (parts.size() < 10) {
            for (int i = 0; i <= 20; i++) {
                if (parts.size() >= 20) {
                    break;
                }
                if (random.nextInt() % 4 == 0) {
                    continue;
                }
                Part part = this.getRandomPart();

                if(this.usedParts.contains(part.getId())){
                    continue;
                }

                parts.add(part);
                this.usedParts.add(part.getId());
            }
        }
        this.usedParts.clear();
        return parts;
    }


    /*XML BELOW*/

    @Override
    public void seedCarsXml() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CarRootDto list = (CarRootDto) unmarshaller.unmarshal(new File(PathConstantsImport.CARS_XML_PATH));

        List<Car> cars = list.getCars().stream()
                .map(c -> this.modelMapper.map(c,Car.class))
                .collect(Collectors.toList());

        for (Car car : cars) {
            car.setParts(this.getRandomParts());
        }

        this.carRepository.saveAll(cars);
    }

    @Override
    public void getToyotaCarsXml() throws JAXBException {
        List<CarToyotaExportDto> carDtos = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(c -> this.modelMapper.map(c, CarToyotaExportDto.class))
                .collect(Collectors.toList());

        CarToyotaExportRootDto rootDto = new CarToyotaExportRootDto();
        rootDto.setCars(carDtos);

        this.xmlParser.exportToXml(rootDto,CarToyotaExportRootDto.class, PathConstantsExport.TOYOTA_CARS_XML);
    }

    @Override
    @Transactional
    public void getCarWithPartsXml() throws JAXBException {
        List<CarExportXmlDto> carDtos = new ArrayList<>();

        for (Car car : this.carRepository.findAll()){
            CarExportXmlDto carDto = this.modelMapper.map(car,CarExportXmlDto.class);
            List<PartExportXmlDto> partDtos = new ArrayList<>();

            for (Part part : car.getParts()){
                PartExportXmlDto partDto = this.modelMapper.map(part,PartExportXmlDto.class);
                partDtos.add(partDto);
            }

            PartExportRootDto partRootDto = new PartExportRootDto();
            partRootDto.setParts(partDtos);
            carDtos.add(carDto);
        }

        CarExportRootDto carRootDto = new CarExportRootDto();
        carRootDto.setCars(carDtos);

        this.xmlParser.exportToXml(carRootDto,CarExportRootDto.class,PathConstantsExport.CAR_WITH_PARTS_XML);
    }
}
