package at.paukl.springplay.domain;

/**
 * @author Paul Klingelhuber
 */
public class TestDto {
    String value;

    public String getValue() {
        return value;
    }

    public TestDto(String value) {
        this.value = value;
    }

    /**
     * equals check that matches ignore-case comparison from TestDtoConverter
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestDto testDto = (TestDto) o;

        return value != null ? value.equalsIgnoreCase(testDto.value) : testDto.value == null;
    }

}
