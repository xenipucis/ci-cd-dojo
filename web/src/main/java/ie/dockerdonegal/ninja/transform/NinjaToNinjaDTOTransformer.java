package ie.dockerdonegal.ninja.transform;

import ie.dockerdonegal.ninja.bean.Ninja;
import ie.dockerdonegal.ninja.dto.NinjaDTO;
import org.springframework.stereotype.Component;

@Component
public class NinjaToNinjaDTOTransformer implements Transformer<Ninja, NinjaDTO> {

    @Override
    public NinjaDTO transform(Ninja source) {
        if (source == null) {
            return null;
        }
        final NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setBelt(source.getBelt());
        ninjaDTO.setId(source.getId());
        ninjaDTO.setName(source.getName());
        ninjaDTO.setDojo(source.getDojo());
        return ninjaDTO;
    }
}
