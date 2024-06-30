package api_partners.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepositoryPersistence extends JpaRepository<PartnerEntity, Long> {
    Optional<PartnerEntity> findById(Long id);
    Boolean existsByDocument(String document);
}
