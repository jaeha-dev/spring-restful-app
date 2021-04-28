package com.inflearn.demo.user;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static Integer usersCount = 3;
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Kenneth", "12345", new Date()));
        users.add(new User(2, "Alice", "12345", new Date()));
        users.add(new User(3, "Elena", "12345", new Date()));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        user.setCreatedAt(new Date());
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(Integer id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(Integer id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(id)) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}