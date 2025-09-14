package maskun.aimanagedsrs.hexagon.shared;

import java.util.NoSuchElementException;

public interface RequirableEntityFinder<E, ID> extends EntityFinder<E, ID> {

    default E require(ID id) throws NoSuchElementException {
        return find(id).orElseThrow(() -> new NoSuchElementException(getEntityClass().getSimpleName() + " 찾을 수 없습니다. id: " + id));
    }
}
