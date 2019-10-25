package softuni.carshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.jsondtos.PartSeedDto;
import softuni.carshop.domain.dtos.xmldtos.seed.PartRootDto;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.domain.entities.Supplier;
import softuni.carshop.repositories.PartRepository;
import softuni.carshop.repositories.SupplierRepository;
import softuni.carshop.services.base.PartService;
import softuni.carshop.utils.constants.PathConstantsImport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedParts(PartSeedDto[] partSeedDtos) {
        for (PartSeedDto partSeedDto : partSeedDtos) {
            Part part = this.modelMapper.map(partSeedDto,Part.class);
            part.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(part);

            //TODO: seed suppliers set if needed
        }
    }

    @Override
    public void seedPartsXML() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PartRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PartRootDto list = (PartRootDto) unmarshaller.unmarshal(new File(PathConstantsImport.PARTS_XML_PATH));

        List<Part> parts = list.getParts().stream()
                .map(p -> this.modelMapper.map(p, Part.class))
                .collect(Collectors.toList());

        for (Part part : parts) {
            part.setSupplier(this.getRandomSupplier());
        }

        this.partRepository.saveAll(parts);
    }

    private Supplier getRandomSupplier(){
        Random random = new Random();
        int id = random.nextInt((int) (this.supplierRepository.count() -1)) +1;
        return this.supplierRepository.getOne(id);
    }
}
