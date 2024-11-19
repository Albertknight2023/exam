package org.example.final_exam.respository;

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.final_exam.model.Comment;
import org.example.final_exam.util.HibernateUtil;
import java.util.List;

@ApplicationScoped

public class CommentRepository {
    public List<Comment> findByArticleId(Integer id)throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();

        List< Comment> comments = null;
        try {
            em.getTransaction().begin();
            comments = em
                    .createQuery("SELECT c FROM Comment c WHERE c.articleId = :articleId",Comment.class)
                    .setParameter("articleId",id)
                    .getResultList();
            em.getTransaction().commit();
        }catch (PersistenceException e){
            em.getTransaction().rollback();
            throw new RuntimeException("" ,e);
        }finally {
            em.close();
        }
        return comments;
    }
    public void create(Comment comment)throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        }catch (PersistenceException e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    public void deleteById(Integer commentId) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, commentId);
            if (comment != null) {
                em.remove(comment);
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("",e);
        } finally {
            em.close();
        }
    }
}
