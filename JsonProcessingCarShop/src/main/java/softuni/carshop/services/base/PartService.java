package softuni.carshop.services.base;

import softuni.carshop.domain.dtos.jsondtos.PartSeedDto;

import javax.xml.bind.JAXBException;

public interface PartService {
    void seedParts(PartSeedDto[] partSeedDtos);
    void seedPartsXML() throws JAXBException;
}
