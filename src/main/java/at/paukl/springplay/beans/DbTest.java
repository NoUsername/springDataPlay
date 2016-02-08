package at.paukl.springplay.beans;

import at.paukl.springplay.domain.TestEntity;
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
        final TestEntity entitySave = new TestEntity();
        entitySave.setName(name);
        entityManager.persist(entitySave);
        Long entityId = entitySave.getId();
        LOG.info("entiy persisted with id {}", entityId);
        return entityId;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void showAll() {
        final TypedQuery<TestEntity> findAll = entityManager.createQuery("SELECT t FROM TestEntity t", TestEntity.class);
        final List<TestEntity> all = findAll.getResultList();
        for (TestEntity testEntity : all) {
            LOG.info("found entity: {}", testEntity.toString());
        }
    }
}
