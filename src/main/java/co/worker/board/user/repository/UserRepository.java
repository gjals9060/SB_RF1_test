package co.worker.board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.worker.board.user.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
