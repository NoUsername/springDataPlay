package at.paukl.springplay.domain;

import javax.persistence.*;

/**
 * @author Paul Klingelhuber
 */
@Entity
public class EntityWithConverter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TestDto testDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "VARCHAR")
    @Convert(converter = TestDtoConverter.class)
    public TestDto getTestDto() {
        return testDto;
    }

    public void setTestDto(TestDto testDto) {
        this.testDto = testDto;
    }


}
