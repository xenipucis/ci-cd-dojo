package ie.dockerdonegal.ninja.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Ninja {

    @Id
    private String id;

    private String name;

    private Belt belt;

    private Dojo dojo;
}
