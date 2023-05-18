package com.arroganteIT.rest.persistance;

import com.arroganteIT.rest.persistance.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

    Tasks findByTasksKey(String taskUuid);
    List<Tasks> findAllByEmployeesIsNull();
    void deleteTasksByTasksKey(String taskKey);
}

































