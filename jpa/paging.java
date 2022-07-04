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
            for(int i = 0; i<100; i++){
                Member member = new Member();
                member.setUsername("member " + i);
                member.setAge(i);
                em.persist(member);
            }


            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member as m order by m.age desc", Member.class)
                    .setFirstResult(1) // 1부터
                    .setMaxResults(10) // 10까지 desc순으로 페이징
                    .getResultList();

            System.out.println("result = " + result.size());
            for(Member member1 : result){
                System.out.println("member1 : "+ member1);
            }

            tx.commit(); // insert 쿼리를 transaction commit 순간 발생D
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
