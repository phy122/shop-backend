package com.example.shoppingmall.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.shoppingmall.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository // DB에 접근
public class UserRepository {
    
    @PersistenceContext 
    private EntityManager em;   // EntityManager : 직접 쿼리 작성 가능

    // insert의 역할
    public void save(User user){
        em.persist(user);
    }

    // 사용자명 중복 검사
    // 엔티티를 기준으로 작성
    public boolean existsByUsername(String username){
        String query = "SELECT count(u) FROM User u WHERE u.username = :username";
        // createQuery() 메소드로 JPQL 실행
        // setParameter() : :username 파라미터에 값을 바인딩(SQL 인젝션 방지)
        Long count = em.createQuery(query, Long.class)
                       .setParameter("username", username)
                       .getSingleResult();
        return count > 0;
    }

    public Optional<User> findByUsername(String username){
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        return em.createQuery(jpql, User.class)
                 .setParameter("username", username)
                 .getResultList()
                 .stream()
                 .findFirst();
        
    }

    public Optional<User> findById(Long id){
        return Optional.ofNullable(em.find(User.class, id));
    }

}
