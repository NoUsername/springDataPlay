package at.paukl.springplay.domain;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;
import org.slf4j.Logger;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Entity
public class TestEntity implements FieldHandled {
    private static final Logger LOG = getLogger(TestEntity.class);

    private Long id;
    private String name;
    private TestChildEntity testChildEntity;
    private FieldHandler fieldHandler;

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
        if (fieldHandler != null) {
            return (TestChildEntity) fieldHandler.readObject(this, "testChildEntity", testChildEntity);
        }
        return testChildEntity;
    }

    public void setTestChildEntity(TestChildEntity testChildEntity) {
        if (fieldHandler != null) {
            this.testChildEntity = (TestChildEntity) fieldHandler.writeObject(this, "testChildEntity", this.testChildEntity, testChildEntity);
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

    @Override
    public void setFieldHandler(FieldHandler handler) {
        this.fieldHandler = handler;
    }

    @Override
    public FieldHandler getFieldHandler() {
        return fieldHandler;
    }
}
