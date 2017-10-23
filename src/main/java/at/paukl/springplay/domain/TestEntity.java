package at.paukl.springplay.domain;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.slf4j.Logger;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Entity
public class TestEntity implements PersistentAttributeInterceptable {
    private static final Logger LOG = getLogger(TestEntity.class);

    private Long id;
    private String name;
    private TestChildEntity testChildEntity;

    public TestEntity() {
        LOG.debug("TestEntity created");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = true, mappedBy = "parent")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public TestChildEntity getTestChildEntity() {
        if (interceptor != null) {
            return (TestChildEntity) interceptor.readObject(this, "testChildEntity", testChildEntity);
        }
        return testChildEntity;
    }

    public void setTestChildEntity(TestChildEntity testChildEntity) {
        if (interceptor != null) {
            this.testChildEntity = (TestChildEntity) interceptor.writeObject(this, "testChildEntity", this.testChildEntity, testChildEntity);
            return;
        }
        this.testChildEntity = testChildEntity;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private PersistentAttributeInterceptor interceptor;

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return this.interceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor) {
        this.interceptor = interceptor;
    }
}
