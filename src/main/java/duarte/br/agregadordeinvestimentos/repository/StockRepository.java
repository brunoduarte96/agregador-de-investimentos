package duarte.br.agregadordeinvestimentos.repository;

import duarte.br.agregadordeinvestimentos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, String> {
}
