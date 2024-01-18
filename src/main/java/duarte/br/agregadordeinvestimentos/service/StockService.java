package duarte.br.agregadordeinvestimentos.service;

import duarte.br.agregadordeinvestimentos.dtos.CreateStockDto;
import duarte.br.agregadordeinvestimentos.model.Stock;
import duarte.br.agregadordeinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createUser(CreateStockDto createStockDto) {
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );
        stockRepository.save(stock);
    }
}
