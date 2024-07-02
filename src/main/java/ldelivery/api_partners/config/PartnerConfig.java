package ldelivery.api_partners.config;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ldelivery.api_partners.infra.gateway.PartnerMapper;
import ldelivery.api_partners.infra.gateway.PartnerRepositoryJpa;
import ldelivery.api_partners.infra.persistence.PartnerRepositoryPersistence;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PartnerConfig {

    @Bean
    CreatePartner createPartner(PartnerRepository partnerRepository) {
        return new CreatePartner(partnerRepository);
    }

    @Bean
    LoadPartner loadPartner(PartnerRepository partnerRepository) {
        return new LoadPartner(partnerRepository);
    }

    @Bean
    SearchPartner searchPartner(PartnerRepository partnerRepository){
        return new SearchPartner(partnerRepository);
    }

    @Bean
    UpdatePartner updatePartner(PartnerRepository partnerRepository) {
        return new UpdatePartner(partnerRepository);
    }

    @Bean
    DeletePartner deletePartner(PartnerRepository partnerRepository) {
        return new DeletePartner(partnerRepository);
    }

    @Bean
    PartnerRepositoryJpa partnerRepositoryJpa(PartnerRepositoryPersistence repositoryPersistence,
                                              PartnerMapper entityMapper) {
        return new PartnerRepositoryJpa(repositoryPersistence, entityMapper);
    }

    @Bean
    PartnerMapper entityMapper() {
        return new PartnerMapper();
    }
}
