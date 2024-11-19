package org.example.final_exam.respository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.final_exam.model.Article;
import org.example.final_exam.util.HibernateUtil;

import java.util.List;

@ApplicationScoped
public class ArticleRepository {
    public List<Article> findAll() throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        List<Article> articles = null;
        try {
            em.getTransaction().begin();
            articles = em
                    .createQuery("SELECT a FROM Article a", Article.class)
                    .getResultList();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();  // 当出了异常时回滚事务
            throw new RuntimeException("articles_not_found", e);
        } finally {
            em.close();
        }
        return articles;
    }
    public Article findByID(Integer id) throws PersistenceException{
        EntityManager em = HibernateUtil.getEntityManager();
        Article article = null;
        try {
            em.getTransaction().begin();
            article = em
                    .createQuery("SELECT a FROM Article a",Article.class)
                    .getSingleResult();
            em.getTransaction().commit();
        }catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("article_not_found", e);
        }finally {
            em.close();
        }
        return article;
    }
    public void create(Article article) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(article);
            em.getTransaction().commit();
        }catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException("", e);
        }finally {
            em.close();
        }
    }
    public void update(Article article) throws PersistenceException {
        EntityManager em = HibernateUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Article existingArticle = em.find(Article.class, article.getId());
            try {
                HibernateUtil.copyNonNullProperties(article, existingArticle);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            em.merge(existingArticle);
            em.getTransaction().commit();
        }catch (PersistenceException e){
            em.getTransaction().rollback();
            throw new RuntimeException("", e);
        }finally {
            em.close();
        }
    }
}

