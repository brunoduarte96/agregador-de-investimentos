package duarte.br.agregadordeinvestimentos.repository;

import duarte.br.agregadordeinvestimentos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
