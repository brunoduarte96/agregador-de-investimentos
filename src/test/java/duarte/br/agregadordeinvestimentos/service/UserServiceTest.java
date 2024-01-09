package duarte.br.agregadordeinvestimentos.service;

import duarte.br.agregadordeinvestimentos.dtos.CreateUserDto;
import duarte.br.agregadordeinvestimentos.dtos.UpdateUserDto;
import duarte.br.agregadordeinvestimentos.model.User;
import duarte.br.agregadordeinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;


    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a new user with sucess")
        void ShouldCreateUser() {
            var user = new User(
                    UUID.randomUUID(),
                    "bruno",
                    "bruno@email.com",
                    "123",
                    Instant.now(), null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("bruno", "bruno@email.com", "321");

            var output = userService.createUser(input);

            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception")
        void shouldThrowException() {
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto("bruno", "bruno@email.com", "321");


            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {
        @Test
        @DisplayName("Should get user by id with sucess")
        void shouldGetUserByIdSucess() {
            var user = new User(
                    UUID.randomUUID(),
                    "bruno",
                    "bruno@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getUserId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with sucess when optional is empty")
        void shouldGetUserByIdSucessWhenOptionIsEmpty() {
            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }


    }

    @Nested
    class listUsers {
        @Test
        @DisplayName("should return all users")
        void shouldListAllUsers() {
            var user = new User(
                    UUID.randomUUID(),
                    "bruno",
                    "bruno@email.com",
                    "123",
                    Instant.now(), null
            );

            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();

            var output = userService.listUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById {
        @Test
        @DisplayName("Should delete user sucess")
        void shouldDeleteUser() {


            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            userService.deleteById(userId.toString());

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));
            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Should not delete user when not exists")
        void shouldNotDeleteUserWhenUserNotExists() {


            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            userService.deleteById(userId.toString());


            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());


        }


    }

    @Nested
    class updateUserById {
        @Test
        @DisplayName("Should update user by id with sucess")
        void udpateById() {
            var updateUserDto = new UpdateUserDto(
                    "maria",
                    "111"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "bruno",
                    "bruno@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.updateUserById(user.getUserId().toString(), updateUserDto);

            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(updateUserDto.username(), userCaptured.getUsername());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }
    }

}