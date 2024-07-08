package br.edu.ifal.meetingbook.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import br.edu.ifal.meetingbook.entities.booking.IBookingRepository;
import br.edu.ifal.meetingbook.entities.user.IUserRepository;
import br.edu.ifal.meetingbook.entities.user.UserModel;
import br.edu.ifal.meetingbook.entities.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @InjectMocks
    private UserService userService;

    private UserModel userModel;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        userModel = new UserModel();
        userModel.setId(userId);
        userModel.setName("Test User");
        userModel.setUsername("testuser");
        userModel.setEmail("test@example.com");
        userModel.setPassword("password");
    }

    @Test
    public void testCreateOrUpdateUserCreateSuccess() throws Exception {
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel result = userService.createOrUpdateUser(userModel, "POST");

        assertEquals(userModel.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(userModel.getEmail());
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    public void testCreateOrUpdateUserCreateExistingUser() {
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userModel);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.createOrUpdateUser(userModel, "POST");
        });

        assertEquals("O usuário informado já existe", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(userModel.getEmail());
    }

    @Test
    public void testCreateOrUpdateUserUpdateSuccess() throws Exception {
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userModel);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel result = userService.createOrUpdateUser(userModel, "PUT");

        assertEquals(userModel.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(userModel.getEmail());
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    public void testCreateOrUpdateUserUpdateNonExistingUser() {
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.createOrUpdateUser(userModel, "PUT");
        });

        assertEquals("Usuário não existe não existe", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(userModel.getEmail());
    }

    @Test
    public void testListOneUserSuccess() throws Exception {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));

        UserModel result = userService.listOneUser(userId);

        assertEquals(userModel, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testListOneUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.listOneUser(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));
        doNothing().when(userRepository).delete(userModel);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(userModel);
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testListUserBookingsSuccess() throws Exception {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));
        when(bookingRepository.findByUserId(userId)).thenReturn(List.of(new BookingModel()));

        List<BookingModel> bookings = userService.listUserBookings(userId);

        assertNotNull(bookings);
        verify(userRepository, times(1)).findById(userId);
        verify(bookingRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testListUserBookingsUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.listUserBookings(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
}

