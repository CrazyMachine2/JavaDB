package soft_uni.jsonproccesing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_uni.jsonproccesing.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u join u.soldProducts p where p.size > 0 and p.buyer is not null order by u.lastName, u.firstName")
    List<User> getSoldProducts();
}
