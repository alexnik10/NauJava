package ru.alex.NauJava.entities;

import jakarta.persistence.*;
import ru.alex.NauJava.enums.ReportStatus;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Lob
    private String content;

    public Report(ReportStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public Report() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
