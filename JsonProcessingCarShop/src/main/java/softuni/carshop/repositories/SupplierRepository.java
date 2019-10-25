package softuni.carshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.carshop.domain.entities.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    @Query("select s from Supplier as s where s.isImporter = false")
    List<Supplier> getLocalImporters();
}
