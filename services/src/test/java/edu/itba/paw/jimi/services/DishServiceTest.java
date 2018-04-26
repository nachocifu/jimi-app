package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.interfaces.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = DishServiceTestConfig.class)
@ComponentScan({ "edu.itba.paw.jimi.services", "edu.itba.paw.jimi.persistence"})
public class DishServiceTest{

    @Autowired
    @InjectMocks
    private DishService dishService;

    @Autowired
    @Mock
    private DishDao dishDao;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {

        Mockito.when(dishDao.findById(1)).thenReturn(new Dish("Papa", 25F, 1, 1));


        Dish testDish = dishService.findById(1);
        Assert.assertEquals("Papa", testDish.getName());
        Assert.assertEquals(25F, testDish.getPrice(), 0.0001F);
        Assert.assertEquals(1, testDish.getId());
        Assert.assertEquals(1, testDish.getStock());
    }

}
