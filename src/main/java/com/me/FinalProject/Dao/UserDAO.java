package com.me.FinalProject.Dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.me.FinalProject.Model.Account;
import com.me.FinalProject.Model.Transaction;
import com.me.FinalProject.Model.User;
import com.me.FinalProject.Model.UserProfile;
import com.me.FinalProject.Model.Payee;

public class UserDAO extends DAO {
	public User queryUserByNameAndPassword(String name, String password)
			throws Exception {
		try {
			// begin();

			Query q = getSession()
					.createQuery(
							"from User where userName = :name and userPassword = :password");
			q.setString("name", name);
			q.setString("password", password);
			User user = (User) q.uniqueResult();
			// commit();
			return user;
		} catch (HibernateException e) {
			// rollback();
			return null;
		}
	}

	public Account getAccount(String userName) throws Exception {
		try {
			// begin();
			Query q = getSession().createQuery(
					"from Account where userName = :id");
			q.setString("id", userName);
			Account account = (Account) q.uniqueResult();
			// commit();

			return account;
		} catch (HibernateException e) {
			// rollback();
			return null;
		}
	}

	public Account getAccountbyAccno(int accno) throws Exception {
		try {
			// begin();
			Query q = getSession().createQuery(
					"from Account where accountNumber = :id");
			q.setInteger("id", accno);
			Account account = (Account) q.uniqueResult();
			// commit();

			return account;
		} catch (HibernateException e) {
			// rollback();
			return null;
		}
	}

	public User getUserByName(String name) throws Exception {
		try {
			// begin();
			Query q = getSession().createQuery(
					"from User where userName = :username");
			q.setString("username", name);

			User user = (User) q.uniqueResult();

			// commit();
			return user;
		} catch (HibernateException e) {
			// rollback();
			return null;
		}
	}

	public int addNewUser(User u) throws Exception {
		try {
			int update = 1;

			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();

			u.setUserRole("customer");

			User user = u;
			Account account = u.getAccount();
			account.setUser(user);
			UserProfile userProfile = u.getUserProfile();
			userProfile.setUser(user);

			session.save(user);
			session.save(account);
			session.save(userProfile);
			session.getTransaction().commit();

			return update;

		} catch (HibernateException e) {
			return 0;
		}
	}

	public User deposit(String userName, double amount) {
		double balance = 0;
		double updatedBalance = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String d = dateFormat.format(cal.getTime());

		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		if (user != null) {
			Account account = user.getAccount();
			balance = user.getAccount().getBalance();
			updatedBalance = balance + amount;
			account.setBalance(updatedBalance);
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			session.update(account);
			session.getTransaction().commit();
			session.close();

			Session session2 = getSession().getSessionFactory().openSession();
			session2.beginTransaction();
			Transaction tx = new Transaction();
			tx.setAccountNumber(user.getAccount().getAccountNumber());
			tx.setAmount(amount);
			tx.setBalance(updatedBalance);
			tx.setDate(d);
			tx.setTransactionType("Credit");
			tx.setUserName(userName);
			tx.setDescription("Check Deposit");
			tx.setCheck(userName);
			session2.save(tx);
			session2.getTransaction().commit();
			session2.close();

			return user;
		} else {
			return null;
		}
	}

	public User withdraw(String userName, double amount) {
		double balance = 0;
		double updatedBalance = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String d = dateFormat.format(cal.getTime());
		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		if (user != null) {
			Account account = user.getAccount();
			balance = user.getAccount().getBalance();
			if (balance < amount) {
				return null;
			}
			updatedBalance = balance - amount;
			account.setBalance(updatedBalance);
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			session.update(account);
			session.getTransaction().commit();
			session.close();
			Session session2 = getSession().getSessionFactory().openSession();
			session2.beginTransaction();
			Transaction tx = new Transaction();
			tx.setAccountNumber(user.getAccount().getAccountNumber());
			tx.setAmount(amount);
			tx.setBalance(updatedBalance);
			tx.setDate(d);
			tx.setTransactionType("Debit");
			tx.setUserName(userName);
			tx.setCheck(null);
			tx.setDescription("Withdrawal");
			session2.save(tx);
			session2.getTransaction().commit();
			session2.close();
			return user;
		} else {
			return null;
		}
	}

	public List<Payee> getPayee(String userName)

	{
		Query q = getSession().createQuery(
				"from Payee where userName = :username");
		q.setString("username", userName);
		@SuppressWarnings("unchecked")
		List<Payee> payee = q.list();

		return payee;

	}

	public double getBalance(String userName) {
		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		JOptionPane.showMessageDialog(null, user.getUserName());
		double balance = user.getAccount().getBalance();
		return balance;
	}

