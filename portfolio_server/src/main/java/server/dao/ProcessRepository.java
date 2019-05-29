package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.ProcessEntity;

import javax.persistence.Table;
import java.util.List;

@Repository
@Transactional
@Table(name = "process")
public interface ProcessRepository extends JpaRepository<ProcessEntity,Integer> {
    ProcessEntity findById(String processId);
    List<ProcessEntity> findAllByUserId(int userId);
}
