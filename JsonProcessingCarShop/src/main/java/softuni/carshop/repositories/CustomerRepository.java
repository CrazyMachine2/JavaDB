package softuni.carshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.carshop.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer as c order by c.birthDate asc, c.isYoungDriver desc")
    List<Customer> findOrderedCustomers();

    @Query("select c from Customer as c where c.sales.size > 0")
    List<Customer> getAllSalesFromCustomer();
}
