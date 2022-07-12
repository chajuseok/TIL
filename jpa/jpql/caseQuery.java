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

            String query_coalesce = "select coalesce(m.username, '이름 없는 회원') from Member m"; //username이 null이면 이름없는 회원으로 반환
            String query_nullif = "select nullif(m.username, '관리자') from Member m"; // username이 관리면 null로 반환 


            String query = "select " +
                            "case when m.age <= 10 then '학생요금' "+
                                "when m.age >=60 then '경로요금' "+
                                "else  '일반요금' " +
                           "end "+
                    "from Member m "; // 나이가 평균보다 많은 회원 조회

            List<String > result = em.createQuery(query, String.class).getResultList();

            for(String s: result){
                System.out.println("s = " + s);
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