	public User transfer(String userName, double amount, String payee) {
		double balance = 0;
		double updatedBalance = 0;
		double payeeBalance = 0;
		double updatedPayeeBalance = 0;

		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		if (user != null) {
			Account userAccount = user.getAccount();
			balance = userAccount.getBalance();
			Query q1 = getSession().createQuery(
					"from User where userName = :username");
			q1.setString("username", payee);
			User payeeUser = (User) q1.uniqueResult();
			Account payeeAccount = payeeUser.getAccount();
			payeeBalance = payeeAccount.getBalance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			String d = dateFormat.format(cal.getTime());
			if (balance <= 0) {
				return null;
			} else if (balance < amount) {
				return null;
			} else {
				updatedBalance = balance - amount;
				updatedPayeeBalance = payeeBalance + amount;
				Session session = getSession().getSessionFactory()
						.openSession();
				session.beginTransaction();
				userAccount.setBalance(updatedBalance);
				session.update(userAccount);
				session.getTransaction().commit();
				session.close();

				Session session1 = getSession().getSessionFactory()
						.openSession();
				session1.beginTransaction();
				payeeAccount.setBalance(updatedPayeeBalance);
				session1.update(payeeAccount);
				session1.getTransaction().commit();
				session1.close();

				Session session2 = getSession().getSessionFactory()
						.openSession();
				session2.beginTransaction();
				Transaction tx = new Transaction();
				tx.setAccountNumber(userAccount.getAccountNumber());
				tx.setAmount(amount);
				tx.setBalance(updatedBalance);
				tx.setDate(d);
				tx.setTransactionType("Debit");
				tx.setDescription("Account Transfer to "+payeeUser.getUserProfile().getFirstName());
				tx.setUserName(user.getUserName());
				session2.save(tx);
				session2.getTransaction().commit();
				session2.close();

				Session session3 = getSession().getSessionFactory()
						.openSession();
				session3.beginTransaction();
				Transaction tx1 = new Transaction();
				tx1.setAccountNumber(payeeAccount.getAccountNumber());
				tx1.setAmount(amount);
				tx1.setBalance(updatedPayeeBalance);
				tx1.setDate(d);
				tx1.setTransactionType("Credit");
				tx1.setUserName(payeeUser.getUserName());
				tx1.setDescription("Deposit from "+ user.getUserProfile().getFirstName());
				session3.save(tx1);
				session3.getTransaction().commit();
				session3.close();

				return user;
			}
		} else {
			return null;
		}
	}

	public User emailtransfer(String userName, double amount, String mail) {
		double balance = 0;
		double updatedBalance = 0;
		double payeeBalance = 0;
		double updatedPayeeBalance = 0;

		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		Account userAccount = user.getAccount();
		balance = userAccount.getBalance();
		Query q1 = getSession().createQuery(
				"from UserProfile where email = :mail");
		q1.setString("mail", mail);
		UserProfile up = (UserProfile) q1.uniqueResult();

		if (up != null) {
			User payeeUser = (User) up.getUser();
			Account payeeAccount = payeeUser.getAccount();
			payeeBalance = payeeAccount.getBalance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			String d = dateFormat.format(cal.getTime());
			if (balance <= 0) {
				return null;
			} else if (balance < amount) {
				return null;
			} else {
				updatedBalance = balance - amount;
				updatedPayeeBalance = payeeBalance + amount;
				Session session = getSession().getSessionFactory()
						.openSession();
				session.beginTransaction();
				userAccount.setBalance(updatedBalance);
				session.update(userAccount);
				session.getTransaction().commit();
				session.close();

				Session session1 = getSession().getSessionFactory()
						.openSession();
				session1.beginTransaction();
				payeeAccount.setBalance(updatedPayeeBalance);
				session1.update(payeeAccount);
				session1.getTransaction().commit();
				session1.close();

				Session session2 = getSession().getSessionFactory()
						.openSession();
				session2.beginTransaction();
				Transaction tx = new Transaction();
				tx.setAccountNumber(userAccount.getAccountNumber());
				tx.setAmount(amount);
				tx.setBalance(updatedBalance);
				tx.setDate(d);
				tx.setTransactionType("Debit");
				tx.setDescription("Account Transfer to "+payeeUser.getUserProfile().getFirstName());
				tx.setUserName(userName);
				session2.save(tx);
				session2.getTransaction().commit();
				session2.close();

				Session session3 = getSession().getSessionFactory()
						.openSession();
				session3.beginTransaction();
				Transaction tx1 = new Transaction();
				tx1.setAccountNumber(payeeAccount.getAccountNumber());
				tx1.setAmount(amount);
				tx1.setBalance(updatedPayeeBalance);
				tx1.setDate(d);
				tx1.setTransactionType("Credit");
				tx1.setDescription("Deposit from "+ user.getUserProfile().getFirstName());
				tx1.setUserName(payeeUser.getUserName());
				session3.save(tx1);
				session3.getTransaction().commit();
				session3.close();

				return user;

			}
		} else {
			return null;
		}
	}

