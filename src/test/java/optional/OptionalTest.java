package optional;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OptionalTest {
    @Test
    public void test_Optional() throws Exception {
        Optional<String> value = Optional.of("value");
        assertThat(value.isPresent(), equalTo(true));
        assertThat(value.get(), equalTo("value"));
    }

    @Test
    public void test_empty_Optional() throws Exception {
        Optional<String> empty = Optional.empty();
        assertThat(empty.isPresent(), equalTo(false));
    }

    @Test
    public void test_map() throws Exception {
        Optional<Member> val = Optional.of(new Member(new Address("100100")));
        Coord result = val.map(Member::getAddress)
                          .map(address -> address.getZipCode())
                          .map(zipCode -> findCoord(zipCode))
                          .orElse(null);
        assertThat(result.getX(), equalTo(0.0));
        assertThat(result.getY(), equalTo(0.0));
    }

    private Coord findCoord(String zipcode) {
        return new Coord();
    }

    public static class Member {
        private Address address;

        public Member(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }
    }

    private static class Address {
        private String zipCode;

        private Address(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getZipCode() {
            return zipCode;
        }
    }

    private class Coord {
        private Double x;
        private Double y;

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }
    }
}
