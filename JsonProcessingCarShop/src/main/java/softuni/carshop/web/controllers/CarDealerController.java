package softuni.carshop.web.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.carshop.domain.dtos.jsondtos.*;
import softuni.carshop.services.base.*;
import softuni.carshop.utils.fileReader.FileReaderUtil;
import softuni.carshop.utils.fileReader.FileReaderUtilImpl;
import softuni.carshop.utils.constants.PathConstantsExport;
import softuni.carshop.utils.constants.PathConstantsImport;
import softuni.carshop.utils.jsonparser.JsonParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
public class CarDealerController implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    private final Gson gson; // You don't need this
    private final FileReaderUtil fileReaderUtil; // You don't need this

    private final JsonParser jsonParser;

    @Autowired
    public CarDealerController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, Gson gson, FileReaderUtil fileReaderUtil, JsonParser jsonParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
        this.fileReaderUtil = fileReaderUtil;
        this.jsonParser = jsonParser;
    }

    @Override
    public void run(String... args) throws Exception {

        /*JSON*/
//        seedSuppliers();
//        seedParts();
//        seedCars();
//        seedCustomers();
//        seedSales();

//        orderedCustomers();
//        getToyotaCars();
//        getLocalSuppliers();
//        getCarWithParts();
//        getCustomerTotalSales();

        /*XML*/
//        seedSuppliersXml();
//        seedPartsXml();
//        seedCarsXml();
//        seedCustomersXml();
//        seedSales();

//        this.customerService.orderedCustomersXml();
//        this.carService.getToyotaCarsXml();
//        this.supplierService.getLocalSuppliersXml();
//        this.carService.getCarWithPartsXml();
    }

/*JSON*/

    private void seedSuppliers() throws IOException {

        SupplierSeedDto[] supplierSeedDtos = this.jsonParser.parseJson(PathConstantsImport.SUPPLIERS_JSON_PATH,SupplierSeedDto[].class);

//        String content = this.fileReaderUtil.fileContent(PathConstantsImport.SUPPLIERS_JSON_PATH);
//        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        this.supplierService.seedSuppliers(supplierSeedDtos);
    }

    private void seedParts() throws IOException {
        String content = this.fileReaderUtil.fileContent(PathConstantsImport.PARTS_JSON_PATH);
        PartSeedDto[] supplierSeedDtos = this.gson.fromJson(content, PartSeedDto[].class);
        this.partService.seedParts(supplierSeedDtos);
    }

    private void seedCars() throws IOException {
        String content = this.fileReaderUtil.fileContent(PathConstantsImport.CARS_JSON_PATH);
        CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);
        this.carService.seedCars(carSeedDtos);
    }

    private void seedCustomers() throws IOException {
        String content = this.fileReaderUtil.fileContent(PathConstantsImport.CUSTOMERS_JSON_PATH);
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content,CustomerSeedDto[].class);
        this.customerService.seedCustomers(customerSeedDtos);
    }

    private void seedSales(){
        this.saleService.seedSales();
    }

    private void orderedCustomers() throws IOException {
        List<CustomerOrderedDto> customerOrderedDtos = this.customerService.orderedCustomers();

        FileWriter fileWriter = new FileWriter(PathConstantsExport.ORDERED_CUSTOMERS);
        this.gson.toJson(customerOrderedDtos,fileWriter);
        fileWriter.close();
    }

    private void getToyotaCars() throws IOException {
        List<CarToyotaDto> carToyotaDtos = this.carService.getToyotaCars();

        this.jsonParser.exportToJson(carToyotaDtos,PathConstantsExport.TOYOTA_CARS);

//        FileWriter fileWriter = new FileWriter(PathConstantsExport.TOYOTA_CARS);
//        this.gson.toJson(carToyotaDtos,fileWriter);
//        fileWriter.close();
    }

    private void getLocalSuppliers() throws IOException {
        List<SupplierLocalDto> localSuppliersDtos = this.supplierService.getLocalSuppliers();
        FileWriter fileWriter = new FileWriter(PathConstantsExport.LOCAL_SUPPLIERS);
        this.gson.toJson(localSuppliersDtos,fileWriter);
        fileWriter.close();
    }

    private void getCarWithParts() throws IOException {
        List<CarWithPartsDto> carWithParts = this.carService.getCarWithParts();
        FileWriter fileWriter = new FileWriter(PathConstantsExport.CAR_WITH_PARTS);
        this.gson.toJson(carWithParts,fileWriter);
        fileWriter.close();
    }

    private void getCustomerTotalSales() throws IOException {
        List<CustomerTotalSalesDto> customerTotalSales = this.customerService.getCustomerTotalSales();
        FileWriter fileWriter = new FileWriter(PathConstantsExport.CUSTOMER_TOTAL_SALES);
        this.gson.toJson(customerTotalSales, fileWriter);
        fileWriter.close();
    }

    /*XML BELOW*/

    private void seedSuppliersXml() throws JAXBException, FileNotFoundException {
        this.supplierService.seedSupplierXml();
    }

    private void seedPartsXml() throws JAXBException {
        this.partService.seedPartsXML();
    }

    private void seedCarsXml() throws JAXBException {
        this.carService.seedCarsXml();
    }

    private void seedCustomersXml() throws JAXBException, ParseException {
        this.customerService.seedCustomersXml();
    }
}
