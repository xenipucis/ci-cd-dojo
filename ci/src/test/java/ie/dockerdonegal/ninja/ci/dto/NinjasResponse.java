package ie.dockerdonegal.ninja.ci.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NinjasResponse {

  private List<NinjaDTO> payload;

}
