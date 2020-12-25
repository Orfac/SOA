package ru.orfac.lab2main.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.orfac.lab2main.model.SpaceMarine;
import ru.orfac.lab2main.model.SpaceMarineList;
import ru.orfac.lab2main.service.MarineService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@RequestMapping("/marines")
@Validated
public class MarinesController {

  private final MarineService marineService;

  public MarinesController(final MarineService marineService) {this.marineService = marineService;}

  @PostMapping(consumes = "application/xml")
  @ResponseStatus(HttpStatus.CREATED)
  public void addMarine(@RequestBody @Valid SpaceMarine marine) {
    marineService.saveNew(marine);
  }

  @GetMapping(produces = "application/xml")
  public SpaceMarineList marines(@RequestParam Map<String,String> parameters) {
    return new SpaceMarineList(marineService.getMarines(parameters));
  }

  @GetMapping(value = "/{id}", produces = "application/xml")
  public SpaceMarine getMarine(
      @PathVariable @Min(value = 1, message = "id should be greater or equal to 1") Long id
  ) {
    return marineService.findById(id);
  }

  @PutMapping(value = "/{id}", consumes = "application/xml")
  public void updateMarine(
      @PathVariable @Min(value = 1, message = "id should be greater or equal to 1") Long id,
      @RequestBody @Valid SpaceMarine marine
  ) {
    marineService.updateMarineById(id, marine);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteMarine(
      @PathVariable @Min(value = 1, message = "id should be greater or equal to 1") Long id
  ) {
    marineService.deleteMarineById(id);
  }
}