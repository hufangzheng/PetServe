package control;

import model.*;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.BaseException;
import util.BusinessException;
import util.HibernateUtil;
import org.hibernate.query.*;

import java.util.List;

public class AppointmentManager {
    public List<bean_appointment> loadAllAppoin() {
        List<bean_appointment> list = HibernateUtil.getSession().createQuery("from bean_appointment ").list();
        return list;
    }

    public void addAppointment(bean_appointment appo) throws BaseException{
        if (appo.getProduct_id() == null)
            throw new BaseException("Please input product ID.");
        if (appo.getUser_id() == null)
            throw new BaseException("Please input user ID.");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.save(appo);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment(int appoId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();



        try {
            transaction.begin();

            bean_appointment appointment = session.get(bean_appointment.class, appoId);
            session.delete(appointment);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<bean_appointment> retrieveAoopintment(String userid) {
        Session session = HibernateUtil.getSession();
        List<bean_appointment> result = null;
        Query query = session.createQuery("from bean_appointment where user_id like ? ");
        query.setParameter(0, "%" + userid + "%");
        result = query.list();
        return result;
    }

    public void modifyAppointment(bean_appointment appo) throws BaseException {
        if (appo.getProduct_id() == null)
            throw new BaseException("Please input the product id of appointment.");
        if (appo.getAppointment_begin_time() == null)
            throw new BaseException("Please input the begin time of appointment.");
        if (appo.getCircumstance() == null)
            throw new BaseException("Please input the circumstance of appointment.");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.update(appo);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
