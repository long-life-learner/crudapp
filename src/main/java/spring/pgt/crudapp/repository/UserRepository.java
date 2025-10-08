package spring.pgt.crudapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.pgt.crudapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNameContaining(String name);

    Boolean existsByNomorInduk(String nomorInduk);

    Optional<User> findByNomorInduk(String nomorInduk);
}
