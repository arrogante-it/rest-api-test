package com.arroganteIT.rest.service;

import com.arroganteIT.rest.exception.ValidationException;
import com.arroganteIT.rest.persistance.EmployeeRepository;
import com.arroganteIT.rest.persistance.TaskRepository;
import com.arroganteIT.rest.persistance.entity.Employee;
import com.arroganteIT.rest.persistance.entity.Task;
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
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final ValidationService<Task> validationService;

    public List<Task> findAllByEmployeesIsNull() {
        return taskRepository.findAllByEmployeeIsNull();
    }

    public String createOrUpdateTask(Task task) throws ValidationException {
        if (validationService.isValid(task)) {
            Task existingTask = taskRepository.findByTaskKey(task.getTaskKey());
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
            Employee byUniqueNumber = employeeRepository.findByUniqueNumber(uniqueNumber);
            Task byTaskKey = taskRepository.findByTaskKey(taskKey);
            if (byUniqueNumber != null && byTaskKey != null) {
                if (byTaskKey.getEmployee() != null) {
                    if (byTaskKey.getEmployee().getUniqueNumber().equals(uniqueNumber)) {
                        byTaskKey.setEmployee(null);
                    }else {
                        throw new ValidationException(List.of(
                                new Violation("Error",
                                "task is already assigned to user")));
                    }
                } else {
                    byTaskKey.setEmployee(byUniqueNumber);
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
        taskRepository.deleteTaskByTaskKey(taskKey);
    }

    private Task updateFields(Task existing, Task upcoming) {
        return existing
                .setTaskName(upcoming.getTaskName())
                .setDescription_task(upcoming.getDescription_task())
                .setDueDate(upcoming.getDueDate());
    }
}










































































