package ru.gameserver.dao;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gameserver.controller.LogController;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DaoInsertImplTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LogController controller;

    @Test
    public void registration() {

    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        String port="8085";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/registration",Integer.class,"asd")).isNotNull();
    }


    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void authorization() {
    }
}
