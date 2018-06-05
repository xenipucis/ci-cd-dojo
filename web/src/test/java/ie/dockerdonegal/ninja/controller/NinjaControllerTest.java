package ie.dockerdonegal.ninja.controller;

import ie.dockerdonegal.ninja.bean.Ninja;
import ie.dockerdonegal.ninja.dto.NinjaDTO;
import ie.dockerdonegal.ninja.response.NinjasResponse;
import ie.dockerdonegal.ninja.service.NinjaService;
import ie.dockerdonegal.ninja.transform.NinjaDTOToNinjaTransformer;
import ie.dockerdonegal.ninja.transform.NinjaToNinjaDTOTransformer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NinjaControllerTest {

    @InjectMocks
    private NinjaController fixture;

    @Spy
    private final NinjaToNinjaDTOTransformer ninjaToNinjaDTOTransformer = new NinjaToNinjaDTOTransformer();

    @Spy
    private final NinjaDTOToNinjaTransformer ninjaDTOToNinjaTransformer = new NinjaDTOToNinjaTransformer();

    @Mock
    private NinjaService ninjaService;

    private PodamFactory podam = new PodamFactoryImpl();

    @Test
    public void shouldRetrieveListOfNinjas() {
        final List<Ninja> listOfNinjas = Arrays.asList(podam.manufacturePojo(Ninja.class), podam.manufacturePojo(Ninja.class));
        final List<NinjaDTO> expectedListOfNinjas = listOfNinjas.stream()
                .map(ninjaToNinjaDTOTransformer::transform)
                .collect(Collectors.toList());
        when(ninjaService.listNinjas()).thenReturn(listOfNinjas);


        final ResponseEntity<NinjasResponse> result = fixture.listNinjas();
        Assert.assertNotNull(result);
        final NinjasResponse ninjasReponse = result.getBody();
        Assert.assertNotNull(ninjasReponse);
        final List<NinjaDTO> retrievedListOfNinjas = ninjasReponse.getPayload();
        Assert.assertEquals(expectedListOfNinjas, retrievedListOfNinjas);
    }
}
