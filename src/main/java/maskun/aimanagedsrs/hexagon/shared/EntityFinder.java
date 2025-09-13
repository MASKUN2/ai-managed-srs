package maskun.aimanagedsrs.hexagon.shared;

import java.util.Optional;

public interface EntityFinder<E, ID> {

    Optional<E> find(ID id);
}
