package com.arroganteIT.rest.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Accessors(chain=true)
@Getter
@Setter
@Table(indexes = @Index(name = "unique_number_idx", columnList = "uniqueNumber"))
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @NotBlank
    @Column(unique = true)
    private String uniqueNumber;
    private String firstName;
    private String lastName;
    private String department;
    private Double salary;
    private LocalDate hired;

    @OneToMany(mappedBy = "employee")
    List<Task> tasks = new ArrayList<>();
}









































