package softuni.carshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.jsondtos.SupplierLocalDto;
import softuni.carshop.domain.dtos.jsondtos.SupplierSeedDto;
import softuni.carshop.domain.dtos.xmldtos.localsuppliers.SupplierLocalExportDto;
import softuni.carshop.domain.dtos.xmldtos.localsuppliers.SupplierLocalRootExportDto;
import softuni.carshop.domain.dtos.xmldtos.seed.SupplierRootDto;
import softuni.carshop.domain.entities.Supplier;
import softuni.carshop.repositories.SupplierRepository;
import softuni.carshop.services.base.SupplierService;
import softuni.carshop.utils.constants.PathConstantsExport;
import softuni.carshop.utils.constants.PathConstantsImport;
import softuni.carshop.utils.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    @Transactional
    public List<SupplierLocalDto> getLocalSuppliers() {
        List<SupplierLocalDto> localDtos = new ArrayList<>();

        for (Supplier supplier : this.supplierRepository.getLocalImporters()){
            SupplierLocalDto localDto = this.modelMapper.map(supplier,SupplierLocalDto.class);
            localDto.setPartsCount(supplier.getParts().size());
            localDtos.add(localDto);
        }

        return localDtos;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
        }
    }

    /*XML BELOW*/

    @Override
    public void seedSupplierXml() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SupplierRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SupplierRootDto list = (SupplierRootDto) unmarshaller.unmarshal(new File(PathConstantsImport.SUPPLIERS_XML_PATH));

        this.supplierRepository.saveAll(list.getSuppliers().stream()
                .map(s -> this.modelMapper.map(s, Supplier.class))
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void getLocalSuppliersXml() throws JAXBException {
       List<SupplierLocalExportDto> supplierDtos = new ArrayList<>();

        for (Supplier supplier: this.supplierRepository.getLocalImporters()) {
                SupplierLocalExportDto exportDto = this.modelMapper.map(supplier,SupplierLocalExportDto.class);
                exportDto.setPartsCount(supplier.getParts().size());
                supplierDtos.add(exportDto);
        }

        SupplierLocalRootExportDto rootDto = new SupplierLocalRootExportDto();
        rootDto.setSuppliers(supplierDtos);

        this.xmlParser.exportToXml(rootDto,SupplierLocalRootExportDto.class, PathConstantsExport.LOCAL_SUPPLIERS_XML);
    }
}
