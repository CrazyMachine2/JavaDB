package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }


    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(Constants.DISTRICTS_JSON_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();
        DistrictImportDto[] districtDtos = this.gson.fromJson(districtsFileContent,DistrictImportDto[].class);

        for (DistrictImportDto districtDto : districtDtos) {
            District district = this.modelMapper.map(districtDto, District.class);

            if(!this.validationUtil.isValid(district)){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (this.districtRepository.findDistinctByName(district.getName()).isPresent()) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town town = this.townRepository.findTownByName(districtDto.getTownName()).orElse(null);

            if(town == null){
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            district.setTown(town);
            this.districtRepository.saveAndFlush(district);
            String message = String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName());
            sb.append(message).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
