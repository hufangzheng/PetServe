package control;

import java.util.ArrayList;
import java.util.List;

import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.BaseException;
import util.HibernateUtil;

public class ProductManager {
    public List<bean_products_information> loadAllProducts() {
        return HibernateUtil.getSession().createQuery("from bean_products_information ").list();
    }

    public void addProduct(bean_products_information newPro) throws BaseException {
        if (newPro.getProduct_name() == null)
            throw new BaseException("Please input the name of product.");
        if (newPro.getTypes() == null)
            throw new BaseException("Please input the type of product.");
        if (newPro.getProduct_code() == null)
            throw new BaseException("Please input the code of product.");
        if (newPro.getPrice() == null)
            throw new BaseException("Please input the price of product.");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.save(newPro);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int proId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        /*Throw BaseException*/
        Query query = session.createQuery("from bean_order where product_id = ? and transfer_state not like ? ");
        query.setParameter(0, proId);
        query.setParameter(1, "Accomplished");
        if (query.list().size() != 0)
            throw new BaseException("This product has unaccomplished order information, can't delete.");

        Query query2 = session.createQuery("from bean_appointment where product_id = ? ");
        query2.setParameter(0, proId);
        if (query2.list().size() != 0)
            throw new BaseException("This product has appointment information, can't delete.");

        /*Delete*/
        try {
            transaction.begin();

            bean_products_information p = session.get(bean_products_information.class, proId);
            session.delete(p);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public List<bean_products_information> retrieveProduct(String keyword, String typeName) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<bean_products_information> result = new ArrayList<bean_products_information>();
        try {
            transaction.begin();

            if (typeName.equals("") == false) {
                Query query = session.createQuery
                        ("select  p.product_id from bean_products_information p, bean_products_types t " +
                                "where p.product_name like ? and t.type_name like ? and p.types = t.type_id");
                query.setParameter(0, "%" + keyword + "%");
                query.setParameter(1, typeName);
                List l = query.list();

                Query query1 = session.createQuery("from bean_products_information where product_id = ?");
                if (l.size() != 0) {
                    for (int i = 0; i < l.size(); i ++) {
                        query1.setParameter(0, l.get(i));
                        result.add((bean_products_information) query1.list().get(0));
                    }
                }

            }
            else {
                Query query = session.createQuery("from bean_products_information ");
                result = query.list();
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void buyProduct(int productId, String quantity) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            bean_order newOrder = new bean_order();
            newOrder.setOrder_begin_time(new java.sql.Date(System.currentTimeMillis()));
            newOrder.setQuantity(new Integer(quantity).intValue());
            newOrder.setProduct_id(productId);
            newOrder.setTransfer_state("Prepare Transport");
            newOrder.setOrder_price(new OrderManager().orderPrice(newOrder));
            newOrder.setUser_id(UserManager.currentUser.getUser_id());

            session.save(newOrder);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void modifyProduct(bean_products_information product) throws BaseException{
        if (product.getProduct_name() == null)
            throw new BaseException("Please input the name of product.");
        if (product.getTypes() == null)
            throw new BaseException("Please input the type of product.");
        if (product.getProduct_code() == null)
            throw new BaseException("Please input the code of product.");
        if (product.getPrice() == null)
            throw new BaseException("Please input the price of product.");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.update(product);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public String productName(int productId) {
        String result = "";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            bean_products_information product = session.get(bean_products_information.class, productId);
            result = product.getProduct_name();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
