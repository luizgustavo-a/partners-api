package ldelivery.api_partners.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PartnerRepositoryPersistence extends JpaRepository<PartnerEntity, Long>, JpaSpecificationExecutor<PartnerEntity> {
    Page<PartnerEntity> findAllByActiveTrue(Pageable pageable);

    Boolean existsByDocument(String document);

    Optional<PartnerEntity> findByIdAndActiveTrue(Long id);
}
