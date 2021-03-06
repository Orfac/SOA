package ru.orfac.marine.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

  private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SS");

  @Override
  public LocalDateTime unmarshal(String xml) throws Exception {
    return dateFormat.parse(xml).toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  @Override
  public String marshal(LocalDateTime object) {
    Date date = Date
        .from(object.atZone(ZoneId.systemDefault())
            .toInstant());
    return dateFormat.format(date);
  }
}
