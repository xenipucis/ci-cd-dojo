package ie.dockerdonegal.ninja.response;

import ie.dockerdonegal.ninja.dto.NinjaDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NinjasResponse {

    private List<NinjaDTO> payload;
}
