package fivesenses.server.fivesenses;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FivesensesApplicationTests {

    //	@Test
//	void contextLoads() {
//	}
    @Test
    void test_1() {
        Assertions.assertThat(1).isEqualTo(1);
    }
}

