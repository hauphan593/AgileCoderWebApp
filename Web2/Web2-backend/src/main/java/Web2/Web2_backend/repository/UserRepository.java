package Web2.Web2_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Web2.Web2_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
     
}
