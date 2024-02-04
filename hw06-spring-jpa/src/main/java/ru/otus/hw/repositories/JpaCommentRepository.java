package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@RequiredArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        Map<String, Object> params = Map.of(FETCH.getKey(), entityManager.getEntityGraph("book-graph"));
        return Optional.ofNullable(entityManager.find(Comment.class, id, params));
    }

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-graph");
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Optional<Comment> comment = Optional.ofNullable(entityManager.find(Comment.class, id));
        comment.ifPresent(entityManager::remove);
    }
}
