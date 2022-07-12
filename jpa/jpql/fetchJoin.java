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

            String query_fetch = "select m From Member m join fetch m.team";

//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//           for(Member member : result){
//                System.out.println("member = "+ member.getTeam().getName());
//            }


           String query_fetchMany = "select distinct t From Team t join fetch t.members";
           List<Team> result = em.createQuery(query_fetchMany, Team.class).getResultList();

           for(Team team : result) {
               System.out.println("team = " + team.getName()+ "|" + team.getMembers().size());
           }
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
