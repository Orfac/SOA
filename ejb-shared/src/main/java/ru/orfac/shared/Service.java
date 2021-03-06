package ru.orfac.shared;

import javax.ejb.EJBException;
import javax.ejb.Remote;
import java.util.List;

public interface Service {
   public void create(Long id,  String name ) throws RequestHandlingException, EJBException;
   public void update(Long id,  Long marineId ) throws RequestHandlingException, EJBException;
   public List<String> get() throws EJBException;
}