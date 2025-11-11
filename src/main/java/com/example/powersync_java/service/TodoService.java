package com.example.powersync_java.service;

import com.example.powersync_java.jpa.Todos;
import com.example.powersync_java.repo.TodosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Heshan Karunaratne
 */
@Service
public class TodoService {

    private final TodosRepository repo;

    public TodoService(TodosRepository repo) {
        this.repo = repo;
    }

    public void upsertTodo(Map<String, Object> data) {

        String id = (String) data.get("id");

        Todos todo = repo.findById(id).orElseGet(() -> {
            Todos t = new Todos();
            t.setId(id);
            return t;
        });

        if (data.containsKey("description"))
            todo.setDescription((String) data.get("description"));

        if (data.containsKey("created_by"))
            todo.setCreatedBy((String) data.get("created_by"));

        if (data.containsKey("list_id"))
            todo.setListId((String) data.get("list_id"));

        if (data.containsKey("completed"))
            todo.setCompleted((Boolean) data.get("completed"));

        if (data.containsKey("completed_by"))
            todo.setCompletedBy((String) data.get("completed_by"));

        if (data.containsKey("completed_at"))
            todo.setCompletedAt(parseDate(data.get("completed_at")));

        repo.save(todo);
    }

    public void updateTodo(Map<String, Object> data) {
        String id = (String) data.get("id");
        Todos todo = repo.findById(id).orElseThrow();

        if (data.containsKey("description"))
            todo.setDescription((String) data.get("description"));

        if (data.containsKey("created_by"))
            todo.setCreatedBy((String) data.get("created_by"));

        if (data.containsKey("list_id"))
            todo.setListId((String) data.get("list_id"));

        if (data.containsKey("completed"))
            todo.setCompleted((Boolean) data.get("completed"));

        if (data.containsKey("completed_by"))
            todo.setCompletedBy((String) data.get("completed_by"));

        if (data.containsKey("completed_at"))
            todo.setCompletedAt(parseDate(data.get("completed_at")));

        repo.save(todo);
    }

    public void deleteTodo(Map<String, Object> data) {
        String id = (String) data.get("id");
        repo.deleteById(id);
    }

    private LocalDateTime parseDate(Object obj) {
        if (obj == null) return null;
        return LocalDateTime.parse(obj.toString());
    }
}
