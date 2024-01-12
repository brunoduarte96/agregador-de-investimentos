package duarte.br.agregadordeinvestimentos.repository;

import duarte.br.agregadordeinvestimentos.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
