package io.quarkus.qute.deployment.devconsole;

import org.hamcrest.Matchers;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusDevModeTest;
import io.restassured.RestAssured;

public class DevConsolePreviewTest {

    @RegisterExtension
    static final QuarkusDevModeTest config = new QuarkusDevModeTest()
            .withApplicationRoot((jar) -> jar
                    .addAsResource(new StringAsset(
                            "{#for i in total}{i}:{/for}"),
                            "templates/loop.txt"));

    @Test
    public void testLoopPreview() {
        RestAssured.with().formParam("template-select", "loop.txt").formParam("template-data", "{\"total\": [1 ,2 ,3]}")
                .post("q/dev-v1/io.quarkus.quarkus-qute/preview")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("1:2:3:"));

    }

}
