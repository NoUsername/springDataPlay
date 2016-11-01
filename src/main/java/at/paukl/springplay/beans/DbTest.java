package at.paukl.springplay.beans;

import at.paukl.springplay.domain.EntityWithConverter;
import at.paukl.springplay.domain.TestDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Component
public class DbTest {
    private static final Logger LOG = getLogger(DbTest.class);

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long saveEntity(String name) {
        final EntityWithConverter entitySave = new EntityWithConverter();
        entitySave.setTestDto(new TestDto("test"));
        entityManager.persist(entitySave);
        Long entityId = entitySave.getId();
        LOG.info("entiy persisted with id {}", entityId);
        return entityId;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadAndRecreateDto() {
        final TypedQuery<EntityWithConverter> findAll = entityManager.createQuery("SELECT e FROM EntityWithConverter e",
                EntityWithConverter.class);
        final List<EntityWithConverter> all = findAll.getResultList();
        for (EntityWithConverter testEntity : all) {
            testEntity.setTestDto(createUppercasedCopy(testEntity.getTestDto()));
            LOG.info("found entity: {}", testEntity.toString());
        }
    }

    public static TestDto createUppercasedCopy(TestDto other) {
        if (other == null) {
            return null;
        }
        return new TestDto(other.getValue().toUpperCase());
    }
}
