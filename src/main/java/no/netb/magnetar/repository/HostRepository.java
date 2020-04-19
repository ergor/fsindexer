package no.netb.magnetar.repository;

import no.netb.magnetar.entities.indexing.Host;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends CrudRepository<Host, Long> {

}
