package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl implements RacerService {
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.RACERS_JSON_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder sb = new StringBuilder();
        RacerImportDto[] racerDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);

        for (RacerImportDto racerDto : racerDtos) {
            Racer racer = this.modelMapper.map(racerDto, Racer.class);

            if (!this.validationUtil.isValid(racer)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (this.racerRepository.findRacerByName(racer.getName()).isPresent()) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town town = this.townRepository.findTownByName(racerDto.getHomeTown()).orElse(null);

            if (town == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            racer.setHomeTown(town);
            this.racerRepository.saveAndFlush(racer);
            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racer.getName());
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();

        this.racerRepository.findAllRacingCars()
                .forEach(r -> {
                    sb.append(String.format("Name: %s%n", r.getName()));

                    if(r.getAge() != null){
                        sb.append(String.format("Age: %d%n", r.getAge()));
                    }

                    sb.append("Cars: ").append(System.lineSeparator());
                    r.getCars().forEach(c ->
                            sb.append(String.format(" %s %s %d", c.getBrand(), c.getModel(), c.getYearOfProduction()))
                                    .append(System.lineSeparator()));
                    sb.append(System.lineSeparator());
                });

        return sb.toString().trim();
    }
}
