package com.oracle.studies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.*;

@Component
public class UserRepository implements IUserRepository {

    @Autowired
    IUserRepository repo;

    @Autowired
    EntityManagerFactory emf;

    public IUserRepository getRepo() {
        return repo;
    }

    public void setRepo(IUserRepository repo) {
        this.repo = repo;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public User findEmailByName(String firstname, String lastname) {
        Iterator<User> emailIterator = getRepo().findAll().iterator();
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
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(s);
        tx.commit();

        getEmf().close();

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
        Iterator<User> userIterator = getRepo().findAll().iterator();
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
        EntityManager em = getEmf().createEntityManager();

        Query q = em.createQuery("SELECT * FROM users");
        List<User> list = q.getResultList();

        getEmf().close();

        return list;
    }

    public Iterable<User> findAllById(Iterable<Long> iterable) {
        EntityManager em = getEmf().createEntityManager();

        List<User> resultList = new ArrayList();
        Iterator<Long> iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Long item = iterator.next();
            Query q = em.createQuery("SELECT * FROM users WHERE id="+item);
            resultList.add((User)q.getSingleResult());
        }
        getEmf().close();

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
        EntityManager em = getEmf().createEntityManager();
        Query q = em.createQuery("DELETE FROM users WHERE id="+aLong);
        q.executeUpdate();
        getEmf().close();
    }

    @Transactional
    public void delete(User user) {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(user);
        tx.commit();
        getEmf().close();
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
