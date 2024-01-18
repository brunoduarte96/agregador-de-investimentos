package duarte.br.agregadordeinvestimentos.controller;

import duarte.br.agregadordeinvestimentos.dtos.AccountResponseDto;
import duarte.br.agregadordeinvestimentos.dtos.CreateAccountDto;
import duarte.br.agregadordeinvestimentos.dtos.CreateUserDto;
import duarte.br.agregadordeinvestimentos.dtos.UpdateUserDto;
import duarte.br.agregadordeinvestimentos.model.User;
import duarte.br.agregadordeinvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByid(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId, @RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccounts(@PathVariable("userId") String userId) {
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);

    }


}