	public User mobiletransfer(String userName, double amount, String mobile) {
		double balance = 0;
		double updatedBalance = 0;
		double payeeBalance = 0;
		double updatedPayeeBalance = 0;

		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		Account userAccount = user.getAccount();
		balance = userAccount.getBalance();
		Query q1 = getSession().createQuery(
				"from UserProfile where mobileNumber = :mobile");
		q1.setString("mobile", mobile);
		UserProfile up = (UserProfile) q1.uniqueResult();

		if (up != null) {
			User payeeUser = (User) up.getUser();
			Account payeeAccount = payeeUser.getAccount();
			payeeBalance = payeeAccount.getBalance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			String d = dateFormat.format(cal.getTime());
			if (balance <= 0) {
				return null;
			} else if (balance < amount) {
				return null;
			} else {
				updatedBalance = balance - amount;
				updatedPayeeBalance = payeeBalance + amount;
				Session session = getSession().getSessionFactory()
						.openSession();
				session.beginTransaction();
				userAccount.setBalance(updatedBalance);
				session.update(userAccount);
				session.getTransaction().commit();
				session.close();

				Session session1 = getSession().getSessionFactory()
						.openSession();
				session1.beginTransaction();
				payeeAccount.setBalance(updatedPayeeBalance);
				session1.update(payeeAccount);
				session1.getTransaction().commit();
				session1.close();

				Session session2 = getSession().getSessionFactory()
						.openSession();
				session2.beginTransaction();
				Transaction tx = new Transaction();
				tx.setAccountNumber(userAccount.getAccountNumber());
				tx.setAmount(amount);
				tx.setBalance(updatedBalance);
				tx.setDate(d);
				tx.setTransactionType("Debit");
				tx.setDescription("Account Transfer to "+payeeUser.getUserProfile().getFirstName());
				tx.setUserName(userName);
				session2.save(tx);
				session2.getTransaction().commit();
				session2.close();

				Session session3 = getSession().getSessionFactory()
						.openSession();
				session3.beginTransaction();
				Transaction tx1 = new Transaction();
				tx1.setAccountNumber(payeeAccount.getAccountNumber());
				tx1.setAmount(amount);
				tx1.setBalance(updatedPayeeBalance);
				tx1.setDate(d);
				tx1.setTransactionType("Credit");
				tx1.setDescription("Deposit from "+ user.getUserProfile().getFirstName());
				tx1.setUserName(payeeUser.getUserName());
				session3.save(tx1);
				session3.getTransaction().commit();
				session3.close();

				return user;
			}

		} else {
			return null;
		}
	}

	public List<Transaction> getTransaction(String name) throws Exception {
		try {
			Query q = getSession().createQuery(
					"from Transaction where userName = :name");
			q.setString("name", name);
			@SuppressWarnings("unchecked")
			List<Transaction> tx = q.list();
			return tx;
		} catch (HibernateException e) {
			return null;
		}
	}

	public List<Transaction> getlast5Transaction(String name) throws Exception {
		try {
			Query q = getSession()
					.createQuery(
							"from Transaction where userName = :name order by transactionId desc");
			q.setString("name", name);
			q.setMaxResults(5);
			@SuppressWarnings("unchecked")
			List<Transaction> tx = q.list();
			return tx;
		} catch (HibernateException e) {
			return null;
		}
	}

	public void addPayee(String payeeUsername, int accountno, User user)
			throws Exception {
		try {
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			Payee payee = new Payee();
			payee.setPayeeAccountNumber(accountno);
			payee.setPayeeUserName(payeeUsername);
			payee.setUserAccount(user.getAccount().getAccountNumber());
			payee.setUserName(user.getUserName());
			session.save(payee);
			session.getTransaction().commit();
			session.close();

		} catch (HibernateException e) {
			throw new Exception("Could not add payee ");
		}
	}

	public User updateAddress(String userName, String address) throws Exception {
		try {
			Query q = getSession().createQuery(
					"from UserProfile where userName = :name");
			q.setString("name", userName);
			UserProfile userProfile = (UserProfile) q.uniqueResult();
			userProfile.setAddress(address);
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			session.update(userProfile);
			session.getTransaction().commit();
			session.close();
			return userProfile.getUser();
		} catch (HibernateException e) {
			return null;
		}
	}

	public int updateMobile(String oldNumber, String newNumber, String userName)
			throws Exception {
		try {
			Query q = getSession().createQuery(
					"from UserProfile where userName = :name");
			q.setString("name", userName);
			UserProfile userProfile = (UserProfile) q.uniqueResult();
			if (!userProfile.getMobileNumber().equalsIgnoreCase(oldNumber)) {
				return 2;
			} else {
				Session session = getSession().getSessionFactory()
						.openSession();
				userProfile.setMobileNumber(newNumber);
				session.beginTransaction();
				session.update(userProfile);
				session.getTransaction().commit();
				session.close();
				return 1;
			}

		} catch (HibernateException e) {
			return 0;
		}
	}

