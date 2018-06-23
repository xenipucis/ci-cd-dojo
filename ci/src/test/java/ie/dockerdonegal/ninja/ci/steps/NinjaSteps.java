package ie.dockerdonegal.ninja.ci.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ie.dockerdonegal.ninja.ci.NinjaIntegrationTestConfig;
import ie.dockerdonegal.ninja.ci.dto.NinjaDTO;
import ie.dockerdonegal.ninja.ci.dto.NinjasResponse;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@ContextConfiguration(classes = NinjaIntegrationTestConfig.class)
public class NinjaSteps {

    private String applicationURL;

    private final TestRestTemplate restTemplate;

    private NinjaDTO returnedNinjaDTO;

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    public NinjaSteps(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Given("^The (.+) is responsive$")
    public void givenApplicationIsResponsive(final String applicationURL) {
        this.applicationURL = applicationURL;
        final String uriString = String.format("%s/ninja/ping", applicationURL);
        final String result = restTemplate.getForObject(URI.create(uriString), String.class);
        Assert.assertEquals("pong", result);
        //Assert.assertTrue(true);
    }

    @When("^adding a (.+) as a new ninja$")
    public void addNewNinja(final String ninjaPayload) {
        try {
            final NinjaDTO dto = JSON_MAPPER.readValue(ninjaPayload, NinjaDTO.class);
            final String uriString = String.format("%s/ninja/save", applicationURL);
            this.returnedNinjaDTO = restTemplate.postForObject(URI.create(uriString), dto, NinjaDTO.class);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        //Assert.assertTrue(true);
    }

    @Then("^the added ninja should be listed$")
    public void assertNinjaIsListed() {
        final String uriString = String.format("%s/ninja/list", applicationURL);
        final ResponseEntity<NinjasResponse> ninjasResponseEntity = restTemplate.getForEntity(URI.create(uriString), NinjasResponse.class);
        Assert.assertNotNull(ninjasResponseEntity);
        final NinjasResponse ninjasResponse = ninjasResponseEntity.getBody();
        Assert.assertNotNull(ninjasResponse);
        final List<NinjaDTO> listOfNinjas = ninjasResponse.getPayload();
        Assert.assertNotNull(listOfNinjas);
        Assert.assertTrue(listOfNinjas.contains(this.returnedNinjaDTO));
        //Assert.assertTrue(true);
    }
}
