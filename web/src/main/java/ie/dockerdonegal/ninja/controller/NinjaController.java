package ie.dockerdonegal.ninja.controller;

import ie.dockerdonegal.ninja.bean.Ninja;
import ie.dockerdonegal.ninja.dto.NinjaDTO;
import ie.dockerdonegal.ninja.response.NinjasResponse;
import ie.dockerdonegal.ninja.service.NinjaService;
import ie.dockerdonegal.ninja.transform.NinjaDTOToNinjaTransformer;
import ie.dockerdonegal.ninja.transform.NinjaToNinjaDTOTransformer;
import ie.dockerdonegal.ninja.transform.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class NinjaController {

    private final NinjaService ninjaService;

    private final NinjaToNinjaDTOTransformer ninjaToNinjaDTOTransformer;

    private final NinjaDTOToNinjaTransformer ninjaDTOToNinjaTransformer;

    @Autowired
    public NinjaController(final NinjaService ninjaService,
                           final NinjaToNinjaDTOTransformer ninjaToNinjaDTOTransformer,
                           final NinjaDTOToNinjaTransformer ninjaDTOToNinjaTransformer) {
        this.ninjaService = ninjaService;
        this.ninjaToNinjaDTOTransformer = ninjaToNinjaDTOTransformer;
        this.ninjaDTOToNinjaTransformer = ninjaDTOToNinjaTransformer;
    }

    @GetMapping("ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }

    @GetMapping(path = "/list")
    public ResponseEntity<NinjasResponse> listNinjas() {
        //TODO Send a proper response here.
        final Optional<List<Ninja>> oNinjas = Optional.ofNullable(ninjaService.listNinjas());
        final List<NinjaDTO> ninjas = oNinjas.orElseGet(Collections::emptyList)
                .stream()
                .map(ninjaToNinjaDTOTransformer::transform)
                .collect(Collectors.toList());
        final NinjasResponse ninjasResponse = NinjasResponse.builder().payload(ninjas).build();
        return ResponseEntity.ok(ninjasResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<NinjaDTO> createNinja(@Valid @RequestBody final NinjaDTO ninjaDTO) {
        final Ninja createdNinja = ninjaService.save(ninjaDTOToNinjaTransformer.transform(ninjaDTO));
        NinjaDTO createdNinjaDTO = ninjaToNinjaDTOTransformer.transform(createdNinja);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNinjaDTO);
    }
}