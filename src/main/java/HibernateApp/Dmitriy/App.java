package HibernateApp.Dmitriy;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().
                addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        //try with resources (не надо писать finally)
        try (sessionFactory){
            session.beginTransaction();

            //код

            session.getTransaction().commit();
        }
    }
}
