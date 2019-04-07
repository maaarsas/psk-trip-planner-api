package lt.vu.trip.repository;

import lt.vu.trip.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long>, JpaSpecificationExecutor<Office> {

	List<Office> findAllByDeletedFalse();

	Office findByIdAndDeletedFalse(Long id);
}
