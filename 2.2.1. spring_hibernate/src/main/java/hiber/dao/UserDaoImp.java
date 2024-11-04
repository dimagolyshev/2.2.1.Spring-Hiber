package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByCar(Car car) {
        User user = null;
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series");
        query.setParameter("model", car.getModel());
        query.setParameter("series", car.getSeries());

        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No user for given car found");
        } catch (PersistenceException e) {
            System.out.println("Exception caught at " + this.getClass());
            e.printStackTrace();
        }

        return user;
    }
}
