package ru.orfac.marine.rest;



import ru.orfac.marine.model.SpaceMarine;
import ru.orfac.marine.model.SpaceMarineList;
import java.util.Map;

public interface MarineApi {
  void addMarine(SpaceMarine marine);
  SpaceMarineList marines(Map<String, String> parameters);
  SpaceMarine getMarine(Long id);
  void updateMarine(Long id, SpaceMarine marine);
  void deleteMarine(Long id);
}
