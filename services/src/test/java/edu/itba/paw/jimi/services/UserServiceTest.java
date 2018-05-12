package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class UserServiceTest {


    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";
    private static Set<String> userRoles;
    private static Set<String> adminRoles;

    @InjectMocks
    @Autowired
    private UserService userService;

    @Autowired
    @Mock
    private UserDao userDao;

    @Before
    public void before(){
        userRoles = new HashSet<String>();
        userRoles.add(User.ROLE_USER);

        adminRoles = new HashSet<String>();
        adminRoles.add(User.ROLE_USER);
        adminRoles.add(User.ROLE_ADMIN);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() {
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRoles(userRoles);
        Mockito.when(userDao.create(USERNAME, PASSWORD, userRoles)).thenReturn(returnUser);

        User user = userService.create(USERNAME, PASSWORD);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());

        assertEquals(1, user.getRoles().size());
        assertEquals(true, user.getRoles().contains(User.ROLE_USER));
    }

    @Test
    public void createAdminTest() {
        User returnUser = new User(USERNAME, 1, PASSWORD);
        returnUser.setRoles(adminRoles);
        Mockito.when(userDao.create(USERNAME, PASSWORD, adminRoles)).thenReturn(returnUser);

        User user = userService.createAdmin(USERNAME, PASSWORD);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());

        assertEquals(2, user.getRoles().size());
        assertEquals(true, user.getRoles().contains(User.ROLE_USER));
        assertEquals(true, user.getRoles().contains(User.ROLE_ADMIN));
    }


    //TODO: findById, findAll, findByUsername.

}
