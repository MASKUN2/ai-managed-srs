package maskun.aimanagedsrs.hexagon.shared;

import java.util.Optional;

public interface EntityFinder<E, ID> extends EntityClassProvider<E> {

    Optional<E> find(ID id);
}
