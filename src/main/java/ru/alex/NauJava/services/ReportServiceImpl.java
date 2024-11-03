package ru.alex.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.NauJava.entities.Contact;
import ru.alex.NauJava.entities.Report;
import ru.alex.NauJava.enums.ReportStatus;
import ru.alex.NauJava.repositories.ContactRepository;
import ru.alex.NauJava.repositories.ReportRepository;
import ru.alex.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, ContactRepository contactRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Optional<Report> getReportById(Long reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED, "");
        report = reportRepository.save(report);
        return report.getId();
    }

    @Override
    public CompletableFuture<Void> generateReport(Long reportId, int delayInSeconds) {
        return CompletableFuture.runAsync(() -> {
            Report report = reportRepository.findById(reportId).orElseThrow();
            report.setStatus(ReportStatus.COMPLETED);
            long startTime = System.currentTimeMillis();
            try {
                Thread.sleep(delayInSeconds * 1000L);
                AtomicLong userCount = new AtomicLong();
                AtomicLong userElapsedTime = new AtomicLong();
                Thread userCountThread = new Thread(() -> {
                    long userStartTime = System.currentTimeMillis();
                    userCount.set(userRepository.count());
                    userElapsedTime.set(System.currentTimeMillis() - userStartTime);
                    System.out.println("Time for user count: " + userElapsedTime + " ms");
                });

                AtomicReference<List<Contact>> contacts = new AtomicReference<>();
                AtomicLong contactElapsedTime = new AtomicLong();
                Thread contactThread = new Thread(() -> {
                    long contactStartTime = System.currentTimeMillis();
                    contacts.set((List<Contact>) contactRepository.findAll());
                    contactElapsedTime.set(System.currentTimeMillis() - contactStartTime);
                    System.out.println("Time for contacts retrieval: " + contactElapsedTime + " ms");
                });

                userCountThread.start();
                contactThread.start();
                userCountThread.join();
                contactThread.join();

                long totalElapsedTime = System.currentTimeMillis() - startTime;

                String content = generateHtmlReport(userCount.get(),
                        contacts.get(),
                        userElapsedTime.get(),
                        contactElapsedTime.get(),
                        totalElapsedTime);

                report.setContent(content);
            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                e.printStackTrace();
            } finally {
                reportRepository.save(report);
            }
        });
    }

    private String generateHtmlReport(Long userCount, List<Contact> contacts, long userElapsedTime, long contactElapsedTime, long totalElapsedTime) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Report</h1>");
        html.append("<p>Number of users: ").append(userCount).append("</p>");
        html.append("<p>Time for user count: ").append(userElapsedTime).append(" ms</p>");

        html.append("<h2>Contacts:</h2>");
        html.append("<table border='1' cellpadding='5' cellspacing='0'>");
        html.append("<tr>")
                .append("<th>First Name</th>")
                .append("<th>Last Name</th>")
                .append("<th>Middle Name</th>")
                .append("<th>Birthday</th>")
                .append("</tr>");

        for (Contact contact : contacts) {
            html.append("<tr>")
                    .append("<td>").append(contact.getFirstName()).append("</td>")
                    .append("<td>").append(contact.getLastName()).append("</td>")
                    .append("<td>").append(contact.getMiddleName() != null ? contact.getMiddleName() : "").append("</td>")
                    .append("<td>").append(contact.getBirthday() != null ? contact.getBirthday().toString() : "").append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        html.append("<p>Time for contacts retrieval: ").append(contactElapsedTime).append(" ms</p>");
        html.append("<p>Total time for report generation: ").append(totalElapsedTime).append(" ms</p>");
        html.append("</body></html>");
        return html.toString();
    }
}
