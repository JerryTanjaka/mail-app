package com.mail.app.file.pdf;

import com.mail.app.entity.Course;
import com.mail.app.entity.User;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class TicketPdfGenerator {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneOffset.UTC);

  public File generate(User user, Course course) {
    var html = toHtml(user, course);
    try {
      var pdfFile = File.createTempFile("ticket-", ".pdf");
      try (var outputStream = new FileOutputStream(pdfFile)) {
        var builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();
      }
      return pdfFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String toHtml(User user, Course course) {
    return """
<html>
  <body style="font-family: Helvetica, Arial, sans-serif; padding: 40px; color: #2c3e50;">
    <h1 style="color: #2c3e50;">Ticket d'inscription</h1>
    <p>Bonjour %s %s,</p>
    <p>Votre inscription au cours suivant est confirmée :</p>
    <table style="border-collapse: collapse; width: 100%%; margin-top: 16px;">
      <tr>
        <td style="padding: 8px; font-weight: bold; border-bottom: 1px solid #ccc;">Cours</td>
        <td style="padding: 8px; border-bottom: 1px solid #ccc;">%s</td>
      </tr>
      <tr>
        <td style="padding: 8px; font-weight: bold; border-bottom: 1px solid #ccc;">Début</td>
        <td style="padding: 8px; border-bottom: 1px solid #ccc;">%s</td>
      </tr>
      <tr>
        <td style="padding: 8px; font-weight: bold;">Fin</td>
        <td style="padding: 8px;">%s</td>
      </tr>
    </table>
    <p style="margin-top: 24px;">Merci de conserver ce ticket, il pourra vous être demandé.</p>
  </body>
</html>
"""
        .formatted(
            user.getFirstName(),
            user.getLastName(),
            course.getTitle(),
            DATE_FORMATTER.format(course.getStartDate()),
            DATE_FORMATTER.format(course.getEndDate()));
  }
}
