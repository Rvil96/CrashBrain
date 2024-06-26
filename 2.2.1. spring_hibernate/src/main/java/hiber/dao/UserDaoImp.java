package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        String hql = "from User u left join fetch u.car";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }


    @Override
    public User getUserByCar(Car car) {
        String hql = "from Car c left join fetch c.user where c.model = :model and c.series = :series";

        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);

        query.setParameter("model", car.getModel());
        query.setParameter("series", car.getSeries());

        Car resultCar = (Car) query.uniqueResult();
        return resultCar.getUser();
    }

}
