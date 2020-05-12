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

@Transactional
public class EmailRepository implements IEmailRepository
{
    @Autowired
    IEmailRepository repo;
    @Autowired
    EntityManagerFactory emf;

    public Email findEmailByName(String firstname, String lastname) {
        Iterator<Email> emailIterator = repo.findAll().iterator();
        while(emailIterator.hasNext()) {
            Email item = emailIterator.next();
            if (item.getFirstname().equals(firstname) &&
                    item.getLastname().equals(lastname)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public <S extends Email> S save(S s) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(s);
        tx.commit();

        emf.close();

        return s;
    }

    @Transactional
    public <S extends Email> Iterable<S> saveAll(Iterable<S> iterable) {
        Iterator it = iterable.iterator();
        while(it.hasNext()) {
            save(((S)it.next()));
        }
        return iterable;
    }

    public Optional<Email> findById(Long aLong) {
        Iterator<Email> emailIterator = repo.findAll().iterator();
        while(emailIterator.hasNext()) {
            Email item = emailIterator.next();
            if (item.getId().equals(aLong)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    public Iterable<Email> findAll() {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("SELECT * FROM emails");
        List<Email> list = q.getResultList();

        emf.close();

        return list;
    }

    public Iterable<Email> findAllById(Iterable<Long> iterable) {
        EntityManager em = emf.createEntityManager();

        List<Email> resultList = new ArrayList();
        Iterator<Long> iter = iterable.iterator();

        while(iter.hasNext()) {
            Long item = iter.next();
            Query q = em.createQuery("SELECT * FROM emails WHERE id="+item);
            resultList.add((Email)q.getSingleResult());
        }
        emf.close();

        return resultList;
    }

    public long count() {
        Iterator<Email> iter = findAll().iterator();
        long num = 0L;
        while(iter.hasNext()) {
            num++;
        }
        return num;
    }

    @Transactional
    public void deleteById(Long aLong) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("DELETE FROM emails WHERE id="+aLong);
        q.executeUpdate();
        emf.close();
    }

    @Transactional
    public void delete(Email email) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(email);
        tx.commit();
        emf.close();
    }

    @Transactional
    public void deleteAll(Iterable<? extends Email> iterable) {
        Iterator iter = iterable.iterator();
        while(iter.hasNext()) {
            deleteById(((Email)iter.next()).getId());
        }
    }

    @Transactional
    public void deleteAll() {
        deleteAll(findAll());
    }

    public void saveAllUsers(Iterable<User> iterable) {
        Iterator<User> iterator = iterable.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();
            save(new Email(user.getFirstname(), user.getLastname()));
        }
    }
}