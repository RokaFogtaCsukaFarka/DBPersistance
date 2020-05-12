package com.oracle.studies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserRepository {

    @Autowired
    IUserRepository repo;
    @Autowired
    EntityManagerFactory emf;

    public User findEmailByName(String firstname, String lastname) {
        Iterator<User> emailIterator = repo.findAll().iterator();
        while(emailIterator.hasNext()) {
            User item = emailIterator.next();
            if (item.getFirstname().equals(firstname) &&
                    item.getLastname().equals(lastname)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public <S extends User> S save(S s) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(s);
        tx.commit();

        emf.close();

        return s;
    }

    @Transactional
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        Iterator it = iterable.iterator();
        while(it.hasNext()) {
            save(((S)it.next()));
        }
        return iterable;
    }

    public Optional<User> findById(Long aLong) {
        Iterator<User> userIterator = repo.findAll().iterator();
        while(userIterator.hasNext()) {
            User item = userIterator.next();
            if (item.getId().equals(aLong)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    public Iterable<User> findAll() {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("SELECT * FROM users");
        List<User> list = q.getResultList();

        emf.close();

        return list;
    }

    public Iterable<User> findAllById(Iterable<Long> iterable) {
        EntityManager em = emf.createEntityManager();

        List<User> resultList = new ArrayList();
        Iterator<Long> iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Long item = iterator.next();
            Query q = em.createQuery("SELECT * FROM users WHERE id="+item);
            resultList.add((User)q.getSingleResult());
        }
        emf.close();

        return resultList;
    }

    public long count() {
        Iterator<User> iter = findAll().iterator();
        long num = 0L;
        while(iter.hasNext()) {
            num++;
        }
        return num;
    }

    @Transactional
    public void deleteById(Long aLong) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("DELETE FROM users WHERE id="+aLong);
        q.executeUpdate();
        emf.close();
    }

    @Transactional
    public void delete(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(user);
        tx.commit();
        emf.close();
    }

    @Transactional
    public void deleteAll(Iterable<? extends User> iterable) {
        Iterator iter = iterable.iterator();
        while(iter.hasNext()) {
            deleteById(((Email)iter.next()).getId());
        }
    }

    @Transactional
    public void deleteAll() {
        deleteAll(findAll());
    }

}
