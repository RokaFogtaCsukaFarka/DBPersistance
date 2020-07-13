package com.oracle.studies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Autowired
    UserRepository uRepo;
    UserRepository uRepoSpy;
    User user01, user02;
    @Autowired
    EntityManagerFactory entityManagerFactoryMock;
    @Autowired
    IUserRepository userRepoMock;

    @BeforeEach
    public void setUp() {
        user01 = new User();
        user02 = new User();
        uRepoSpy = spy(uRepo);
        doReturn(userRepoMock).when(uRepoSpy).getRepo();
        doReturn(entityManagerFactoryMock).when(uRepoSpy).getEmf();
    }

    @Test
    void saveAll() {
        List<User> userList = new ArrayList();
        userList.add(user01);
        userList.add(user02);
        uRepoSpy.saveAll(userList);

        verify(uRepoSpy,times(2)).save(any());
    }
}