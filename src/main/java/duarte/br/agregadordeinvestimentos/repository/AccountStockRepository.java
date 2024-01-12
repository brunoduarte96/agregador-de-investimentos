package duarte.br.agregadordeinvestimentos.repository;

import duarte.br.agregadordeinvestimentos.model.AccountStock;
import duarte.br.agregadordeinvestimentos.model.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
