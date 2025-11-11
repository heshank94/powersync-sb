package com.example.powersync_java.repo;

import com.example.powersync_java.jpa.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Heshan Karunaratne
 */
@Repository
public interface TodosRepository extends JpaRepository<Todos, String> {
}
