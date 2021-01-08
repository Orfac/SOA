package ru.orfac.lab2spring.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.orfac.lab2spring.xml.LocalDateTimeAdapter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "SpaceMarine")
@Entity
public class SpaceMarine {
  @JacksonXmlProperty
  @Id
  @GeneratedValue
  private Long id = null;
  //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

  @JacksonXmlProperty
  @NotNull(message = "Spacemarine should have a name")
  @NotBlank(message = "Name cannot be empty")
  private String name;

  @JacksonXmlProperty
  @NotNull
  @Valid
  private Coordinates coordinates; //Поле не может быть null

  @JacksonXmlProperty
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @NotNull
  private LocalDateTime creationDate = LocalDateTime.now();
  //Поле не может быть null, Значение этого поля должно генерироваться автоматически

  @JacksonXmlProperty
  @Min(value = 1, message = "Spacemarine should have their health greater than 0")
  private Long health; //Поле может быть null, Значение поля должно быть больше 0

  @JacksonXmlProperty
  @Min(value = 1, message = "Spacemarine should have their health count greater than 0")
  @Max(value = 3, message = "Spacemarine cannot have more than 3 hearts")
  private Integer heartCount;      //Поле может быть null

  @JacksonXmlProperty
  @Enumerated(EnumType.STRING)
  private AstartesCategory category; //Поле может быть null

  @JacksonXmlProperty
  @Enumerated(EnumType.STRING)
  private MeleeWeapon meleeWeapon; //Поле может быть null

  @JacksonXmlProperty
  @Valid
  private Chapter chapter; //Поле может быть null

  public SpaceMarine() { }

  public SpaceMarine(
      final String name,
      final Coordinates coordinates,
      final Long health,
      final Integer heartCount,
      final AstartesCategory category,
      final MeleeWeapon meleeWeapon,
      final Chapter chapter
  ) {
    this.name = name;
    this.coordinates = coordinates;
    this.health = health;
    this.heartCount = heartCount;
    this.category = category;
    this.meleeWeapon = meleeWeapon;
    this.chapter = chapter;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public Long getHealth() {
    return health;
  }

  public Integer getHeartCount() {
    return heartCount;
  }

  public AstartesCategory getCategory() {
    return category;
  }

  public MeleeWeapon getMeleeWeapon() {
    return meleeWeapon;
  }

  public Chapter getChapter() {
    return chapter;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setCoordinates(final Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public void setCreationDate(final LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public void setHealth(final Long health) {
    this.health = health;
  }

  public void setHeartCount(final Integer heartCount) {
    this.heartCount = heartCount;
  }

  public void setCategory(final AstartesCategory category) {
    this.category = category;
  }

  public void setMeleeWeapon(final MeleeWeapon meleeWeapon) {
    this.meleeWeapon = meleeWeapon;
  }

  public void setChapter(final Chapter chapter) {
    this.chapter = chapter;
  }
}