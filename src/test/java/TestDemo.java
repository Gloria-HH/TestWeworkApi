import com.deme.wework.module.common.Wework;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by gloria on 2019/4/4.
 */
public class TestDemo {

    @Test
    public void test() {
        String accessToken = Wework.getAccessToken();
        assertThat(accessToken, not(equalTo(null)));
    }
}