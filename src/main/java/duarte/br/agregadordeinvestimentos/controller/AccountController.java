package duarte.br.agregadordeinvestimentos.controller;

import duarte.br.agregadordeinvestimentos.dtos.AccountStockReponseDto;
import duarte.br.agregadordeinvestimentos.dtos.AssociateAccountDto;
import duarte.br.agregadordeinvestimentos.dtos.CreateAccountDto;
import duarte.br.agregadordeinvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId, @RequestBody AssociateAccountDto associateAccountDto) {
        accountService.associateStock(accountId, associateAccountDto);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockReponseDto>> associateStock(@PathVariable("accountId") String accountId){
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);

    }


}
