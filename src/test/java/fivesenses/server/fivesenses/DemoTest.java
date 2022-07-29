package fivesenses.server.fivesenses;


import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DemoTest {

    @Test
    void test_1(){
        Assertions.assertThat(1).isEqualTo(1);
    }
}
