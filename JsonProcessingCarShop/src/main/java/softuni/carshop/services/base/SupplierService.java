package softuni.carshop.services.base;

import softuni.carshop.domain.dtos.jsondtos.SupplierLocalDto;
import softuni.carshop.domain.dtos.jsondtos.SupplierSeedDto;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface SupplierService {
    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);
    List<SupplierLocalDto> getLocalSuppliers();

    void seedSupplierXml() throws JAXBException, FileNotFoundException;
    void getLocalSuppliersXml() throws JAXBException;
}
