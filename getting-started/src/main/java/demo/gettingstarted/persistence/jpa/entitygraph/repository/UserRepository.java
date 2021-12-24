package demo.gettingstarted.persistence.jpa.entitygraph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.gettingstarted.persistence.jpa.entitygraph.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
