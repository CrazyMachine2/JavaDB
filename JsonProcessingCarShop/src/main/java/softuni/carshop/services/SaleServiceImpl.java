package softuni.carshop.services;

import org.springframework.stereotype.Service;
import softuni.carshop.domain.entities.Car;
import softuni.carshop.domain.entities.Customer;
import softuni.carshop.domain.entities.Sale;
import softuni.carshop.repositories.CarRepository;
import softuni.carshop.repositories.CustomerRepository;
import softuni.carshop.repositories.SaleRepository;
import softuni.carshop.services.base.SaleService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private Random random;
    private List<Integer> usedCars;
    private List<Integer> discounts;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.random = new Random();
        this.usedCars = new ArrayList<>();
        this.discounts = Arrays.asList(0,5,10,15,20,30,40,50);
    }

    @Override
    @Transactional
    public void seedSales() {

        for (int i = 0; i < 50; i++) {
            Sale sale = new Sale();
            sale.setCar(this.getRandomCar());
            sale.setCustomer(this.getRandomCustomer());
            sale.setDiscountPercentage(this.getRandomDiscount(sale.getCustomer()));

            this.saleRepository.saveAndFlush(sale);
        }
    }

    private Customer getRandomCustomer(){
        int id = this.random.nextInt((int) (this.customerRepository.count() -1)) +1;
        return this.customerRepository.getOne(id);
    }

    private Car getRandomCar() {
        int id = this.random.nextInt((int) (this.carRepository.count() -1)) +1;

        while (this.usedCars.contains(id)){
            id = this.random.nextInt((int) (this.carRepository.count() -1)) +1;
        }

        this.usedCars.add(id);
        return this.carRepository.getOne(id);
    }

    private Integer getRandomDiscount(Customer customer) {
        int index = this.random.nextInt(this.discounts.size());

        Integer discount = this.discounts.get(index);

        if(customer.isYoungDriver()){
            discount+=5;
        }

        return discount;
    }
}
