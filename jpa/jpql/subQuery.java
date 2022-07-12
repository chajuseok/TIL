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

            String sub = "select mm.age , mm.username from (select m.age , m.username from Member m) as mm"; // JPA는 FROM절의 서브쿼리 지원X
            String query_exist = "select m from Member as m where exists (select t from m.team t where t.name = 'A')"; // 팀A에 소속된 회원
            String query_Any = "select m from Member as m where m.team = ANY(select t from Team t)"; // 어떤 팀이든 소속된 회원
            String query = "select m from Member as m where m.age > (select avg(m2.age) from Member as m2)"; // 나이가 평균보다 많은 회원 조회

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

//            Member m = result.get(result.size()-1);
//            System.out.println("member name : "+ m.getTeam());

            tx.commit(); // insert 쿼리를 transaction commit 순간 발생D
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
