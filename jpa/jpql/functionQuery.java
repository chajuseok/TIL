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


            String query_concat = "select concat('a', 'b') From Member m";
            String query_substring = "select substring(m.username, 2, 5) From Member m"; // username 2~5까지 substr
            String query_trim = "select trim(m.username) From Member m"; // 공백제거
            String query_lower = "select Lower(m.username) From Member m";
            String query_locate = "select locate('cha', m.username) From Member m"; //createQuery는 Integer 타입으로 반환 


            List<String > result = em.createQuery(query_lower, String.class).getResultList();

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
