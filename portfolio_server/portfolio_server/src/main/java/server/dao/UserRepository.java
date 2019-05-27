package server.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.UserEntity;

import javax.persistence.Table;

@Repository
@Transactional
@Table(name = "user")
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUsername(String username);
}
