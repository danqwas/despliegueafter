package com.afterdrawaing.serviceproject.core.entity;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Size(max = 15)
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Size(max = 15)
    @Column(name = "description", nullable = false)
    private String description;
    //future list de wireframes o mockups
}
