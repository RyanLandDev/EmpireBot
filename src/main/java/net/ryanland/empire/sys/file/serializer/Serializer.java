package net.ryanland.empire.sys.file.serializer;

/**
 * Interface for serializer classes.
 *
 * @param <S> Serialized value
 * @param <D> Deserialized value
 */
public interface Serializer<S, D> {

    S serialize(D toSerialize);

    D deserialize(S toDeserialize);
}
