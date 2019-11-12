package com.interrogation.interrogation;
import com.interrogation.interrogation.model.Poll;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interrogation.interrogation.model.Question;
import com.interrogation.interrogation.sevice.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionRestControllerTest {

    private static final ObjectMapper om = new ObjectMapper();



    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private QuestionServiceImpl mockRepository;

    @BeforeEach
    public void init(){

        Question question = new Question(1, "Сколько планет в нашей солнечной системе?", "1");

        when(mockRepository.get(1, 1)).thenReturn(question);
    }

    @Test
    void getQuestion() throws Exception {
        mockMvc.perform(get("/question/1/poll/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.questionText", is("Сколько планет в нашей солнечной системе?")))
                .andExpect(jsonPath("$.display", is("1")));

    }

    @Test
    void getAll() throws Exception {
        Poll poll = new Poll(1, "Program", LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2,3), true);
        Question question = new Question(1, "Сколько пальцев у обезьяны на лапе?","1");
        Question question1 = new Question(2, "Сколько планет в нашей солнечной системе?","2");
        question.setPoll(poll);
        question1.setPoll(poll);
        List<Question> questionList =  Arrays.asList(question, question1);


        when(mockRepository.getAll(1)).thenReturn(questionList);

        mockMvc.perform(get("/questions/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].questionText", is("Сколько пальцев у обезьяны на лапе?")))
                .andExpect(jsonPath("$[0].display", is("1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].questionText", is("Сколько планет в нашей солнечной системе?")))
                .andExpect(jsonPath("$[1].display", is("2")));
        verify(mockRepository, times(1)).getAll(1);
    }
    @Test
    void notFoundById() throws Exception{
        mockMvc.perform(get("question/5/poll/1/")).andExpect(status().isNotFound());
    }
    @Test
    void update() throws Exception {
        Question questionUpdate = new Question(1, "Сколько?","1");
        doNothing().when(mockRepository).update(questionUpdate, 1);
        mockMvc.perform(put("/question/1/poll/1/")
                .content(om.writeValueAsString(questionUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.questionText", is("Сколько?")))
                .andExpect(jsonPath("$.display", is("1")));

    }

    @Test
    void save() throws Exception {


        Question questionSave = new Question("Сколько пальцев у волка на лапе?","1");

        when(mockRepository.save(any(Question.class),eq(1))).thenReturn(questionSave);
        mockMvc.perform(post("/question/save/poll/1")
                .content(om.writeValueAsString(questionSave))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.questionText", is("Сколько пальцев у волка на лапе?")))
                .andExpect(jsonPath("$.display", is("1")));
        verify(mockRepository, times(1)).save(any(Question.class), eq(1));
    }
}
