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

public class TypeManagement {
    public String typeName(int typeid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        String result = "";
        try {
            transaction.begin();

            bean_products_types ty = session.get(bean_products_types.class, typeid);
            result = ty.getType_name();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<bean_products_types> reloadAllTypes() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from bean_products_types ");
        return query.list();
    }

    public void addType(bean_products_types t) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        if (t.getType_name().equals(""))
            throw new BaseException("Please input the name of type.");
        try {
            transaction.begin();

            session.save(t);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteType(int typeId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        Query query = session.createQuery("from bean_products_information where types = ? ");
        query.setParameter(0, typeId);
        if (query.list().size() != 0)
            throw new BaseException("This type has product, can't delete.");

        try {
            transaction.begin();

            bean_products_types t = session.get(bean_products_types.class, typeId);
            session.delete(t);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyType(bean_products_types t) throws BaseException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        if (t.getType_name().equals(""))
            throw new BaseException("Please input the name of type.");
        try {
            transaction.begin();

            session.update(t);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
