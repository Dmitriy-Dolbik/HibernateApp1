package HibernateApp.Dmitriy;


import HibernateApp.Dmitriy.model.Item;
import HibernateApp.Dmitriy.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.SocketImpl;
import java.sql.SQLOutput;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().
                addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try (sessionFactory){
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека");

            session.getTransaction().commit();//объект person перешел в состояние detached

            System.out.println("Сессия закончилась (session.close)");

            session = sessionFactory.getCurrentSession();//открыли вторую сессию
            session.beginTransaction();//открыли транзакцию

            System.out.println("Внутри второй сессии");

            person = (Person) session.merge(person);//вернули объект person в Persistent state

            //подгрузили связанные сущности
            Hibernate.initialize(person.getItems());

            session.getTransaction().commit();

            System.out.println("Вне второй сессии");

            //это работает т.к. связанные товары получены вручную
            System.out.println(person.getItems());

        }
    }
}
