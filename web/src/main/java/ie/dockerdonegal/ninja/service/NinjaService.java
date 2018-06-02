package ie.dockerdonegal.ninja.service;

import ie.dockerdonegal.ninja.bean.Ninja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NinjaService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public NinjaService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Ninja save(Ninja ninja) {
        mongoTemplate.save(ninja);
        return ninja;
    }

    public List<Ninja> listNinjas() {
        return mongoTemplate.findAll(Ninja.class);
    }
}