	public int updateEmail(String oldEmail, String newEmail, String userName)
			throws Exception {
		try {
			Query q = getSession().createQuery(
					"from UserProfile where userName = :name");
			q.setString("name", userName);
			UserProfile userProfile = (UserProfile) q.uniqueResult();
			if (!userProfile.getEmail().equalsIgnoreCase(oldEmail)) {
				return 2;
			} else {
				Session session = getSession().getSessionFactory()
						.openSession();
				userProfile.setEmail(newEmail);
				session.beginTransaction();
				session.update(userProfile);
				session.getTransaction().commit();
				session.close();
				return 1;
			}

		} catch (HibernateException e) {
			return 0;
		}
	}

	public int updatePassword(String oldPassword, String newPassword,
			String userName) throws Exception {
		try {
			Query q = getSession().createQuery(
					"from User where userName = :name");
			q.setString("name", userName);
			User user = (User) q.uniqueResult();
			if (!user.getUserPassword().equals(oldPassword)) {
				return 2;
			} else {
				Session session = getSession().getSessionFactory()
						.openSession();
				user.setUserPassword(newPassword);
				session.beginTransaction();
				session.update(user);
				session.getTransaction().commit();
				session.close();
				return 1;
			}

		} catch (HibernateException e) {
			return 0;
		}
	}

	public User billPay(String userName, double amount, String biller) {
		double balance = 0;
		double updatedBalance = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String d = dateFormat.format(cal.getTime());
		Query q = getSession().createQuery(
				"from User where userName = :username");
		q.setString("username", userName);
		User user = (User) q.uniqueResult();
		if (user != null) {
			Account account = user.getAccount();
			balance = user.getAccount().getBalance();
			if (balance < amount) {
				return null;
			}
			updatedBalance = balance - amount;
			account.setBalance(updatedBalance);
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			session.update(account);
			session.getTransaction().commit();
			session.close();
			Session session2 = getSession().getSessionFactory().openSession();
			session2.beginTransaction();
			Transaction tx = new Transaction();
			tx.setAccountNumber(user.getAccount().getAccountNumber());
			tx.setAmount(amount);
			tx.setBalance(updatedBalance);
			tx.setDate(d);
			tx.setTransactionType("Debit");
			tx.setUserName(userName);
			tx.setDescription(biller);
			session2.save(tx);
			session2.getTransaction().commit();
			session2.close();
			return user;
		} else {
			return null;
		}
	}

	public String getMail(String userName) throws Exception {
		try {
			// begin();
			Query q = getSession()
					.createQuery("from User where userName = :id");
			q.setString("id", userName);
			User user = (User) q.uniqueResult();
			String email = user.getUserProfile().getEmail();
			// commit();

			return email;
		} catch (HibernateException e) {
			// rollback();
			return "failure";
		}
	}

	public String generateOtp() {
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			numbers.add(i);
		}

		Collections.shuffle(numbers);

		String result = "";
		for (int i = 0; i < 4; i++) {
			result += numbers.get(i).toString();
		}
		return result;
	}

	public int addOtp(String userName, String otp) throws Exception {
		try {
			// begin();
			Query q = getSession()
					.createQuery("from User where userName = :id");
			q.setString("id", userName);
			User user = (User) q.uniqueResult();
			Session session = getSession().getSessionFactory().openSession();
			session.beginTransaction();
			UserProfile userProfile = user.getUserProfile();
			if (userProfile != null) {
				userProfile.setOtp(otp);
				session.update(userProfile);
				session.getTransaction().commit();
				session.close();
				return 1;
			} else {
				return 0;
			}
		} catch (HibernateException e) {
			// rollback();
			return 0;
		}
	}

	public UserProfile getOtp(String userName, String otp) {
		Query q = getSession().createQuery(
				"from UserProfile where userName = :id and otp = :otp ");
		q.setString("id", userName);
		q.setString("otp", otp);
		UserProfile user = (UserProfile) q.uniqueResult();
		return user;
	}

	public void changePassword(String newPassword, String userName)
			throws Exception {
		try {
			Query q = getSession().createQuery(
					"from User where userName = :name");
			q.setString("name", userName);
			User user = (User) q.uniqueResult();
			if (user != null) {
				Session session = getSession().getSessionFactory()
						.openSession();
				user.setUserPassword(newPassword);
				session.beginTransaction();
				session.update(user);
				session.getTransaction().commit();
				session.close();
			}

		} catch (HibernateException e) {
			throw new Exception("Could not update Email ID");
		}
	}
}
