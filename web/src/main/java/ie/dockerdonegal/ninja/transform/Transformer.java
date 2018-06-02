package ie.dockerdonegal.ninja.transform;

// S for source, D for destination
public interface Transformer<S, D> {

    D transform(S source);
}
