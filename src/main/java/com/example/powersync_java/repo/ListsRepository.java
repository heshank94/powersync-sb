package com.example.powersync_java.repo;

import com.example.powersync_java.jpa.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Heshan Karunaratne
 */
@Repository
public interface ListsRepository extends JpaRepository<Lists, String> {
}