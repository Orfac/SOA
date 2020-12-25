package ru.orfac.lab2_extra.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SpaceShip {

  @Id private Long id;

  private String name;

  private String marineIds;

  public SpaceShip(final String name, final String marineIds) {
    this.name = name;
    this.marineIds = marineIds;
    this.id = 0L;
  }

  public SpaceShip(final String name) {
    this.name = name;
    marineIds = "";
  }

  public SpaceShip() {

  }

  public Long getId() {
    return id;
  }

  public String getMarineIds() {
    return marineIds;
  }

  public String getName() {
    return name;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setMarineIds(final String marineIds) {
    this.marineIds = marineIds;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
