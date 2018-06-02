package ie.dockerdonegal.ninja.transform;

import ie.dockerdonegal.ninja.bean.Ninja;
import ie.dockerdonegal.ninja.dto.NinjaDTO;
import org.springframework.stereotype.Component;

@Component
public class NinjaDTOToNinjaTransformer implements Transformer<NinjaDTO, Ninja> {

    @Override
    public Ninja transform(NinjaDTO source) {
        if(source == null) {
            return null;
        }
        final Ninja ninja = new Ninja();
        ninja.setBelt(source.getBelt());
        ninja.setId(source.getId());
        ninja.setName(source.getName());
        ninja.setDojo(source.getDojo());
        return ninja;
    }
}
