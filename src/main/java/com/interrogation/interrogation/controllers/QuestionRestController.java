package com.interrogation.interrogation.controllers;

import com.interrogation.interrogation.model.Poll;
import com.interrogation.interrogation.model.Question;
import com.interrogation.interrogation.sevice.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.interrogation.interrogation.util.ValidationUtil.assureIdConsistent;
import static com.interrogation.interrogation.util.ValidationUtil.checkNew;

@RestController
@Api( value = "Система управления вопросами в опросе " , description = "Операции, относящиеся к вопросам в системе управления вопросами в опросе" )
public class QuestionRestController {

    private QuestionService questionService;

    public QuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/{questionId}/poll/{pollId}")
    @ApiOperation(value = "Получить вопрос по идентификатору вопроса и опроса")
    public Question get(@PathVariable("questionId") int id,@PathVariable("pollId") int pollId ){
        return questionService.get(id, pollId);
    }
    @DeleteMapping("/question/delete/{questionId}/poll/{pollId}")
    @ApiOperation(value = "Удалить вопрос в опросе")
    public  @ResponseBody void delete(@PathVariable("questionId") int questionId, @PathVariable("pollId") int pollId ){
        questionService.delete(questionId, pollId);
    }

    @PutMapping(value = "/question/{questionId}/poll/{pollId}")
    @ApiOperation(value = "Обновить вопрос")
    public ResponseEntity<Question> update(@RequestBody Question question, @PathVariable("questionId") int questionId, @PathVariable("pollId") int pollId){
        assureIdConsistent(question, questionId);
        questionService.update(question, pollId);
        return ResponseEntity.ok(question);

    }

    @GetMapping("/questions/{pollId}")
    @ApiOperation( value = "Просмотреть список всех вопросов в опросе" , response = List.class )
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Список успешно найден"),
            @ApiResponse( code = 404, message = "Ресурс, который вы пытались получить, не найден")
    })
    public List<Question> getAll(@PathVariable("pollId") int pollId){
        return questionService.getAll(pollId);
    }

    @PostMapping(value = "/question/save/poll/{pollId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Сохранить вопрос")
    public ResponseEntity<Question> save(@RequestBody Question question, @PathVariable("pollId") int pollId){
        checkNew(question);
        Question created = questionService.save(question, pollId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("question/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);

    }
}
