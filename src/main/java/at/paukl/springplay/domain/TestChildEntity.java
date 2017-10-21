package at.paukl.springplay.domain;

import org.slf4j.Logger;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Entity
public class TestChildEntity {
    private static final Logger LOG = getLogger(TestChildEntity.class);

    private Long id;
    private String name;
    private TestEntity parent;

    public TestChildEntity() {
        LOG.debug("TestChildEntity created");
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

    @OneToOne
    public TestEntity getParent() {
        return parent;
    }

    public void setParent(TestEntity parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "TestChildEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
