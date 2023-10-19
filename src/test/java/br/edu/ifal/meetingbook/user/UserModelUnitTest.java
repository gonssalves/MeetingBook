package br.edu.ifal.meetingbook.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserModelUnitTest {
    
    @Test
    public void testUserModelConstructor() {
        UserModel user = new UserModel("Kanye West", "kimye", "kimye@gmail.com", "1234", "Cliente");

        assertEquals("Kanye West", user.getName());
        assertEquals("kimye", user.getUsername());
        assertEquals("kimye@gmail.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("Cliente", user.getType());
    }

    @Test
    public void testUserModelSetters() {
        UserModel user = new UserModel(null, null, null, null, null);

        user.setName("Kanye West");
        user.setUsername("kimye");
        user.setEmail("kimye@gmail.com");
        user.setPassword("1234");
        user.setType("Cliente");
        
        assertEquals("Kanye West", user.getName());
        assertEquals("kimye", user.getUsername());
        assertEquals("kimye@gmail.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("Cliente", user.getType());
    }
}
