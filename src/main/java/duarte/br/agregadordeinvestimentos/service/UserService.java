package duarte.br.agregadordeinvestimentos.service;

import duarte.br.agregadordeinvestimentos.dtos.AccountResponseDto;
import duarte.br.agregadordeinvestimentos.dtos.CreateAccountDto;
import duarte.br.agregadordeinvestimentos.dtos.CreateUserDto;
import duarte.br.agregadordeinvestimentos.dtos.UpdateUserDto;
import duarte.br.agregadordeinvestimentos.model.Account;
import duarte.br.agregadordeinvestimentos.model.BillingAddress;
import duarte.br.agregadordeinvestimentos.model.User;
import duarte.br.agregadordeinvestimentos.repository.AccountRepository;
import duarte.br.agregadordeinvestimentos.repository.BillingAddressRepository;
import duarte.br.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();

    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }


    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());

            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }


    }


    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }

    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDto.description(),
                new ArrayList<>()
        );

        var accountSaved = accountRepository.save(account);

        var billingAddres = new BillingAddress(
                accountSaved.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddres);
    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
       return  user.getAccounts()
                .stream()
                .map(account -> new AccountResponseDto(account.getAccountId().toString(), account.getDescription()))
                .toList();

    }
}
