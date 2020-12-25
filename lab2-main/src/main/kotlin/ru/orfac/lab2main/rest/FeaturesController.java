package ru.orfac.lab2main.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.orfac.lab2main.model.AstartesCategory;
import ru.orfac.lab2main.model.SpaceMarine;
import ru.orfac.lab2main.model.SpaceMarineList;
import ru.orfac.lab2main.service.MarineService;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    final AtomicReference<Long> summaryHealth = new AtomicReference<>(0L);
    List<SpaceMarine> marineList = marineService.getMarines();
    marineService.getMarines().forEach((it) -> {
      if (it.getHealth() != null) {
        summaryHealth.updateAndGet(v -> v + it.getHealth());
      }
    });
    return summaryHealth.get();
  }
}
