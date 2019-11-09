package com.interrogation.interrogation.controllers;

import com.interrogation.interrogation.model.Poll;
import com.interrogation.interrogation.sevice.PollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.interrogation.interrogation.util.ValidationUtil.assureIdConsistent;
import static com.interrogation.interrogation.util.ValidationUtil.checkNew;

@RestController
@Api( value = "Система управления опросами " , description = "Операции, относящиеся к опросу в системе управления опросами" )
public class PollRestController{

    private PollService pollService;

    public PollRestController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/poll/{id}")
    @ApiOperation(value = "Получить опрос по идентификатору")
    @ApiResponse( code = 404, message = "Нет опроса с таким идентификатором ")
    public Poll get(@PathVariable("id")int id ){
        return pollService.get(id);
    }

    @DeleteMapping("/poll/delete/{id}")
    @ApiOperation( value  =  "Удалить опрос" , response = List.class )
    @ApiResponse( code = 404, message = "Нет опроса с таким идентификатором ")
    public void delete(@PathVariable("id") int id ){
        pollService.delete(id);
    }

    @GetMapping("/polls/{date}")
    @ApiOperation( value = "Просмотреть список всех опросов" , response = List.class )
    @ApiResponses( value = {
            @ApiResponse ( code = 200, message = "Список успешно найден"),
            @ApiResponse( code = 404, message = "Ресурс, который вы пытались получить, не найден")
    })
    public List<Poll> getAll(@PathVariable("date") String val){
        return pollService.getAll(val);
    }

    @PutMapping(value = "/poll/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Обновить опрос")
    public ResponseEntity<Poll> update(@RequestBody Poll poll, @PathVariable("id") int id){
        assureIdConsistent(poll, id);
        pollService.update(poll);
        return ResponseEntity.ok(poll);
    }
    @PostMapping(value = "/poll/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Добавить новый опрос")
    public ResponseEntity<Poll> save(@RequestBody Poll poll){
        checkNew(poll);
        Poll created = pollService.save(poll);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("poll/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);

    }

}
