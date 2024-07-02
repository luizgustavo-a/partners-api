package ldelivery.api_partners.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepositoryPersistence extends JpaRepository<PartnerEntity, Long> {
    Page<PartnerEntity> findAllByActiveTrue(Pageable pageable);

    List<PartnerEntity> findAllByActiveTrue();

    Boolean existsByDocument(String document);

    Optional<PartnerEntity> findByIdAndActiveTrue(Long id);
}
