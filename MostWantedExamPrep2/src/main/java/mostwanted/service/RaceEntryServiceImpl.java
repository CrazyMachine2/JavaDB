package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
    private final RaceEntryRepository raceEntryRepository;
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository, CarRepository carRepository, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.raceEntryRepository = raceEntryRepository;
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(Constants.RACE_ENTRIES_XML_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceEntryImportRootDto raceEntryDtos = this.xmlParser.parseXml(RaceEntryImportRootDto.class,Constants.RACE_ENTRIES_XML_PATH);

        for (RaceEntryImportDto raceEntryDto : raceEntryDtos.getRaceEntries()) {
            RaceEntry raceEntry = this.modelMapper.map(raceEntryDto,RaceEntry.class);

            if(!this.validationUtil.isValid(raceEntry)){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Car car = this.carRepository.findById(raceEntryDto.getCarId()).orElse(null);
            Racer racer = this.racerRepository.findRacerByName(raceEntryDto.getRacer()).orElse(null);

            if(car == null || racer == null){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            raceEntry.setCar(car);
            raceEntry.setRacer(racer);
            raceEntry.setRace(null);
            raceEntry = this.raceEntryRepository.saveAndFlush(raceEntry);

            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId());
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
