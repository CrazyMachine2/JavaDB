package softuni.carshop.services.base;

import softuni.carshop.domain.dtos.jsondtos.CustomerOrderedDto;
import softuni.carshop.domain.dtos.jsondtos.CustomerSeedDto;
import softuni.carshop.domain.dtos.jsondtos.CustomerTotalSalesDto;

import javax.xml.bind.JAXBException;
import java.text.ParseException;
import java.util.List;

public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);
    void seedCustomersXml() throws JAXBException, ParseException;

    List<CustomerOrderedDto> orderedCustomers();
    List<CustomerTotalSalesDto> getCustomerTotalSales();

    void orderedCustomersXml() throws JAXBException;
}
