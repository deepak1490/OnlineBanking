package com.me.FinalProject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.me.FinalProject.Model.Account;
import com.me.FinalProject.Model.CheckDeposit;
import com.me.FinalProject.Model.Payee;
import com.me.FinalProject.Model.Transaction;
import com.me.FinalProject.Model.User;
import com.me.FinalProject.Model.UserProfile;
import com.me.FinalProject.Util.SendMail;
import com.me.FinalProject.Dao.UserDAO;
import com.me.FinalProject.Validator.UserValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private HttpSession session;
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	@Autowired
	private UserDAO userDao;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private UserValidator userValidator;

	@InitBinder("user")
	private void initUserBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/")
	public String initUserLoginForm(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		User user = new User();
		User u = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length >= 2) {
			try {
				for (int i = 0; i <= cookies.length; i++) {
					u = userDao.queryUserByNameAndPassword(
							cookies[i].getValue(), cookies[i + 1].getValue());

					if (u != null) {
						session = request.getSession();
						session.setAttribute("user", u);
						model.addAttribute("user", u);
						model.addAttribute("userProfile", u.getUserProfile());
						model.addAttribute("account", u.getAccount());
						return "Dashboard";
					}
				}
			} catch (Exception e) {
				return "error";
			}
		}
		model.addAttribute("user", user);
		return "home";
	}

	/*
	 * Process From request
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitForm(Model model, @Validated User user,
			BindingResult result, HttpServletResponse response,
			HttpServletRequest request) {
		String remember = (request.getParameter("Remember") != null ? "Remember"
				: "No");
		try {
			User u = userDao.queryUserByNameAndPassword(user.getUserName(),
					user.getUserPassword());

			if (u != null) {
				session = request.getSession();
				session.setAttribute("user", u);
				if (u.getUserRole().equalsIgnoreCase("admin")) {
					User user1 = new User();
					model.addAttribute("user", user1);
					return "registration1";
				}

				if (remember != null && remember.equalsIgnoreCase("Remember")) {
					Cookie userName = new Cookie("userName", user.getUserName());
					userName.setMaxAge(3600 * 24 * 7);
					response.addCookie(userName);
					Cookie userPassword = new Cookie("userPassword",
							user.getUserPassword());
					userPassword.setMaxAge(3600 * 24 * 7);
					response.addCookie(userPassword);
				}

				model.addAttribute("user", u);
				model.addAttribute("userProfile", u.getUserProfile());
				model.addAttribute("account", u.getAccount());
				return "Dashboard";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
		}
		return "home";
	}

	@RequestMapping(value = "logout")
	public String logout(Model model, HttpServletResponse response) {
		// model.addAttribute("response", response);

		Cookie userName = new Cookie("userName", "");
		userName.setMaxAge(0);
		response.addCookie(userName);
		Cookie userPassword = new Cookie("userPassword", "");
		userPassword.setMaxAge(0);
		response.addCookie(userPassword);
		session.removeAttribute("user");
		model.addAttribute(response);
		return "logout";
	}

	@RequestMapping(value = "registration1")
	public String goToRegistrationPage(Model model) {
		User user = new User();
		UserProfile userProfile = new UserProfile();
		Account account = new Account();
		user.setUserProfile(userProfile);
		user.setAccount(account);
		model.addAttribute("user", user);
		return "registration1";
	}

	@RequestMapping(value = "userRegistration", method = RequestMethod.POST)
	public String sendMessage(Model model, @Validated User user,
			BindingResult result) {
		model.addAttribute("user", user);
		if (result.hasErrors()) {
			return "registration";
		} else {
			try {
				User u = userDao.getUserByName(user.getUserName());
				if (u != null) {
					String error = "UserName already exist. Please enter a different UserName!";
					model.addAttribute("error", error);
					return "registration1";
				} else {
					int update = userDao.addNewUser(user);
					if (update == 1) {
						JOptionPane.showMessageDialog(null,
								"User registered successfully");
						User user1 = new User();
						model.addAttribute("user", user1);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "error";
			}

		}
		User user2 = new User();
		model.addAttribute("user", user2);
		return "registration1";
	}

	@RequestMapping(value = "Dashboard")
	public String Dashboard(Model model, User user, HttpServletRequest request)
			throws Exception {

		session = request.getSession();
		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		model.addAttribute("userProfile", u.getUserProfile());
		model.addAttribute("account",u.getAccount());
		return "Dashboard";
	}

	@RequestMapping(value = "Deposit")
	public String deposit(Model model, User user, CheckDeposit cPic)
			throws Exception {

		User u = (User) session.getAttribute("user");
		CheckDeposit cdPic = new CheckDeposit();
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		model.addAttribute("pPic", cdPic);
		return "Deposit";
	}

	@RequestMapping(value = "doDeposit", method = RequestMethod.POST)
	public String doDeposit(Model model, CheckDeposit pPic,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount) throws Exception {

		User user = userDao.deposit(userName, amount);
		model.addAttribute("account", user.getAccount());
		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		CommonsMultipartFile file = pPic.getCheck();

		File localFile = new File(
				"C:\\Users\\Deepak\\Documents\\workspace-sts-3.6.4.RELEASE\\OnlineBanking\\src\\main\\webapp\\resources\\images\\Check\\",
				u.getUserName() + ".jpg");
		try {
			file.transferTo(localFile);
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}

		model.addAttribute("pPic", pPic);
		return "doDeposit";

	}

	@RequestMapping(value = "Withdraw")
	public String withdraw(Model model, User user) throws Exception {

		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		return "Withdraw";
	}

	@RequestMapping(value = "doWithdraw", method = RequestMethod.POST)
	public String doWithdraw(Model model,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount) {
		User u = (User) session.getAttribute("user");
		User user = userDao.withdraw(userName, amount);
		if (user != null) {
			model.addAttribute("userProfile", user.getUserProfile());
			model.addAttribute("account", user.getAccount());
			return "doWithdraw";
		} else {
			JOptionPane.showMessageDialog(null,
					"Insufficient funds in the account, enter lesser amount");
			model.addAttribute("user", u);
			model.addAttribute("account", u.getAccount());
			return "Withdraw";
		}
	}

	@RequestMapping(value = "Transfer")
	public String transfer(Model model, User user) throws Exception {

		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		List<Payee> payee = userDao.getPayee(u.getUserName());
		model.addAttribute("payee", payee);
		return "Transfer";
	}

	@RequestMapping(value = "doTransfer", method = RequestMethod.POST)
	public String doTransfer(Model model,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount,
			@RequestParam("payee") String payee) {
		User u = userDao.transfer(userName, amount, payee);
		if (u != null) {
			model.addAttribute("userProfile", u.getUserProfile());
			model.addAttribute("account", u.getAccount());
			return "doTransfer";
		} else {
			double balance = userDao.getBalance(userName);
			if (balance == 0) {
				JOptionPane
						.showMessageDialog(null,
								"Zero balance in account. Please deposit to continue with transfer");
			} else if (balance < amount) {
				JOptionPane
						.showMessageDialog(null,
								"Insufficient funds in the account, enter lesser amount");
			}
		}
		User u2 = (User) session.getAttribute("user");
		model.addAttribute("user", u2);
		model.addAttribute("account", u2.getAccount());
		List<Payee> payee1 = userDao.getPayee(u2.getUserName());
		model.addAttribute("payee", payee1);
		return "Transfer";
	}

	@RequestMapping(value = "EmailTransfer")
	public String emailTransfer(Model model, User user2) throws Exception {

		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		return "EmailTransfer";
	}

	@RequestMapping(value = "doEmailTransfer", method = RequestMethod.POST)
	public String doEmailTransfer(Model model,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount,
			@RequestParam("email") String email) {
		User u = userDao.emailtransfer(userName, amount, email);
		if (u != null) {
			model.addAttribute("userProfile", u.getUserProfile());
			model.addAttribute("account", u.getAccount());
			return "doEmailTransfer";
		} else {
			double balance = userDao.getBalance(userName);
			if (balance == 0) {
				JOptionPane
						.showMessageDialog(null,
								"Zero balance in account. Please deposit to continue with transfer");
			} else if (balance < amount) {
				JOptionPane
						.showMessageDialog(null,
								"Insufficient funds in the account, enter lesser amount");
			}
			if(u==null){
				JOptionPane.showMessageDialog(null, "Payee not found");
			}
		}
		User u2 = (User) session.getAttribute("user");
		model.addAttribute("user", u2);
		model.addAttribute("account", u2.getAccount());
		return "EmailTransfer";
	}

	@RequestMapping(value = "MobileTransfer")
	public String mobileTransfer(Model model, User user2) throws Exception {
		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		return "MobileTransfer";
	}

	@RequestMapping(value = "doMobileTransfer", method = RequestMethod.POST)
	public String doMobileTransfer(Model model,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount,
			@RequestParam("mobile") String mobile) {
		User u = userDao.mobiletransfer(userName, amount, mobile);
		if (u != null) {
			model.addAttribute("userProfile", u.getUserProfile());
			model.addAttribute("account", u.getAccount());
			return "doMobileTransfer";
		} else {
			double balance = userDao.getBalance(userName);
			if (balance == 0) {
				JOptionPane
						.showMessageDialog(null,
								"Zero balance in account. Please deposit to continue with transfer");
			} else if (balance < amount) {
				JOptionPane
						.showMessageDialog(null,
								"Insufficient funds in the account, enter lesser amount");
			}
			if(u==null){
				JOptionPane.showMessageDialog(null, "Payee not found");
			}
		}
		User u2 = (User) session.getAttribute("user");
		model.addAttribute("user", u2);
		model.addAttribute("account", u2.getAccount());
		return "MobileTransfer";
	}

	@RequestMapping(value = "TransactionDetails")
	public String Transaction(Model model, User user) throws Exception {

		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		String returnVal = "TransactionDetails";

		List<Transaction> tx = userDao.getTransaction(user.getUserName());
		if (tx != null) {
			model.addAttribute("userProfile", user.getUserProfile());
			model.addAttribute("tx", tx);
			return returnVal;
		}
		return "TransactionDetails";
	}

	@RequestMapping(value = "last5Transactions")
	public String last5Transaction(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		String returnVal = "last5Transactions";
		try {
			List<Transaction> tx = userDao.getlast5Transaction(user
					.getUserName());
			if (tx != null) {
				model.addAttribute("userProfile", user.getUserProfile());
				model.addAttribute("tx", tx);

				return returnVal;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
		}

		return "last5Transactions";
	}

	@RequestMapping(value = "AddPayee")
	public String addPayee(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "AddPayee";
	}

	@RequestMapping(value = "doAddPayee", method = RequestMethod.POST)
	public String doPayee(Model model,
			@RequestParam("payeeUserName") String payeeUser,
			@RequestParam("payeeAccountNumber") int accno) throws Exception {
		User user = (User) session.getAttribute("user");
		User payeeuser = userDao.getUserByName(payeeUser);
		List<Payee> payee1 = userDao.getPayee(user.getUserName());
		if (payeeuser != null) {
			if (payeeuser.getAccount().getAccountNumber() != accno) {
				JOptionPane
						.showMessageDialog(null,
								"Username and Account dont match!!, Please enter again");
				user = (User) session.getAttribute("user");
				model.addAttribute("user", user);
				return "AddPayee";
			}
			for (Payee payee : payee1) {
				if (payee.getPayeeUserName().equalsIgnoreCase(payeeUser)) {
					JOptionPane.showMessageDialog(null,
							"Payee already added to your list");
					user = (User) session.getAttribute("user");
					model.addAttribute("user", user);
					return "AddPayee";
				}
			}
			userDao.addPayee(payeeUser, accno, user);
			JOptionPane.showMessageDialog(null, "Payee added Successfully!!!");
			model.addAttribute("user", user);
			model.addAttribute("userProfile", user.getUserProfile());
			return "AddPayee";
		} else {
			JOptionPane
					.showMessageDialog(null,
							"User not found in the database. Only interbak transfers allowed");
			user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			return "AddPayee";
		}
	}

	@RequestMapping(value = "Profile")
	public String profile(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "Profile";
	}

	@RequestMapping(value = "goToProfile")
	public String goToProfile(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		JOptionPane.showMessageDialog(null, user.getUserProfile()
				.getMobileNumber());
		return "Profile";
	}

	@RequestMapping(value = "updateAddress", method = RequestMethod.POST)
	public String updateAddress(Model model, HttpServletRequest request)
			throws Exception {

		String address = request.getParameter("address");
		User u = (User) session.getAttribute("user");
		User user = userDao.updateAddress(u.getUserName(), address);
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "Profile";
	}

	@RequestMapping(value = "changePassword")
	public String changePassword(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "changePassword";
	}

	@RequestMapping(value = "updateMobile")
	public @ResponseBody String updateMobile(Model model,
			HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("user");
		String oldMobile = request.getParameter("oldMobile");
		String newMobile = request.getParameter("newMobile");
		int result = userDao.updateMobile(oldMobile, newMobile,
				user.getUserName());
		if (result == 1) {
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "updateEmail")
	public @ResponseBody String updateEmail(Model model,
			HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("user");
		String oldEmail = request.getParameter("oldEmail");
		String newEmail = request.getParameter("newEmail");
		int result = userDao
				.updateEmail(oldEmail, newEmail, user.getUserName());
		if (result == 1) {
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "updatePassword")
	public @ResponseBody String updatePassword(Model model,
			HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("user");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		int result = userDao.updatePassword(oldPassword, newPassword,
				user.getUserName());
		if (result == 1) {
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "ContactUs")
	public String contactUs(Model model, User user) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "ContactUs";
	}

	@RequestMapping(value = "BillPay")
	public String billPay(Model model, User user) throws Exception {

		User u = (User) session.getAttribute("user");
		model.addAttribute("user", u);
		User u1 = userDao.getUserByName(u.getUserName());
		model.addAttribute("user", u1);
		model.addAttribute("userProfile", u1.getUserProfile());
		model.addAttribute("account", u1.getAccount());
		return "BillPay";
	}

	@RequestMapping(value = "doBillPay", method = RequestMethod.POST)
	public String doBillPay(Model model,
			@RequestParam("userName") String userName,
			@RequestParam("Amount") double amount,
			@RequestParam("biller") String biller) {
		User u = (User) session.getAttribute("user");
		User user = userDao.billPay(userName, amount, biller);
		if (user != null) {
			model.addAttribute("userProfile", user.getUserProfile());
			model.addAttribute("account", user.getAccount());
			return "doBillPay";
		} else {
			JOptionPane.showMessageDialog(null,
					"Insufficient funds in the account, enter lesser amount");
			model.addAttribute("user", u);
			model.addAttribute("account", u.getAccount());
			return "BillPay";
		}
	}

	@RequestMapping(value = "forgot")
	public String forgot() {
		return "forgotPassword";
	}
	
	@RequestMapping(value = "doContact")
	public String contact(User user, Model model) {
		user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfile", user.getUserProfile());
		return "doContact";
	}

	@RequestMapping(value = "forgotPassword", method = RequestMethod.POST)
	public @ResponseBody String forgotPassword(Model model,
			HttpServletRequest request) throws Exception {

		String userName = request.getParameter("userName");
		User user = userDao.getUserByName(userName);
		if (user != null) {
			String otp = userDao.generateOtp();
			userDao.addOtp(userName, otp);
			sendMail.send(user.getUserProfile(), otp);
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "forgotPassword1", method = RequestMethod.POST)
	public @ResponseBody String forgotPassword1(Model model,
			HttpServletRequest request) throws Exception {

		String userName = request.getParameter("userName");
		String otp = request.getParameter("otp");
		String password = request.getParameter("password");
		UserProfile user = userDao.getOtp(userName, otp);
		if (user != null) {
			userDao.changePassword(password, userName);
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "checkAvailability")
	public @ResponseBody String checkAvailability(
			@RequestParam("username") String username) throws Exception {
		String res = "1";
		User up = userDao.getUserByName(username);
		if (up == null) {
			res = "0";
		}
		return res;
	}
	@RequestMapping(value = "checkAccAvailability")
	public @ResponseBody String checkAccAvailability(
			@RequestParam("accno") int accno) throws Exception {
		String res = "1";
		Account acc = userDao.getAccountbyAccno(accno);
		if (acc == null) {
			res = "0";
		}
		return res;
	}

	@RequestMapping(value = "generatePDF")
	public void generatePDF(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		User u;
		try {
			u = userDao.queryUserByNameAndPassword(user.getUserName(),
					user.getUserPassword());
			List<Transaction> transactions = userDao.getTransaction(u
					.getUserName());

			Document document = new Document();
			response.setContentType("application/pdf");
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			document.addHeader("Header", "Manaran Bank");

			document.add(new Paragraph("Name: "
					+ u.getUserProfile().getFirstName() + " "
					+ u.getUserProfile().getLastName()));
			document.add(new Paragraph("Account Number: "
					+ u.getAccount().getAccountNumber()));
			document.add(Chunk.NEWLINE);
			PdfPTable table = new PdfPTable(6);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Transaction ID"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Amount"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Current Balance"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Transaction Date"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Transaction Type"));
			PdfPCell cell6 = new PdfPCell(new Paragraph(
					"Transaction Description"));

			BaseColor color = WebColors.getRGBColor("#880000");

			cell1.setBackgroundColor(color);
			cell2.setBackgroundColor(color);
			cell3.setBackgroundColor(color);
			cell4.setBackgroundColor(color);
			cell5.setBackgroundColor(color);
			cell6.setBackgroundColor(color);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);

			for (Transaction tx : transactions) {
				table.addCell(String.valueOf(tx.getTransactionId()));
				table.addCell(String.valueOf(tx.getAmount()));
				table.addCell(String.valueOf(tx.getBalance()));
				table.addCell(tx.getDate());
				table.addCell(tx.getTransactionType());
				table.addCell(tx.getDescription());
			}
			document.add(table);
			document.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}

}
