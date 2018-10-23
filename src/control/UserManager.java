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

import javax.swing.*;
import java.util.List;

public class UserManager {
    public static bean_user_information currentUser = null;

    /*Load user accroding to userName*/
    public bean_user_information loadUser(String userId) {
        Session session = HibernateUtil.getSession();
        bean_user_information user = (bean_user_information)session.get(bean_user_information.class, userId);
        return user;
    }

    public void createUser(bean_user_information user) throws BaseException{
        if(user.getUser_name() == null || user.getUser_name().length() > 20) {
            throw new BaseException("The user name must be 20 characters.");
        }
        if(user.getPassword() == null) {
            throw new BaseException("Please input password");
        }
        if(user.getEmail() == null) {
            throw new BaseException("Please input email address");
        }
        try {
            Session session = HibernateUtil.getSession();
            org.hibernate.Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<bean_user_information> loadAllUser() {
        Query query = HibernateUtil.getSession().createQuery("from bean_user_information ");
        return query.list();
    }

    public void deleteUser(String userId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        /*Throw BaseException*/
        Query query1 = session.createQuery("from bean_pet_information where pet_owner like ? ");
        query1.setParameter(0, userId);
        if (query1.list().size() != 0)
            throw new BaseException("This user has pet information, can't delete.");

        Query query2 = session.createQuery("from bean_order where user_id like ? and transfer_state not like ? ");
        query2.setParameter(0, userId);
        query2.setParameter(1, "Accomplished");
        if (query2.list().size() != 0)
            throw new BaseException("This user has unaccomplished order, can't delete.");

        /*Delete*/
        transaction.begin();
        try {
            String hql = "from bean_pet_information p where p.pet_owner = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userId);
            List<bean_pet_information> petlist = query.list();
            if(petlist.size() != 0) {
                throw new BaseException("The user has pet.");
            }

            bean_user_information user = session.get(bean_user_information.class, userId);
            session.delete(user);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public List<bean_user_information> retrieveUserById(String keyword) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<bean_user_information> result = null;
        try {
            transaction.begin();

            Query query = session.createQuery("from bean_user_information where user_id like ? ");
            query.setParameter(0, "%" + keyword + "%");
            result =  query.list();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public List<bean_user_information> retrieveUserByName(String keyword) throws BaseException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<bean_user_information> result = null;
        try {
            transaction.begin();

            Query query = session.createQuery("from bean_user_information where user_name like ? ");
            query.setParameter(0, "%" + keyword + "%");
            result =  query.list();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return result;
    }

    public void modifyUserPassword(String userId, String oldPassword, String newPassword, String confirmPassword) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        bean_user_information user = session.get(bean_user_information.class, userId);
        if (user.getPassword().equals(oldPassword) == false) {
            throw new BaseException("The Password is not correct.");
        }
        if (oldPassword.equals("")) {
            throw new BaseException("Please input the old password.");
        }
        if (newPassword.equals("")) {
            throw new BaseException("Please input the new password.");
        }
        if (confirmPassword.equals("")) {
            throw new BaseException("Please input the confirm password.");
        }
        if (newPassword.equals(confirmPassword) == false) {
            throw new BaseException("The new password and confirm password are not match.");
        }
        try {
            transaction.begin();

            user.setPassword(newPassword);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyUser(bean_user_information user) throws BaseException{
        if (user.getPassword() == null)
            JOptionPane.showMessageDialog(null, "Please input the password.", "Hint", JOptionPane.ERROR_MESSAGE);
        if (user.getUser_name() == null)
            JOptionPane.showMessageDialog(null, "Please input the name of user.", "Hint", JOptionPane.ERROR_MESSAGE);
        if (user.getAuthority() == null)
            JOptionPane.showMessageDialog(null, "Please input the authority of user.", "Hint", JOptionPane.ERROR_MESSAGE);
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.update(user);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
