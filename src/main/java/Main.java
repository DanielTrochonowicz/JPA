import entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        //tworzenie nowego rekordu
        em.getTransaction().begin();
        Todo todo1 = new Todo();
        todo1.setSummary(LocalDateTime.now().toString());
        todo1.setDescription("wyprowadzić psa");
        em.persist(todo1);
        em.getTransaction().commit();
        printToDo();

        //pobranie rekordu
        Todo result = em.find(Todo.class, 19L);
        System.out.println(result.toString());

        //zmodyfikowanie pobranego rekordu
        em.getTransaction().begin();
        Todo result2 = em.find(Todo.class, 6L);
        result2.setSummary("aaaa");
        em.getTransaction().commit();
        printToDo();

        //usuwanie
        em.getTransaction().begin();
        Todo remove = em.find(Todo.class, 1l);
        em.remove(remove);
        em.getTransaction().commit();
        printToDo();


        em.close();
    }

    public static void printToDo() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("select t from Todo t");
        List<Todo> todoList = q.getResultList();
        for (Todo todo : todoList) {
            System.out.println(todo);
        }
        System.out.println("Size: " + todoList.size());
        System.out.println();
    }
}