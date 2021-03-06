package ru.orfac.marine.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.orfac.marine.model.AstartesCategory;
import ru.orfac.marine.model.SpaceMarineList;
import ru.orfac.marine.service.MarineService;

@RestController
@RequestMapping("/")
@Validated
public class FeaturesController {
  private final MarineService marineService;

  public FeaturesController(final MarineService marineService) {this.marineService = marineService;}

  @DeleteMapping("/random/{category}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRandomMarine(@PathVariable final AstartesCategory category) {
    marineService.deleteRandomByCategory(category);
  }

  @GetMapping(value = "/compare/{health}", produces = "application/xml")
  public SpaceMarineList getGreaterHp(@PathVariable final Long health) {
    return new SpaceMarineList(marineService.marinesWithHpGreater(health));
  }

  @GetMapping("/health")
  public Long getAllHealth() {
    return marineService.getMarinesHealthSummary();
  }
}
