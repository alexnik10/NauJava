package ru.alex.NauJava.services;

import ru.alex.NauJava.entities.Report;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ReportService {
     Optional<Report> getReportById(Long reportId);

     Long createReport();

     CompletableFuture<Void> generateReport(Long reportId, int delayInSeconds);
}
