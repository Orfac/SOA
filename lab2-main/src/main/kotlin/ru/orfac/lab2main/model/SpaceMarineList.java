package ru.orfac.lab2main.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@JacksonXmlRootElement(localName = "SpaceMarineCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineList{

  @JacksonXmlProperty(localName = "SpaceMarine")
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<SpaceMarine> marines;

  public SpaceMarineList(final List<SpaceMarine> marines) {this.marines = marines;}
  public SpaceMarineList(){}

  public List<SpaceMarine> getMarines() {
    return marines;
  }

  public void setMarines(final List<SpaceMarine> marines) {
    this.marines = marines;
  }
}

