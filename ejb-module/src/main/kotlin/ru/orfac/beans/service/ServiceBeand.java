//package ru.orfac.beans.service;
//
//import static java.util.Collections.emptyList;
//import ru.orfac.beans.client.ClientApi;
//import ru.orfac.beans.config.EntityManagerConfig;
//import ru.orfac.beans.model.SpaceShip;
//import ru.orfac.beans.utils.UtilsKt;
//import ru.orfac.shared.RequestHandlingException;
//import ru.orfac.shared.Service;
//import javax.ejb.Remote;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Stateless(name = "MarineService")
//@Remote(Service.class)
//public class ServiceBean implements Service {
//  private ClientApi clientApi = ClientApi.INSTANCE;
//
//  private String table = "SpaceShip";
//
//  private EntityManager entityManager = EntityManagerConfig.INSTANCE.getEntityManager();
//
//  @Override
//  public void create(final Long id, final String name) throws RequestHandlingException {
//    checkNameContainsEnglishLettersOnly(name);
//    checkShipIsNotExisted(id);
//
//    SpaceShip spaceShip = new SpaceShip(name);
//    spaceShip.setId(id);
//
//    save(spaceShip);
//  }
//
//  @Override
//  public void update(final Long id, final Long marineId) throws RequestHandlingException {
//    SpaceShip spaceShip = findSpaceShip(id);
//    List<Long> marineIds = parseMarineIds(spaceShip.getMarineIds());
//
//    checkMarineIsNotLoadedBefore(marineId, marineIds);
//    checkMarineExists(marineId);
//
//    if (marineIds.isEmpty()) {
//      spaceShip.setMarineIds(marineId.toString());
//    } else {
//      spaceShip.setMarineIds(spaceShip.getMarineIds() + ",$marineId");
//    }
//    save(spaceShip);
//  }
//
//  @Override
//  public List<String> get() {
//    List<SpaceShip> list = (List<SpaceShip>) entityManager
//        .createQuery("SELECT e FROM " + table + " e").getResultList();
//    return UtilsKt.sortToString(list);
//  }
//
//  private SpaceShip findSpaceShip(Long id) throws RequestHandlingException {
//    SpaceShip spaceShip = entityManager.find(SpaceShip.class, id);
//    if (spaceShip == null) {
//      throw new RequestHandlingException(String.format("Spaceship with id %d doesn't exist", id));
//    } else {
//      return spaceShip;
//    }
//  }
//
//  private void checkShipIsNotExisted(Long id) throws RequestHandlingException {
//    SpaceShip existedSpaceShip = entityManager.find(SpaceShip.class, id);
//    if (existedSpaceShip != null) {
//      throw new RequestHandlingException("Ship with id + " + id.toString() + " already existed");
//    }
//  }
//
//  private void checkNameContainsEnglishLettersOnly(String name) throws RequestHandlingException {
//    if (!UtilsKt.isEnglishAlphabet(name)) {
//      throw new RequestHandlingException("Ship name should contain only english alphabet letters");
//    }
//  }
//
//  private List<Long> parseMarineIds(String value) {
//    if (value.isEmpty()) return emptyList();
//    return Arrays.stream(value.split(",")).map(Long::parseLong).collect(Collectors.toList());
//  }
//
//  private void save(SpaceShip spaceShip) {
//    entityManager.getTransaction().begin();
//    entityManager.persist(spaceShip);
//    entityManager.getTransaction().commit();
//  }
//
//  private void checkMarineIsNotLoadedBefore(
//      Long marineId,
//      List<Long> marineIds
//  ) throws RequestHandlingException {
//    if (marineIds.contains(marineId)) {
//      throw new RequestHandlingException("Marine is already loaded");
//    }
//  }
//
//  private void checkMarineExists(Long marineId ) throws RequestHandlingException {
//    if (!clientApi.checkMarine(marineId)) {
//      throw new RequestHandlingException("Marine was not found");
//    }
//  }
//}
