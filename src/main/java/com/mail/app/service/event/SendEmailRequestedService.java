package com.mail.app.service.event;

import com.mail.app.endpoint.event.model.SendEmailRequested;
import com.mail.app.file.pdf.TicketPdfGenerator;
import com.mail.app.mail.Email;
import com.mail.app.mail.Mailer;
import com.mail.app.repository.CourseRepository;
import com.mail.app.repository.UserRepository;
import jakarta.mail.internet.InternetAddress;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailRequestedService implements Consumer<SendEmailRequested> {
  private final Mailer mailer;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final TicketPdfGenerator ticketPdfGenerator;

  @SneakyThrows
  @Override
  public void accept(SendEmailRequested sendEmailRequested) {
    var user = userRepository.findById(sendEmailRequested.getUserId()).orElseThrow();
    var course = courseRepository.findById(sendEmailRequested.getCourseId()).orElseThrow();

    var ticketPdf = ticketPdfGenerator.generate(user, course);
    try {
      InternetAddress recipientAddress = new InternetAddress(sendEmailRequested.getTo());
      mailer.accept(
          new Email(
              recipientAddress,
              List.of(),
              List.of(),
              "Votre ticket d'inscription - " + course.getTitle(),
              "Bonjour " + user.getFirstName() + ", veuillez trouver votre ticket en pièce jointe.",
              List.of(ticketPdf)));
    } finally {
      Files.deleteIfExists(ticketPdf.toPath());
    }
  }
}
