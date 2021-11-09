package io.github.v3nd3774.uta2218.cse6324.generate;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GraphTest {

    String init();
    String[] inbound();
    String[] outbound();
}
