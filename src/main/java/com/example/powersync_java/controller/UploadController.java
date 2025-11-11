package com.example.powersync_java.controller;

import com.example.powersync_java.request.MutationRequest;
import com.example.powersync_java.service.ListService;
import com.example.powersync_java.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Heshan Karunaratne
 */
@RestController
@RequestMapping("/api")
public class UploadController {

    private final TodoService todoService;
    private final ListService listService;

    public UploadController(TodoService todoService, ListService listService) {
        this.todoService = todoService;
        this.listService = listService;
    }

    @PutMapping("/upload_data/")
    public ResponseEntity<?> put(@RequestBody MutationRequest req) {
        return handle(req, "PUT");
    }

    @PatchMapping("/upload_data/")
    public ResponseEntity<?> patch(@RequestBody MutationRequest req) {
        return handle(req, "PATCH");
    }

    @DeleteMapping("/upload_data/")
    public ResponseEntity<?> delete(@RequestBody MutationRequest req) {
        return handle(req, "DELETE");
    }

    private ResponseEntity<?> handle(MutationRequest req, String method) {
        System.out.println("Calling upload api");
        switch (req.getTable()) {

            case "todos":
                return handleTodos(req.getData(), method);

            case "lists":
                return handleLists(req.getData(), method);

            default:
                return ResponseEntity.badRequest().body("Unknown table");
        }
    }

    private ResponseEntity<?> handleTodos(Map<String, Object> data, String method) {
        switch (method) {
            case "PUT" -> todoService.upsertTodo(data);
            case "PATCH" -> todoService.updateTodo(data);
            case "DELETE" -> todoService.deleteTodo(data);
        }
        return ResponseEntity.ok("Todo " + method.toLowerCase() + " successful");
    }

    private ResponseEntity<?> handleLists(Map<String, Object> data, String method) {
        switch (method) {
            case "PUT" -> listService.upsertList(data);
            case "PATCH" -> listService.updateList(data);
            case "DELETE" -> listService.deleteList(data);
        }
        return ResponseEntity.ok("List " + method.toLowerCase() + " successful");
    }
}
