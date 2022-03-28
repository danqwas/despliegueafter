package com.afterdrawaing.serviceproject.core.repository;

import com.afterdrawaing.serviceproject.core.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}