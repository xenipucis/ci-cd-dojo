package ie.dockerdonegal.ninja.dto;

import ie.dockerdonegal.ninja.bean.Belt;
import ie.dockerdonegal.ninja.bean.Dojo;
import lombok.Data;

@Data
public class NinjaDTO {

    private String id;

    private String name;

    private Belt belt;

    private Dojo dojo;
}
