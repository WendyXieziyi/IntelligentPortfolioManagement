package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.ProcessEntity;

public interface ProcessRepository extends JpaRepository<ProcessEntity,Integer> {
}
