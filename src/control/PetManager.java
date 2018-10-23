package control;

import model.*;

import java.sql.Blob;
import java.util.*;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.sql.*;
import util.*;

import javax.swing.*;
import java.io.*;

public class PetManager {
    public List<bean_pet_information> loadAllPet() {
        Session session = HibernateUtil.getSession();
        List<bean_pet_information> petList = session.createQuery("from bean_pet_information ").list();

        return petList;
    }

    public void addPetInf(bean_pet_information pet) throws BaseException{
        if (pet.getPet_name() == null)
            throw new BaseException("Please input the name of pet.");
        if (pet.getPet_owner() == null)
            throw new BaseException("Please input the owner of pet.");
        if (pet.getPet_age() == null)
            throw new BaseException("Please input the age of pet.");
        if (pet.getHealthy() == null)
            throw new BaseException("Please input the health of pet.");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(pet);
            transaction.commit();
        } catch (HibernateException e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void writePicture(bean_pet_information pet, String filepath) throws IOException{
        Session session = HibernateUtil.getSession();
        File file = new File(filepath);
        InputStream input = new FileInputStream(file);
        try {
            Blob image = Hibernate.getLobCreator(session).createBlob(input, input.available());
            pet.setPicture_path(image);
            addPetInf(pet);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            input.close();
        }
    }

    public void readPicture(int petid, String imagePath) {
        Session sessoin = HibernateUtil.getSession();
        bean_pet_information pet = sessoin.get(bean_pet_information.class, petid);
        Blob image = pet.getPicture_path();
        try {
            InputStream input = image.getBinaryStream();
            File file = new File(imagePath);
            OutputStream output = new FileOutputStream(file);
            byte[] buff = new byte[input.available()];

            input.read(buff);
            output.write(buff);
            input.close();
            output.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePetInf(int petId) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        /*Throw BaseException*/
        Query query = session.createQuery("from bean_appointment where pet_id = ? ");
        query.setParameter(0, petId);
        if (query.list().size() != 0)
            throw new BaseException("This pet has appointment, can't delete.");

        try {
            transaction.begin();

            bean_pet_information pet = (bean_pet_information) session.get(bean_pet_information.class, petId);
            session.delete(pet);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public List<bean_pet_information> retrievePet(String keyword){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<bean_pet_information> result = null;
        try {
            transaction.begin();

            Query query = session.createQuery("from bean_pet_information where nick_name like ? ");
            query.setParameter(0, "%" + keyword + "%");
            result = query.list();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<bean_pet_information> retrievePetByUserId(String userid) {
        Session session = HibernateUtil.getSession();
        List<bean_pet_information> result = new ArrayList<>();
        try {
            Query query = session.createQuery("from bean_pet_information where pet_owner like ? ");
            query.setParameter(0, userid);
            result = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void modifyPet(bean_pet_information pet) throws BaseException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
//        bean_pet_information pet = session.get(bean_pet_information.class, petid);
        if (pet.getPet_name() == null)
            throw new BaseException("Please input the name of pet.");
        if (pet.getNick_name() == null)
            throw new BaseException("Please input the nick name.");
        if (pet.getPet_owner() == null)
            throw new BaseException("Please input the user id of pet.");
        if (pet.getHealthy() == null)
            throw new BaseException("Please select the healthy state of pet.");
        try {
            transaction.begin();

            session.update(pet);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public String petNickName(int petId) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        bean_pet_information pet = session.get(bean_pet_information.class, petId);

        String result = "";
        result = pet.getNick_name();
        return result;
    }
}
