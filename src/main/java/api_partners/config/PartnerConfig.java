package api_partners.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import api_partners.application.gateway.PartnerRepository;
import api_partners.application.usecases.CreatePartner;
import api_partners.application.usecases.LoadPartner;
import api_partners.application.usecases.SearchPartner;
import api_partners.infra.gateway.PartnerMapper;
import api_partners.infra.gateway.PartnerRepositoryJpa;
import api_partners.infra.persistence.PartnerRepositoryPersistence;

@Configuration
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
    PartnerRepositoryJpa partnerRepositoryJpa(PartnerRepositoryPersistence repositoryPersistence,
                                              PartnerMapper entityMapper) {
        return new PartnerRepositoryJpa(repositoryPersistence, entityMapper);
    }

    @Bean
    PartnerMapper entityMapper() {
        return new PartnerMapper();
    }
}
