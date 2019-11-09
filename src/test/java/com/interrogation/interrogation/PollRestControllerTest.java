package com.interrogation.interrogation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.interrogation.interrogation.model.Poll;
import com.interrogation.interrogation.sevice.PollServiceImpl;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollRestControllerTest {

    private static final ObjectMapper om = new ObjectMapper();




    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PollServiceImpl mockRepository;

    @BeforeEach
    public void init() {
        Poll poll = new Poll(1, "Program", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2,3), true);
        when(mockRepository.get(1)).thenReturn(poll);
    }

    @Test
    public void get_pollId_OK() throws JSONException {

        String expected = "{id:1,title:\"Program\",startDate:\"2020-02-02\",endDate:\"2020-02-03\",activity:true}";

        ResponseEntity<String> response = restTemplate.getForEntity("/poll/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).get(1);

    }

    @Test
    public void get_allPoll_OK() throws Exception {


        List<Poll> polls = Arrays.asList(new Poll(1,"Work", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 2), false),
                                         new Poll(2,"World", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 2), true));

        when(mockRepository.getAll("title")).thenReturn(polls);
        String expected = om.writeValueAsString(polls);
        ResponseEntity<String> response = restTemplate.getForEntity("/polls/title", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).getAll("title");
    }

//    @Test
//    public void get_pollIdNotFound_404() throws Exception {
//
//
//        ResponseEntity<String> response = restTemplate.getRestTemplate().getForEntity("/poll/112", String.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

//
//    }

    @Test
    public void save_book_OK() throws Exception {

        Poll newPoll = new Poll("Work", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 2), false);

        when(mockRepository.save(Mockito.any(Poll.class))).thenReturn(newPoll);

        String expected = om.writeValueAsString(newPoll);

        ResponseEntity<String> response = restTemplate.postForEntity("/poll/save", newPoll, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).save(Mockito.any(Poll.class));

    }

    @Test
    public void update_poll_OK() throws Exception {

        Poll updatePoll = new Poll(1,"Work", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 2), false);
        doNothing().when(mockRepository).update(updatePoll);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(updatePoll), headers);

        ResponseEntity<String> response = restTemplate.exchange("/poll/update/1", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(om.writeValueAsString(updatePoll), response.getBody(), false);

        verify(mockRepository, times(1)).update(any(Poll.class));


    }



    @Test
    public void delete_poll_OK() {

        doNothing().when(mockRepository).delete(1);

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/poll/delete/1", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(mockRepository, times(1)).delete(1);
    }



}
