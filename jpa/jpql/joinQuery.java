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

            String parm_query = "select m from Member as m where m.username = :username"; // 나중에 :username에 인자값 전달
            String DTO_query = "select new jpql.MemberDTO(m.username, m.age) from Member as m"; /// 추가로 class는 DTO 클래스로
            String join_query = "select m from Member as m join m.team as t";
            String left_query = "select m from Member as m left join Team as t order by m.id desc"; // id로 내림차순
            String seta_query = "select m from Member m , Team t where m.username = t.name";
            String query = "select m from Member as m left join Team as t on t.name = 'A'"; // 외래키 조인 x, on 으로 조인인
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
