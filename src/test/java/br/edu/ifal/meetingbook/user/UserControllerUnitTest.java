package br.edu.ifal.meetingbook.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifal.meetingbook.entities.user.UserController;
import br.edu.ifal.meetingbook.entities.user.UserModel;
import br.edu.ifal.meetingbook.entities.user.UserService;
import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import br.edu.ifal.meetingbook.entities.user.IUserRepository;

public class UserControllerUnitTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        when(userService.createOrUpdateUser(userModel, "POST")).thenReturn(userModel);

        ResponseEntity<Object> response = userController.create(userModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userModel, response.getBody());
    }

    @Test
    public void testCreateUserFailure() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        doThrow(new Exception("Erro ao criar usuário")).when(userService).createOrUpdateUser(userModel, "POST");

        ResponseEntity<Object> response = userController.create(userModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao criar usuário", response.getBody());
    }

    @Test
    public void testListAllUsersSuccess() {
        List<UserModel> users = new ArrayList<>();
        UserModel user1 = new UserModel();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        users.add(user1);

        UserModel user2 = new UserModel();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserModel> response = userController.listAll(null);
        assertEquals(2, response.size());
    }

    @Test
    public void testListAllUsersFailure() {
        doThrow(new RuntimeException("Erro ao listar usuários")).when(userRepository).findAll();

        try {
            userController.listAll(null);
        } catch (Exception e) {
            assertEquals("Erro ao listar usuários", e.getMessage());
        }
    }

    @Test
    public void testListOneUserSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        UserModel userModel = new UserModel();
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        when(userService.listOneUser(userId)).thenReturn(userModel);

        ResponseEntity<Object> response = userController.listOne(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userModel, response.getBody());
    }

    @Test
    public void testListOneUserFailure() throws Exception {
        UUID userId = UUID.randomUUID();

        doThrow(new Exception("Usuário não encontrado")).when(userService).listOneUser(userId);

        ResponseEntity<Object> response = userController.listOne(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateUserSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        
        UserModel existingUser = new UserModel();
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        
        UserModel updatedUser = new UserModel();
        updatedUser.setName("John Smith");
        updatedUser.setEmail("john.smith@example.com");

        when(userService.createOrUpdateUser(updatedUser, "PUT")).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<Object> response = userController.update(updatedUser, null, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserModel responseUser = (UserModel) response.getBody();
        assertEquals("John Smith", responseUser.getName());
    }

    @Test
    public void testUpdateUserFailure() throws Exception {
        UUID userId = UUID.randomUUID();
        
        UserModel updatedUser = new UserModel();
        updatedUser.setName("John Smith");
        updatedUser.setEmail("john.smith@example.com");

        doThrow(new Exception("Erro ao atualizar usuário")).when(userService).createOrUpdateUser(updatedUser, "PUT");

        ResponseEntity<Object> response = userController.update(updatedUser, null, userId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao atualizar usuário", response.getBody());
    }

    @Test
    public void testDeleteUserByIdSuccess() throws Exception {
        UUID userId = UUID.randomUUID();

        ResponseEntity<Object> response = userController.deleteOne(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteUserByIdFailure() throws Exception {
        UUID userId = UUID.randomUUID();
        
        doThrow(new Exception("Usuário não encontrado")).when(userService).deleteUser(userId);

        ResponseEntity<Object> response = userController.deleteOne(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody());
    }

    @Test
    public void testDeleteAllUsersSuccess() {
        ResponseEntity<Void> response = userController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAllUsersFailure() {
        doThrow(new RuntimeException("Erro ao deletar todos os usuários")).when(userRepository).deleteAll();

        try {
            userController.deleteAll();
        } catch (Exception e) {
            assertEquals("Erro ao deletar todos os usuários", e.getMessage());
        }
    }

    @Test
    public void testListUserBookingsSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        List<BookingModel> bookings = new ArrayList<>();

        when(userService.listUserBookings(userId)).thenReturn(bookings);

        ResponseEntity<Object> response = userController.listBooking(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
    }

    @Test
    public void testListUserBookingsFailure() throws Exception {
        UUID userId = UUID.randomUUID();

        doThrow(new Exception("Usuário não encontrado")).when(userService).listUserBookings(userId);

        ResponseEntity<Object> response = userController.listBooking(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody());
    }
}