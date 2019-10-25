package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(Constants.RACES_XML_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceImportRootDto raceDtos = this.xmlParser.parseXml(RaceImportRootDto.class,Constants.RACES_XML_PATH);

        raceDtos:
        for (RaceImportDto raceDto : raceDtos.getRaces()) {
            Race race = this.modelMapper.map(raceDto, Race.class);

            if(!this.validationUtil.isValid(race)){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            District district = this.districtRepository.findDistinctByName(raceDto.getDistrictName()).orElse(null);

            if(district == null){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            List<RaceEntry> entries = new ArrayList<>();

            for(EntryImportDto entryDto : raceDto.getEntries().getEntries()){
                RaceEntry raceEntry = this.raceEntryRepository.getOne(entryDto.getId());

                if(raceEntry == null){
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue raceDtos;
                }
                raceEntry.setRace(race);
                entries.add(raceEntry);
            }

            race.setDistrict(district);
            race.setEntries(entries);
            race = this.raceRepository.saveAndFlush(race);


            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId());
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}