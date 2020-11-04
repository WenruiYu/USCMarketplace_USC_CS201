package com.usc.market.repo;

import com.usc.market.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 10:12 下午
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    List<User> findAllByTypeNotAndUsernameIsLikeOrderByIdDesc(int type, String username);

    List<User> findAllByTypeNotOrderByIdDesc(int dep);

}
