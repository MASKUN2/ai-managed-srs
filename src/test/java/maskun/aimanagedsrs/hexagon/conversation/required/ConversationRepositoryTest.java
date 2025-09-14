package maskun.aimanagedsrs.hexagon.conversation.required;

import jakarta.persistence.EntityManager;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ConversationRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ConversationRepository repository;

    @Test
    void saveAndFindById() {
        Conversation saved = repository.save(Conversation.startNew());

        entityManager.flush();
        entityManager.clear();

        Conversation retrieved = repository.findById(saved.getId()).orElseThrow();
        assertEquals(saved, retrieved);
    }
}