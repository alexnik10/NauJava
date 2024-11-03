package ru.alex.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.NauJava.entities.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
