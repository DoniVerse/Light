package com.example.light.data.dao;

import androidx.room.*;
import com.example.light.data.model.User;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    Single<User> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    Single<User> getUserByEmailAndPassword(String email, String password);

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE email = :email)")
    Single<Boolean> doesEmailExist(String email);

    @Query("SELECT * FROM users")
    Single<List<User>> getAllUsers();

    @Update
    Completable updateUser(User user);

    @Delete
    Completable deleteUser(User user);
}
