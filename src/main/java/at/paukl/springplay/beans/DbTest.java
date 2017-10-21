package at.paukl.springplay.beans;

import at.paukl.springplay.domain.TestChildEntity;
import at.paukl.springplay.domain.TestEntity;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Long saveEntityWithChild(String name) {
        final TestEntity entitySave = new TestEntity();
        final TestChildEntity child = new TestChildEntity();
        entitySave.setName(name);
        entityManager.persist(child);
        entitySave.setTestChildEntity(child);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<String> loadAllByIds(ArrayList<Long> ids) {

        List<TestEntity> tests = entityManager.createQuery("SELECT e FROM TestEntity e WHERE e.id IN :ids")
                .setParameter("ids", ids)
                .getResultList();

        LOG.info("fetched {} entities", tests.size());

        final List<String> names = tests.stream()
                .map(it -> it.getName())
                .collect(Collectors.toList());
        LOG.info("names collected, returning");


        // NOTE: without the next line, hibernate will load all child entities at the end of the transaction
        //  (when exiting the function, after the return)
        //tests.stream().forEach(it -> entityManager.detach(it));

        return names;
    }
}
