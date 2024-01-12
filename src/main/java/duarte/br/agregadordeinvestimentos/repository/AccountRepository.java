package duarte.br.agregadordeinvestimentos.repository;

import duarte.br.agregadordeinvestimentos.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
