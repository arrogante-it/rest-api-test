package com.arroganteIT.rest.controller;

import com.arroganteIT.rest.exception.ValidationException;
import com.arroganteIT.rest.persistance.entity.Tasks;
import com.arroganteIT.rest.service.TasksService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${application.endpoint.task}")
@RequiredArgsConstructor
public class TasksController {

    private final TasksService taskService;

    @GetMapping
    public ResponseEntity<List<Tasks>> getUnassignedTasks() {
        return ResponseEntity.ok().body(taskService.findUnassignedTasks());
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateTask(@RequestBody Tasks task) {
        try {
            String orUpdateTask = taskService.createOrUpdateTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(orUpdateTask);
        }catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getViolations());
        }
    }

    @PatchMapping
    public ResponseEntity<?> assignEmployee(@RequestBody JsonNode jsonNode) {
        try {
            taskService.manageTask(jsonNode);
        }catch(ValidationException e) {
            return ResponseEntity.badRequest().body(e.getViolations());
        }
    }

    public ResponseEntity<?> deleteByUuid(@RequestBody JsonNode jsonNode) {
        taskService.deleteByUuid(jsonNode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



























































