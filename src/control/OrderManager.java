package control;

import java.util.List;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.BaseException;
import util.HibernateUtil;

public class OrderManager {
    public List<bean_order> loadAllOrder() {
        Query query = HibernateUtil.getSession().createQuery("from bean_order ");
        return query.list();
    }

    public void addOrder(bean_order newOrder) throws BaseException{
        if (newOrder.getUser_id() == null)
            throw new BaseException("Please input the user id.");
        if (newOrder.getOrder_begin_time() == null)
            throw new BaseException("Please input the begin time.");
        if (newOrder.getTransfer_state() == null)
            throw new BaseException("Please input the transaction state.");
        try {
            Session session = HibernateUtil.getSession();
            Transaction transaction = session.getTransaction();

            transaction.begin();
            session.save(newOrder);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double orderPrice(bean_order ord) {
        String hql = "select p.price from bean_products_information p where p.product_id  = ? ";
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery(hql);
        int k = ord.getProduct_id().intValue();
        query.setParameter(0, k);
        List list = query.list();
        if (list.size() != 0) {
            double result = new Double(list.get(0).toString()).doubleValue() * ord.getQuantity();
            return result;
        }
        else
            return 0;
    }

    public void deleteOrder(int orderId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        try {
            transaction.begin();
            bean_order o = (bean_order) session.get(bean_order.class, orderId);
            session.delete(o);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public List<bean_order> retrieveOrder(String userid) {
        Session session = HibernateUtil.getSession();
        List<bean_order> result = null;
        try {
            Query query = session.createQuery("from bean_order  where user_id like ? ");
            query.setParameter(0, "%" + userid + "%");
            result = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<bean_order> retrieveOrderByUserIdAccurate(String userid) {
        Session session = HibernateUtil.getSession();
        List<bean_order> result = null;
        try {
            Query query = session.createQuery("from bean_order  where user_id like ? ");
            query.setParameter(0, userid);
            result = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void modifyOrder(bean_order order) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        if (order.getProduct_id() == null)
            throw new BaseException("Please input the product id of order.");
        if (order.getOrder_begin_time() == null)
            throw new BaseException("Please input begin time.");
        if (order.getUser_id() == null)
            throw new BaseException("Please input user id.");
        if (order.getTransfer_state() == null)
            throw new BaseException("Please select the transfer state of order.");
        try {
            transaction.begin();

            session.update(order);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
