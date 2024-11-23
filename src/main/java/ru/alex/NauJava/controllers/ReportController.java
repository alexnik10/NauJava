package ru.alex.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.entities.Report;
import ru.alex.NauJava.enums.ReportStatus;
import ru.alex.NauJava.services.ReportService;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createReport(@RequestParam(defaultValue = "0") int delayInSeconds) {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId, delayInSeconds);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<String> getReportContent(@PathVariable Long reportId) {
        Optional<Report> reportOptional = reportService.getReportById(reportId);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();

            if (report.getStatus() == ReportStatus.CREATED) {
                return ResponseEntity.ok("Отчет находится в процессе формирования.");
            } else if (report.getStatus() == ReportStatus.ERROR) {
                return ResponseEntity.ok("Ошибка при формировании отчета.");
            } else if (report.getStatus() == ReportStatus.COMPLETED) {
                return ResponseEntity.ok(report.getContent());
            }
        }

        return ResponseEntity.notFound().build();
    }
}
