package com.arroganteIT.rest.service;

import com.arroganteIT.rest.exception.ValidationException;
import com.arroganteIT.rest.persistance.EmployeesRepository;
import com.arroganteIT.rest.persistance.TasksRepository;
import com.arroganteIT.rest.persistance.entity.Employees;
import com.arroganteIT.rest.persistance.entity.Tasks;
import com.arroganteIT.rest.validation.ValidationService;
import com.arroganteIT.rest.validation.Violation;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.arroganteIT.rest.utils.FieldUtils.getValueFromJsonNode;

@Service
@RequiredArgsConstructor
@Transactional
public class TasksService {

    private final TasksRepository taskRepository;
    private final EmployeesRepository employeeRepository;
    private final ValidationService<Tasks> validationService;

    public List<Tasks> findUnassignedTasks() {
        return taskRepository.findAllUnassignedTasks();
    }

    public String createOrUpdateTask(Tasks task) throws ValidationException {
        if (validationService.isValid(task)) {
            Tasks existingTask = taskRepository.findByTasksKey(task.getTaskKey());
            if (existingTask == null) {
                task.setCreatedDate(LocalDate.now());
                taskRepository.save(task);
            } else {
                taskRepository.save(updateFields(existingTask, task));
            }
        }
        return task.getTaskKey();
    }

    public void manageTask(JsonNode jsonNode) throws ValidationException{
        String taskKey = getValueFromJsonNode(jsonNode, "taskKey");
        String uniqueNumber = getValueFromJsonNode(jsonNode, "uniqueNumber");

        if (taskKey != null && uniqueNumber != null) {
            Employees byUniqueNumber = employeeRepository.findByUniqueNumber(uniqueNumber);
            Tasks byTaskKey = taskRepository.findByTasksKey(taskKey);
            if (byUniqueNumber != null && byTaskKey != null) {
                if (byTaskKey.getEmployees() != null) {
                    if (byTaskKey.getEmployees().getUniqueNumber().equals(uniqueNumber)) {
                        byTaskKey.setEmployees(null);
                    }else {
                        throw new ValidationException(List.of(
                                new Violation("Error",
                                "task is already assigned to user")));
                    }
                } else {
                    byTaskKey.setEmployees(byUniqueNumber);
                }
                taskRepository.save(byTaskKey);
            } else {
                throw new ValidationException(
                        List.of(new Violation("Not such task or employee found",
                                String.format("Task: %s, Employee: %s",
                                        byTaskKey, byUniqueNumber)))
                );
            }
        } else {
            throw new ValidationException(
                    List.of(
                            new Violation("Error",
                            "taskKye or employee uniqueNumber should not be blank"))
            );
        }
    }

    public void deleteByTaskKey(JsonNode jsonNode) {
        String taskKey = getValueFromJsonNode(jsonNode, "taskKey");
        taskRepository.deleteTasksByTasksKey(taskKey);
    }

    private Tasks updateFields(Tasks existing, Tasks upcoming) {
        return existing
                .setTaskName(upcoming.getTaskName())
                .setDescription(upcoming.getDescription())
                .setDueDate(upcoming.getDueDate());
    }
}










































































