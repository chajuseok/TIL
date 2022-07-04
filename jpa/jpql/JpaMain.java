package jpql;


import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //persistence의 persistence-unit name을 설정 정보 hello

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("c");
            member.setAge(10);
            em.persist(member);
//
//            Member singleResult = em.createQuery("select m from Member as m where m.username = :username", Member.class)
//                    .setParameter("username", "c")
//                    .getSingleResult();

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member as m", MemberDTO.class).getResultList();

            System.out.println("username :  " + resultList.get(0).getUsername());


            tx.commit(); // insert 쿼리를 transaction commit 순간 발생
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
