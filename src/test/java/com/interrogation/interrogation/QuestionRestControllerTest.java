package com.interrogation.interrogation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interrogation.interrogation.sevice.QuestionServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionRestControllerTest {

    private static final ObjectMapper om = new ObjectMapper();




    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private QuestionServiceImpl mockRepository;
}
