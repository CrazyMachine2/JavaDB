package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.TOWNS_JSON_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder sb = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);

        for (TownImportDto townImportDto : townImportDtos) {
            Town town = this.modelMapper.map(townImportDto, Town.class);

            if (!this.validationUtil.isValid(town)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (this.townRepository.findTownByName(town.getName()).isPresent()) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.townRepository.saveAndFlush(town);
            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName());
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(this.townRepository.getAllRacingTowns()).forEach(o -> {
            Object[] info = (Object[]) o;
            sb.append(String.format("Name: %s", info[0])).append(System.lineSeparator());
            sb.append(String.format("Size: %s", info[1])).append(System.lineSeparator());
            sb.append(System.lineSeparator());
        });

//        Object[] racingTowns = this.townRepository.getAllRacingTowns();
//
//        for (Object o1 : racingTowns) {
//            Object[] info = (Object[]) o1;
//
//           sb.append(String.format("Name: %s", info[0])).append(System.lineSeparator());
//           sb.append(String.format("Size: %s", info[1])).append(System.lineSeparator());
//           sb.append(System.lineSeparator());
//        }

        return sb.toString().trim();
    }
}
