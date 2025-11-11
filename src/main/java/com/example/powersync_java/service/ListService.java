package com.example.powersync_java.service;

import com.example.powersync_java.jpa.Lists;
import com.example.powersync_java.repo.ListsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Heshan Karunaratne
 */
@Service
public class ListService {

    private final ListsRepository repo;

    public ListService(ListsRepository repo) {
        this.repo = repo;
    }

    public void upsertList(Map<String, Object> data) {
        String id = (String) data.get("id");

        Lists list = repo.findById(id).orElseGet(() -> {
            Lists l = new Lists();
            l.setId(id);
            return l;
        });

        if (data.containsKey("created_at"))
            list.setCreatedAt(parseDate(data.get("created_at")));

        if (data.containsKey("name"))
            list.setName((String) data.get("name"));

        if (data.containsKey("owner_id"))
            list.setOwnerId((String) data.get("owner_id"));

        repo.save(list);
    }

    public void updateList(Map<String, Object> data) {
        String id = (String) data.get("id");
        Lists list = repo.findById(id).orElseThrow();

        if (data.containsKey("created_at"))
            list.setCreatedAt(parseDate(data.get("created_at")));

        if (data.containsKey("name"))
            list.setName((String) data.get("name"));

        if (data.containsKey("owner_id"))
            list.setOwnerId((String) data.get("owner_id"));

        repo.save(list);
    }

    public void deleteList(Map<String, Object> data) {
        repo.deleteById((String) data.get("id"));
    }

    private LocalDateTime parseDate(Object obj) {
        if (obj == null) return null;
        return LocalDateTime.parse(obj.toString());
    }
}
