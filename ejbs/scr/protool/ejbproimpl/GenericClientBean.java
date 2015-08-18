
package scr.protool.ejbproimpl;
import java.util.*;
import javax.ejb.*;
import javax.persistence.*;
import scr.protool.ejbpro.*;
import java.io.*;
import java.sql.Date;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.*;
import scr.protool.ejbprointer.GenericClient;


	
	@Stateless

    public class GenericClientBean implements Serializable, GenericClient{

	@PersistenceContext(unitName="GenericEM") 
	private EntityManager em;
	private int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private int DECIMALS = 2;
	  
	  
	public byte[] addProject(byte[] projects, byte[] quoteline){
		return quotePDFSummary(genericProject(projects, quoteline));
	}
	
	/**
		**genericProject(byte[] projects, byte[] quoteline)
		**bean helper method to add new project details
		**use by addProject bean and addOnProject bean method
		**return new added project id
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private int genericProject(byte[] projects, byte[] quoteline){
		ArrayList<Object> prodetails = null;
		ArrayList<ArrayList<String>>  quotedetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(projects);
		ByteArrayInputStream bais_quote = new ByteArrayInputStream(quoteline);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			ObjectInputStream ois_quote = new ObjectInputStream(bais_quote);
		try{
			prodetails = (ArrayList<Object>)ois.readObject();
			quotedetails = (ArrayList<ArrayList<String>>)ois_quote.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Project> project = new ArrayList<Project>();
		Customer customer = new Customer();
		Project pro = new Project();
		
	    pro.setProjectName((String)prodetails.get(0));
		customer.setCompanyName((String)prodetails.get(1));
		
		BillingAddress billing = billingAddresses((String[])prodetails.get(2), (ArrayList<String>)prodetails.get(7));
		customer.setBillingAddress(billing);
		
		SiteAddress site = null;
		if(((String[])prodetails.get(5)).length > 0){
			site = siteAddresses((String[])prodetails.get(5), (ArrayList<String>)prodetails.get(8));
		}
		pro.setSiteAddress(site);
		
	    customer.setRegisterDate(java.sql.Date.valueOf((String)prodetails.get(3)));
		pro.setStartDate(java.sql.Date.valueOf((String)prodetails.get(3)));
	    pro.setPaymentTerm((String)prodetails.get(9));
		String strDateValid = (String)prodetails.get(4);
		String status = (String)prodetails.get(6);
		if(status.equals("Open")){
			pro.setStatus(ProjectStatus.OPEN);
		}
		else if(status.equals("Continue")){
			pro.setStatus(ProjectStatus.CONTINUE);
		}
		pro.setCustomer(customer);
		project.add(pro);
		customer.setProject(project);
	    em.persist(customer);
		
		 return projectQuote(quotedetails, pro.getProjectId(), strDateValid);
		
		}
	
	/**
		**BillingAddress(String str[]) 
		**bean helper method add billing address to project
		**use by addProject bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private BillingAddress billingAddresses(String str[], ArrayList<String> contact){
		BillingAddress billing = new BillingAddress();
		Address address = new Address();
		address.setAddressLine1(str[0]);
		address.setAddressLine2(str[1]);
		address.setTownCity(str[2]);
		address.setCountyState(str[3]);
		address.setPostCode(str[4]);
		address.setCountry(str[5]);
		billing.setAddresses(address);
		BillingContact contacts = billingContact(contact);
		billing.setBillingContact(contacts);
		em.persist(billing);
		return billing;
	}
	
	/**
		**SiteAddress(String str[])
		**bean helper method add billing address to project
		**use by addProject bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private SiteAddress siteAddresses(String str[], ArrayList<String> contact){
		SiteAddress site = new SiteAddress();
		Address address = new Address();
		address.setAddressLine1(str[0]);
		address.setAddressLine2(str[1]);
		address.setTownCity(str[2]);
		address.setCountyState(str[3]);
		address.setPostCode(str[4]);
		address.setCountry(str[5]);
		site.setAddresses(address);
		SiteContact contacts = siteContact(contact);
		site.setSiteContact(contacts);
		em.persist(site);
		return site;
	}
	
	/**
		**SiteContact(ArrayList<String>) 
		**bean helper method add billing address to project
		**use by SiteAddress bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private SiteContact siteContact(ArrayList<String> contact){
		SiteContact siteCont = new SiteContact();
		siteCont.setMobile(contact.get(0));
		siteCont.setContactName(contact.get(1));
		em.persist(siteCont);
		return siteCont;
	}
	
	/**
		**billingContact(ArrayList<String>) 
		**bean helper method add billing address to project
		**use by BillingAddress bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private BillingContact billingContact(ArrayList<String> contact){
		BillingContact billingCont = new BillingContact();
		billingCont.setClientTele(contact.get(0));
		billingCont.setMobile(contact.get(1));
	    billingCont.setClientEmail(contact.get(2));
		billingCont.setContactName(contact.get(3));
		em.persist(billingCont);
		return billingCont;
	}
	
		/**
		**copyProject(byte[] projects, byte[] quoteline)
		**bean helper method to add new project details
		**use by addOnProject bean method
		**return new added project id
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private int copyProject(byte[] projects, byte[] quoteline){
		ArrayList<Object> prodetails = null;
		ArrayList<ArrayList<String>>  quotedetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(projects);
		ByteArrayInputStream bais_quote = new ByteArrayInputStream(quoteline);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			ObjectInputStream ois_quote = new ObjectInputStream(bais_quote);
		try{
			prodetails = (ArrayList<Object>)ois.readObject();
			quotedetails = (ArrayList<ArrayList<String>>)ois_quote.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ArrayList<Project> project = new ArrayList<Project>();
		//customer from existing project
		Customer customer = em.find(Project.class, (Integer.valueOf((String)prodetails.get(0))).intValue()).getCustomer();
		Project pro = new Project();
		
	    pro.setProjectName((String)prodetails.get(1));
		//customer.setCompanyName((String)prodetails.get(1));
		
		//BillingAddress billing = billingAddresses((String[])prodetails.get(2), (ArrayList<String>)prodetails.get(7));
		//customer.setBillingAddress(billing);
		
		SiteAddress site = null;
		if(((String[])prodetails.get(4)).length > 0){
			site = siteAddresses((String[])prodetails.get(4), (ArrayList<String>)prodetails.get(5));
		}
		pro.setSiteAddress(site);
		
	    //customer.setRegisterDate(java.sql.Date.valueOf((String)prodetails.get(3)));
		pro.setStartDate(java.sql.Date.valueOf((String)prodetails.get(2)));
	    pro.setPaymentTerm((String)prodetails.get(7));
		String strDateValid = (String)prodetails.get(3);
		String status = (String)prodetails.get(6);
		if(status.equals("Open")){
			pro.setStatus(ProjectStatus.OPEN);
		}
		else if(status.equals("Continue")){
			pro.setStatus(ProjectStatus.CONTINUE);
		}
		pro.setCustomer(customer);
		//project.add(pro);
		ArrayList<Project> previourProject = new ArrayList<Project>(customer.getProject());
		previourProject.add(previourProject.size(), pro);
		customer.setProject(previourProject);
	    em.persist(customer);
		
		 return projectQuote(quotedetails, pro.getProjectId(), strDateValid);
		
		}
		
	public byte[] addOnProject(byte[] projects, byte[] quoteline){
		
		return quotePDFSummary(copyProject(projects, quoteline));
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public int projectQuote(ArrayList<ArrayList<String>> quotedetails, int projectId, String validDate){
		Quote quotes = new Quote();
		Project pro = em.find(Project.class, projectId);
		quotes.setProject(pro);
		//quotes.setDateAdded(pro.getStartDate());
		quotes.setDateValid(java.sql.Date.valueOf(validDate));
	
		ArrayList<Quoteline> quotearray = new ArrayList<Quoteline>();
		Iterator<ArrayList<String>> it = quotedetails.iterator();
		while(it.hasNext()){
		Quoteline lines = new Quoteline();
			ArrayList<String> singleline = it.next();
				if(!singleline.get(0).equals("")){
				 lines.setDescription(singleline.get(0));
				 lines.setUnitPrice(new BigDecimal(singleline.get(1)));
				 lines.setQty((new BigDecimal(singleline.get(2))));
				 }
				quotearray.add(lines);
				lines.setQuotes(quotes);

		}
		quotes.setQuotelines(quotearray);
		em.persist(quotes);
		
		return quotes.getQuoteId();
	}
	
	/**
		return byte[] of quote summary for pdf file
		helper method for addProject
		stand alone method for regenerate quote pdf file
	*/
	public byte[] quotePDFSummary(int quoteid){
		//initialised all pdf information
		ArrayList<Object> datas = new ArrayList<Object>();
		ArrayList<ArrayList<String>> quotelist = new ArrayList<ArrayList<String>>();
		ArrayList<String> customer = new ArrayList<String>();
		ArrayList<String> siteAddress = new ArrayList<String>();
		
		Quote quote = em.find(Quote.class, quoteid);
		Project project = quote.getProject();
		Customer cust = project.getCustomer();
		
		//quote summary upper part of pdf
		String[] strSummary = {project.getStartDate().toString(), String.valueOf(quoteid),
								String.valueOf(project.getProjectId()), quote.getDateValid().toString()};
		datas.add(strSummary);
		
		//quote line for the pdf table
		ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
		for(Quoteline qline : quoteline){
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(qline.getDescription());
			temp.add(qline.getUnitPrice().toString());
			temp.add(qline.getQty().toString());
			temp.add((qline.getUnitPrice().multiply(qline.getQty())).toString());
			temp.add(String.valueOf("1"));//VAT CODE COME FROM INITIAL SYSTEM CONFIGURATION 
			quotelist.add(temp);
		}
		datas.add(quotelist);
	
		//customer details upper part of pdf
		BillingAddress billAddress = cust.getBillingAddress();
		BillingContact billContact = billAddress.getBillingContact();
		customer.add(billContact.getContactName());
		customer.add(cust.getCompanyName());
		Address address = billAddress.getAddresses();
		customer.add(address.getAddressLine1());
		if(address.getAddressLine2() != " "){
			customer.add(address.getAddressLine2());
		}
		customer.add(address.getTownCity());
		if(address.getAddressLine2() != " "){
			customer.add(String.format("%s %s", address.getCountyState(), address.getPostCode()));
		}
		else{
			customer.add(address.getCountyState());
			customer.add(address.getPostCode());
		}
		customer.add(billContact.getMobile());
		datas.add(customer);
		
		SiteAddress siteAddresses = project.getSiteAddress();
		Address siteAddre = siteAddresses.getAddresses();
		siteAddress.add(siteAddre.getAddressLine1());
		if(siteAddre.getAddressLine2() != " "){
			siteAddress.add(siteAddre.getAddressLine2());
		}
		siteAddress.add(siteAddre.getTownCity());
		if(siteAddre.getAddressLine2() != " "){
			siteAddress.add(String.format("%s %s", siteAddre.getCountyState(), siteAddre.getPostCode()));
		}
		else{
			siteAddress.add(siteAddre.getCountyState());
			siteAddress.add(siteAddre.getPostCode());
		}
		datas.add(siteAddress);
		
		return objectWriter(datas);
	}
	
	/**
		**method project details takes project id as an argument
		**@parameter byte array byte[] of project details plus associate quote
		**use by update project GUI  
	*/
	
	public byte[] projectDetail(int projectId){
		java.util.List<Object> datas = new ArrayList<Object>();
		//Project Project = em.find(Project.class, (Integer.valueOf(projectId)).intValue());
		Project Project = em.find(Project.class, projectId);
		Customer customer = Project.getCustomer();
		ArrayList<String> straddr = new ArrayList<String>();
		datas.add(Project.getProjectName());
		datas.add(customer.getCompanyName());
		BillingAddress billAddress = customer.getBillingAddress();
		BillingContact billContact = billAddress.getBillingContact();
		Address address = billAddress.getAddresses();
		straddr.add(address.getAddressLine1());
		straddr.add(address.getAddressLine2());
		straddr.add(address.getTownCity());
		straddr.add(address.getCountyState());
		straddr.add(address.getPostCode());
		straddr.add(address.getCountry());
		datas.add(straddr);
		datas.add(billContact.getClientTele());
		datas.add(billContact.getMobile());
		datas.add(billContact.getClientEmail());
		datas.add(Project.getStatus().name());
		datas.add(Project.getStartDate().toString());
		datas.add(Project.getPaymentTerm());
		datas.add(billContact.getContactName());
		return objectWriter(datas);
	}
	
	/** addPaymentIn
		**method project details takes project id as an argument
		**@parameter byte array byte[] of project details plus associate quote
		**use by projectDetails GUI
	*/
	
	public byte[] editableProjectDetails(int projectId){
	
		java.util.List<Object> datas = new ArrayList<Object>();
		ArrayList<String> general = new ArrayList<String>();
		ArrayList<String> billAddress = new ArrayList<String>();
		ArrayList<String> siteAddress = new ArrayList<String>();
		
		Project project = em.find(Project.class, projectId);
		Customer customer = project.getCustomer();
		
		general.add(project.getProjectName());
		general.add(customer.getCompanyName());
		general.add(project.getStartDate().toString());
		general.add(project.getStatus().name());
		datas.add(general);
		
		
		BillingAddress bAddresses = customer.getBillingAddress();
		BillingContact billContact = bAddresses.getBillingContact();
		Address bAddress = bAddresses.getAddresses();
		billAddress.add(billContact.getContactName());
		billAddress.add(bAddress.getAddressLine1());
		billAddress.add(bAddress.getAddressLine2());
		billAddress.add(bAddress.getTownCity());
		billAddress.add(bAddress.getCountyState());
		billAddress.add(bAddress.getPostCode());
		billAddress.add(bAddress.getCountry());
		billAddress.add(billContact.getClientTele());
		billAddress.add(billContact.getMobile());
		billAddress.add(billContact.getClientEmail());
		datas.add(billAddress);
		
		SiteAddress sAddresses = project.getSiteAddress();
		SiteContact siteContact = sAddresses.getSiteContact();
		Address sAddress = sAddresses.getAddresses();
		siteAddress.add(sAddress.getAddressLine1());
		siteAddress.add(sAddress.getAddressLine2());
		siteAddress.add(sAddress.getTownCity());
		siteAddress.add(sAddress.getCountyState());
		siteAddress.add(sAddress.getPostCode());
		siteAddress.add(sAddress.getCountry());
		siteAddress.add(siteContact.getContactName());
		siteAddress.add(siteContact.getMobile());
		datas.add(siteAddress);
		return objectWriter(datas);
	}
	/**
		**method project details takes project id as an argument
		**@parameter byte array byte[] of project details plus associate quote
		**use by projectDetails GUI
	*/
	
	public byte[] projectDetails(int projectId){
		java.util.List<Object> datas = new ArrayList<Object>();
		Project project = em.find(Project.class, projectId);
		Customer customer = project.getCustomer();
		ArrayList<String> projects = new ArrayList<String>();
		ArrayList<String> siteAddr = new ArrayList<String>();
		
		BillingAddress billAddress = customer.getBillingAddress();
		BillingContact billContact = billAddress.getBillingContact();
		Address address = billAddress.getAddresses();
		
		projects.add(String.valueOf(projectId));
		projects.add(project.getProjectName());
		projects.add(billContact.getContactName());
		projects.add(address.getAddressLine1());
		projects.add(address.getAddressLine2());
		projects.add(address.getTownCity());
		projects.add(address.getCountyState());
		projects.add(address.getPostCode());
		projects.add(address.getCountry());
		projects.add(billContact.getClientTele());
		projects.add(billContact.getMobile());
		projects.add(billContact.getClientEmail());
		projects.add(((ProjectStatus)project.getStatus()).toString());
		projects.add(getPamentIn(project) == null ? "No" : "Yes");
		projects.add(String.valueOf(customer.getCustomerId()));
		datas.add(projects);
		
		ArrayList<String> quoteList = new ArrayList<String>();
		Query query = em.createQuery("SELECT q FROM Quote q WHERE q.project=:projects");
		query.setParameter("projects", project);
					
		try{
			Quote quote = (Quote)query.getSingleResult();
			ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
			quoteList.add(String.valueOf(quote.getQuoteId()));
			quoteList.add(quote.getDateValid().toString());
			BigDecimal net = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			BigDecimal vat = BigDecimal.ZERO;
			for(Quoteline qline : quoteline){

				net = net.add(qline.getUnitPrice().multiply(qline.getQty()));
			}
			quoteList.add(net.toString());
			vat = net.multiply(new BigDecimal("0.2"));//hand coded vat value, net to get info from initial config
			quoteList.add(vat.toString());
			total = net.add(vat);
			quoteList.add(total.toString());
			quoteList.add(String.valueOf(quoteline.size()));
		}
		catch(NoResultException ex){}
		datas.add(quoteList);
		
		SiteAddress siteAddresses = project.getSiteAddress();
		SiteContact siteContact = siteAddresses.getSiteContact();
		Address siteaddress = siteAddresses.getAddresses();
		siteAddr.add(siteContact.getContactName());
		siteAddr.add(siteaddress.getAddressLine1());
		siteAddr.add(siteaddress.getAddressLine2());
		siteAddr.add(siteaddress.getTownCity());
		siteAddr.add(siteaddress.getCountyState());
		siteAddr.add(siteaddress.getPostCode());
		siteAddr.add(siteaddress.getCountry());
		siteAddr.add(siteContact.getMobile());
		datas.add(siteAddr);
		
		return objectWriter(datas);
	}
	
	/**
		**method updateProjectQuote update project and quotewhen there is an amendment 
		**@parameter bytes array byte[] as argument
		**use by EditProject GUI 
	*/
	public void updateProjectQuote(byte[] project, byte[]genericinfo, byte[] quote){
		updateProject(project);
		updateQuotes(genericinfo, quote);
	}
	
	/**
		**method updateProject update project when there is an amendment 
		**@parameter bytes array byte[] as argument
		**use by EditProject GUI 
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void updateProject(byte[] project){
		ArrayList<Object> prodetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(project);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
		try{
			prodetails = (ArrayList<Object>)ois.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Project pro = em.find(Project.class, (Integer.valueOf((String)prodetails.get(0))).intValue());
			Customer customer = pro.getCustomer();

		if(!((String)prodetails.get(1)).equals(pro.getProjectName())){
			pro.setProjectName((String)prodetails.get(1));
		}
		if(!((String)prodetails.get(2)).equals(customer.getCompanyName())){
			customer.setCompanyName((String)prodetails.get(2));
		}
		
		BillingAddress billAddresses = customer.getBillingAddress();
		BillingContact billContact = billAddresses.getBillingContact();
		if(((String[])prodetails.get(3)).length > 1){
			
			String str[] = (String[])prodetails.get(3);
			Address billAddress = new Address();
			billAddress.setAddressLine1(str[0]);
			billAddress.setAddressLine2(str[1]);
			billAddress.setTownCity(str[2]);
			billAddress.setCountyState(str[3]);
			billAddress.setPostCode(str[4]);
			billAddress.setCountry(str[5]);
			billAddresses.setAddresses(billAddress);
		}
		
		SiteAddress siteAddresses = pro.getSiteAddress();
		if(((String[])prodetails.get(10)).length > 1){
			String strs[] = (String[])prodetails.get(10);
			Address siteAddress = new Address();
			siteAddress.setAddressLine1(strs[0]);
			siteAddress.setAddressLine2(strs[1]);
			siteAddress.setTownCity(strs[2]);
			siteAddress.setCountyState(strs[3]);
			siteAddress.setPostCode(strs[4]);
			siteAddress.setCountry(strs[5]);
			siteAddresses.setAddresses(siteAddress);
		}
		SiteContact siteContact = siteAddresses.getSiteContact();
		if(!((String)prodetails.get(12)).equals(""))
			siteContact.setContactName((String)prodetails.get(12));
		if(!((String)prodetails.get(13)).equals(""))
			siteContact.setMobile((String)prodetails.get(13));
			
		if(!((String)prodetails.get(4)).equals(""))
			billContact.setClientTele((String)prodetails.get(4));
			
		if(!((String)prodetails.get(5)).equals(""))
			billContact.setMobile((String)prodetails.get(5));
			
		if(!((String)prodetails.get(6)).equals(""))
			billContact.setClientEmail((String)prodetails.get(6));
			
		if(!((String)prodetails.get(7)).equals(""))
			pro.setStartDate(java.sql.Date.valueOf((String)prodetails.get(7)));
		
		if(!((String)prodetails.get(8)).equals(""))
			pro.setPaymentTerm((String)prodetails.get(8));
			
		if(!((String)prodetails.get(11)).equals("")){
			String status = (String)prodetails.get(11);
			if(status.equals("Open")){
				pro.setStatus(ProjectStatus.OPEN);
			}
			else if(status.equals("Continue")){
				pro.setStatus(ProjectStatus.CONTINUE);
			}
			else if(status.equals("Cancel")){
				pro.setStatus(ProjectStatus.CANCELLED);
			}
			else{
				pro.setStatus(ProjectStatus.CLOSED);
			}
		}
		
		if(!((String)prodetails.get(9)).equals(""))
			billContact.setContactName((String)prodetails.get(9));
			
		} catch (OptimisticLockException e) {
			throw new EJBException(e);
		}
	   // em.persist(pro);//check if persist is neccessary to update records
	}
	
	/**
		**method updateQuotes update quote when there is amendment updateProject
		**@parameter 2 bytes array byte[] as argument
		**use by EditQuote GUI - non invoice quote update
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void updateQuotes(byte[]genericinfo, byte[] quote){
		String[] details = null;
		ArrayList<ArrayList<String>>  quotedetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(genericinfo);
		ByteArrayInputStream bais_quote = new ByteArrayInputStream(quote);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			ObjectInputStream ois_quote = new ObjectInputStream(bais_quote);
		try{
			details = (String[])ois.readObject();
			quotedetails = (ArrayList<ArrayList<String>>)ois_quote.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Quote quotes = em.find(Quote.class, (Integer.valueOf(details[0])).intValue());
		
		if(!details[1].equals(quotes.getDateValid().toString())){
			quotes.setDateValid(java.sql.Date.valueOf(details[1]));
		}
		if(!quotedetails.isEmpty()){
		
			ArrayList<Quoteline> oldquoteline = new ArrayList<Quoteline>(quotes.getQuotelines());
			if(!oldquoteline.isEmpty()){
				for(int i = 0; i < oldquoteline.size(); i++){
				Quoteline current = oldquoteline.get(i);
				em.remove(current);
				}
			}
		
			ArrayList<Quoteline> quotearray = new ArrayList<Quoteline>();

			for(Iterator<ArrayList<String>> it = quotedetails.iterator(); it.hasNext();){
				Quoteline lines = new Quoteline();
				ArrayList<String> singleline = it.next();
					if(!singleline.get(0).equals(" ")){
					lines.setDescription(singleline.get(0));
					lines.setUnitPrice(new BigDecimal(singleline.get(1)));
					lines.setQty((new BigDecimal(singleline.get(2))));
					}
					quotearray.add(lines);
					lines.setQuotes(quotes);

			}
			quotes.setQuotelines(quotearray); 
			em.merge(quotes);
			
		}
	}
	
	/**
		**method quoteValues update quote when there is amendment updateProject
		**@parameter 2 bytes array byte[] as argument
		**use by EditQuote GUI - non invoice quote update 
	*/
	public byte[] quoteValues(int quoteId){
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		ArrayList<Object> datas = new ArrayList<Object>();
 
		Quote quote = em.find(Quote.class, quoteId);
		datas.add(quote.getDateValid().toString());
		ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
		for(Quoteline qline : quoteline){
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(qline.getDescription());
			temp.add(qline.getQty().toString());
			temp.add(qline.getUnitPrice().toString());
			data.add(temp);
		}
		datas.add(data);
		return objectWriter(datas);
	}
	/**
		**method updateIncomingIn update payment received for invoice issue to customer
		**@parameter byte array byte[] incoming
	*/
	public void updateIncomingIn(byte[] incoming){
		Vector<Object> incomedetails = null;
		Posting ps = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(incoming);

		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			incomedetails = (Vector<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	    BigDecimal cis_tax = new BigDecimal((String)incomedetails.elementAt(1));

		Incoming income = em.find(Incoming.class,(new Integer((String)incomedetails.elementAt(6))).intValue());
		Journal jr = income.getJournal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		ArrayList<Posting> postdata = new ArrayList<Posting>(jr.getPostingdrcr());
		for(int i = 0; i < postdata.size(); i++){
			if(postdata.get(i).getCodeId().getCodeId() == 10010){
				Posting ps1 = postdata.get(i);
				ps1.setAmount(new BigDecimal((String)incomedetails.elementAt(5)));
				ps1.setCodeId(em.find(AccountCode.class, 10010));
				postarray.add(ps1);
				ps1.setJournal(jr);
			}
			else if(postdata.get(i).getCodeId().getCodeId() == 10020){
				Posting ps2 = postdata.get(i);
				ps2.setAmount(new BigDecimal((String)incomedetails.elementAt(5)).negate().add(cis_tax.negate()));
				ps2.setCodeId(em.find(AccountCode.class, 10020));
				postarray.add(ps2);
				ps2.setJournal(jr);
			}
			else if(postdata.get(i).getCodeId().getCodeId() == 70010){
				if(cis_tax.compareTo(new BigDecimal("0")) > 0){
					ps = postdata.get(i);
					ps.setAmount(cis_tax);
					postarray.add(ps);
					ps.setJournal(jr);
				}
				else{
					ps = postdata.get(i);
					em.remove(ps);
				}
			}
		}
		if(ps == null && cis_tax.compareTo(new BigDecimal("0")) > 0){
			ps = new Posting();
			Query acquerycrcis = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=70010");
			AccountCode codeDRCIS = (AccountCode)acquerycrcis.getSingleResult();
			ps.setAmount(cis_tax);
			ps.setCodeId(codeDRCIS);
			postarray.add(ps);
			ps.setJournal(jr);
		}
	   jr.setPostingdrcr(postarray);
	   em.merge(jr);
	   
	   income.setJournal(jr);
	   income.setPaymentInDate((Date)incomedetails.elementAt(0));
	   income.setNote((String)incomedetails.elementAt(2));
	  // income.setPaymentType((String)incomedetails.elementAt(3));
	  // income.setPayment(em.find(PaymentIn.class,(new Integer((String)incomedetails.elementAt(4))).intValue()));
	   em.merge(income);
	   
	}
	
	
	/**DONE
		**method addIncomingIn add payment received for invoice issue to customer
		**@parameter byte array byte[] incoming 
	*/
	public void addIncomingIn(byte[] incoming){
		ArrayList<String> incomedetails = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(incoming);

		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			incomedetails = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}  

	   ArrayList<Incoming> incomingList = new ArrayList<Incoming>();
	   PaymentIn payment = em.find(PaymentIn.class,(Integer.valueOf(incomedetails.get(0))).intValue());
	   BigDecimal amount = new BigDecimal(incomedetails.get(2));
	   BigDecimal cis = new BigDecimal(incomedetails.get(3));
	   Incoming income = new Incoming();
	   Journal jr = null;
	   int codenumber = (Integer.valueOf(incomedetails.get(4))).intValue();
	   if(codenumber == 60110){//posting is for bad debt
		
			jr = badDebtJornal(amount, codenumber, payment.getJournal());
	   }
	   else{ //posting is incoming money
			jr = incomingJornal(amount, cis, codenumber, payment.getJournal());//hand coded vat %
	   }
	   income.setJournal(jr);
	   income.setPaymentInDate(java.sql.Date.valueOf(incomedetails.get(1)));
	   income.setNote(incomedetails.get(5));
	   //income.setPaymentType((String)incomedetails.elementAt(3));
	   income.setPayment(payment);
	   	Query query = em.createQuery("SELECT i FROM Incoming i WHERE i.payment = :payments");
		query.setParameter("payments", payment);
		java.util.List<Incoming> incomings =  (java.util.List<Incoming>)query.getResultList();
		if(!incomings.isEmpty()){
			for(Incoming incomes : incomings){
				incomingList.add(incomes);
			}
		}
	   incomingList.add(income);
	   payment.setIncoming(incomingList);
	   em.persist(payment);
	
	}
	
	/**
		**incomingJornal(BigDecimal incoming, BigDecimal cis_tax, int incominCode, Jornal paymentIn)
		**bean helper method add incoming on project invoice to either bank or cash account
		**use by addIncomingIn bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private Journal incomingJornal(BigDecimal incoming, BigDecimal cis_tax, int incominCode, Journal paymentIn){
	
	   ArrayList<Posting> postarray = new ArrayList<Posting>();
	   
	   Journal jr = new Journal();
	   AccountCode codeDRCIS = em.find(AccountCode.class, 70010);
	  
	   if(cis_tax.compareTo(new BigDecimal("0")) > 0){
			//cis_tax = new BigDecimal((String)incomedetails.elementAt(1));
			Posting ps = new Posting();
			ps.setAmount(cis_tax);
			ps.setCodeId(codeDRCIS);
			postarray.add(ps);
			ps.setJournal(jr);
	   }
	   
	   AccountCode codeDR = em.find(AccountCode.class, incominCode);
	   Posting ps1 = new Posting();
	   ps1.setAmount(incoming);
	   ps1.setCodeId(codeDR);
	   postarray.add(ps1);
	   ps1.setJournal(jr);
	   
	   updateReceivable(paymentIn, incoming.add(cis_tax), jr);
	   
	   //persist account group as string in Jornal
	   if(cis_tax.signum() > 0){ // we have only cis
			 jr.setCodeDR(String.valueOf(codeDR.getGroup().getGroupId())+"."+
							String.valueOf(codeDRCIS.getGroup().getGroupId()));
	   
	   }

	   
	   else{//no cis
			jr.setCodeDR(String.valueOf(codeDR.getGroup().getGroupId()));
	   }
	   
	   jr.setPostingdrcr(postarray);
	   em.persist(jr);
	   return jr;
	}
	
	/**
		**method badDebtJornal(BigDecimal badDebt, String strvat, int badDebtCode, Jornal paymentIn)
		**bean helper method add bad debt to operation expenses
		**use by addIncomingIn bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private Journal badDebtJornal(BigDecimal badDebt, int badDebtCode, Journal paymentIn){
	
	    ArrayList<Posting> postarray = new ArrayList<Posting>();
	   Journal jr = new Journal();
	   AccountCode debtDr = em.find(AccountCode.class, badDebtCode);
	   AccountCode codeVAT = em.find(AccountCode.class, 70020);
		
	   updateReceivable(paymentIn, badDebt, jr);
	   
	   BigDecimal vatAmount = BigDecimal.ZERO;
		Posting debtVAT = null;
	   	Query query = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = 70020");
		query.setParameter("jornal",  paymentIn);
		
		try{
			debtVAT = (Posting)query.getSingleResult();
			
		}
		catch(NoResultException ex){}
		//re adjust vat for bad debt
		if(debtVAT != null){
			Posting vat = new Posting();
			BigDecimal vatPercentage = getVATExclusive(paymentIn, 40010, 70020);
			vatAmount = (vatPercentage.multiply(badDebt)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE );
			
			vat.setAmount(vatAmount.abs());
			vat.setCodeId(codeVAT);
			postarray.add(vat);
			vat.setJournal(jr);
	   }
		
	   Posting ps1 = new Posting();
	   ps1.setAmount(badDebt.subtract(vatAmount.abs()));
	   ps1.setCodeId(debtDr);
	   postarray.add(ps1);
	   ps1.setJournal(jr);
	   
	   	if(vatAmount.signum() > 0 ){ // we have vat on bad debt
			 jr.setCodeDR(String.valueOf(debtDr.getGroup().getGroupId())+"."+
							String.valueOf(codeVAT.getGroup().getGroupId()));
	   
	   }
	   
	   else{//no vat on bad debt
			jr.setCodeDR(String.valueOf(debtDr.getGroup().getGroupId()));
	   }
	   
	   jr.setPostingdrcr(postarray);
	   em.persist(jr);
	   
	   return jr;
	}
	
	/**
		**method updateReceivable(Jornal paymentIn, BigDecimal amount, Jornal jr)
		**bean helper method update receivable when payment is received on invoice
		**use by incomingJornal bean helper method or badDebtJornal bean helper method
	*/
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private void updateReceivable(Journal paymentIn, BigDecimal amount, Journal jr){

		Query query = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = 10020");
		query.setParameter("jornal",  paymentIn);
		Posting receivable = null;
		try{
			receivable = (Posting)query.getSingleResult();
			receivable.setAmount(receivable.getAmount().subtract(amount));
		}
		catch(NoResultException ex){}
		AccountCode receivableCode = em.find(AccountCode.class, 10020);
		jr.setCodeCR(String.valueOf(receivableCode.getGroup().getGroupId()));
	}
	

	/**
		**method totalIncoming(PaymentIn pr), take PaymentIn as argument
		**bean helper method return total money received on a particuler invoice
		**use by incomingJornal bean helper method or badDebtJornal bean helper method AND p.codeId.codeId != 10020
	*/
	private BigDecimal totalIncoming(PaymentIn pr){
		ArrayList<Incoming> income = new ArrayList<Incoming>(pr.getIncoming());
		BigDecimal payments = BigDecimal.ZERO;
		
		for(Incoming incomes : income){
			Query query = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal=:jornal");
			query.setParameter("jornal",  incomes.getJournal());
		
			try{

				payments = payments.add((BigDecimal)query.getSingleResult());
			}
		catch(NoResultException ex){}
				
		}
		
		return  payments;
	}
	
	/**DONE
		**method generate invoice and create Jornal with its posting for the new invoice
		**it takes paymentIn details plus change in quote if any
		**create new paymentIn plus merge quote if any 
	*/
	public void addPaymentIn(byte[] payment){
		ArrayList<String> paymentdetails = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(payment);

		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			paymentdetails = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	   int projectIncomingCode = (Integer.valueOf(paymentdetails.get(0))).intValue();
	   String strvat = paymentdetails.get(6);
	   BigDecimal amount = new BigDecimal(paymentdetails.get(4));
	   Journal jr = receivableJornal(amount, strvat, projectIncomingCode);
		
	   PaymentIn pay = new PaymentIn();
	   pay.setProject(em.find(Project.class,(Integer.valueOf(paymentdetails.get(1)).intValue())));
		pay.setPaymentTerm(paymentdetails.get(5));
	   pay.setJournal(jr);
	   pay.setInvoiceDate(java.sql.Date.valueOf(paymentdetails.get(3)));
	   em.persist(pay);
	   
	  if((paymentdetails.get(2)).equals("")){//alto generate invoice number when no invoice number specified
			pay.setInvoice(String.valueOf(pay.getpaymentInId()));
	   }
	   
	  else{
			pay.setInvoice(paymentdetails.get(2));
	  }
	}
	
	/**
		**method receivableJornal(BigDecimal amount, String strvat, int projectIncomingCode)
		**bean helper method for posting jornal for accounts receivable invoice
		**return Jornal
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private Journal receivableJornal(BigDecimal amount, String strvat, int projectIncomingCode){
	
	   ArrayList<Posting> postarray = new ArrayList<Posting>();
	   Journal jr = new Journal();
	   
	   AccountCode accountReceivable = em.find(AccountCode.class, 10020);
	   AccountCode projectIncoming = em.find(AccountCode.class, projectIncomingCode);
	   AccountCode codeVAT = em.find(AccountCode.class, 70020);
	   
	   BigDecimal vatAmount =  VATPoster(jr, strvat, postarray, amount, false);
	   
	   Posting ps1 = new Posting();
	   ps1.setAmount(amount.add(vatAmount));
	   ps1.setCodeId(accountReceivable);
	   postarray.add(ps1);
	   ps1.setJournal(jr);
	   jr.setCodeDR(String.valueOf(accountReceivable.getGroup().getGroupId()));
	   
	   Posting ps2 = new Posting();
	   ps2.setAmount(amount.negate());
	   ps2.setCodeId(projectIncoming);
	   postarray.add(ps2);
	   ps2.setJournal(jr);
	   
	   if(vatAmount.signum() > 0 ){ // vatable system user
			 jr.setCodeCR(String.valueOf(projectIncoming.getGroup().getGroupId())+"."+
							String.valueOf(codeVAT.getGroup().getGroupId()));
	   
	   }
	   
	   else{// non vatable system user
			jr.setCodeCR(String.valueOf(projectIncoming.getGroup().getGroupId()));
	   }
	   
	   jr.setPostingdrcr(postarray);
	   em.persist(jr);
	   return jr;
	}
	
	/**
		**method to update existing invoice, also update Posting, Jornal remain unchange
		**it takes paymentIn details plus change in quote if any
		**create new paymentIn plus merge quote if any
	*/
	public void updatePaymentIn(byte[] payment){
		Vector<Object> paymentdetails = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(payment);

		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			paymentdetails = (Vector<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Query query = em.createQuery("SELECT j FROM  Journal j, PaymentIn p WHERE j.journalId=p.journal.journalId AND p.paymentInId=?1");
		query.setParameter(1, (new Integer((String)paymentdetails.elementAt(0))).intValue()); 
		Journal jr = (Journal)query.getSingleResult();

	   ArrayList<Posting> postarray = new ArrayList<Posting>();
	   ArrayList<Posting> postdata = new ArrayList<Posting>(jr.getPostingdrcr());
	   
	   Posting ps1 = postdata.get(0);
	   ps1.setAmount(new BigDecimal((String)paymentdetails.elementAt(5)));
	   postarray.add(ps1);
	   ps1.setJournal(jr);
	   
	   Posting ps2 = postdata.get(1);
	   ps2.setAmount(new BigDecimal((String)paymentdetails.elementAt(5)).negate());
	   postarray.add(ps2);
	   ps2.setJournal(jr);

		
	   jr.setPostingdrcr(postarray);
	   em.merge(jr);
	   
	   PaymentIn pay = em.find(PaymentIn.class,(new Integer((String)paymentdetails.elementAt(0))).intValue());
	   if(((String)paymentdetails.elementAt(3)).equals("")){
			pay.setInvoice((String)paymentdetails.elementAt(0));
	   }
	   
	  else{
			pay.setInvoice((String)paymentdetails.elementAt(3));
	  }
	   pay.setInvoiceDate((Date)paymentdetails.elementAt(4));
	  // pay.setVat((String)paymentdetails.elementAt(6));
	   em.merge(pay);
	}
	
	//////////////////////////////////////////////////////////////////////////////////SUPPLIES and SUB-CONTRACTOR BEANS supplierInfos
	/**DONE
		**method addSupplier() add new supplier to database 
		**it takes byte of supplier infomation
		**use by AddSupplier GUI getSupplierSingleDetails
	*/
	
	public void addSupplier(byte[] supplierinfo){
		ArrayList<Object> supplier = null;
		
		ByteArrayInputStream bais = new ByteArrayInputStream(supplierinfo);

		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			supplier = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Supplier sp = new Supplier();
		Address address = new Address();
		sp.setCompanyName((String)supplier.get(0));
		sp.setContactName((String)supplier.get(1));
		sp.setCompanyTele((String)supplier.get(2));
		sp.setCompanyEmail((String)supplier.get(3));
		sp.setStartDate((Date)supplier.get(4));
		sp.setCredit(new BigDecimal((String)supplier.get(5)));
		sp.setPaymentTerm((String)supplier.get(6));
		sp.setAccount((String)supplier.get(7));
		sp.setFax((String)supplier.get(8));
		String str[] = (String[])supplier.get(9);
		address.setAddressLine1(str[0]);
		address.setAddressLine2(str[1]);
		address.setTownCity(str[2]);
		address.setCountyState(str[3]);
		address.setPostCode(str[4]);
		address.setCountry(str[5]);
		sp.setAddresses(address);
		em.persist(sp);
	}
	
	/*DONE
		**method getSupplierData() get all supplier details from database
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	public byte[] getSupplierData(){
		java.util.List<Supplier> ls = (java.util.List<Supplier>)em.createQuery("SELECT s FROM Supplier s").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Supplier pr : ls){
			BigDecimal total =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pr.getCompanyName());
			temp.add(pr.getAccount());
			Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier=:supplierbal");
			query.setParameter("supplierbal", pr);
			java.util.List<SupplierInvoice> l = (java.util.List<SupplierInvoice>)query.getResultList();
			if(l.isEmpty()){
				total =  new BigDecimal ("0");
				temp.add(total);
			}
	
			else{
				for(SupplierInvoice si : l){
					Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
					balquery.setParameter(1, si.getJournal().getJournalId());
					Posting ps = (Posting)balquery.getSingleResult();
					total = total.add(ps.getAmount().abs());
				}
				temp.add(total);
			}				
			temp.add(pr.getCredit());
			temp.add(pr.getPaymentTerm());
			temp.add(pr.getStartDate());
			Integer supplierID = Integer.valueOf(pr.getSupplierId());
			temp.add(supplierID);
			if(supplierID.intValue() != 29999){
				datas.add(temp);
			}
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
		
		
	}
	
	/*DONE
		**method getSupplierDataId() get a specific supplier details from database using its id
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	public byte[] getSupplierDataId(String supplierAcc){
		ArrayList<Object> temp = new ArrayList<Object>();
		try{
			Query squery = em.createQuery("SELECT s FROM Supplier s WHERE s.account=?1");
			squery.setParameter(1, supplierAcc);
			Supplier supplier = (Supplier)squery.getSingleResult();
			BigDecimal total =  BigDecimal.ZERO;
			temp.add(supplier.getCompanyName());
			temp.add(supplier.getAccount());
			Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier.supplierId=?1");
			query.setParameter(1, supplier.getSupplierId());
			java.util.List<SupplierInvoice> l = (java.util.List<SupplierInvoice>)query.getResultList();
			if(l.isEmpty()){
				total =  new BigDecimal ("0");
				temp.add(total);
			}
	
			else{
				for(SupplierInvoice si : l){
					Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
					balquery.setParameter(1, si.getJournal().getJournalId());
					Posting ps = (Posting)balquery.getSingleResult();
					total = total.add(ps.getAmount().abs());
				}
				temp.add(total);
			}			
			temp.add(supplier.getCredit());
			temp.add(supplier.getPaymentTerm());
			temp.add(supplier.getStartDate());
			temp.add(new Integer(supplier.getSupplierId()));
		}
			catch(NoResultException ex){}	
		
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*DONE
		**method getSupplierDataName() get a specific supplier details from database using its name
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	public byte[] getSupplierDataName(String suppliername){	
		ArrayList<Object> temp = new ArrayList<Object>();
		try{
			Query squery = em.createQuery("SELECT s FROM Supplier s WHERE s.companyName=?1");
			squery.setParameter(1, suppliername);
			Supplier supplier = (Supplier)squery.getSingleResult();

			BigDecimal total =  BigDecimal.ZERO;
			temp.add(supplier.getCompanyName());
			temp.add(supplier.getAccount());
			Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier.supplierId=?1");
			query.setParameter(1, supplier.getSupplierId());
			java.util.List<SupplierInvoice> l = (java.util.List<SupplierInvoice>)query.getResultList();
			if(l.isEmpty()){
				total =  new BigDecimal ("0");
				temp.add(total);
			}
	
			else{
				for(SupplierInvoice si : l){
					Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
					balquery.setParameter(1, si.getJournal().getJournalId());
					Posting ps = (Posting)balquery.getSingleResult();
					total = total.add(ps.getAmount().abs());
				}
				temp.add(total);
			}			
			temp.add(supplier.getCredit());
			temp.add(supplier.getPaymentTerm());
			temp.add(supplier.getStartDate());
			temp.add(new Integer(supplier.getSupplierId()));
			}
			catch(NoResultException ex){}	
		
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	/*DONE
		**method getSupplierDataBalance() get all supplier details from database which acc balance from and to
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	
	
	public byte[] getSupplierDataBalance(BigDecimal from, BigDecimal to){
		java.util.List<Supplier> ls = (java.util.List<Supplier>)em.createQuery("SELECT s FROM Supplier s").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Supplier pr : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			BigDecimal total =  BigDecimal.ZERO;
			Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier.supplierId=?1");
			query.setParameter(1, pr.getSupplierId());
			java.util.List<SupplierInvoice> l = (java.util.List<SupplierInvoice>)query.getResultList();
			if(l.isEmpty()){
				total= new BigDecimal ("0");
			}
			else{
				for(SupplierInvoice si : l){
					Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
					balquery.setParameter(1, si.getJournal().getJournalId());
					Posting ps = (Posting)balquery.getSingleResult();
					total = total.add(ps.getAmount().abs());
				}
			}
			if(total.compareTo(from) >= 0 && total.compareTo(to) <= 0){
				temp.add(pr.getCompanyName());
				temp.add(pr.getAccount());
				temp.add(total);
				temp.add(pr.getCredit());
				temp.add(pr.getPaymentTerm());
				temp.add(pr.getStartDate());
				temp.add(new Integer(pr.getSupplierId()));
				datas.add(temp);

			}
		}	
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*SEEN
		**method getSupplierSingleData(int) get single supplier details from database
		**return byte array of supplier infomation
		**use by SupplierDetails GUI for viewing 
	*/
	public byte[] getSupplierSingleData(int supplierid){
		ArrayList<Object> temp = new ArrayList<Object>();
		try{
			Query squery = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierId=?1");
			squery.setParameter(1, supplierid);
			Supplier supplier = (Supplier)squery.getSingleResult();
			temp.add(supplier.getContactName());
			Address address = supplier.getAddresses();
			temp.add(address. getAddressLine1());	
			temp.add(address. getAddressLine2());	
			temp.add(address.getTownCity());	
			temp.add(address.getCountyState());	
			temp.add(address.getPostCode());	
			temp.add(address.getCountry());	
			temp.add(supplier.getCompanyTele());	
			temp.add(supplier.getFax());	
			temp.add(supplier.getCompanyEmail());	
			}
			catch(NoResultException ex){}	
		
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*SEEN
		**method getSupplierSingleDetails(int) get single supplier details from database for editing
		**return byte array of supplier infomation
		**use by EditSupplier GUI 
	*/
	public byte[] getSupplierSingleDetails(int supplierId){
		ArrayList<Object> temp = new ArrayList<Object>();
		ArrayList<String> addtemp = new ArrayList<String>();
		try{
			Query squery = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierId=?1");
			squery.setParameter(1, supplierId);
			Supplier supplier = (Supplier)squery.getSingleResult();
			temp.add(supplier.getCompanyName());
			temp.add(supplier.getContactName());
			temp.add(supplier.getAccount());
			Address address = supplier.getAddresses();
			addtemp.add(address.getAddressLine1());	
			addtemp.add(address.getAddressLine2());	
			addtemp.add(address.getTownCity());	
			addtemp.add(address.getCountyState());	
			addtemp.add(address.getPostCode());	
			addtemp.add(address.getCountry());
			temp.add(addtemp);
			temp.add(supplier.getCompanyTele());	
			temp.add(supplier.getFax());	
			temp.add(supplier.getCompanyEmail());
			temp.add(supplier.getStartDate());	
			temp.add(supplier.getCredit());	
			temp.add(supplier.getPaymentTerm());				
		}
			catch(NoResultException ex){}	
		
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	/*DONE
		**method updateSupplier(byte[] byte[]) update supplier details
		**use by EditSupplier GUI 
	*/
	public void updateSupplier(byte[] suppliers, byte[] supplierId){
		ArrayList<Object>  supplierdetails = null;
		Integer supplierids = null;
		ByteArrayInputStream bais_quote = new ByteArrayInputStream(suppliers);
		ByteArrayInputStream bais_proid = new ByteArrayInputStream(supplierId);
		try {
			ObjectInputStream ois_quote = new ObjectInputStream(bais_quote);
			ObjectInputStream ois_proid = new ObjectInputStream(bais_proid);
		try{
			supplierdetails = (ArrayList<Object>)ois_quote.readObject();
			supplierids = (Integer)ois_proid.readObject();
		} finally {
			ois_quote.close();
			ois_proid.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierId=?1");
		query.setParameter(1, supplierids.intValue()); 
		Supplier sp = (Supplier)query.getSingleResult();
		Address address = sp.getAddresses();
		sp.setCompanyName((String)supplierdetails.get(0));
		sp.setContactName((String)supplierdetails.get(1));
		sp.setCompanyTele((String)supplierdetails.get(2));
		sp.setCompanyEmail((String)supplierdetails.get(3));
		sp.setStartDate((Date)supplierdetails.get(4));
		sp.setCredit(new BigDecimal((String)supplierdetails.get(5)));
		sp.setPaymentTerm((String)supplierdetails.get(6));
		sp.setAccount((String)supplierdetails.get(7));
		sp.setFax((String)supplierdetails.get(8));
		String str[] = (String[])supplierdetails.get(9);
		address.setAddressLine1(str[0]);
		address.setAddressLine2(str[1]);
		address.setTownCity(str[2]);
		address.setCountyState(str[3]);
		address.setPostCode(str[4]);
		address.setCountry(str[5]);
		sp.setAddresses(address);
		em.merge(sp);
	}
		
	/*DONE
		**method addSupplierInvoice(byte[]) add new invoice to of a perticular supplier
		**return byte array of supplier invoice details
		**use by AddSupplierInvoice GUI 
	*/
	public void addSupplierInvoice(byte[] supplierinvoice){
		ArrayList<Object> supplierInv = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(supplierinvoice);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			supplierInv = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Posting> postarray = new ArrayList<Posting>();
	   Journal jr = new Journal();
	   
		//calculate vat
		Posting ps = null;
		
		BigDecimal amount = new BigDecimal((String)supplierInv.get(5));
		BigDecimal vatAmount =  BigDecimal.ZERO;
	   if(!supplierInv.get(6).equals("None")){
		 ps = new Posting();
		 String vat[] = ((String)supplierInv.get(6)).split("%");
		 BigDecimal vatValue = new BigDecimal(vat[0]);
		 if(vatValue.compareTo(new BigDecimal("0")) > 0){
			vatAmount = (amount.multiply(vatValue)).divide((new BigDecimal("100").add(vatValue)), DECIMALS, ROUNDING_MODE);
			//amount = amount.subtract(vatAmount);
			}
		}
		
	    AccountCode codeDR = em.find(AccountCode.class, ((Integer)supplierInv.get(2)).intValue()); 
	    AccountCode codeCR =  em.find(AccountCode.class, 20010); 
	   // jr.setcodeIdCR(codeCR.getGroup());
	   // jr.setcodeIdDR(codeDR.getGroup());
	   
	    Posting ps1 = new Posting();
	    ps1.setAmount(amount.subtract(vatAmount));
	    ps1.setCodeId(codeDR);
	    postarray.add(ps1);
	    ps1.setJournal(jr);
	   
	   Posting ps2 = new Posting();
	   ps2.setAmount(new BigDecimal((String)supplierInv.get(5)).negate());
	   ps2.setCodeId(codeCR);
	   postarray.add(ps2);
	   ps2.setJournal(jr);
	   
	   if(ps != null){
		 AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
		 ps.setAmount(vatAmount);
		 ps.setCodeId(vatcodeDR);
		 postarray.add(ps);
		 ps.setJournal(jr);
	   }
	   
	   jr.setPostingdrcr(postarray);
	   em.persist(jr);
	   
	   SupplierInvoice invoice = new SupplierInvoice();
	   invoice.setProject(em.find(Project.class, ((Integer)supplierInv.get(0)).intValue()));
	   invoice.setSupplier(em.find(Supplier.class, ((Integer)supplierInv.get(1)).intValue()));
	   invoice.setJournal(jr);
	   invoice.setInvoiceDate((Date)supplierInv.get(4));
	   invoice.setInvoiceNumber((String)supplierInv.get(3));
	   em.persist(invoice);
	}
	
	/**SEEN
		**method getMaterialOutgoingData() get all supplier invoices for view
		**return byte array of supplier's invoice
		**use by Outgoingview GUI 
	*/
	public byte[] getMaterialOutgoingData(){
		java.util.List<SupplierInvoice> ls = (java.util.List<SupplierInvoice>)em.createQuery("SELECT s FROM SupplierInvoice s").getResultList();	
		return objectWriter(supplierInvoiceSearch(ls));
	}
	
	/*SEEN
		**method supplierInvoiceDate(Date from, Date to) get all supplier invoices for view between 2 dates
		**return byte array of supplier's invoice
		**use by Outgoingview GUI 
	*/
	public byte[] supplierInvoiceDate(Date from, Date to){
		Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.invoiceDate  BETWEEN ?1 AND ?2");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<SupplierInvoice> ls = (java.util.List<SupplierInvoice>)query.getResultList();
		return objectWriter(supplierInvoiceSearch(ls));
	}
	
	/*DONE
		**method supplierInvoiceAmount(Double from, Double to) get all supplier invoices for view in between 2 amounts 
		**return byte array of supplier's invoice
		**use by Outgoingview GUI 
	*/
	public byte[] supplierInvoiceAmount(BigDecimal from, BigDecimal to){
		java.util.List<SupplierInvoice> ls = (java.util.List<SupplierInvoice>)em.createQuery("SELECT s FROM SupplierInvoice s").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SupplierInvoice si : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", si.getJournal());
			Posting ps = (Posting)payablequery.getSingleResult();
		
			Query paymentquery = em.createQuery("SELECT s FROM SupplierPayment s WHERE s.supplierInvoice=:supplierInv");
			paymentquery.setParameter("supplierInv", si);
			java.util.List<SupplierPayment> sp = (java.util.List<SupplierPayment>)paymentquery.getResultList();
			BigDecimal total =  BigDecimal.ZERO;
			if(sp.isEmpty()){
				total =  new BigDecimal ("0");
			}
			else{
				for(SupplierPayment pay : sp){
	
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010 OR p.codeId.codeId=10040");				
					cashquery.setParameter("jornal", pay.getJournal());
					
					Query ccquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20020");
					ccquery.setParameter("jornal", pay.getJournal());
					
					try{
						Posting ccps = (Posting)ccquery.getSingleResult();
						total = total.add(ccps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
					try{
						Posting cashps = (Posting)cashquery.getSingleResult();
						total = total.add(cashps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
				}
				temp.add(total);
			}
			
			if(total.compareTo(from) >= 0 && total.compareTo(to) <= 0){
				temp.add(si.getSupplier().getCompanyName());
				temp.add(si.getProject().getProjectName());
				temp.add(si.getInvoiceNumber());
				temp.add(si.getInvoiceDate());
				temp.add(ps.getAmount().abs());
				temp.add(total);
				int daydiff = days(si.getInvoiceDate());
				String [] strterm = si.getSupplier().getPaymentTerm().split("\\s+");
				int term = (new Integer(strterm[0])).intValue();
				if(daydiff < term){
					temp.add(new String("0 days"));
				}
				else{
					int diff = daydiff - term;
					String days = null;
						if(diff == 1)
							days = "day";
						else{
							days = "days";
						}
					temp.add(String.format("%d %s", diff, days));
				}
				temp.add(new Integer(si.getSupplierInvoiceId()));
			}
			datas.add(temp);
		}
		return objectWriter(datas);
	
	}
	
	/*SEEN
		**method supplierInvoiceID(int supplierid) get single supplier invoices for view with supplier id
		**return byte array of supplier's invoice
		**use by Outgoingview GUI 
	*/
	public byte[] supplierInvoiceID(int supplierid){
		Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier=:suppliers");
		query.setParameter("suppliers", em.find(Supplier.class, supplierid));   
		java.util.List<SupplierInvoice> ls = (java.util.List<SupplierInvoice>)query.getResultList();
		return objectWriter(supplierInvoiceSearch(ls));
	}
	
	/*DONE
		**method supplierInvoice(String invoice) get single supplier invoices for view with invoice number
		**return byte array of supplier's invoice
		**use by Outgoingview GUI 
	*/
	public byte[] supplierInvoice(String invoice){
		Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.invoiceNumber = :invoice");	
		query.setParameter("invoice", invoice);
		SupplierInvoice si = null;
		try{
		    si = (SupplierInvoice)query.getSingleResult();
		}
		catch(NoResultException ex){}
	
		ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(si.getSupplier().getCompanyName());
			temp.add(si.getProject().getProjectName());
			temp.add(si.getInvoiceNumber());
			temp.add(si.getInvoiceDate());
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", si.getJournal());
			Posting ps = (Posting)payablequery.getSingleResult();
			temp.add(ps.getAmount().abs());
			Query paymentquery = em.createQuery("SELECT s FROM SupplierPayment s WHERE s.supplierInvoice=:supplierInv");
			paymentquery.setParameter("supplierInv", si);
			java.util.List<SupplierPayment> sp = (java.util.List<SupplierPayment>)paymentquery.getResultList();
			BigDecimal total =  BigDecimal.ZERO;
			if(sp.isEmpty()){
				total = new BigDecimal("0");
				temp.add(total);
			}

			else{
				for(SupplierPayment pay : sp){
	
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010 OR p.codeId.codeId=10040");				
					cashquery.setParameter("jornal", pay.getJournal());
					
					Query ccquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20020");
					ccquery.setParameter("jornal", pay.getJournal());
					
					try{
						Posting ccps = (Posting)ccquery.getSingleResult();
						total = total.add(ccps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
					try{
						Posting cashps = (Posting)cashquery.getSingleResult();
						total = total.add(cashps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
				}
				temp.add(total);
			}
			int daydiff = days(si.getInvoiceDate());
			String [] strterm = si.getSupplier().getPaymentTerm().split("\\s+");
			int term = (new Integer(strterm[0])).intValue();
			if(daydiff < term){
				temp.add(new String("0 days"));
			}
			else{
				int diff = daydiff - term;
				String days = null;
					if(diff == 1)
						days = "day";
					else{
						days = "days";
					}
				temp.add(String.format("%d %s", diff, days));
			}
			temp.add(new Integer(si.getSupplierInvoiceId()));
			
		return objectWriter(temp);
	}
	
	/*DONE
		**method editSupplierInvoices(int invoiceid) get single supplier details for editing
		**return byte array of supplier's invoice
		**use by EditSupplierInvoice GUI 
	*/
	public byte[] editSupplierInvoice(int invoiceid){

		ArrayList<Object> temp = new ArrayList<Object>();
		SupplierInvoice si  = em.find(SupplierInvoice.class,  invoiceid);
		Project pro = si.getProject();
		temp.add(String.valueOf(pro.getProjectId())+"."+pro.getProjectName());
		temp.add(String.valueOf(si.getSupplier().getSupplierId()));
		temp.add(si.getInvoiceNumber());
		temp.add(si.getInvoiceDate());
		Journal jr = si.getJournal();
		
		Query querymaterial = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=50010");
		querymaterial.setParameter("jornal" , jr);
		Posting accmaterial = null;
		
		Query queryvate = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
		queryvate.setParameter("jornal" , jr);
		Posting queryvat = null;
		
		BigDecimal accountpayable = BigDecimal.ZERO; 
		try{
			accmaterial = (Posting)querymaterial.getSingleResult();
		}
		catch(NoResultException e){}
		
		try{
			queryvat = (Posting)queryvate.getSingleResult();
		}
		catch(NoResultException e){}
		
		accountpayable = (accmaterial.getAmount().abs());
		
		temp.add(accountpayable);
		
		if(queryvat != null){
			
			temp.add(parcentage(accountpayable, queryvat.getAmount()));
		}
		else{
			temp.add("none");
		}

		return objectWriter(temp);
	}
	
	/*DONE
		**method parcentage(double, double) update helper method for any bean method calculating parcentage
		**String value
		**use by any bean method
	*/
	String parcentage(BigDecimal netvat, BigDecimal vat){
		return String.valueOf((vat.multiply(new BigDecimal("100"))).divide(netvat, DECIMALS, ROUNDING_MODE));
	}
	
	/*DONE
		**method editSupplierInvoices(byte[]) update supplier invoice to of a perticular supplier invoice
		**return byte array of supplier invoice details
		**use by EditSupplierInvoice GUI 
	*/
	public void editSupplierInvoices(byte[] supplierinvoice){
		ArrayList<Object> supplierInv = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(supplierinvoice);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			supplierInv = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SupplierInvoice invoice = em.find(SupplierInvoice.class, ((Integer)supplierInv.get(0)).intValue());
		Journal jr = em.find(Journal.class, invoice.getJournal().getJournalId());
		ArrayList<Posting> post = new ArrayList<Posting>(jr.getPostingdrcr());
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		//calculate vat
		//long amount = ((long)supplierInv.get(6));
		Posting ps = null;
		BigDecimal amount = new BigDecimal((String)supplierInv.get(6));
		BigDecimal vatAmount =  new BigDecimal("0");
	   if(!((String)supplierInv.get(7)).equals("none") && post.size()> 2){
		 ps = post.get(2);
		 String vat[] = ((String)supplierInv.get(7)).split("%");
		 BigDecimal vatValue = new BigDecimal(vat[0]);
		 if(vatValue.compareTo(new BigDecimal("0")) > 0){
			vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
			amount = amount.subtract(vatAmount);
			}
		 AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
		 ps.setAmount(vatAmount);
		 ps.setCodeId(vatcodeDR);
		 postarray.add(ps);
		 ps.setJournal(jr);
		}
		else if(ps == null && !((String)supplierInv.get(7)).equals("none")){
			ps = new Posting();
			String vat[] = ((String)supplierInv.get(7)).split("%");
			BigDecimal vatValue = new BigDecimal(vat[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
				amount = amount.subtract(vatAmount);
			}
			AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
			ps.setAmount(vatAmount);
			ps.setCodeId(vatcodeDR);
			postarray.add(ps);
			ps.setJournal(jr);
	   }
	   else if(((String)supplierInv.get(7)).equals("none") && post.size()>2) {
	   
			em.remove( post.get(2));
			
	   }
	   
	    AccountCode codeDR = em.find(AccountCode.class, ((Integer)supplierInv.get(3)).intValue()); 
	    AccountCode codeCR =  em.find(AccountCode.class, 20010); 
	   // jr.setcodeIdCR(codeCR.getGroup());
	   // jr.setcodeIdDR(codeDR.getGroup());
	   
	    Posting ps1 =  post.get(0);
	    ps1.setAmount(amount);
	    ps1.setCodeId(codeDR);
	    postarray.add(ps1);
	    ps1.setJournal(jr);
	   
	   Posting ps2 =  post.get(1);
	   ps2.setAmount(new BigDecimal((String)supplierInv.get(6)).negate());
	   ps2.setCodeId(codeCR);
	   postarray.add(ps2);
	   ps2.setJournal(jr);
	   
	   jr.setPostingdrcr(postarray);
	   em.persist(jr);
	   //em.flush();
	   
	   invoice.setProject(em.find(Project.class, ((Integer)supplierInv.get(1)).intValue()));
	   invoice.setSupplier(em.find(Supplier.class, ((Integer)supplierInv.get(2)).intValue()));
	   invoice.setJournal(jr);
	   invoice.setInvoiceDate((Date)supplierInv.get(5));
	   invoice.setInvoiceNumber((String)supplierInv.get(4));
	   em.persist(invoice);
	}
	
	//SEEN
	public byte[] jornalSize(int id){
		Journal jr = em.find(Journal.class, id);
		ArrayList<Posting> post = new ArrayList<Posting>(jr.getPostingdrcr());
		return objectWriter(String.valueOf(post.get(2).getAmount()));
	}
	
	/**DONE
		**method addMaterialPayment(byte[] materialpayment) add payment to supplier invoice
		**use SupplierPayment entity to add payment to database
		**use by MaterialPayment GUI 
	*/
	public void addMaterialPayment(byte[] materialpayment){
		ArrayList<String> materialPayments = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(materialpayment);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			materialPayments = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		ArrayList<SupplierPayment> paymentList = new ArrayList<SupplierPayment>();
		SupplierPayment payment = new SupplierPayment();
		Journal jrsettle = null;
		
		//ArrayList<Posting> post = new ArrayList<Posting>();
		SupplierInvoice invoice = em.find(SupplierInvoice.class, (Integer.valueOf(materialPayments.get(0))).intValue());
		ArrayList<SupplierPayment> paymentPosted = new ArrayList<SupplierPayment>(invoice.getSupplierPayment());

		Journal oldjr = em.find(Journal.class, invoice.getJournal().getJournalId());
		ArrayList<Posting> payableinfo = new ArrayList<Posting>(oldjr.getPostingdrcr());
		
		BigDecimal amount = new BigDecimal(materialPayments.get(1));
		int codeid = (Integer.valueOf(materialPayments.get(2))).intValue();
		java.sql.Date receivedDate = java.sql.Date.valueOf(materialPayments.get(5));
		String noteStr = materialPayments.get(4);
		//AccountCode from = null;
		
		Query updatepayable = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
		Posting payable = null;
		try{
			updatepayable.setParameter("jornal", oldjr);
		    payable = (Posting)updatepayable.getSingleResult();
		}
		  catch(NoResultException ex){}

		/* if(codeid == 20000){
			MaterialsPayableCreditNote note = new MaterialsPayableCreditNote();
			note.setAmount(amount);
			note.setSupplierInvoice(invoice);
			note.setCNnumber(String.valueOf(materialPayments.get(4)));
			note.setIssueDate((Date)materialPayments.get(5));

			Query materialquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=50010");
			Posting updatematerials = null;
			Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
			Posting updatevat = null;
			BigDecimal vatparcentage =  BigDecimal.ZERO;
			BigDecimal vatcn =  BigDecimal.ZERO;
			try{
				materialquery.setParameter("jornal", oldjr);
				updatematerials = (Posting)materialquery.getSingleResult();
				vatquery.setParameter("jornal", oldjr);
				updatevat = (Posting)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			if(updatevat != null){
				//vatparcentage = updatevat.getAmount()*new BigDecimal("100")/(updatematerials.getAmount() + updatevat.getAmount());
				vatparcentage = new BigDecimal(parcentage(updatematerials.getAmount(), updatevat.getAmount()));
				vatcn = (vatparcentage.multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
				updatevat.setAmount(updatevat.getAmount().subtract(vatcn));
				post.add(updatevat);
				updatevat.setJournal(oldjr);
			}
			note.setVat(vatcn);
			em.persist(note);
			updatematerials.setAmount(updatematerials.getAmount().subtract(amount.subtract(vatcn)));   //credit note take away cn vat minus old material value
			post.add(updatematerials);
			updatematerials.setJournal(oldjr);
			
			payable.setAmount(payable.getAmount().add( amount));
			post.add(payable);
			payable.setJournal(oldjr);
			
			oldjr.setPostingdrcr(post);
			em.persist(oldjr);	
		}
		
		else{
			payable.setAmount(payable.getAmount().add(amount));
			//post.add(payable);
			//payable.setJournal(oldjr);
			if(codeid == 10010){
				from = em.find(AccountCode.class, codeid);
				Posting ps = new Posting();
				ps.setCodeId(from);
				ps.setAmount(amount.negate());
				settlement.add(ps);
				ps.setJournal(jrsettle);
			}
			else{
				from = em.find(AccountCode.class, codeid);
				Posting ps1 = new Posting();
				ps1.setCodeId(from);
				ps1.setAmount(amount.negate());
				settlement.add(ps1);
				ps1.setJournal(jrsettle);
			}
		
		}*/
		
		if(codeid == 20000){
			materialCreditNote(invoice, oldjr, payable, receivedDate, amount, noteStr);
		}
		
		else{
			jrsettle = materialPaymentJournal(codeid, payable, amount);
		}
		
		if(!paymentPosted.isEmpty()){
			for(SupplierPayment payments : paymentPosted){
				paymentList.add(payments);
			}
		}
		//if(!settlement.isEmpty()){
			//jrsettle.setPostingdrcr(settlement);
			//em.persist(jrsettle);
		payment.setJournal(jrsettle);
			//payment.setSupplierInvoice(invoice);
		payment.setSupplierInvoice(invoice);
		payment.setType(materialPayments.get(3));
		payment.setNote(noteStr);
		payment.setPaymentDate(receivedDate);
		paymentList.add(payment);
		invoice.setSupplierPayment(paymentList);
		em.persist(invoice);
		//}
	}
	
	/**
		**materialPaymentJournal(SupplierInvoice invoice, Journal oldjr)
		**bean helper method to handle credit note transaction
		**use by addMaterialPayment bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private Journal materialPaymentJournal(int codeid, Posting payable, BigDecimal amount){
		payable.setAmount(payable.getAmount().add(amount));
		ArrayList<Posting> settlement = new ArrayList<Posting>();
		Journal jrsettle = new Journal();
		AccountCode from = em.find(AccountCode.class, codeid);
		
		jrsettle.setCodeDR(String.valueOf(payable.getCodeId().getGroup().getGroupId()));
		jrsettle.setCodeCR(String.valueOf(from.getGroup().getGroupId()));
		
		Posting ps = new Posting();
		ps.setCodeId(from);
		ps.setAmount(amount.negate());
		settlement.add(ps);
		ps.setJournal(jrsettle);
		jrsettle.setPostingdrcr(settlement);
		em.persist(jrsettle);
		return jrsettle;
	}
	
	/**
		**materialCreditNote(SupplierInvoice invoice, Journal oldjr)
		**bean helper method to handle credit note transaction
		**use by addMaterialPayment bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	private void materialCreditNote(SupplierInvoice invoice, Journal oldjr, Posting payable, 
									java.sql.Date dateReceice, BigDecimal amount, String creditNumber){
		//if(codeid == 20000){
		ArrayList<MaterialsPayableCreditNote> noteList = new ArrayList<MaterialsPayableCreditNote>();
		ArrayList<MaterialsPayableCreditNote> postedList = 
						new ArrayList<MaterialsPayableCreditNote>(invoice.getMaterialsPayableCreditNote());
						
		MaterialsPayableCreditNote note = new MaterialsPayableCreditNote();
		note.setAmount(amount);
		note.setSupplierInvoice(invoice);
		note.setCNnumber(creditNumber);
		note.setIssueDate(dateReceice);

		Query materialquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=50010");
		Posting updatematerials = null;
		Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
		Posting updatevat = null;
		BigDecimal vatparcentage =  BigDecimal.ZERO;
		BigDecimal vatcn =  BigDecimal.ZERO;
		try{
			materialquery.setParameter("jornal", oldjr);
			updatematerials = (Posting)materialquery.getSingleResult();
			vatquery.setParameter("jornal", oldjr);
			updatevat = (Posting)vatquery.getSingleResult();
		}
		catch(NoResultException ex){}
		if(updatevat != null){
				
			vatparcentage = new BigDecimal(parcentage(updatematerials.getAmount(), updatevat.getAmount()));
			vatcn = (vatparcentage.multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
			updatevat.setAmount(updatevat.getAmount().subtract(vatcn));
			//post.add(updatevat); check if this type of update is necessary
			//updatevat.setJournal(oldjr);check if this type of update is necessary
		}
		note.setVat(vatcn);
		if(!postedList.isEmpty()){
			for(MaterialsPayableCreditNote postedCreditNote : postedList){
				noteList.add(postedCreditNote);
			}
		}
		noteList.add(note);
		invoice.setMaterialsPayableCreditNote(noteList);
		
		
		updatematerials.setAmount(updatematerials.getAmount().subtract(amount.subtract(vatcn)));   //credit note take away cn vat minus old material value
		//post.add(updatematerials); check if this type of update is necessary
		//updatematerials.setJournal(oldjr); check if this type of update is necessary
			
		payable.setAmount(payable.getAmount().add( amount));
		//post.add(payable); check if this type of update is necessary
		//payable.setJournal(oldjr); check if this type of update is necessary
			
		//oldjr.setPostingdrcr(post); check if this type of update is necessary
		//em.persist(oldjr);	check if this type of update is necessary
		//}
		em.persist(invoice);
	}
	/*DONE
		**method addMaterialPayments(byte[], byte[]) add payments to supplier invoices
		**use SupplierPayment entity to add payment to database
		**use by BatchPayment GUI 
	*/
	public void addMaterialPayments(byte[] paymentDetails, byte[] paymentInfo){
		ArrayList<ArrayList<Object>> payDetails = null;
		ArrayList<Object> payInfo = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(paymentDetails);
		ByteArrayInputStream baispayInfo = new ByteArrayInputStream(paymentInfo);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			ObjectInputStream oispayInfo = new ObjectInputStream(baispayInfo);

		try{
			payDetails = (ArrayList<ArrayList<Object>>)ois.readObject();
			payInfo = (ArrayList<Object>)oispayInfo.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Posting payable = null;
		AccountCode from = null;
		for(int i = 0; i < payDetails.size(); i++){
		
			SupplierInvoice invoice = em.find(SupplierInvoice.class, ((Integer)(payDetails.get(i).get(0))).intValue());
			Journal oldjr = em.find(Journal.class, invoice.getJournal().getJournalId());
			
			BigDecimal amount = new BigDecimal((String)payDetails.get(i).get(1)); //COMING BACK
			
			ArrayList<Posting> settlement = new ArrayList<Posting>();
			SupplierPayment payment = new SupplierPayment();
			Journal jrsettle = new Journal();
			ArrayList<Posting> post = new ArrayList<Posting>();

			Query updatepayable = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			try{
				updatepayable.setParameter("jornal", oldjr);
				payable = (Posting)updatepayable.getSingleResult();
			}
			catch(NoResultException ex){}
			payable.setAmount(payable.getAmount().add(amount));
			post.add(payable);
			payable.setJournal(oldjr);
			
			
			from = em.find(AccountCode.class, ((Integer)(payInfo.get(0))).intValue());
			Posting ps = new Posting();
			ps.setCodeId(from);
			ps.setAmount(amount.negate());
			settlement.add(ps);
			ps.setJournal(jrsettle);
			
		
	
			jrsettle.setPostingdrcr(settlement);
			em.persist(jrsettle);
			payment.setJournal(jrsettle);
			payment.setSupplierInvoice(invoice);
			payment.setType(String.valueOf(payInfo.get(1)));
			payment.setNote(String.valueOf(payDetails.get(i).get(2)));
			payment.setPaymentDate((Date)payInfo.get(2));
			em.persist(payment);
			
		}
	}
	
	/*DONE
		**method byte[] materialPaymentBalance(int) get the balance on supplier account
		**return byte[] array of double object
		**use by material payment details GUI
	*/
	
	public byte[] materialPaymentBalance(int invoiceId){
		Query query = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplierInvoiceId = ?1");
		query.setParameter(1, invoiceId);   
		SupplierInvoice si = null;
		try{
		    si = (SupplierInvoice)query.getSingleResult();
		}
		catch(NoResultException ex){}
		Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
		payablequery.setParameter("jornal", si.getJournal());
		Posting ps = null;
		try{
			ps = (Posting)payablequery.getSingleResult();
		}
		catch(NoResultException ex){}
		return objectWriter(ps.getAmount().abs());
	}
	
	/*DONE
		**method double getVATdouble bean helper get double use to calculate vat (need to be repair, wrong calculation for VAT)
		**return double figure
		**use by bean  method
	*/
	BigDecimal getVATdouble(Journal obj, int code1, int code2){
		Query  query = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
		Posting updatematerials = null;
		Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?2");
		Posting updatevat = null;
		BigDecimal vatparcentage =  new BigDecimal ("0");
		BigDecimal vatcn =  new BigDecimal ("0");
		try{
			 query.setParameter("jornal", obj);
			 query.setParameter(1, code1);
			updatematerials = (Posting) query.getSingleResult();
		}
		catch(NoResultException ex){}
		
		try{
			vatquery.setParameter("jornal", obj);
			vatquery.setParameter(2, code2);
			updatevat = (Posting)vatquery.getSingleResult();
			//vatparcentage = updatevat.getAmount()*100/(updatematerials.getAmount() + updatevat.getAmount());
		}
		catch(NoResultException ex){}
		if(updatevat != null){
			vatparcentage = (updatevat.getAmount().multiply(new BigDecimal("100"))).divide(updatematerials.getAmount().add(updatevat.getAmount()), DECIMALS, ROUNDING_MODE);
		}
			
		return vatparcentage;
			
	}
				
				
	
	/***DONE
		**method getSupplierPaymentDetails(int) get individual payment detail for single invoice
		**return byte array of payment details
		**use by MaterialPaymentDetails GUI 
	*/
	public byte[] getSupplierPaymentDetails(int supplierInvoiceId){
		
		Query creditnotequery = em.createQuery("SELECT c FROM MaterialsPayableCreditNote c WHERE c.supplierInvoice = :supplierInvoice");
		creditnotequery.setParameter("supplierInvoice", em.find(SupplierInvoice.class, supplierInvoiceId));
		java.util.List<MaterialsPayableCreditNote> cn = (java.util.List<MaterialsPayableCreditNote>)creditnotequery.getResultList();
		
		Query paymentquery = em.createQuery("SELECT p FROM SupplierPayment p WHERE p.supplierInvoice = :supplierInvoice");
		SupplierInvoice invoice = em.find(SupplierInvoice.class, supplierInvoiceId);
		paymentquery.setParameter("supplierInvoice", invoice);
		java.util.List<SupplierPayment> pay = (java.util.List<SupplierPayment>)paymentquery.getResultList();
		ArrayList<ArrayList<Object>> supplierpay = new ArrayList<ArrayList<Object>>();
		
		for(SupplierPayment payment : pay){
			ArrayList<Object> temp = new ArrayList<Object>();
			BigDecimal amount =  BigDecimal.ZERO;
			temp.add(payment.getPaymentDate());
			Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010 OR p.codeId.codeId=10040");				
			cashquery.setParameter("jornal", payment.getJournal());
					
			Query ccquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20020");
			ccquery.setParameter("jornal", payment.getJournal());
					
			try{
				Posting ccps = (Posting)ccquery.getSingleResult();
				
				if(ccps != null){
					amount = ccps.getAmount();
					temp.add(amount.abs());
				}
			}
			catch(NoResultException ex){}
			try{
				Posting cashps = (Posting)cashquery.getSingleResult();
				if(cashps != null){
					amount = cashps.getAmount();
					temp.add(amount.abs());
				}
						
			}
			catch(NoResultException ex){}
			temp.add((getVATdouble(invoice.getJournal(), 50010, 70020).multiply(amount.abs())).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
			temp.add(payment.getType());
			temp.add(payment.getNote());
			//temp.add(payment.getPaymentDate());
			temp.add(Integer.valueOf(payment.getSupplierPaymentId()));
			supplierpay.add(temp);
		}
		
		for(MaterialsPayableCreditNote note : cn){
			ArrayList<Object> temp1 = new ArrayList<Object>();
			temp1.add(note.getIssueDate());	
			temp1.add(note.getAmount()); 	
			temp1.add(note.getVat());			
			temp1.add("Credit Note");	
			temp1.add(note.getCNnumber());	
			temp1.add(Integer.valueOf(note.getCreditNoteId()));
			supplierpay.add(temp1);			
			
		}
		return objectWriter(supplierpay);
	}
	
	/*SEEN
		objectWriter(obj) write object data to use by any bean method that return byte array
		bean method helper
		return byte array
	*/
	public byte[] objectWriter(Object obj){
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(obj);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*DONE
		suppplierInvoiceSearch(List) helper method for bean method return supplier payment view
		takes List has an argument
		return arraylist of arraylist of object
	*/
	public ArrayList<ArrayList<Object>> supplierInvoiceSearch(java.util.List<SupplierInvoice> ls){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SupplierInvoice si : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(si.getSupplier().getCompanyName());
			temp.add(si.getProject().getProjectName());
			temp.add(si.getInvoiceNumber());
			temp.add(si.getInvoiceDate());
			
			Query cnquery = em.createQuery("SELECT c FROM MaterialsPayableCreditNote c WHERE c.supplierInvoice=:supplierInvoice");
			cnquery.setParameter("supplierInvoice", si);
			java.util.List<MaterialsPayableCreditNote> cn = (java.util.List<MaterialsPayableCreditNote>)cnquery.getResultList();
			BigDecimal cntotal =  BigDecimal.ZERO;
			for(MaterialsPayableCreditNote note : cn){
				cntotal = cntotal.add(note.getAmount());
			}
			
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=50010");
			payablequery.setParameter("jornal", si.getJournal());
			Posting psmaterials = null;
			try{
				psmaterials = (Posting)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
			vatquery.setParameter("jornal", si.getJournal());
			Posting psvat = null;
			try{
				psvat = (Posting)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			BigDecimal vat =  BigDecimal.ZERO;
			if(psvat != null){
				vat = psvat.getAmount();
			}
			
			temp.add((psmaterials.getAmount().add(cntotal)).add(vat));
			Query paymentquery = em.createQuery("SELECT s FROM SupplierPayment s WHERE s.supplierInvoice=:supplierInv");
			paymentquery.setParameter("supplierInv", si);
			java.util.List<SupplierPayment> sp =  (java.util.List<SupplierPayment>)paymentquery.getResultList();
			BigDecimal total =  BigDecimal.ZERO;
			if(sp.isEmpty()){
				total =  new BigDecimal ("0");
				temp.add(total.add(cntotal));
			}
			else{
				for(SupplierPayment pay : sp){
	
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010 OR p.codeId.codeId=10040");				
					cashquery.setParameter("jornal", pay.getJournal());
					
					Query ccquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20020");
					ccquery.setParameter("jornal", pay.getJournal());
					
					try{
						Posting ccps = (Posting)ccquery.getSingleResult();
						total = total.add(ccps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
					try{
						Posting cashps = (Posting)cashquery.getSingleResult();
						total = total.add(cashps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
				}
				temp.add(total.add(cntotal));
			}
			int daydiff = days(si.getInvoiceDate());
			String [] strterm = si.getSupplier().getPaymentTerm().split("\\s+");
			int term = (Integer.valueOf(strterm[0])).intValue();
			if(daydiff < term){
				temp.add(new String("0 days"));
			}
			else{
				int diff = daydiff - term;
				String days = null;
					if(diff == 1)
						days = "day";
					else{
						days = "days";
					}
				temp.add(String.format("%d %s", diff, days));
			}
			temp.add(Integer.valueOf(si.getSupplierInvoiceId()));
			datas.add(temp);
		}
		return datas;
	}
	
	/*DONE
		supplierBatchInvoice(int) method get supplier invoice number and outstanding amount for batch payment
		takes supplier id as an argument
		return arraylist of arraylist of object
	*/
	public byte[] supplierBatchInvoice(int supplierId){
		Query supplierquery = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierId=?1");
		supplierquery.setParameter(1, supplierId);
		Supplier sp = null;
		try{
			sp = (Supplier)supplierquery.getSingleResult();
		}
		catch(NoResultException ex){}
		Query invoicequery = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.supplier = :supplier");
		invoicequery.setParameter("supplier", sp); 
		java.util.List<SupplierInvoice> ls = (java.util.List<SupplierInvoice>)invoicequery.getResultList();
		ArrayList<ArrayList<Object>> objarray = new ArrayList<ArrayList<Object>>();
		for(SupplierInvoice invoice : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(invoice.getInvoiceNumber());
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", invoice.getJournal());
			Posting ps = null;
			try{
				ps = (Posting)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			temp.add(String.valueOf(ps.getAmount().abs()));
			temp.add(String.valueOf(invoice.getSupplierInvoiceId()));
			objarray.add(temp);
		}
		return objectWriter(objarray);
	}

		/*SEEN
			**method addSubContractor(byte[] subContractinfo) add new sub contractor to database
			**it takes byte of sub contractor infomation
			**use by AddSubContractor GUI
		*/
		public void addSubContractor(byte[] subContractinfo){
			ArrayList<Object> subcontractor = null;
		
			ByteArrayInputStream bais = new ByteArrayInputStream(subContractinfo);

			try {
				ObjectInputStream ois = new ObjectInputStream(bais);

			try{
				subcontractor = (ArrayList<Object>)ois.readObject();

			} finally {
				ois.close();
				}
			} catch (Exception e) {
			e.printStackTrace();
			}
		
			SubContractor sp = new SubContractor();
			Address address = new Address();
			sp.setCompanyName((String)subcontractor.get(0));
			sp.setBusiness((String)subcontractor.get(1));
			sp.setContactName((String)subcontractor.get(2));
			if(subcontractor.get(3)!=null){
				sp.setReference((String)subcontractor.get(3));
			}
			else{
				sp.setReference(String.valueOf(sp.getSubContractorId()));
			}
			sp.setCompanyTele((String)subcontractor.get(4));
			sp.setMobile((String)subcontractor.get(5));
			sp.setCompanyEmail((String)subcontractor.get(6));
			sp.setStartDate((Date)subcontractor.get(7));
			sp.setUtr((String)subcontractor.get(8));
			sp.setPaymentTerm((String)subcontractor.get(9));
			String str[] = (String[])subcontractor.get(10);
			address.setAddressLine1(str[0]);
			address.setAddressLine2(str[1]);
			address.setTownCity(str[2]);
			address.setCountyState(str[3]);
			address.setPostCode(str[4]);
			address.setCountry(str[5]);
			sp.setAddresses(address);
			em.persist(sp);
		
		}
		
		//SEEN
		public void updateSubContractor(byte[] subContract, byte[] subContractId){
			ArrayList<Object> subcontractor = null;
			Integer subId = null;
			ByteArrayInputStream bais = new ByteArrayInputStream(subContract);
			ByteArrayInputStream baid = new ByteArrayInputStream(subContractId);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);
				ObjectInputStream oid = new ObjectInputStream(baid);

			try{
				subcontractor = (ArrayList<Object>)ois.readObject();
				subId = (Integer)oid.readObject();

			} finally {
				ois.close();
				}
			} catch (Exception e) {
			e.printStackTrace();
			}
		
			SubContractor sp = em.find(SubContractor.class, subId.intValue());
			Address address = new Address();
			sp.setCompanyName((String)subcontractor.get(0));
			sp.setBusiness((String)subcontractor.get(1));
			sp.setContactName((String)subcontractor.get(2));
			if(subcontractor.get(3)!=null){
				sp.setReference((String)subcontractor.get(3));
			}
			else{
				sp.setReference(String.valueOf(sp.getSubContractorId()));
			}
			sp.setCompanyTele((String)subcontractor.get(4));
			sp.setMobile((String)subcontractor.get(5));
			sp.setCompanyEmail((String)subcontractor.get(6));
			sp.setStartDate((Date)subcontractor.get(7));
			sp.setUtr((String)subcontractor.get(8));
			sp.setPaymentTerm((String)subcontractor.get(9));
			String str[] = (String[])subcontractor.get(10);
			address.setAddressLine1(str[0]);
			address.setAddressLine2(str[1]);
			address.setTownCity(str[2]);
			address.setCountyState(str[3]);
			address.setPostCode(str[4]);
			address.setCountry(str[5]);
			sp.setAddresses(address);
			em.persist(sp);
		
		}
		
		/*DONE
		**method addSubContractorInvoice(byte[]) add new invoice to of a perticular sub contractor in subcotractor table
		**return byte array of supplier invoice details
		**use by AddSubContractorInvoice GUI 
		*/
		public void addSubContractorInvoice(byte[] subContractorinvoice){
			ArrayList<Object> subContractorInv = null;
			ByteArrayInputStream bais = new ByteArrayInputStream(subContractorinvoice);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);

			try{
				subContractorInv = (ArrayList<Object>)ois.readObject();

			} finally {
				ois.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<Posting> postarray = new ArrayList<Posting>();
			Journal jr = new Journal();
	   
			//calculate vat
			Posting ps = null;
		
			BigDecimal amount = new BigDecimal((String)subContractorInv.get(5));
			BigDecimal vatAmount =  BigDecimal.ZERO;
			if(!subContractorInv.get(6).equals("none")){
				ps = new Posting();
				String vat[] = ((String)subContractorInv.get(6)).split("%");
				BigDecimal vatValue = new BigDecimal(vat[0]);
				if(vatValue.compareTo(new BigDecimal("0")) > 0){
					vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
					amount = amount.subtract(vatAmount);
				}
			}
		
			AccountCode codeDR = em.find(AccountCode.class, ((Integer)subContractorInv.get(2)).intValue()); 
			AccountCode codeCR =  em.find(AccountCode.class, 20010); 
			//jr.setcodeIdCR(codeCR.getGroup());
			//jr.setcodeIdDR(codeDR.getGroup());
	   
			Posting ps1 = new Posting();
			ps1.setAmount(amount);
			ps1.setCodeId(codeDR);
			postarray.add(ps1);
			ps1.setJournal(jr);
	   
			Posting ps2 = new Posting();
			ps2.setAmount(new BigDecimal((String)subContractorInv.get(5)).negate());
			ps2.setCodeId(codeCR);
			postarray.add(ps2);
			ps2.setJournal(jr);
	   
			if(ps != null){
				AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
				ps.setAmount(vatAmount);
				ps.setCodeId(vatcodeDR);
				postarray.add(ps);
				ps.setJournal(jr);
			}
	   
			jr.setPostingdrcr(postarray);
			em.persist(jr);
	   
			SubContractorInvoice invoice = new SubContractorInvoice();
			invoice.setProject(em.find(Project.class, ((Integer)subContractorInv.get(0)).intValue()));
			invoice.setSubcontractor(em.find(SubContractor.class, ((Integer)subContractorInv.get(1)).intValue()));
			invoice.setJournal(jr);
			invoice.setInvoiceDate((Date)subContractorInv.get(4));
			invoice.setInvoiceNumber((String)subContractorInv.get(3));
			em.persist(invoice);
		}
		
		/**DONE
		**method addSubContractorPayment(byte[] materialpayment) add payment to sub-contractor invoice
		**use SubcontractorPayment entity to add payment to database
		**use by subContractorPayment GUI 
		*/
		public void addSubContractorPayment(byte[] subContractorpayment){
			ArrayList<String> subPayment = new ArrayList<String>();
			ByteArrayInputStream bais = new ByteArrayInputStream(subContractorpayment);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);

			try{
				subPayment = (ArrayList<String>)ois.readObject();

			} finally {
				ois.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//payment made
			BigDecimal amount = new BigDecimal(subPayment.get(2));

				//initial initialisations
			
			ArrayList<SubContractorPayment> paymentList = new ArrayList<SubContractorPayment>();
			SubContractorPayment payment = new SubContractorPayment();
			
			//set relationship between sub contract invoice and sub contract payment
			SubContractorInvoice invoice = em.find(SubContractorInvoice.class, (Integer.valueOf(subPayment.get(0))).intValue());
			int paymentOutCode = (Integer.valueOf(subPayment.get(5))).intValue();
			Journal jr = subContractorJournal(subPayment.get(3), invoice, amount, paymentOutCode);
			ArrayList<SubContractorPayment> postedList = new ArrayList<SubContractorPayment>(invoice.getSubContractorPayment());
			AccountCode codeCR = em.find(AccountCode.class, paymentOutCode); 
			//update sub contractor invoice for the accounts payable
			/*Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", invoice.getJournal());
			Posting pslabour = null;
			try{
				pslabour = (Posting)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			pslabour.setAmount(pslabour.getAmount().add(amount));
			post.add(pslabour);
			pslabour.setJournal(invoice.getJournal());
			
			//post new jornal entrys for both bank/cash and cis
			AccountCode codeCis = em.find(AccountCode.class, 70010); 
			AccountCode codeCR = em.find(AccountCode.class, paymentOutCode); 
			//jr.setcodeIdCR(codeCis.getGroup());
			//jr.setcodeIdDR(codeCR.getGroup());
	   
			//find the parcentage for cis
			String perstr[] = ((String)subPayment.get(3)).split("%");
			BigDecimal perdouble = new BigDecimal(perstr[0]);
			BigDecimal cisamount = (perdouble.multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
			
			//post cis jornal
			Posting ps1 = new Posting();
			ps1.setAmount(cisamount.negate());
			ps1.setCodeId(codeCis);
			postarray.add(ps1);
			ps1.setJournal(jr);
			
			//post bank/cash jornal
			Posting ps2 = new Posting();
			ps2.setAmount((amount.subtract(cisamount)).negate());
			ps2.setCodeId(codeCR);
			postarray.add(ps2);
			ps2.setJournal(jr);
			
			jr.setPostingdrcr(postarray);
			em.persist(jr);*/
			if(!postedList.isEmpty()){
				for(SubContractorPayment subPay : postedList){
					paymentList.add(subPay);
				}
			}
			payment.setJournal(jr);
			payment.setSubContractorInvoice(invoice);
			payment.setNote(subPayment.get(4));
			payment.setType(codeCR.getCodeName());
			payment.setPaymentDate(java.sql.Date.valueOf(subPayment.get(1)));
			paymentList.add(payment);
			invoice.setSubContractorPayment(paymentList);
			em.persist(invoice);
		}
		
		/**
		**subContractorJournal(String cisStr, SubContractorInvoice invoice, BigDecimal amount, int paymentOutCode)
		**bean helper method to handle journal posting for sub contractor payment
		**use by addSubContractorPayment bean method
		*/
		private Journal subContractorJournal(String cisStr, SubContractorInvoice invoice, BigDecimal amount, int paymentOutCode){
			Journal jr = new Journal();
			ArrayList<Posting> postarray = new ArrayList<Posting>();
			
					//update sub contractor invoice for the accounts payable
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", invoice.getJournal());
			Posting pslabour = null;
			try{
				pslabour = (Posting)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			pslabour.setAmount(pslabour.getAmount().add(amount));
			//post.add(pslabour);
			//pslabour.setJournal(invoice.getJournal());
			
			//post new jornal entrys for both bank/cash and cis
			AccountCode codeCis = em.find(AccountCode.class, 70010); 
			AccountCode codeCR = em.find(AccountCode.class, paymentOutCode); 
			//jr.setcodeIdCR(codeCis.getGroup());
			//jr.setcodeIdDR(codeCR.getGroup());
	   
			//find the parcentage for cis
			String perstr[] = (cisStr).split("%");
			BigDecimal perdouble = new BigDecimal(perstr[0]);
			BigDecimal cisamount = (perdouble.multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
			
			//post cis jornal
			if( perdouble.signum() == 1 ){
				Posting ps1 = new Posting();
				ps1.setAmount(cisamount.negate());
				ps1.setCodeId(codeCis);
				postarray.add(ps1);
				ps1.setJournal(jr);
				jr.setCodeCR(String.valueOf(codeCR.getGroup().getGroupId()) + "."+
							String.valueOf(codeCis.getGroup().getGroupId()) );
			}
			else{
				jr.setCodeCR(String.valueOf(codeCR.getGroup().getGroupId()));
			}
			//post bank/cash jornal
			Posting ps2 = new Posting();
			ps2.setAmount((amount.subtract(cisamount)).negate());
			ps2.setCodeId(codeCR);
			postarray.add(ps2);
			ps2.setJournal(jr);
			
			jr.setPostingdrcr(postarray);
			em.persist(jr);
			return jr;
		}
		
		
		/*SEEN
		**method getSubContractorData() get all sub contractor details from database ( might not go live use for debuging only)
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
		*/
		public byte[] getSubContractorData(){
			java.util.List<SubContractor> ls = (java.util.List<SubContractor>)em.createQuery("SELECT s FROM SubContractor s").getResultList();	
			return objectWriter(subContrctorData(ls));
		}
	
	/*DONE
		**method subContrctorData(java.util.List<SubContractor> ls) helper method for all searches for sub contractor details
		**return arralist of arralist of object
		**use by supplier view GUI
	*/
	public ArrayList<ArrayList<Object>> subContrctorData(java.util.List<SubContractor> ls){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SubContractor pr : ls){
			BigDecimal total =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pr.getCompanyName());
			temp.add(pr.getContactName());
			temp.add(pr.getReference());
			Query query = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.subcontractor=:subcontractorbal");
			query.setParameter("subcontractorbal", pr);
			java.util.List<SubContractorInvoice> l = (java.util.List<SubContractorInvoice>)query.getResultList();
				if(l.isEmpty()){
					total =  new BigDecimal ("0");
					temp.add(total);
				}
	
				else{
					for(SubContractorInvoice si : l){
						Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
						balquery.setParameter(1, si.getJournal().getJournalId());
						Posting ps = (Posting)balquery.getSingleResult();
						total = total.add(ps.getAmount().abs());
					}
					temp.add(total);
				}				
			temp.add(pr.getPaymentTerm());
			temp.add(pr.getStartDate());
			temp.add(Integer.valueOf(pr.getSubContractorId()));
			datas.add(temp);
		}
		return datas;
	}
		
	/*SEEN
		**method getSupplierSingleDetails(int) get single supplier details from database for editing
		**return byte array of supplier infomation
		**use by EditSupplier GUI 
	*/
	public byte[] getSubContractorSingleDetails(int subcontractorId){
		ArrayList<Object> temp = new ArrayList<Object>();
		ArrayList<String> addtemp = new ArrayList<String>();
		try{
			Query squery = em.createQuery("SELECT s FROM SubContractor s WHERE s.subContractorId=?1");
			squery.setParameter(1, subcontractorId);
			SubContractor subcontract = (SubContractor)squery.getSingleResult();
			temp.add(Integer.valueOf(subcontract.getSubContractorId()));
			temp.add(subcontract.getContactName());
			temp.add(subcontract.getUtr());
			temp.add(subcontract.getCompanyName());
			temp.add(subcontract.getBusiness());
			temp.add(subcontract.getReference());
			Address address = subcontract.getAddresses();
			addtemp.add(address.getAddressLine1());	
			addtemp.add(address.getAddressLine2());	
			addtemp.add(address.getTownCity());	
			addtemp.add(address.getCountyState());	
			addtemp.add(address.getPostCode());	
			addtemp.add(address.getCountry());
			temp.add(addtemp);
			temp.add(subcontract.getCompanyTele());	
			temp.add(subcontract.getMobile());	
			temp.add(subcontract.getCompanyEmail());
			temp.add(subcontract.getStartDate());		
			temp.add(subcontract.getPaymentTerm());				
		}
			catch(NoResultException ex){}	
		
		return objectWriter(temp);
	
	}
	
	/*DONE
		**method subContractInvoiceSearch(java.util.List<SupplierInvoice> ls) helper method for sub contract invoice search
		**return byte array of arraylist object
		**use by any method need to search for sub contract invoice
	*/
	public ArrayList<ArrayList<Object>> subContractInvoiceSearch(java.util.List<SubContractorInvoice> ls){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SubContractorInvoice sub : ls){
			ArrayList<Object> temp = new ArrayList<Object>();		
			temp.add(sub.getSubcontractor().getContactName());
			temp.add(sub.getProject().getProjectName());
			temp.add(sub.getInvoiceNumber());
			temp.add(sub.getInvoiceDate());
						
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=50020");
			payablequery.setParameter("jornal", sub.getJournal());
			Posting pslabour = null;
			try{
				pslabour = (Posting)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
			vatquery.setParameter("jornal", sub.getJournal());
			Posting psvat = null;
			try{
				psvat = (Posting)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			BigDecimal vat =  BigDecimal.ZERO;
			if(psvat != null){
				vat = psvat.getAmount();
			}
			
			temp.add(pslabour.getAmount().add(vat));
			
			Query paymentquery = em.createQuery("SELECT s FROM SubContractorPayment s WHERE s.subContractorInvoice=:subcontractorInv");
			paymentquery.setParameter("subcontractorInv", sub);
			java.util.List<SubContractorPayment> sp = (java.util.List<SubContractorPayment>)paymentquery.getResultList();
			BigDecimal total =  BigDecimal.ZERO;
			if(sp.isEmpty()){
				total =  new BigDecimal ("0");
				temp.add(total);
			}
			else{
				for(SubContractorPayment pay : sp){
	
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010 OR p.codeId.codeId=10040");				
					cashquery.setParameter("jornal", pay.getJournal());
					
					Query cisquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70010");
					cisquery.setParameter("jornal", pay.getJournal());
					
					try{
						Posting ccps = (Posting)cisquery.getSingleResult();
						total = total.add(ccps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
					try{
						Posting cashps = (Posting)cashquery.getSingleResult();

						total = total.add(cashps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
				}
				temp.add(total);
			}
			int daydiff = days(sub.getInvoiceDate());
			String [] strterm = sub.getSubcontractor().getPaymentTerm().split("\\s+");
			int term = (Integer.valueOf(strterm[0])).intValue();
			if(daydiff < term){
				temp.add(new String("0 days"));
			}
			else{
				int diff = daydiff - term;
				String days = null;
					if(diff == 1)
						days = "day";
					else{
						days = "days";
					}
				temp.add(String.format("%d %s", diff, days));
			}
			temp.add(Integer.valueOf(sub.getSubContractorInvoiceId()));
			datas.add(temp);
		}
		return datas;
	}
	
	/*SEEN
		**method getLabourOutgoingData() get all sub contrcator invoices for view
		**return byte array of sub contractor invoice
		**use by Outgoingview GUI 
	*/
	public byte[] getLabourOutgoingData(){
		java.util.List<SubContractorInvoice> ls = (java.util.List<SubContractorInvoice>)em.createQuery("SELECT s FROM SubContractorInvoice s").getResultList();	
		return objectWriter(subContractInvoiceSearch(ls));
	}
	
		/*SEEN
		**method getLabourOutgoingData(Date from, Date to) get all sub contrcator invoices for view with date range
		**return byte array of sub contractor invoice
		**use by Outgoingview GUI 
	*/
	public byte[] getLabourOutgoingData(Date from, Date to){
		Query datequery = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.invoiceDate BETWEEN ?1 AND ?2");
		datequery.setParameter(1, from);
		datequery.setParameter(2, to);
		java.util.List<SubContractorInvoice> ls = (java.util.List<SubContractorInvoice>)datequery.getResultList();	
		return objectWriter(subContractInvoiceSearch(ls));
	}
	
	/*DONE
		**method getLabourOutgoingData(double from, double to) get all sub contrcator invoices for view between amount from an to
		**return byte array of sub contractor invoice
		**use by Outgoingview GUI 
	*/
	public byte[] getLabourOutgoingData(BigDecimal from, BigDecimal to){
		//java.util.List<SupplierInvoice> ls = em.createQuery("SELECT s FROM SupplierInvoice s").getResultList();	
		java.util.List<SubContractorInvoice> ls = (java.util.List<SubContractorInvoice>)em.createQuery("SELECT s FROM SubContractorInvoice s").getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SubContractorInvoice si : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20010");
			payablequery.setParameter("jornal", si.getJournal());
			Posting ps = (Posting)payablequery.getSingleResult();
		
			Query paymentquery = em.createQuery("SELECT s FROM SubContractorPayment s WHERE s.subContractorInvoice=:subcontractorInv");
			paymentquery.setParameter("subcontractorInv", si);
			java.util.List<SubContractorPayment> sp =  (java.util.List<SubContractorPayment>)paymentquery.getResultList();
			BigDecimal total =  BigDecimal.ZERO;
			if(sp.isEmpty()){
				total =  new BigDecimal ("0");
			}
			else{
				for(SubContractorPayment pay : sp){
	
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010");				
					cashquery.setParameter("jornal", pay.getJournal());
					
					Query cisquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70010");
					cisquery.setParameter("jornal", pay.getJournal());
					
					try{
						Posting cisps = (Posting)cisquery.getSingleResult();
						total = total.add(cisps.getAmount().abs());
						
				}
					catch(NoResultException ex){}
					try{
						Posting cashps = (Posting)cashquery.getSingleResult();
						total = total.add(cashps.getAmount().abs());
						
					}
					catch(NoResultException ex){}
				}
				//temp.add(new Double(total));//watch out for this bit, looks like it coresponde to see below
			}
			
			if(total.compareTo(from) >= 0 && total.compareTo(to) <= 0){
				temp.add(si.getSubcontractor().getContactName());
				temp.add(si.getProject().getProjectName());
				temp.add(si.getInvoiceNumber());
				temp.add(si.getInvoiceDate());
				temp.add(ps.getAmount().abs());
				temp.add(total);//watch out
				int daydiff = days(si.getInvoiceDate());
				String [] strterm = si.getSubcontractor().getPaymentTerm().split("\\s+");
				int term = (new Integer(strterm[0])).intValue();
				if(daydiff < term){
					temp.add(new String("0 days"));
				}
				else{
					int diff = daydiff - term;
					String days = null;
						if(diff == 1)
							days = "day";
						else{
							days = "days";
						}
					temp.add(String.format("%d %s", diff, days));
				}
				temp.add(new Integer(si.getSubContractorInvoiceId()));
				datas.add(temp);
			}

		}
		return objectWriter(datas);
	}
	
	/*SEEN
		**method getLabourOutgoingData(int subcontrctorId) get single sub contrcator invoices for view
		**return byte array of sub contractor invoice
		**use by Outgoingview GUI 
	*/
	public byte[] getLabourOutgoingData(int subcontrctorId){
		Query datequery = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.subcontractor=:subcontractor");
		datequery.setParameter("subcontractor", em.find(SubContractor.class, subcontrctorId));
		java.util.List<SubContractorInvoice> ls = (java.util.List<SubContractorInvoice>)datequery.getResultList();	
		return objectWriter(subContractInvoiceSearch(ls));
	}
	
	/*SEEN
		**method getLabourOutgoingData(String invoice) get single sub contrcator invoices for view
		**return byte array of sub contractor invoice
		**use by Outgoingview GUI invoiceNumber
	*/
	public byte[] getLabourOutgoingData(String invoice){
		Query datequery = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.invoiceNumber=?1");
		datequery.setParameter(1, invoice);
		java.util.List<SubContractorInvoice> ls = (java.util.List<SubContractorInvoice>)datequery.getResultList();	
		return objectWriter(subContractInvoiceSearch(ls));
	}
	
	/*DONE
		**method getSubContractorPaymentDetails(int supplierInvoiceId) return detail information for single sub cotractor invoice payment
		**return byte array of sub contractor payment info
		**use by SubPaymentDetails GUI invoiceNumber
	*/
	public byte[] getSubContractorPaymentDetails(int invoiceId){
		Query subinvoice = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.subContractorInvoiceId=?1");
		subinvoice.setParameter(1, invoiceId);
		SubContractorInvoice subcontractorInv = null;
		ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		try{
			subcontractorInv = (SubContractorInvoice)subinvoice.getSingleResult();
		
		}
		catch(NoResultException ex){}
		Query subpayment = em.createQuery("SELECT s FROM SubContractorPayment s WHERE s.subContractorInvoice=:subconinvoice");
		subpayment.setParameter("subconinvoice", subcontractorInv);
		
		java.util.List<SubContractorPayment> ls = (java.util.List<SubContractorPayment>)subpayment.getResultList();
		for(SubContractorPayment pay : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pay.getPaymentDate());
			Posting cash = null;
			Posting cis = null;
			BigDecimal amount =  BigDecimal.ZERO;
			BigDecimal vat =  BigDecimal.ZERO;
			Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10010");				
			cashquery.setParameter("jornal", pay.getJournal());
			try{
				cash = (Posting)cashquery.getSingleResult();
				amount = (cash.getAmount().abs());
				vat = (getVATExclusive(subcontractorInv.getJournal(), 50020, 70020).multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
				temp.add(amount.subtract(vat));

			}
			catch(NoResultException ex){}
			temp.add(vat);
			Query cisquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70010");
			cisquery.setParameter("jornal", pay.getJournal());
			try{
				cis = (Posting)cisquery.getSingleResult();
				temp.add(cis.getAmount().abs());
			}
			catch(NoResultException ex){}
			temp.add(pay. getType());
			temp.add(pay.getNote());
			temp.add(Integer.valueOf(pay.getSubContractorPaymentId()));
			data.add(temp);
		}

		return objectWriter(data);
	
	}
	
	/*SEEN
		**method getSubContractorData(int ) get a sub contractor details from database with specific id
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	public byte[] getSubContractorRef(String ref){
		Query query = em.createQuery("SELECT s FROM SubContractor s WHERE s.reference=?1");
		query.setParameter(1, ref);
		java.util.List<SubContractor> ls = (java.util.List<SubContractor>)query.getResultList();	
		return objectWriter(subContrctorData(ls));
	}
	
	/*SEEN
		**method getSubContractorData() get a sub contractor details from database using the company name or the sub contractor name
		**return byte array of supplier infomation 
		**use by ViewSupplier GUI 
	*/
	public byte[] getSubContractorData(String subcontractorName){		
		Query query = em.createQuery("SELECT s FROM SubContractor s WHERE s.contactName=?1");
		query.setParameter(1, subcontractorName);
		java.util.List<SubContractor> ls = (java.util.List<SubContractor>)query.getResultList();

		return objectWriter(subContrctorData(ls));
	}
	
	/*DONE
		**method getSubContractorData() get all sub contractor details from database with outstanding balance from and to
		**return byte array of supplier infomation
		**use by ViewSupplier GUI 
	*/
	public byte[] getSubContractorData(BigDecimal from, BigDecimal to){
		java.util.List<SubContractor> ls = (java.util.List<SubContractor>)em.createQuery("SELECT s FROM SubContractor s").getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(SubContractor pr : ls){
			BigDecimal total =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();

			Query query = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.subcontractor=:subcontractorbal");
			query.setParameter("subcontractorbal", pr);
			java.util.List<SubContractorInvoice> l =  (java.util.List<SubContractorInvoice>)query.getResultList();
			if(l.isEmpty()){
				total =  new BigDecimal ("0");
			}
	
			else{
				for(SubContractorInvoice si : l){
					Query balquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal.journalId=?1 AND p.codeId.codeId=20010");
					balquery.setParameter(1, si.getJournal().getJournalId());
					Posting ps = (Posting)balquery.getSingleResult();
					total = total.add(ps.getAmount().abs());
				}
			}
			if(total.compareTo(from) >= 0 && total.compareTo(to) <= 0){
				temp.add(pr.getCompanyName());
				temp.add(pr.getContactName());
				temp.add(pr.getReference());
				temp.add(total);
				temp.add(pr.getPaymentTerm());
				temp.add(pr.getStartDate());
				temp.add(Integer.valueOf(pr.getSubContractorId()));
				datas.add(temp);
			}

		}
		return objectWriter(datas);
	}

	
	/*SEEN
		**method supplierInfos get ALL supplier details from database
		**return byte array of supplier infomation
		**use by AddExpensesxxxx GUIS 
	*/
	public byte[] supplierInfos(){
		java.util.List<Supplier> ls = (java.util.List<Supplier>)em.createQuery("SELECT s FROM Supplier s").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Supplier supplier : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			ArrayList<String> addtemp = new ArrayList<String>();
			temp.add(new Integer(supplier.getSupplierId()));
			temp.add(supplier.getCompanyName());
			temp.add(supplier.getContactName());
			temp.add(supplier.getAccount());
			Address address = supplier.getAddresses();
			addtemp.add(address.getAddressLine1());	
			addtemp.add(address.getAddressLine2());	
			addtemp.add(address.getTownCity());	
			addtemp.add(address.getCountyState());	
			addtemp.add(address.getPostCode());	
			addtemp.add(address.getCountry());
			temp.add(addtemp);
			temp.add(supplier.getCompanyTele());	
			temp.add(supplier.getFax());	
			temp.add(supplier.getCompanyEmail());
			temp.add(supplier.getStartDate());	
			temp.add(supplier.getCredit());	
			temp.add(supplier.getPaymentTerm());
			datas.add(temp);
		}
		return objectWriter(datas);
	}
	
	public byte[] supplierInfoListing(){
		java.util.List<Supplier> ls = (java.util.List<Supplier>)em.createQuery("SELECT s FROM Supplier s").getResultList();	
		ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		for(Supplier supplier : ls){
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(supplier.getCompanyName());
			int id = supplier.getSupplierId();
			temp.add(String.valueOf(id));
			if(id != 29999){
				datas.add(temp);
			}
		}
		return objectWriter(datas);
	}
	
	public byte[] defaultSupplier(){
		Query query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierId = 29999");
		Supplier supplier = null;
		ArrayList<String> temp = new ArrayList<String>();
		try{
			supplier = (Supplier) query.getSingleResult();
		}
		catch(NoResultException ex){}
		temp.add(supplier.getCompanyName());
		int id = supplier.getSupplierId();
		temp.add(String.valueOf(id));
		return objectWriter(temp);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////SUPPLIERS END
	
	/////////////////////////////////////////////////////////////////////////////////////////////////Beans helper mathods
	    
	/*SEEN
		**beans helper method day(Date)
		**use by beans method for day difference calculation 
		**returns int value
	*/
	public int days(java.sql.Date date){
	
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date today = java.sql.Date.valueOf(dateFormat.format(calendar.getTime()));			 

		return (int)((today.getTime() - date.getTime())/(1000*60*60*24));
		
	}
	
	//SEEN
	public ArrayList<java.sql.Date> dataRange(java.sql.Date start, int period){
		ArrayList<java.sql.Date> temp = new ArrayList<java.sql.Date>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar leapyr = new GregorianCalendar();
		Calendar calendar = Calendar.getInstance();	
		calendar.setTime(start);
		int startDay = calendar.get(Calendar.DAY_OF_MONTH);
		int counter = 0;
		int diff = days(start);
		
		int reminder = diff % 30;
		diff = (diff - reminder)/30;

		if(diff > period){
			counter = period;
		}
		
		else{
			counter = diff;
		}
		
		for(int i = 0; i < counter; i++){
			calendar.add(Calendar.MONTH, 1);
			if(startDay <= calendar.get(Calendar.DAY_OF_MONTH)){

				temp.add(java.sql.Date.valueOf(dateFormat.format(calendar.getTime())));
			}
			//correction for march when date span from february
			else if(startDay > calendar.get(Calendar.DAY_OF_MONTH) &&  calendar.get(Calendar.MONTH) == Calendar.MARCH){
				calendar.set(Calendar.DAY_OF_MONTH, startDay);

				temp.add(java.sql.Date.valueOf(dateFormat.format(calendar.getTime())));
			}
			else{

				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				temp.add(java.sql.Date.valueOf(dateFormat.format(calendar.getTime())));

			}
		}
		return temp;
	}
	
	/*DONE
		**method double getVATdouble bean helper get double use to calculate vat (need to be repair, wrong calculation for VAT)
		**return double figure
		**use by bean  method
	*/
	BigDecimal getVATInclusive(Journal obj, int code1, int code2){
		Query  query = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
		Posting postInclusive = null;
		Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?2");
		Posting postVAT = null;
		BigDecimal vatparcentage =  BigDecimal.ZERO;

		try{
			 query.setParameter("jornal", obj);
			 query.setParameter(1, code1);
			  postInclusive = (Posting) query.getSingleResult();
		}
		catch(NoResultException ex){}
		
		try{
			vatquery.setParameter("jornal", obj);
			vatquery.setParameter(2, code2);
			postVAT = (Posting)vatquery.getSingleResult();
			//vatparcentage = updatevat.getAmount()*100/(updatematerials.getAmount() + updatevat.getAmount());
		}
		catch(NoResultException ex){}
		if(postVAT != null){
			vatparcentage = ((postVAT.getAmount().multiply(new BigDecimal("100"))).divide((postInclusive.getAmount().abs()).subtract(postVAT.getAmount()), DECIMALS, ROUNDING_MODE));
		}
			
		return vatparcentage;
			
	}
	
	/*DONE
		**method double getVATdouble bean helper get double use to calculate vat (need to be repair, wrong calculation for VAT)
		**return double figure
		**use by bean  method workplace
	*/
	BigDecimal getVATExclusive(Journal obj, int code1, int code2){
		Query  query = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
		Posting postInclusive = null;
		Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?2");
		Posting postVAT = null;
		BigDecimal vatparcentage =  BigDecimal.ZERO;
		try{
			 query.setParameter("jornal", obj);
			 query.setParameter(1, code1);
			  postInclusive = (Posting) query.getSingleResult();
		}
		catch(NoResultException ex){}
		
		try{
			vatquery.setParameter("jornal", obj);
			vatquery.setParameter(2, code2);
			postVAT = (Posting)vatquery.getSingleResult();
			//vatparcentage = updatevat.getAmount()*100/(updatematerials.getAmount() + updatevat.getAmount());
		}
		catch(NoResultException ex){}
		if(postVAT != null){
			vatparcentage = ((postVAT.getAmount().abs()).multiply(new BigDecimal("100")).divide(postInclusive.getAmount().abs(), DECIMALS, ROUNDING_MODE));
		}
			
		return vatparcentage;
			
	}
	
	private BigDecimal getVATValue(Journal jr, ArrayList<Posting> postarray, String vatstr, BigDecimal amount){
		//find the vat
		Posting vat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
		//post vat jornal
		if(vat != null){
			AccountCode codeVAT = em.find(AccountCode.class, 70020); 
			vat.setAmount(vatAmount);
			vat.setCodeId(codeVAT);
			//vat.setPostManual(false);
			postarray.add(vat);
			vat.setJournal(jr);
		}
		
		return vatAmount;
	}
	
	/***DONE
		**VATPoster(Jornal jr, String strvat ArrayList<Posting> postarray, BigDecimal amount, boolean sign) bean method for posting vat 
		**@parameter Jornal jr, String strvat, ArrayList<Posting> postarray and BigDecimal amount
		**return vat amount for any jornal posting
	*/
	private BigDecimal VATPoster(Journal jr, String strvat, ArrayList<Posting> postarray, BigDecimal amount, boolean sign){
	
	 Posting post = null;
	 BigDecimal vatAmount = BigDecimal.ZERO;
	 AccountCode codeVAT = em.find(AccountCode.class, 70020);
	 
	   if(!strvat.equals("None")){
			post = new Posting();
			String vat[] = strvat.split("%");
			BigDecimal vatValue = new BigDecimal(vat[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				if(sign){
					
					vatAmount = (amount.multiply(vatValue)).divide((new BigDecimal("100").add(vatValue)), DECIMALS, ROUNDING_MODE);
				}
				else{
					vatAmount = (amount.multiply(vatValue)).divide((new BigDecimal("100")), DECIMALS, ROUNDING_MODE);
				}
			}
		}
		
		
	  if(post != null){
		 if(sign){//vat on purchases
			post.setAmount(vatAmount);
		 }
		 else{//vat on account receivable
			post.setAmount(vatAmount.negate());
		 }
		 post.setCodeId(codeVAT);
		 postarray.add(post);
		 post.setJournal(jr);
	   }
	   
	   return vatAmount;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////End Beans helper mathods
	
	/*DONE
		**method to update quote and at the same time update invoice when it already generated
		**it takes paymentIn details plus change in quote if any
		**create new paymentIn plus merge quote if any
	*/
	public void updateQuote(byte[] newquoteline, byte[] projectId){
		Vector<Vector<String>>  quotedetails = null;
		Integer projectid = null;
		ByteArrayInputStream bais_quote = new ByteArrayInputStream(newquoteline);
		ByteArrayInputStream bais_proid = new ByteArrayInputStream(projectId);
		try {
			ObjectInputStream ois_quote = new ObjectInputStream(bais_quote);
			ObjectInputStream ois_proid = new ObjectInputStream(bais_proid);
		try{
			quotedetails = (Vector<Vector<String>>)ois_quote.readObject();
			projectid = (Integer)ois_proid.readObject();
		} finally {
			ois_quote.close();
			ois_proid.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createQuery("SELECT q FROM Quote q WHERE q.project.projectId=?1");
		query.setParameter(1, projectid.intValue()); 
		Quote quotes = (Quote)query.getSingleResult();
		ArrayList<Quoteline> quoteline = new ArrayList<Quoteline>(quotes.getQuotelines());
		//updateQuoteLine(quotes, quoteline);
		int quotecounter = 0;
		ArrayList<Quoteline> quotearray = new ArrayList<Quoteline>();
		Iterator<Vector<String>> it = quotedetails.iterator();

		if(quotedetails.size() > quoteline.size()){
			while(it.hasNext()){
				Vector<String> singleline = it.next();
				if(quotecounter <= quoteline.size()-1){
					Quoteline current = quoteline.get(quotecounter++);
					current.setDescription(singleline.get(0));
					current.setUnitPrice(new BigDecimal(singleline.get(1)));
					current.setQty(new BigDecimal(singleline.get(2)));
					quotearray.add(current);
					current.setQuotes(quotes);
					}
				else{
					Quoteline addnew = new Quoteline();
					addnew.setDescription(singleline.get(0));
					addnew.setUnitPrice(new BigDecimal(singleline.get(1) ));
					addnew.setQty(new BigDecimal(singleline.get(2) ));
					quotearray.add(addnew);
					addnew.setQuotes(quotes);
				}
				quotes.setQuotelines(quotearray);
				em.merge(quotes);
			}

		}
		
		else if(quotedetails.size() < quoteline.size()){
			while(it.hasNext()){
				Quoteline current = quoteline.get(quotecounter);
				if(quotecounter <= quotedetails.size()-1){
					Vector<String> singleline = it.next();
					current.setDescription(singleline.get(0));
					current.setUnitPrice(new BigDecimal(singleline.get(1) ));
					current.setQty(new BigDecimal(singleline.get(2) ));
					quotearray.add(current);
					current.setQuotes(quotes);
					quotecounter++;
					}

				quotes.setQuotelines(quotearray);
				em.merge(quotes);
			}
			for(int i = quotedetails.size(); i < quoteline.size(); i++){
				Quoteline current = quoteline.get(i);
				em.remove(current);
			}
		}
		else{
			while(it.hasNext()){
				Vector<String> singleline = it.next();
				Quoteline current = quoteline.get(quotecounter++);
				current.setDescription(singleline.get(0));
				current.setUnitPrice(new BigDecimal(singleline.get(1) ));
				current.setQty(new BigDecimal(singleline.get(2) ));
				em.merge(current);
			}
		}
		
	}
	
	/**
		**bean helper method updateQuoteLine(Quote, ArrayList<Quoteline>) it takes Quote object and ArrayList of QuoteLine
		** use by any bean method needing to update quote and quoteline
		**return null
	*/
	public void updateQuoteLine(Quote quotes, ArrayList<ArrayList<String>> quotedetails, ArrayList<Quoteline> quoteline){
	
		int quotecounter = 0;
		ArrayList<Quoteline> quotearray = new ArrayList<Quoteline>();
		Iterator<ArrayList<String>> it = quotedetails.iterator();

		if(quotedetails.size() > quoteline.size()){
			while(it.hasNext()){
				ArrayList<String> singleline = it.next();
				if(quotecounter <= quoteline.size()-1){
					Quoteline current = quoteline.get(quotecounter++);
					current.setDescription(singleline.get(0));
					current.setUnitPrice(new BigDecimal(singleline.get(1)));
					current.setQty(new BigDecimal(singleline.get(2)));
					quotearray.add(current);
					current.setQuotes(quotes);
					}
				else{
					Quoteline addnew = new Quoteline();
					addnew.setDescription(singleline.get(0));
					addnew.setUnitPrice(new BigDecimal(singleline.get(1) ));
					addnew.setQty(new BigDecimal(singleline.get(2) ));
					quotearray.add(addnew);
					addnew.setQuotes(quotes);
				}
				quotes.setQuotelines(quotearray);
				em.merge(quotes);
			}

		}
		
		else if(quotedetails.size() < quoteline.size()){
			while(it.hasNext()){
				Quoteline current = quoteline.get(quotecounter);
				if(quotecounter <= quotedetails.size()-1){
					ArrayList<String> singleline = it.next();
					current.setDescription(singleline.get(0));
					current.setUnitPrice(new BigDecimal(singleline.get(1) ));
					current.setQty(new BigDecimal(singleline.get(2) ));
					quotearray.add(current);
					current.setQuotes(quotes);
					quotecounter++;
					}

				quotes.setQuotelines(quotearray);
				em.merge(quotes);
			}
			for(int i = quotedetails.size(); i < quoteline.size(); i++){
				Quoteline current = quoteline.get(i);
				em.remove(current);
			}
		}
		else{
			while(it.hasNext()){
				ArrayList<String> singleline = it.next();
				Quoteline current = quoteline.get(quotecounter++);
				current.setDescription(singleline.get(0));
				current.setUnitPrice(new BigDecimal(singleline.get(1) ));
				current.setQty(new BigDecimal(singleline.get(2) ));
				em.merge(current);
			}
		}
	
	}
	
	/*DONE
		**bean helper method returns total quote for project view
		**it takes project id as argument
		**return total qoute as a double projectDetails
	*/
	public BigDecimal getQuoteTotal(int projectId){
		BigDecimal total= BigDecimal.ZERO;
		Query query = em.createQuery("SELECT q FROM Quote q WHERE q.project.projectId=?1");
		query.setParameter(1, projectId); 
		Quote quote = (Quote)query.getSingleResult();			
		ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
		for(Quoteline qline : quoteline){
			total = total.add(qline.getUnitPrice().multiply(qline.getQty()));
		}
		return total;
	}
	
	/*DONE
		**bean helper method returns total incoming for incoming view
		**@parameter paymentId it takes payment id as argument
		**return total incoming as a double
	*/
	public BigDecimal getIncomingTotal(int paymentId){
		BigDecimal total= BigDecimal.ZERO;
		Query query = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 10010  AND p.journal.journalId IN" +
										"(SELECT i.journal.journalId FROM Incoming i WHERE i.payment.paymentInId=?1)");
		query.setParameter(1, paymentId); 
		java.util.List<Posting> ls = (java.util.List<Posting>)query.getResultList();			
		for(Posting post : ls){
			total = total.add(post.getAmount());
		}
		return total;
	}
	
	/*DONE
		**bean helper method returns total cis paid for incoming view
		**@parameter paymentId it takes payment id as argument
		**return total incoming as a double
	*/
	public BigDecimal getIncomingTAX(int paymentId){
		BigDecimal total= BigDecimal.ZERO;
		Query query = em.createQuery("SELECT i FROM Incoming i WHERE i.payment.paymentInId=?1");
		query.setParameter(1, paymentId); 
		java.util.List<Incoming> ls = (java.util.List<Incoming>)query.getResultList();
		for(Incoming income : ls){
			try{
				Query querycis = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 70010 AND p.journal.journalId = ?1 ");
				querycis.setParameter(1, income.getJournal().getJournalId());
				Posting postcis = (Posting)querycis.getSingleResult();
				total = total.add(postcis.getAmount());
			  }
			  catch(NoResultException ex){}
		}
		return total;
	}
	
	
	/*DONE
		**Method getGenericIncoming calculate any accounting code total that is relate to incoming
		**@parameter paymentId, @parameter codeId it takes payment id and code id as argument
		**return byte array
	*/
	public byte[] getGenericIncoming(int paymentId, int codeId){
		BigDecimal total= BigDecimal.ZERO;
		Query query = em.createQuery("SELECT i FROM Incoming i WHERE i.payment.paymentInId=?1");
		query.setParameter(1, paymentId); 
		java.util.List<Incoming> ls = (java.util.List<Incoming>)query.getResultList();
		for(Incoming income : ls){
			try{
				Query querycis = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = ?2 AND p.journal.journalId = ?1 ");
				querycis.setParameter(1, income.getJournal().getJournalId());
				querycis.setParameter(2, codeId);
				Posting postcis = (Posting)querycis.getSingleResult();
				total = total.add(postcis.getAmount());
			  }
			  catch(NoResultException ex){}
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(total);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	
	/*DONE
		**Method getIncomingExclude calculate any accounting code total that is relate to incoming
		**@parameter paymentId, @parameter codeId and @parameter incomingId - it takes payment id and code id as argument and incoming id
		**return byte array
	*/
	public byte[] getIncomingExclude(int paymentId, int codeId, int incomingId){
		BigDecimal total= BigDecimal.ZERO;
		Query query = em.createQuery("SELECT i FROM Incoming i WHERE i.payment.paymentInId=?1");
		query.setParameter(1, paymentId); 
		java.util.List<Incoming> ls = (java.util.List<Incoming>)query.getResultList();
		for(Incoming income : ls){
			if(income.getIncomingId() != incomingId){
				try{
					Query querycis = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = ?2 AND p.journal.journalId = ?1 ");
					querycis.setParameter(1, income.getJournal().getJournalId());
					querycis.setParameter(2, codeId);
					Posting postcis = (Posting)querycis.getSingleResult();
					total = total.add(postcis.getAmount());
				}
				catch(NoResultException ex){}
			}
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(total);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/**DONE
		**paymentBalance bean method for payment in details view
		**@parameter paymentId it takes payment id as argument
		**return byte array of double
	*/
    public byte[] getPaymentBalance(int paymentId){
	 BigDecimal balance =  BigDecimal.ZERO;
	 PaymentIn pay = em.find(PaymentIn.class, paymentId);
	 Query query = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal and p.codeId.codeId=10020");
	 query.setParameter("jornal", pay.getJournal());
		
	 try{
		 balance = (BigDecimal)query.getSingleResult();
	 }
	 catch(NoResultException ex){}
	 return objectWriter(balance);
	}
	
	
	/**DONE
		**paymentInDetails bean method for payment in details view
		**@parameter paymentId it takes payment id as argument
		**return arraylist of arraylist of paymentIn details workplace
	*/
    public byte[] getPaymentInDetails(int paymentId){
		ArrayList<ArrayList<Object>> detailsObject = new ArrayList<ArrayList<Object>>();
		
		PaymentIn invoice = em.find(PaymentIn.class, paymentId);
		Posting vat = null;
		BigDecimal vat_pacentage = BigDecimal.ZERO;
		Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 70020 AND p.journal = :jornal");
		vatquery.setParameter("jornal", invoice.getJournal());
		try{
			vat = (Posting)vatquery.getSingleResult();
			vat_pacentage = getVATExclusive(invoice.getJournal(), 10020, 70020);
		}
		catch(NoResultException e){}
		

		ArrayList<Incoming> ls = new ArrayList<Incoming>(invoice.getIncoming());
		for(Incoming income : ls){
			ArrayList<Object>  details = new ArrayList<Object>();
			
			//check for bad debt
			Posting baddebt = null;
			Query query_customerdebt = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 60110 AND p.journal = :jornal");
			query_customerdebt.setParameter("jornal", income.getJournal());
			try{
				baddebt = (Posting)query_customerdebt.getSingleResult();
			}
			catch(NoResultException e){}
			
			if(baddebt != null ){
				details.add(baddebt.getAmount());
				details.add(baddebt.getCodeId().getCodeName());
				if(vat != null){
				    Query query_vatAmount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.codeId.codeId = 70020 AND p.journal = :jornal");
					query_vatAmount.setParameter("jornal", income.getJournal());
					details.add((BigDecimal)query_vatAmount.getSingleResult());
					
					//cis not applicable to bad debt
					details.add(new BigDecimal("0"));
				}
				
				else{
					//none vat registered client
					details.add(new BigDecimal("0"));
					
					//cis not applicable to bad debt
					details.add(new BigDecimal("0"));
				}
			
			}
			else{
				Query query_incoming = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId != 70010 AND p.journal = :jornal ");
				query_incoming.setParameter("jornal", income.getJournal());
				Posting post_incoming = (Posting)query_incoming.getSingleResult();
				details.add(post_incoming.getAmount());
				details.add(post_incoming.getCodeId().getCodeName());
				BigDecimal totalIncoming = BigDecimal.ZERO;
				
				//check for cis
				Posting cis = null;
				Query cisquery = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 70010 AND p.journal = :jornal");
				cisquery.setParameter("jornal", income.getJournal());
				try{
					cis = (Posting)cisquery.getSingleResult();
					totalIncoming = post_incoming.getAmount().add(cis.getAmount());
				}
				catch(NoResultException e){totalIncoming = post_incoming.getAmount();}
				if(cis != null && vat != null){
					BigDecimal vat_incoming = (totalIncoming.multiply(vat_pacentage)).divide(vat_pacentage.add(new BigDecimal("100")), DECIMALS, ROUNDING_MODE);
					// vat registered client
					details.add(vat_incoming);
					//cis value added to list
					details.add(cis.getAmount());
					
				}
				else if(cis != null && vat == null){
					//none vat registered client
					details.add(new BigDecimal("0"));
					//cis value added to list
					details.add(cis.getAmount());
				}
				else if(cis == null && vat != null){
					BigDecimal vat_incoming = (totalIncoming.multiply(vat_pacentage)).divide(vat_pacentage.add(new BigDecimal("100")), DECIMALS, ROUNDING_MODE);
					// vat registered client
					details.add(vat_incoming);
					
					//no cis voucher recieved
					details.add(new BigDecimal("0"));
				}
				
				else{
					//none vat registered client
					details.add(new BigDecimal("0"));
					
					//no cis voucher recieved
					details.add(new BigDecimal("0"));
				}
			
			}
			
			/*Query queryp = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 10010 AND p.journal.journalId = ?1 ");
			queryp.setParameter(1, income.getJournal().getJournalId());
			Posting post = (Posting)queryp.getSingleResult();
			try{
				Query querycis = em.createQuery("SELECT p FROM Posting p WHERE p.codeId.codeId = 70010 AND p.journal.journalId = ?1 ");
				querycis.setParameter(1, income.getJournal().getJournalId());
				Posting postcis = (Posting)querycis.getSingleResult();
				details.add(postcis.getAmount());
			  }
			  catch(NoResultException ex){details.add(new BigDecimal("0"));}*/
			details.add(income.getNote());
			details.add(income.getPaymentInDate());
			//details.add(income.getPaymentType());
			//details.add(post.getAmount());
			details.add(income.getIncomingId());
			detailsObject.add(details);
		}
		
		/*try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(detailsObject);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }*/
		return objectWriter(detailsObject);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////General Expenses
	
	/*DONE
		**addExpensesPaid(byte[] expensePaid) bean method for inputing Paid Expenses 
		**@parameter byte array expensePaid  as argument
		**populate PaidExpensesInvoice entity
	*/
	public void addExpensesPaid(byte[] expensePaid){
		ArrayList<Object> expenses = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(expensePaid);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		PaidExpensesInvoice paid = new PaidExpensesInvoice();
		BigDecimal amount = new BigDecimal((String)expenses.get(4));
		int codeOpExpense = ((Integer)expenses.get(1)).intValue();
		int codePayType =  ((Integer)expenses.get(5)).intValue();
		String vat =  (String)expenses.get(6);

		Journal jr = paidExpensesJornal(amount, codeOpExpense, codePayType, vat);
		paid.setJournal(jr);
		paid.setSupplier(em.find(Supplier.class, ((Integer)expenses.get(0)).intValue()));
		paid.setInvoiceDate(java.sql.Date.valueOf((String)expenses.get(3)));
		paid.setInvoiceNumber((String)expenses.get(2));
		paid.setPaymentMethod(em.find(AccountCode.class, codePayType));
		paid.setOperationExpenses(em.find(AccountCode.class, codeOpExpense));
		em.persist(paid);
		
	}
	/***paidExpensesJornal(BigDecimal amount, int codePaymentType, String vatstr) bean method for posting paid jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal paidExpensesJornal(BigDecimal amount, int codeExpenseType, int codePaymentType, String vatstr){
		Journal jr = new Journal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		AccountCode codeOpExpenses = em.find(AccountCode.class, codeExpenseType); 
		AccountCode codePayType = em.find(AccountCode.class, codePaymentType); 
		
		//jr.setcodeIdCR(codePayType.getGroup());
		//jr.setcodeIdDR(codeOpExpenses.getGroup());
		jr.setPostManual(false);
	   
		//find the parcentage for vat
		Posting vat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);

			}
		}
		
			
		//post  operationl expenses jornal
		Posting opExpenses = new Posting();
		opExpenses.setAmount(amount.subtract(vatAmount));
		opExpenses.setCodeId(codeOpExpenses);
	//	opExpenses.setPostManual(false);
		postarray.add(opExpenses);
		opExpenses.setJournal(jr);
			
		//post cash/credit card jornal
		Posting cash = new Posting();
		cash.setAmount(amount.negate());
		cash.setCodeId(codePayType);
		//cash.setPostManual(false);
		postarray.add(cash);
		cash.setJournal(jr);
			
		//post vat jornal
		if(vat != null){
			AccountCode codeVAT = em.find(AccountCode.class, 70020); 
			vat.setAmount(vatAmount);
			vat.setCodeId(codeVAT);
			//vat.setPostManual(false);
			postarray.add(vat);
			vat.setJournal(jr);
		}
		jr.setPostingdrcr(postarray);
		em.persist(jr);
	
		return jr;
	}
	

	/*DONE
		**addExpensesPrepaid(byte[] expensePrepaid) bean method for inputing Suppliers GeneralExpenses 
		**@parameter byte array expenseAccrued  as argument
		**populate PrepaidExpensesInvoice entity
	*/
	public void addExpensesPrepaid(byte[] expensePrepaid){
		ArrayList<Object> expenses = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(expensePrepaid);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		PrepaidExpensesInvoice prepaid = new PrepaidExpensesInvoice();

		BigDecimal amount = new BigDecimal((String)expenses.get(5));
		String vat = (String)expenses.get(7);
		
		Journal jr = prepaidExpensesJornal(amount, ((Integer)expenses.get(6)).intValue(), vat);
		prepaid.setJournal(jr);
		prepaid.setSupplier(em.find(Supplier.class, ((Integer)expenses.get(0)).intValue()));
		prepaid.setOperationExpenses(em.find(AccountCode.class, ((Integer)expenses.get(1)).intValue()));
		prepaid.setPaymentMethod(em.find(AccountCode.class, ((Integer)expenses.get(6)).intValue()));
		prepaid.setInvoiceNumber((String)expenses.get(2));
		prepaid.setInvoiceDate(java.sql.Date.valueOf((String)expenses.get(3)));
		String [] strperiod = ((String)expenses.get(4)).split("\\s+");
		prepaid.setPeriod((Integer.valueOf(strperiod[0])).intValue());
		em.persist(prepaid);
		prepaidAllotments();
	}
	
	/***prepaidExpensesJornal(BigDecimal amount, int codePaymentType, String vatstr) bean method for posting accrued jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal prepaidExpensesJornal(BigDecimal amount, int codePaymentType, String vatstr){
	
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		Journal jr = new Journal();
		
		AccountCode codePayType = em.find(AccountCode.class, codePaymentType);  
		AccountCode codePrepaid = em.find(AccountCode.class, 10030); 

		//jr.setcodeIdCR(codePayType.getGroup());
		//jr.setcodeIdDR(codePrepaid.getGroup());
		jr.setPostManual(false);
	   
		//find the parcentage for vat
		Posting vat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
			
		//post  operationl expenses jornal
		Posting opPrepaid = new Posting();
		opPrepaid.setAmount(amount.subtract(vatAmount));
		opPrepaid.setCodeId(codePrepaid);
		//opPrepaid.setPostManual(false);
		postarray.add(opPrepaid);
		opPrepaid.setJournal(jr);
			
		//post accounts bank/cash or credit card jornal
		Posting paid = new Posting();
		paid.setAmount(amount.negate());
		paid.setCodeId(codePayType);
		//paid.setPostManual(false);
		postarray.add(paid);
		paid.setJournal(jr);
			
		//post vat jornal
		if(vat != null){
			AccountCode codeVAT = em.find(AccountCode.class, 70020); 
			vat.setAmount(vatAmount);
			vat.setCodeId(codeVAT);
			//vat.setPostManual(false);
			postarray.add(vat);
			vat.setJournal(jr);
		}
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		return jr;
	}
	
	/***DONE
		***prepaidAllotments() bean method helper for prepaid view and P&L expenses view
		**auto update / allot prepaid expenses according current date
	*/
	public void prepaidAllotments(){
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)em.createQuery("SELECT p FROM PrepaidExpensesInvoice p").getResultList();
		for(PrepaidExpensesInvoice prepaid : ls){
			
			ArrayList<Posting> postarray = new ArrayList<Posting>();
			//ArrayList<Posting> post = new ArrayList<Posting>();//update prepaid posting need no need not added to new ArrayList
			BigDecimal amount =  BigDecimal.ZERO;
			BigDecimal singlepayment =  BigDecimal.ZERO;
			
			Query query = em.createQuery("SELECT p FROM PrepaidAllotment p WHERE p.prepaidExpenses = :prepaidExp");
			query.setParameter("prepaidExp", prepaid);
			java.util.List<PrepaidAllotment> p =  (java.util.List<PrepaidAllotment>)query.getResultList();
			
			ArrayList<java.sql.Date> range = dataRange(prepaid.getInvoiceDate(), prepaid.getPeriod());
			AccountCode opExpenses = prepaid.getOperationExpenses();
			
			Query prepaidquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10030");//update prepaid must be in different transaction			
			prepaidquery.setParameter("jornal", prepaid.getJournal());
			
			Posting prepaidpost = null;
			try{
				 prepaidpost = (Posting)prepaidquery.getSingleResult();
				amount = prepaidpost.getAmount();
				 singlepayment = new BigDecimal(prepaidpost.getAmount().toPlainString()).divide(new BigDecimal(String.valueOf(prepaid.getPeriod())), DECIMALS, ROUNDING_MODE);
				// singlepayment.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			}
			catch(NoResultException ex){}

			for(int i = p.isEmpty() ? 0 : p.size() + 1 ; 
				i < range.size(); i++){
				PrepaidAllotment allot = new PrepaidAllotment();
				Journal jr = new Journal();
				//jr.setcodeIdDR(opExpenses.getGroup());
				//jr.setcodeIdCR(prepaidpost.getCodeId().getGroup());
				jr.setPostManual(false);
				
				allot.setPrepaidExpenses(prepaid);
				allot.setPaymentDate(range.get(i));
				
				Posting allotment = new Posting();
				if( i == prepaid.getPeriod()-1){
					allotment.setAmount(prepaidpost.getAmount());
					prepaidpost.setAmount(prepaidpost.getAmount().subtract(prepaidpost.getAmount()));//update prepaid must be in different transaction
				}
				else{
					prepaidpost.setAmount(prepaidpost.getAmount().subtract(singlepayment));//update prepaid must be in different transaction
					allotment.setAmount(singlepayment);
				}
				//post.add(prepaidpost);//update prepaid posting need no need not added to new ArrayList 
				//prepaidpost.setJournal(prepaid.getJournal());//update prepaid posting need not setJournal
				allotment.setCodeId(opExpenses);
				//allotment.setPostManual(false);
				postarray.add(allotment);
				allotment.setJournal(jr);
						
				jr.setPostingdrcr(postarray);
				em.persist(jr);
				String prefix =  null;
				if(i == 0){
					prefix = "st";
				}
				else if(i == 1){
					prefix = "nd";
				}
				
				else if(i == 2){
					prefix = "rd";
				}
				
				else{
					prefix = "th";
				}
				int prefixnum = i + 1;
				allot.setPeriod(String.format("%s %s", prefixnum+prefix, "month"));
				allot.setJournal(jr);
				em.persist(allot);
			}
			
		}
	
	}
	
	/***DONE
		**accruedExpensesJornal(BigDecimal amount, int codeExpense) bean method for posting accrued jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal accruedExpensesJornal(BigDecimal amount, int codeExpense){
		Journal jr = new Journal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		AccountCode codePayType = em.find(AccountCode.class, codeExpense);  
		AccountCode codeAccrued = em.find(AccountCode.class, 20030); 
		
		//jr.setcodeIdDR(codePayType.getGroup());
		jr.setCodeDR(String.valueOf(codePayType.getGroup().getGroupId()) + ".");
		jr.setCodeCR(String.valueOf(codeAccrued.getGroup().getGroupId()) + ".");
		jr.setPostManual(false);
				
		//post  operationl expenses jornal
		Posting expen = new Posting();
		expen.setAmount(amount);
		expen.setCodeId(codePayType);
		//expen.setPostManual(false);
		postarray.add(expen);
		expen.setJournal(jr);
			
		//add accrued to posting
		Posting accru = new Posting();
		accru.setAmount(amount.negate());
		accru.setCodeId(codeAccrued);
		//accru.setPostManual(false);
		postarray.add(accru);
		accru.setJournal(jr);
		
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		return jr;
	}
	
	/***DONE
		** addExpensesAccrued(byte[] expensePayable) bean method for inputing Suppliers General Expenses 
		**@parameter byte array accruedPayable  as argument
		**populate AccruedExpensesInvoice entity
	*/
	public void addExpensesAccrued(byte[] accruedPayable){
		ArrayList<Object> expenses = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(accruedPayable);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		AccruedExpensesInvoice accrued = new AccruedExpensesInvoice();
		
		int codeExpense = ((Integer)expenses.get(1)).intValue();
		BigDecimal amount = new BigDecimal((String)expenses.get(3));
		Journal jr = accruedExpensesJornal(amount, codeExpense);
		
		accrued.setJournal(jr);
		accrued.setSupplier(em.find(Supplier.class, ((Integer)expenses.get(0)).intValue()));
		accrued.setInvoiceDate(java.sql.Date.valueOf((String)expenses.get(2)));
		accrued.setOperationExpenses(em.find(AccountCode.class,codeExpense));
		accrued.setNote((String)expenses.get(4));
		accrued.setInvoiceReceived(false);
		accrued.setAmount(amount);
		em.persist(accrued);
	}
	
	/***DONE
		** accruedExpensesPayment(byte[] accruedsettle) bean method for inputing invoice from accrued expenses when received
		**@parameter byte array accruedsettle  as argument
		**populate AccruedExpensesPayment entity
	*/
	public void accruedExpensesPayment(byte[] accruedsettle){
		ArrayList<String> expenses = new ArrayList<String>();
		ByteArrayInputStream bais = new ByteArrayInputStream(accruedsettle);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		AccruedExpensesPayment accruedprocessed = new AccruedExpensesPayment();
		Journal jr = null;
		
		int postedto = (Integer.valueOf(expenses.get(1))).intValue();
		int id =  (Integer.valueOf(expenses.get(0))).intValue();
		BigDecimal amount = new BigDecimal(expenses.get(4));
		java.sql.Date date = java.sql.Date.valueOf(expenses.get(3));
		String invoiceNumber = expenses.get(2);
		
		String vat = expenses.get(5);
		
		if(postedto == 20010){
			jr = accruedPayableInvoice(id, amount, vat, date , invoiceNumber);
		
		}
		
		else{
			jr = accrueSettlementPosting(id, amount, vat, postedto);
		}
		accruedprocessed.setExpensesAccruedInvoice(em.find(AccruedExpensesInvoice.class, id));
		accruedprocessed.setPaymentType(em.find(AccountCode.class, postedto));
		accruedprocessed.setInvoiceNumber(invoiceNumber);
		accruedprocessed.setPaymentDate(date);
		accruedprocessed.setJournal(jr);
		em.persist(accruedprocessed);
	
	}
	/***getExpensesAccrued(int accruedId) bean method to get accruedexpensesinvoice at the same time update invoicereceived to true 
		**@parameternew int accruedId  as arguments
		**return AccruedExpensesInvoice for accruedPayablePosting and accrueSettlementPosting bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public AccruedExpensesInvoice getExpensesAccrued(int accruedId){
		AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, accruedId);
		accrued.setInvoiceReceived(true);
		//accrued.setJournal(jornal);
		em.persist(accrued);
		return accrued;
	}
	
	/***accruedPayableInvoice(int id, BigDecimal amount, String vat) bean method for posting payable jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal accruedPayableInvoice(int id, BigDecimal amount, String vat, java.sql.Date invoiceDate, String invoiceNumber){
		
		PayableExpensesInvoice payableexp = new PayableExpensesInvoice();
		
		AccruedExpensesInvoice accruedpayable = getExpensesAccrued(id);
		Journal jr = accruedPayablePosting(accruedpayable, amount, vat);		
		payableexp.setJournal(jr);
		payableexp.setSupplier(accruedpayable.getSupplier());
		payableexp.setInvoiceDate(invoiceDate);
		payableexp.setInvoiceNumber(invoiceNumber);
		payableexp.setOperationExpenses(accruedpayable.getOperationExpenses());
		em.persist(payableexp);
		return jr;
	
	}
	
	
	/***accruedPayablePosting(int id, BigDecimal amount, String vat) bean method for posting payable Journal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Journal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal accruedPayablePosting(AccruedExpensesInvoice accrued, BigDecimal amount, String vat){
		
		Posting payable = new Posting();
		Journal latest =  new Journal();
		ArrayList<Posting> settle = new ArrayList<Posting>();
		String adjustment = postAccruedJornal(accrued, amount, vat);
		
		AccountCode payablecode = em.find(AccountCode.class, 20010); 
		payable.setAmount(amount.negate());
		payable.setCodeId(payablecode);
		//payable.setPostManual(false);
		settle.add(payable);
		payable.setJournal(latest);
		latest.setPostingdrcr(settle);
		
		latest.setCodeDR(adjustment);
		latest.setCodeCR(String.valueOf(payablecode.getGroup().getGroupId()));
		latest.setPostManual(false);
		
		em.persist(latest);
		return latest;
	}
	
	/***accrueSettlementPosting(BigDecimal amount, int codePaymentType, String vatstr) bean method for posting payable jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesAcrued bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal accrueSettlementPosting(int id, BigDecimal amount, String vat, int paymentMethod){
		AccruedExpensesInvoice accruedpayable = getExpensesAccrued(id);
		
		Posting payment = new Posting();
		Journal latest =  new Journal();
		ArrayList<Posting> settle = new ArrayList<Posting>();
		String adjustment = postAccruedJornal(accruedpayable, amount, vat);
		
		AccountCode paymentcode = em.find(AccountCode.class, paymentMethod); 
		payment.setAmount(amount.negate());
		payment.setCodeId(paymentcode);
		//payment.setPostManual(false);
		settle.add(payment);
		payment.setJournal(latest);
		latest.setPostingdrcr(settle);
		
		latest.setCodeDR(adjustment);
		latest.setCodeCR(String.valueOf(paymentcode.getGroup().getGroupId()));
		latest.setPostManual(false);
		
		em.persist(latest);
		return latest;
	}
	
	/***postAccruedJornal(AccruedExpensesInvoice accruedpayable, BigDecimal amount, String vatstr) bean method to adjust expenses and accrued posted after invoice recieved  
		**@AccruedExpensesInvoice  as arguments
		**use by accruedPayablePosting and accrueSettlementPosting bean methods
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public String postAccruedJornal(AccruedExpensesInvoice accruedpayable, BigDecimal amount, String vatstr){
		ArrayList<Posting> update = new ArrayList<Posting>();
 		Posting accrued = null;
		Posting expenses = null;
		Posting vat = null;
		AccountCode codeVAT = null;
		Journal accruedjornal = accruedpayable.getJournal();
		Query queryaccrued = em.createQuery("SELECT p  FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId=20030");
		queryaccrued.setParameter("jornal", accruedjornal);
		try{
			accrued = (Posting)queryaccrued.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Query queryexpenses = em.createQuery("SELECT p  FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
		queryexpenses.setParameter("jornal", accruedjornal);
		queryexpenses.setParameter(1, accruedpayable.getOperationExpenses().getCodeId());
		
		try{
			expenses = (Posting)queryexpenses.getSingleResult();
		}
		catch(NoResultException ex){}
		
		BigDecimal amountadj = accrued.getAmount().abs().subtract(amount);
		accrued.setAmount(new BigDecimal("0"));
		update.add(accrued);
		accrued.setJournal(accruedjornal);
				
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
		expenses.setAmount(expenses.getAmount().add(new BigDecimal("1").negate().multiply(amountadj)).subtract(vatAmount));
		update.add(expenses);
		expenses.setJournal(accruedjornal);
		
		if(vat != null){
			codeVAT = em.find(AccountCode.class, 70020); 
			vat.setAmount(vatAmount);
			vat.setCodeId(codeVAT);
			//vat.setPostManual(false);
			update.add(vat);
			vat.setJournal(accruedjornal);
		}
		String str[] = accruedjornal.getCodeDR().split("\\.");
		accruedjornal.setPostingdrcr(update);
		em.persist(accruedjornal);
		return str[0]+"."+String.valueOf(codeVAT.getGroup().getGroupId());//return result need rewritting as invoice might not be vat invoice
	}
	
	/***DONE
		** mutiplePrepaidInvoices pupolate accrued expenses in general expenses view
		**use by general expenses view gui
	*/
	public ArrayList<ArrayList<Object>> mutipleAccruedInvoices(java.util.List<AccruedExpensesInvoice> ls){

		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccruedExpensesInvoice accrued : ls){
			BigDecimal amount =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(accrued.getSupplier().getCompanyName());
			temp.add(accrued.getOperationExpenses().getCodeName());
			temp.add(accrued.getInvoiceDate());
			temp.add(accrued.getAmount());
			temp.add(accrued.getInvoiceReceived() ? "Received" : "Pending");
			//temp.add("No");
			temp.add(Integer.valueOf(accrued.getExpensesInvoiceId()));			
			datas.add(temp);
		}
		return datas;
	}
	
	public byte [] accruedInvoicesSearch(){
		java.util.List<AccruedExpensesInvoice> ls = (java.util.List<AccruedExpensesInvoice>)em.createQuery("SELECT a FROM AccruedExpensesInvoice a").getResultList();
		return objectWriter(mutipleAccruedInvoices(ls));
	}
	
	public byte [] accruedInvoicesSearch(Date from, Date to, BigDecimal amtfrom, BigDecimal amtto){
		Query query = em.createQuery("SELECT a FROM AccruedExpensesInvoice a WHERE a.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<AccruedExpensesInvoice> ls = (java.util.List<AccruedExpensesInvoice>)query.getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccruedExpensesInvoice accrued : ls){
			BigDecimal amount =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			amount = accrued.getAmount();
			if(amount.compareTo(amtfrom) >= 0 && amount.compareTo(amtto) <= 0){
				temp.add(accrued.getSupplier().getCompanyName());
				temp.add(accrued.getOperationExpenses().getCodeName());
				temp.add(accrued.getInvoiceDate());
				temp.add(amount);
				temp.add(accrued.getInvoiceReceived() ? "Received" : "Pending");
				temp.add(Integer.valueOf(accrued.getExpensesInvoiceId()));			
				datas.add(temp);
			}
		}
		return objectWriter(datas);
	
	}
	
	public byte [] accruedInvoicesSearch(Date from, Date to){
		Query query = em.createQuery("SELECT a FROM AccruedExpensesInvoice a WHERE a.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<AccruedExpensesInvoice> ls = (java.util.List<AccruedExpensesInvoice>)query.getResultList();
		return objectWriter(mutipleAccruedInvoices(ls));
	}
	
	public byte [] accruedInvoicesSearch(Date from, Date to, int supplier){
		Query query = em.createQuery("SELECT a FROM AccruedExpensesInvoice a WHERE a.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.supplier.supplierId = ?1 AND e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, supplier);
		query.setParameter(2, from);   
        query.setParameter(3, to);
		java.util.List<AccruedExpensesInvoice> ls = (java.util.List<AccruedExpensesInvoice>)query.getResultList();		
		return objectWriter(mutipleAccruedInvoices(ls));
	}
	
	public byte [] accruedInvoicesSearch(Date from, Date to, String invoice){
		Query query = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a, AccruedExpensesInvoice p WHERE a.invoiceNumber = ?1 AND p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, invoice); 
		query.setParameter(2, from);   
        query.setParameter(3, to);
		java.util.List<AccruedExpensesInvoice> ls = (java.util.List<AccruedExpensesInvoice>)query.getResultList();		
		return objectWriter(mutipleAccruedInvoices(ls));
	}
	
	/***getPayableBalance(int payableId) bean method for automate remain balance on accounts payable 
		**@payable id as parameter and return byte[] array of payable balance
		**use by AccrueExpensesPayment GUI
	*/
	public byte[] getAccruedBalance(int accruedId){
		BigDecimal postamount =  BigDecimal.ZERO;

		AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, accruedId);
		Query queryposting = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal and p.codeId.codeId=20030");
		queryposting.setParameter("jornal", accrued.getJournal());
		
		try{
			postamount = (BigDecimal)queryposting.getSingleResult();
		}
		catch(NoResultException ex){}
		return objectWriter(postamount.abs());
	}
	
	/***getAccruedDetails(int payableId) bean method to get accrued expenses details
		**@payable id as parameter and return byte[] array of payable balance
		**use by ExpensesPaymentDetails GUI
	*/
	public byte[] getAccruedDetails(int accruedId){
		BigDecimal postamount = BigDecimal.ZERO;
		BigDecimal vatamount = BigDecimal.ZERO;
		BigDecimal netamount = BigDecimal.ZERO;
		BigDecimal vatcreditnote = BigDecimal.ZERO;
		BigDecimal creditnote = BigDecimal.ZERO;
		ArrayList<Object> data = new ArrayList<Object>();
		Object[] bigDArray = null;

		AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, accruedId);
		
		Query queryaccrued = em.createQuery("SELECT p FROM AccruedExpensesPayment p WHERE p.expensesAccruedInvoice=:accrueds");	
		queryaccrued.setParameter("accrueds", accrued);
		AccruedExpensesPayment accruedpayment = null;
		try{	
			accruedpayment =(AccruedExpensesPayment)queryaccrued.getSingleResult();
		}
		catch(NoResultException ex){}
		

		Query queryposting = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=20030");
		queryposting.setParameter("jornal", accrued.getJournal());
		
		try{
			postamount = (BigDecimal)queryposting.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Query queryvat = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
		queryvat.setParameter("jornal", accrued.getJournal());
		
		try{
			
			vatamount = (BigDecimal)queryvat.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId=:code");
		queryamount.setParameter("jornal", accrued.getJournal());
		queryamount.setParameter("code", accrued.getOperationExpenses());
		
		try{
			netamount = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}

		Posting invoice = null;
		if(accruedpayment != null){
			Query querypayment = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId=:code");
			querypayment.setParameter("jornal", accruedpayment.getJournal());
			querypayment.setParameter("code", accruedpayment.getPaymentType());

			try{
				invoice = (Posting)querypayment.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query querycreditnote = em.createQuery("SELECT SUM(c.amount), SUM(c.vat) FROM CreditNote c, ExpensesPayableCreditNote e, ExpensesInvoice p, AccruedExpensesPayment u " +
			"WHERE (c.creditNoteId = e.creditNoteId AND p.expensesInvoiceId = e.expensesPayableInvoice.expensesInvoiceId) AND (u.journal=:jornal AND  u.journal.journalId = p.journal.journalId)");
			querycreditnote.setParameter("jornal", accruedpayment.getJournal());
		
			try{
				bigDArray = (Object[])querycreditnote.getSingleResult();
			}
			catch(NoResultException ex){}
			if(bigDArray[0] != null){
				creditnote = (BigDecimal)bigDArray[0];
				vatcreditnote = (BigDecimal)bigDArray[1];
			}
			
			data.add(accruedpayment.getPaymentDate());
			data.add(netamount.add(creditnote.subtract(vatcreditnote)));
			data.add(vatamount.add(vatcreditnote));
			data.add(invoice.getCodeId().getCodeName());
			data.add(accrued.getNote());
			data.add((netamount.add(vatamount).add(creditnote)).subtract(accrued.getAmount()));
			data.add(postamount.abs());
			data.add(Integer.valueOf(accruedpayment.getAccruedPaymentId()));
		}

		else{
			data.add(postamount.abs());
		}
		return objectWriter(data);
	}
	/***DONE
		** mutiplePrepaidInvoices(java.util.List<PrepaidExpensesInvoice> ls) prepaid bean search helper method arraylist of arraylis object
		**return @parameter arraylist of arraylis object getPaymentData
	*/
	public ArrayList<ArrayList<Object>> mutiplePrepaidInvoices(java.util.List<PrepaidExpensesInvoice> ls){

		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();

		for(PrepaidExpensesInvoice pre : ls){
			BigDecimal amount =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pre.getSupplier().getCompanyName());
			temp.add(pre.getInvoiceNumber());
			temp.add(pre.getOperationExpenses().getCodeName());
			temp.add(pre.getInvoiceDate());
			
			Query queryjr = em.createQuery("SELECT p  FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?2");
			queryjr.setParameter("jornal", pre.getJournal());
			queryjr.setParameter(2, pre.getPaymentMethod().getCodeId());
			temp.add(((Posting)queryjr.getSingleResult()).getAmount().abs());
			temp.add(Integer.valueOf(pre.getPeriod()));
			BigDecimal percentagevat = getVATInclusive(pre.getJournal(), pre.getPaymentMethod().getCodeId(), 70020);
			BigDecimal grossvat = percentagevat.add(new BigDecimal("100"));
			Query querypr = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.codeId.codeId = ?1 AND p.journal.journalId IN(SELECT a.journal.journalId FROM PrepaidAllotment a WHERE a.prepaidExpenses=:prepaid)");
			querypr.setParameter("prepaid" , pre);
			querypr.setParameter(1, pre.getOperationExpenses().getCodeId());
			try{
				amount = (BigDecimal)querypr.getSingleResult();
			}
			catch(NoResultException ex){}

			temp.add(amount.multiply(grossvat).divide(new BigDecimal("100")));
			temp.add(Integer.valueOf(pre.getExpensesInvoiceId()));
			datas.add(temp);

		}
		return datas;
	
	}
	
	/***SEEN
		** prepaidInfomations() bean method return byte array for prepaid expenses view
		**return @parameter byte array 
	*/
	public byte[] prepaidInfomations(){
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)em.createQuery("SELECT p FROM PrepaidExpensesInvoice p").getResultList();

		return objectWriter(mutiplePrepaidInvoices(ls));
	}
	
	/***SEEN
		** prepaidInfomations(Date from, Date to) bean method return byte array for prepaid expenses view
		**return @parameter byte array 
	*/
	public byte[] prepaidInfomations(Date from, Date to){
		Query query = em.createQuery("SELECT p FROM PrepaidExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)query.getResultList();
		return objectWriter(mutiplePrepaidInvoices(ls));
	}
	
	/***DONE
		** prepaidInfomations(BigDecimal from, BigDecimal to) bean method return byte array for prepaid expenses view
		**return @parameter byte array 
	*/
	public byte[] prepaidInfomations(Date from, Date to, BigDecimal amtfrom, BigDecimal amtto){
	
		Query query = em.createQuery("SELECT p FROM PrepaidExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)query.getResultList();
		
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		BigDecimal filteramount =  BigDecimal.ZERO;
		for(PrepaidExpensesInvoice pre : ls){
			BigDecimal amount =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			
			Query queryjr = em.createQuery("SELECT p  FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId=?2");
			queryjr.setParameter("jornal", pre.getJournal());
			queryjr.setParameter(2, pre.getPaymentMethod().getCodeId());
			filteramount = ((Posting)queryjr.getSingleResult()).getAmount().abs();
			if(filteramount.compareTo(amtfrom) >= 0 && filteramount.compareTo(amtto) <= 0){
				temp.add(pre.getSupplier().getCompanyName());
				temp.add(pre.getInvoiceNumber());
				temp.add(pre.getOperationExpenses().getCodeName());
				temp.add(pre.getInvoiceDate());
				temp.add(filteramount);
				temp.add(Integer.valueOf(pre.getPeriod()));
			
				Query prepaidAllot = em.createQuery("SELECT p FROM PrepaidAllotment p WHERE p.prepaidExpenses=:prepaid");
				prepaidAllot.setParameter("prepaid" , pre);
				java.util.List<PrepaidAllotment> lsa = (java.util.List<PrepaidAllotment>)prepaidAllot.getResultList();
			
				for(PrepaidAllotment allot : lsa){
					Query querypr = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
					querypr.setParameter("jornal", allot.getJournal());
					querypr.setParameter(1, pre.getOperationExpenses().getCodeId());
					Posting prepaidpost = null;
					try{
					prepaidpost = (Posting)querypr.getSingleResult();
					amount = amount.add(prepaidpost.getAmount());
					}
					catch(NoResultException ex){}
				}
				temp.add(amount);
				temp.add(Integer.valueOf(pre.getExpensesInvoiceId()));
				datas.add(temp);
			}
		}
		return objectWriter(datas);
	}
	
	/***SEEN
		** prepaidInfomations(int invoice) bean method return byte array for prepaid expenses view single invoice
		**return @parameter byte array 
	*/
	public byte[] prepaidInfomations(Date from, Date to, String invoice){
		Query query = em.createQuery("SELECT p FROM PrepaidExpensesInvoice p WHERE p.invoiceNumber = ?1 AND p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, invoice);
		query.setParameter(2, from);   
        query.setParameter(3, to);		
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)query.getResultList();		
		return objectWriter(mutiplePrepaidInvoices(ls));
		
	}
	
	
	/***SEEN
		** prepaidInfomations() bean method return byte array for prepaid expenses view to get all suplier invoice
		**return @parameter byte array 
	*/
	public byte[] prepaidInfomations(Date from, Date to, int supplier){
	    Query query = em.createQuery("SELECT p FROM PrepaidExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.supplier.supplierId = ?1 AND e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, supplier);
		query.setParameter(2, from);   
        query.setParameter(3, to);
		java.util.List<PrepaidExpensesInvoice> ls = (java.util.List<PrepaidExpensesInvoice>)query.getResultList();		
		return objectWriter(mutiplePrepaidInvoices(ls));
	}
	
	/***DONE
		** allotmentInfoDetails(int prepaidId) bean method return byte array for allotment breakdown use by ExpensesPaymentDeatils view
		**return @parameter byte array 
	*/
	public byte[] allotmentInfoDetails(int prepaidId){
	
		PrepaidExpensesInvoice pre = em.find(PrepaidExpensesInvoice.class, prepaidId);
		
		Query queryvat = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = 70020");
		queryvat.setParameter("jornal" , pre.getJournal());
		BigDecimal vatamount =  BigDecimal.ZERO;
		int counter = 0;
		Posting vat = null;
		try{
			 vat = (Posting)queryvat.getSingleResult();
		}
		catch(NoResultException ex){}
		if(vat != null){
			vatamount = vat.getAmount().divide(new BigDecimal(String.valueOf(pre.getPeriod())), DECIMALS, ROUNDING_MODE);
		}
		
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		Query prepaidAllot = em.createQuery("SELECT p FROM PrepaidAllotment p WHERE p.prepaidExpenses=:prepaid ORDER BY p.paymentDate ASC");
		prepaidAllot.setParameter("prepaid" , pre);
		java.util.List<PrepaidAllotment> lsa = (java.util.List<PrepaidAllotment>)prepaidAllot.getResultList();
		for(PrepaidAllotment allot : lsa){
			ArrayList<Object> temp = new ArrayList<Object>();
			Query querypr = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
			querypr.setParameter("jornal", allot.getJournal());
			querypr.setParameter(1, pre.getOperationExpenses().getCodeId());
			Posting prepaidpost = null;
			try{
			   prepaidpost = (Posting)querypr.getSingleResult();
			}
			catch(NoResultException ex){}
			temp.add(allot.getPaymentDate());
			temp.add(prepaidpost.getAmount());
			temp.add((prepaidpost.getAmount().abs().multiply(getVATInclusive(pre.getJournal(), pre.getPaymentMethod().getCodeId(), 70020))).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
			temp.add(pre.getPaymentMethod().getCodeName());
			temp.add(String.format("%s %d","Priod" , ++counter));
			temp.add(Integer.valueOf(allot.getPrepaidId()));
			datas.add(temp);
		}
		return objectWriter(datas);
	}
	/***DONE
		**method byte[] prepiadPaymentBalance(int) get the balance on supplier account
		**return byte[] array of double object
		**use by expenses payment details GUI
	*/
	
	public byte[] prepaidPaymentBalance(int prepaidId){

		PrepaidExpensesInvoice pre = em.find(PrepaidExpensesInvoice.class, prepaidId);
		Query payablequery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=10030");
		payablequery.setParameter("jornal", pre.getJournal());
		Posting ps = null;
		try{
			ps = (Posting)payablequery.getSingleResult();
		}
		catch(NoResultException ex){}
		return objectWriter((ps.getAmount().abs().multiply(getVATInclusive(pre.getJournal(), pre.getPaymentMethod().getCodeId(), 70020).add(new BigDecimal("100")))).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
	}
	
	/***DONE
		**expensesResolutions bean method for processed expenses 
		**@parameter null
		**use by processed expenses tappanel in the general expenses view
	*/
	public byte[] expensesResolutions(){ 
		java.util.List<Object[]> ls = (java.util.List<Object[]>)em.createQuery("SELECT p.dtype, p.expensesInvoiceId FROM ExpensesInvoice p").getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Object[] obj : ls){
			if(((String)obj[0]).equals("PrepaidExpensesInvoice")){
				PrepaidExpensesInvoice prepaid = em.find(PrepaidExpensesInvoice.class, ((Integer)obj[1]).intValue());
				
				BigDecimal vatparcentage = BigDecimal.ZERO;
				vatparcentage = getVATInclusive(prepaid.getJournal(), prepaid.getPaymentMethod().getCodeId(), 70020);
				
				Query queryallot = em.createQuery("SELECT pre FROM PrepaidAllotment pre WHERE pre.prepaidExpenses=:prepaids");
				queryallot.setParameter("prepaids", prepaid);
				java.util.List<PrepaidAllotment> prels = (java.util.List<PrepaidAllotment>)queryallot.getResultList();
				for(PrepaidAllotment pre : prels){
					ArrayList<Object> temp = new ArrayList<Object>();
					temp.add("Prepaid");
					temp.add(prepaid.getSupplier().getCompanyName());
					temp.add(prepaid.getInvoiceNumber());
					temp.add(prepaid.getOperationExpenses().getCodeName());
					temp.add(pre.getPaymentDate());
					Query querypr = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
					querypr.setParameter("jornal", pre.getJournal());
					querypr.setParameter(1, prepaid.getOperationExpenses().getCodeId());
					Posting prepaidpost = null;
					try{
						prepaidpost = (Posting)querypr.getSingleResult();
					}
					catch(NoResultException ex){}
					BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
					//changes 05/02
					temp.add(prepaidpost.getAmount().multiply(grossvat).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
					temp.add(pre.getPrepaidId());
					datas.add(temp);
				}
			}
			if(((String)obj[0]).equals("PaidExpensesInvoice")){
				ArrayList<Object> temp1 = new ArrayList<Object>();
				PaidExpensesInvoice paid = em.find(PaidExpensesInvoice.class, ((Integer)obj[1]).intValue());
				temp1.add("Paid");
				temp1.add(paid.getSupplier().getCompanyName());
				temp1.add(paid.getInvoiceNumber());
				temp1.add(paid.getOperationExpenses().getCodeName());
				temp1.add(paid.getInvoiceDate());
				Query querypa = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
				querypa.setParameter("jornal", paid.getJournal());
				querypa.setParameter(1, paid.getPaymentMethod().getCodeId());
				Posting paidpost = null;
				try{
					paidpost = (Posting)querypa.getSingleResult();
				}
				catch(NoResultException ex){}
				temp1.add(paidpost.getAmount().abs());
				temp1.add(paid.getExpensesInvoiceId());
				datas.add(temp1);
			}
			
			if(((String)obj[0]).equals("PayableExpensesInvoice")){

				PayableExpensesInvoice payable = em.find(PayableExpensesInvoice.class, ((Integer)obj[1]).intValue());
				
				BigDecimal vatparcentage = BigDecimal.ZERO;
				vatparcentage = getVATExclusive(payable.getJournal(), payable.getOperationExpenses().getCodeId(), 70020);
				
				if(vatparcentage.compareTo(new BigDecimal("0")) == 0){
					AccruedExpensesInvoice accruedinvoice = null;
					Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.jornal=:jornal");
					accrued.setParameter("jornal",  payable.getJournal());
					try{
						accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
					}
					catch(NoResultException ex){}
				
					vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
				
				}
				
				Query querypayable = em.createQuery("SELECT p FROM PayableExpensesPayment p WHERE p.expensesPayableInvoice=:payables");
				querypayable.setParameter("payables", payable);
				java.util.List<PayableExpensesPayment> sps = (java.util.List<PayableExpensesPayment>)querypayable.getResultList();
				for(PayableExpensesPayment pay : sps){
					ArrayList<Object> temp2 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");				
					cashquery.setParameter("jornal", pay.getJournal());
					Posting post = null;
					try{	
						post = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					if(post != null){
						temp2.add("Payable");
						temp2.add(payable.getSupplier().getCompanyName());
						temp2.add(payable.getInvoiceNumber());
						temp2.add(payable.getOperationExpenses().getCodeName());
						temp2.add(pay.getPaymentDate());
						BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
						//changes 05/02
						temp2.add(post.getAmount().abs());//.multiply(new BigDecimal("100")).divide(grossvat, DECIMALS, ROUNDING_MODE));
						temp2.add(pay.getExpensesPaymentId());
						datas.add(temp2);
					}
				}
				
			}
			if(((String)obj[0]).equals("AccruedExpensesInvoice")){

				AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, ((Integer)obj[1]).intValue());
				Query queryaccrued = em.createQuery("SELECT p FROM AccruedExpensesPayment p WHERE p.expensesAccruedInvoice=:accrueds");
				
				queryaccrued.setParameter("accrueds", accrued);
				AccruedExpensesPayment sp = null;
				try{	
					sp =(AccruedExpensesPayment)queryaccrued.getSingleResult();
				}
				catch(NoResultException ex){}
		
				if(sp != null ){
					ArrayList<Object> temp3 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");
					cashquery.setParameter("jornal", sp.getJournal());				
					Posting postcash = null;
					try{	
						postcash = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					
					BigDecimal vatparcentage = getVATExclusive(accrued.getJournal(), accrued.getOperationExpenses().getCodeId(), 70020);
					
					if(postcash.getCodeId().getCodeId() != 20010){
						temp3.add("Accrued");
						temp3.add(accrued.getSupplier().getCompanyName());
						temp3.add(sp.getInvoiceNumber());
						temp3.add(accrued.getOperationExpenses().getCodeName());
						temp3.add(sp.getPaymentDate());
						if(postcash != null){
							BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
							//changes 05/02
							temp3.add(postcash.getAmount().abs());//.multiply(new BigDecimal("100")).divide(grossvat, DECIMALS, ROUNDING_MODE));
						}

						temp3.add(sp.getAccruedPaymentId());
						datas.add(temp3);
					}
				}
			}
		
		}
		return objectWriter(datas);
	}
	
				

	public byte[] expensesResolutions(String datefrom, String dateto){ 
		Query query = em.createNamedQuery("ExpensesResolution");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));

		java.util.List<Object[]> ls = (java.util.List<Object[]>)query.getResultList();
		
		return objectWriter(multipleExpensesResolutions(ls));
	}
	
	public byte[] expensesResolutions(String datefrom, String dateto, int supplier){ 
		Query query = em.createNamedQuery("ExpensesResolutionSupplier");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter(3, supplier);
		
		java.util.List<Object[]> ls = (java.util.List<Object[]>)query.getResultList();
		
		return objectWriter(multipleExpensesResolutions(ls));
	}
	
	public byte[] expensesResolutions(String datefrom, String dateto, String invoice){ 
		Query query = em.createNamedQuery("ExpensesResolutionInvoice");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter(3, invoice);
		
		java.util.List<Object[]> ls = (java.util.List<Object[]>)query.getResultList();
		
		return objectWriter(multipleExpensesResolutions(ls));
	}
	
	
	public byte[] expensesResolutions(String datefrom, String dateto, BigDecimal amtfrom, BigDecimal amtto){ 
		Query query = em.createNamedQuery("ExpensesResolution");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Object[]> ls = (java.util.List<Object[]>)query.getResultList();
		
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		
		for(Object[] obj : ls){
		
			PrepaidAllotment pre = null;
			PayableExpensesPayment pay = null;
			AccruedExpensesPayment sp = null;
			
			if(((String)obj[2]).equals("PaidExpensesInvoice")){
				ArrayList<Object> temp1 = new ArrayList<Object>();
				PaidExpensesInvoice paid = em.find(PaidExpensesInvoice.class, ((Integer)obj[1]).intValue());
				Query querypa = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
				querypa.setParameter("jornal", paid.getJournal());
				querypa.setParameter(1, paid.getPaymentMethod().getCodeId());
				Posting paidpost = null;
				try{
					paidpost = (Posting)querypa.getSingleResult();
				}
				catch(NoResultException ex){}
				BigDecimal paidamount = paidpost.getAmount().abs();
				if(paidamount.compareTo(amtfrom) >= 0 && paidamount.compareTo(amtto) <= 0){
					temp1.add("Paid");
					temp1.add(paid.getSupplier().getCompanyName());
					temp1.add(paid.getInvoiceNumber());
					temp1.add(paid.getOperationExpenses().getCodeName());
					temp1.add(paid.getInvoiceDate());
					temp1.add(paidamount);
					temp1.add(paid.getExpensesInvoiceId());
					datas.add(temp1);
				}					
			}
			
			if(((String)obj[2]).equals("PayableExpensesInvoice")){
		
				pay = em.find(PayableExpensesPayment.class, ((Integer)obj[1]).intValue());
				if(pay != null){
				
					PayableExpensesInvoice payable = em.find(PayableExpensesInvoice.class, pay.getExpensesPayableInvoice().getExpensesInvoiceId());
				
					ArrayList<Object> temp2 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");				
					cashquery.setParameter("jornal", pay.getJournal());
					Posting post = null;
					try{	
						post = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					BigDecimal payablepaid = post.getAmount().abs();
					if(payablepaid.compareTo(amtfrom) >= 0 && payablepaid.compareTo(amtto) <= 0){
						temp2.add("Payable");
						temp2.add(payable.getSupplier().getCompanyName());
						temp2.add(payable.getInvoiceNumber());
						temp2.add(payable.getOperationExpenses().getCodeName());
						temp2.add(pay.getPaymentDate());
						temp2.add(payablepaid);
						temp2.add(pay.getExpensesPaymentId());
						datas.add(temp2);
					}
					
				}
			}
			
			if(((String)obj[2]).equals("AccruedExpensesInvoice")){
				sp = em.find(AccruedExpensesPayment.class, ((Integer)obj[1]).intValue());
				if(sp != null){
					AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, sp.getExpensesAccruedInvoice().getExpensesInvoiceId());
					ArrayList<Object> temp3 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");
					cashquery.setParameter("jornal", sp.getJournal());				
					Posting postcash = null;
					try{	
						postcash = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					BigDecimal accruedpay = postcash.getAmount().abs();
					if(postcash.getCodeId().getCodeId() != 20010){
						if(accruedpay.compareTo(amtfrom) >= 0 && accruedpay.compareTo(amtto) <= 0){
	
							temp3.add("Accrued");
							temp3.add(accrued.getSupplier().getCompanyName());
							temp3.add(sp.getInvoiceNumber());
							temp3.add(accrued.getOperationExpenses().getCodeName());
							temp3.add(sp.getPaymentDate());
							if(postcash != null){
								temp3.add(accruedpay);
							}

							temp3.add(sp.getAccruedPaymentId());
							datas.add(temp3);
						}
					}
				}
			}	
			
			if(((String)obj[2]).equals("PrepaidExpensesInvoice")){
			
				ArrayList<Object> temp = new ArrayList<Object>();
				pre = em.find(PrepaidAllotment.class, ((Integer)obj[1]).intValue());
				
				if(pre != null){

					PrepaidExpensesInvoice prepaid = em.find(PrepaidExpensesInvoice.class, pre.getPrepaidExpenses().getExpensesInvoiceId());
	
					BigDecimal vatparcentage = BigDecimal.ZERO;
					vatparcentage = getVATInclusive(prepaid.getJournal(), prepaid.getPaymentMethod().getCodeId(), 70020);
					Query querypr = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
					querypr.setParameter("jornal", pre.getJournal());
					querypr.setParameter(1, prepaid.getOperationExpenses().getCodeId());
					Posting prepaidpost = null;
					try{
						prepaidpost = (Posting)querypr.getSingleResult();
					}
					catch(NoResultException ex){}
					BigDecimal allotamount = prepaidpost.getAmount();
					if(allotamount.compareTo(amtfrom) >= 0 && allotamount.compareTo(amtto) <= 0){
						temp.add("Prepaid");
						temp.add(prepaid.getSupplier().getCompanyName());
						temp.add(prepaid.getInvoiceNumber());
						temp.add(prepaid.getOperationExpenses().getCodeName());
						temp.add(pre.getPaymentDate());
						BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
						temp.add(allotamount.multiply(grossvat).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
						temp.add(pre.getPrepaidId());
						datas.add(temp);
					}
				}
			}
		}

		return objectWriter(datas);
	
	}
	
	public ArrayList<ArrayList<Object>> multipleExpensesResolutions(java.util.List<Object[]> ls){
		
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		
		for(Object[] obj : ls){
		
			PrepaidAllotment pre = null;
			PayableExpensesPayment pay = null;
			AccruedExpensesPayment sp = null;
			
			if(((String)obj[2]).equals("PaidExpensesInvoice")){
				ArrayList<Object> temp1 = new ArrayList<Object>();
				PaidExpensesInvoice paid = em.find(PaidExpensesInvoice.class, ((Integer)obj[1]).intValue());
				temp1.add("Paid");
				temp1.add(paid.getSupplier().getCompanyName());
				temp1.add(paid.getInvoiceNumber());
				temp1.add(paid.getOperationExpenses().getCodeName());
				temp1.add(paid.getInvoiceDate());
				Query querypa = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
				querypa.setParameter("jornal", paid.getJournal());
				querypa.setParameter(1, paid.getPaymentMethod().getCodeId());
				Posting paidpost = null;
				try{
					paidpost = (Posting)querypa.getSingleResult();
				}
				catch(NoResultException ex){}
				temp1.add(paidpost.getAmount().abs());
				temp1.add(paid.getExpensesInvoiceId());
				datas.add(temp1);		
					
			}
			if(((String)obj[2]).equals("PayableExpensesInvoice")){
		
				pay = em.find(PayableExpensesPayment.class, ((Integer)obj[1]).intValue());
				if(pay != null){
				
					PayableExpensesInvoice payable = em.find(PayableExpensesInvoice.class, pay.getExpensesPayableInvoice().getExpensesInvoiceId());
				
					ArrayList<Object> temp2 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");				
					cashquery.setParameter("jornal", pay.getJournal());
					Posting post = null;
					try{	
						post = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					
						temp2.add("Payable");
						temp2.add(payable.getSupplier().getCompanyName());
						temp2.add(payable.getInvoiceNumber());
						temp2.add(payable.getOperationExpenses().getCodeName());
						temp2.add(pay.getPaymentDate());
						temp2.add(post.getAmount().abs());
						temp2.add(pay.getExpensesPaymentId());
						datas.add(temp2);
					
					
				}
			}
			
			if(((String)obj[2]).equals("AccruedExpensesInvoice")){
				sp = em.find(AccruedExpensesPayment.class, ((Integer)obj[1]).intValue());
				if(sp != null){
					AccruedExpensesInvoice accrued = em.find(AccruedExpensesInvoice.class, sp.getExpensesAccruedInvoice().getExpensesInvoiceId());
					ArrayList<Object> temp3 = new ArrayList<Object>();
					Query cashquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal");
					cashquery.setParameter("jornal", sp.getJournal());				
					Posting postcash = null;
					try{	
						postcash = (Posting)cashquery.getSingleResult();
					}
					catch(NoResultException ex){}
					
					if(postcash.getCodeId().getCodeId() != 20010){
						temp3.add("Accrued");
						temp3.add(accrued.getSupplier().getCompanyName());
						temp3.add(sp.getInvoiceNumber());
						temp3.add(accrued.getOperationExpenses().getCodeName());
						temp3.add(sp.getPaymentDate());
						if(postcash != null){
							temp3.add(postcash.getAmount().abs());
						}

						temp3.add(sp.getAccruedPaymentId());
						datas.add(temp3);
					}
				
				}
			}	
			
			if(((String)obj[2]).equals("PrepaidExpensesInvoice")){
			
				ArrayList<Object> temp = new ArrayList<Object>();
				pre = em.find(PrepaidAllotment.class, ((Integer)obj[1]).intValue());
				
				if(pre != null){

					PrepaidExpensesInvoice prepaid = em.find(PrepaidExpensesInvoice.class, pre.getPrepaidExpenses().getExpensesInvoiceId());
	
					BigDecimal vatparcentage = BigDecimal.ZERO;
					vatparcentage = getVATInclusive(prepaid.getJournal(), prepaid.getPaymentMethod().getCodeId(), 70020);
			
					temp.add("Prepaid");
					temp.add(prepaid.getSupplier().getCompanyName());
					temp.add(prepaid.getInvoiceNumber());
					temp.add(prepaid.getOperationExpenses().getCodeName());
					temp.add(pre.getPaymentDate());
					Query querypr = em.createQuery("SELECT p FROM Posting  p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
					querypr.setParameter("jornal", pre.getJournal());
					querypr.setParameter(1, prepaid.getOperationExpenses().getCodeId());
					Posting prepaidpost = null;
					try{
						prepaidpost = (Posting)querypr.getSingleResult();
					}
					catch(NoResultException ex){}
					BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
					temp.add(prepaidpost.getAmount().multiply(grossvat).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
					temp.add(pre.getPrepaidId());
					datas.add(temp);
				}
			}
		}

		return datas;
	
	}
		
	/***DONE
		**paidEexpensesResolutionDetails bean method for viewing details payment information 
		**@parameter int
		**use by processed expensespaymentdetail in processed expenses tapped panel
	*/
	public byte[] paidExpensesResolutionDetails(int id){
		PaidExpensesInvoice paid = em.find(PaidExpensesInvoice.class, id);
		BigDecimal vatamount = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		
		Query queryvat = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
		queryvat.setParameter("jornal", paid.getJournal());
		
		try{
			vatamount = (BigDecimal)queryvat.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId=:code");
		queryamount.setParameter("jornal", paid.getJournal());
		queryamount.setParameter("code", paid.getOperationExpenses());
		
		try{
			amount = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}
		//ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		ArrayList<Object> temp = new ArrayList<Object>();
		temp.add(paid.getInvoiceDate());
		temp.add(amount);
		temp.add(vatamount);
		temp.add(paid.getPaymentMethod().getCodeName());
		temp.add(paid.getSupplier().getCompanyName());
		//temp.add(paid.getExpensesInvoiceId());
		return objectWriter(temp);
	}
	
	/***DONE
		**prepaidEexpensesResolutionDetails bean method for viewing details payment information 
		**@parameter int
		**use by processed expensespaymentdetail in processed expenses tapped panel
	*/
	public byte[] prepaidExpensesResolutionDetails(int id){
		PrepaidAllotment allot = em.find(PrepaidAllotment.class, id);
		BigDecimal vatamount = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal vatparcentage = BigDecimal.ZERO;
		
		PrepaidExpensesInvoice prepaid =  null;
		
		Query queryprepaid = em.createQuery("SELECT p.prepaidExpenses FROM PrepaidAllotment p WHERE p.prepaidId=?1");
		queryprepaid.setParameter(1, id);
		try{
			prepaid = (PrepaidExpensesInvoice)queryprepaid.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Query querypr = em.createQuery("SELECT p.amount FROM Posting  p WHERE p.journal=:jornal AND p.codeId=:code");
		querypr.setParameter("jornal", allot.getJournal());
		querypr.setParameter("code", prepaid.getOperationExpenses());
		
		try{
			amount = (BigDecimal)querypr.getSingleResult();
		}
		catch(NoResultException ex){}
		
						
		vatparcentage = getVATInclusive(prepaid.getJournal(), prepaid.getPaymentMethod().getCodeId(), 70020);
					
		//ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		ArrayList<Object> temp = new ArrayList<Object>();
		temp.add(allot.getPaymentDate());
		temp.add(amount);
		temp.add(amount.multiply(vatparcentage).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
		temp.add(prepaid.getPaymentMethod().getCodeName());
		temp.add(allot.getPeriod());
	//	temp.add(allot.getPrepaidId());
		return objectWriter(temp);
		
	}
	
	/***DONE
		**payableEexpensesResolutionDetails bean method for viewing details payment information 
		**@parameter int
		**use by processed expensespaymentdetail in processed expenses tapped panel
	*/
	public byte[] payableExpensesResolutionDetails(int id){
		PayableExpensesPayment payment = em.find(PayableExpensesPayment.class, id);
		BigDecimal vatparcentage = BigDecimal.ZERO;
		BigDecimal amountincl = BigDecimal.ZERO;
		
		Query querypayable = em.createQuery("SELECT p.expensesPayableInvoice FROM PayableExpensesPayment p WHERE p.expensesPaymentId = ?1");
		querypayable.setParameter(1, id);
		PayableExpensesInvoice payable = null;
		try{
			payable = (PayableExpensesInvoice)querypayable.getSingleResult();
		}
		catch(NoResultException ex){}
		
		vatparcentage = getVATExclusive(payable.getJournal(), payable.getOperationExpenses().getCodeId(), 70020);
				
		if(vatparcentage.compareTo(new BigDecimal("0")) == 0){
			AccruedExpensesInvoice accruedinvoice = null;
			Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
			accrued.setParameter("jornal",  payable.getJournal());
			try{
				accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
			}
			catch(NoResultException ex){}
				
			vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
				
		}
		
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal");
		queryamount.setParameter("jornal", payment.getJournal());
		try{
			amountincl = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}
				
		//ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		ArrayList<Object> temp = new ArrayList<Object>();
		BigDecimal gross = new BigDecimal("100").add(vatparcentage);

		temp.add(payment.getPaymentDate());
		temp.add(amountincl.abs().multiply(new BigDecimal("100")).divide(gross, DECIMALS, ROUNDING_MODE));
		temp.add(amountincl.abs().multiply(vatparcentage).divide(gross, DECIMALS, ROUNDING_MODE));
		temp.add(payment.getPaymentType().getCodeName());
		temp.add(payment.getNote());
		//temp.add(payment.getExpensesPaymentId());
		return objectWriter(temp);
		
	}
	
	/***DONE
		**accruedEexpensesResolutionDetails bean method for viewing details payment information 
		**@parameter int
		**use by processed expensespaymentdetail in processed expenses tapped panel
	*/
	public byte[] accruedExpensesResolutionDetails(int id){
		AccruedExpensesPayment accruedpay =  em.find(AccruedExpensesPayment.class, id);
		BigDecimal vatparcentage = BigDecimal.ZERO;
		BigDecimal amountincl = BigDecimal.ZERO;
		
		Query queryaccrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.accruedPaymentId = ?1");
		queryaccrued.setParameter(1, id);
		AccruedExpensesInvoice accrued = null;
		try{
			accrued = (AccruedExpensesInvoice)queryaccrued.getSingleResult();
		}
		catch(NoResultException ex){}
		
		vatparcentage = getVATExclusive(accrued.getJournal(), accrued.getOperationExpenses().getCodeId(), 70020);
		
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal");
		queryamount.setParameter("jornal", accruedpay.getJournal());
		try{
			amountincl = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}
		
		//ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		ArrayList<Object> temp = new ArrayList<Object>();
		BigDecimal gross = new BigDecimal("100").add(vatparcentage);
				
		temp.add(accruedpay.getPaymentDate());
		temp.add(amountincl.abs().multiply(new BigDecimal("100")).divide(gross, DECIMALS, ROUNDING_MODE));
		temp.add(amountincl.abs().multiply(vatparcentage).divide(gross, DECIMALS, ROUNDING_MODE));
		temp.add(accruedpay.getPaymentType().getCodeName());
		temp.add(accrued.getNote());
		//temp.add(accruedpay.getAccruedPaymentId());
		return objectWriter(temp);	
		
	}
	
	/***DONE
		**addExpensesPayable(byte[] expensePayable) bean method for inputing Suppliers General Expenses 
		**@parameter byte array expenseAccrued  as argument
		**populate PayableExpensesInvoice entity 
	*/
	public void addExpensesPayable(byte[] expensePayable){
		ArrayList<Object> expenses = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(expensePayable);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		PayableExpensesInvoice payableexp = new PayableExpensesInvoice();

		BigDecimal amount = new BigDecimal((String)expenses.get(4));
		String vat = (String)expenses.get(5);

		Journal jr = payableExpensesJornal(amount, ((Integer)expenses.get(1)).intValue(), vat);
		payableexp.setJournal(jr);
		payableexp.setSupplier(em.find(Supplier.class, ((Integer)expenses.get(0)).intValue()));
		payableexp.setInvoiceDate(java.sql.Date.valueOf((String)expenses.get(3)));
		payableexp.setInvoiceNumber((String)expenses.get(2));
		payableexp.setOperationExpenses(em.find(AccountCode.class, ((Integer)expenses.get(1)).intValue()));
		em.persist(payableexp);
	}
	
	/***payableExpensesJornal(BigDecimal amount, int codePaymentType, String vatstr) bean method for posting payable jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addExpensesPayable bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal payableExpensesJornal(BigDecimal amount, int codeExpenseType, String vatstr){
		Journal jr = new Journal();
		AccountCode codeOpExpenses = em.find(AccountCode.class, codeExpenseType); 
		AccountCode codePayable = em.find(AccountCode.class, 20010); 
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		//jr.setcodeIdCR(codePayable.getGroup());
		//jr.setcodeIdDR(codeOpExpenses.getGroup());
		jr.setPostManual(false);
	   
		//find the parcentage for vat
		Posting vat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
			
		//post  operationl expenses jornal
		Posting opExpenses = new Posting();
		opExpenses.setAmount(amount.subtract(vatAmount));
		opExpenses.setCodeId(codeOpExpenses);
		//opExpenses.setPostManual(false);
		postarray.add(opExpenses);
		opExpenses.setJournal(jr);
			
		//post accounts payable jornal
		Posting payable = new Posting();
		payable.setAmount(amount.negate());
		payable.setCodeId(codePayable);
		//payable.setPostManual(false);
		postarray.add(payable);
		payable.setJournal(jr);
			
		//post vat jornal
		if(vat != null){
			AccountCode codeVAT = em.find(AccountCode.class, 70020); 
			vat.setAmount(vatAmount);
			vat.setCodeId(codeVAT);
			//vat.setPostManual(false);
			postarray.add(vat);
			vat.setJournal(jr);
		}
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		return jr;
	}
	
	/***mutiplePayableInvoices bean method for searching multiple payable posting use by GeneralExpensesView  
		**no ragument parameter
		**return byte [] array 
	*/
	public ArrayList<ArrayList<Object>> mutiplePayableInvoices(java.util.List<PayableExpensesInvoice> ls){

		ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		for(PayableExpensesInvoice payable : ls){
		
			BigDecimal postamount =  BigDecimal.ZERO;
			BigDecimal creditamount =  BigDecimal.ZERO;
			BigDecimal creditamountvat = BigDecimal.ZERO;
			BigDecimal payableamount =  BigDecimal.ZERO;
			BigDecimal vatValue =  BigDecimal.ZERO;
			BigDecimal vat =  BigDecimal.ZERO;
			BigDecimal vatparcentage =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			
			temp.add(payable.getSupplier().getCompanyName());
			temp.add(payable.getInvoiceNumber());
			temp.add(payable.getOperationExpenses().getCodeName());
			temp.add(payable.getInvoiceDate());

			Query payablequery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
			payablequery.setParameter("jornal", payable.getJournal());
			payablequery.setParameter(1, payable.getOperationExpenses().getCodeId());
			try{
				payableamount = (BigDecimal)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query vatquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
	
			vatquery.setParameter("jornal",  payable.getJournal());
			try{
				
				vat = (BigDecimal)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			vatparcentage = getVATExclusive(payable.getJournal(), payable.getOperationExpenses().getCodeId(), 70020);
			
			if(payableamount.compareTo(new BigDecimal("0")) == 0){
				AccruedExpensesInvoice accruedinvoice = null;
				Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
				accrued.setParameter("jornal",  payable.getJournal());
				try{
					accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
				}
				catch(NoResultException ex){}
			
				Query accruedquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
				accruedquery.setParameter("jornal", accruedinvoice.getJournal());
				accruedquery.setParameter(1, accruedinvoice.getOperationExpenses().getCodeId());
				
				Query vataccruequery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
				vataccruequery.setParameter("jornal",  accruedinvoice.getJournal());
				try{
				
					vat = (BigDecimal)vataccruequery.getSingleResult();
					payableamount = (BigDecimal)accruedquery.getSingleResult();
				}
				catch(NoResultException ex){}
				
				vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
			
			}
			
			
			
			Query querypr = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal.journalId IN(SELECT a.journal.journalId FROM PayableExpensesPayment a WHERE a.expensesPayableInvoice=:payables)");

			querypr.setParameter("payables" , payable);

			try{
				postamount = (BigDecimal)querypr.getSingleResult();
			}
			catch(NoResultException ex){}

			Query querycreditnote = em.createQuery("SELECT SUM(c.amount) FROM CreditNote c WHERE c.creditNoteId IN(SELECT e.creditNoteId FROM  ExpensesPayableCreditNote e WHERE e.expensesPayableInvoice=:payablecredit)");
			querycreditnote.setParameter("payablecredit" , payable);
			
			try{
				creditamount = (BigDecimal)querycreditnote.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query querycreditnotevat = em.createQuery("SELECT SUM(c.vat) FROM CreditNote c WHERE c.creditNoteId IN(SELECT e.creditNoteId FROM  ExpensesPayableCreditNote e WHERE e.expensesPayableInvoice=:payablecredit)");
			querycreditnotevat.setParameter("payablecredit" , payable);
			
			try{
				creditamountvat = (BigDecimal)querycreditnotevat.getSingleResult();
			}
			catch(NoResultException ex){}
			
			//account payable total invoice amount 
			if(creditamount == null && vat == null){
				temp.add(payableamount.abs());
			}
			
			else if(creditamount != null && vat == null){
				temp.add(payableamount.abs().add(creditamount));
			
			}
			
			else if(creditamount == null && vat != null){
				temp.add(payableamount.abs().add(vat));
			}
			else{
				temp.add((payableamount.abs().add(vat)).add(creditamount));
			}
			
			//payment made toward expenses account payable
			BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
			if(postamount != null){
				vatValue = (postamount.abs().multiply(vatparcentage)).divide(grossvat, DECIMALS, ROUNDING_MODE);
			}

			
			if(creditamount == null && postamount == null){

				temp.add(BigDecimal.ZERO);
			}
			else if(postamount == null && creditamount != null){
	
				temp.add(creditamount.abs());
			}
			else if(postamount != null && creditamount == null){

				temp.add(postamount.abs());
			}
			else{
				temp.add(creditamount.abs().add(postamount.abs()));
			}
			int daydiff = days(payable.getInvoiceDate());
			String [] strterm = payable.getSupplier().getPaymentTerm().split("\\s+");
			int term = (Integer.valueOf(strterm[0])).intValue();
			if(daydiff < term){
				temp.add("0 days");
			}
			else{
				int diff = daydiff - term;
				String days = null;
					if(diff == 1)
						days = "day";
					else{
						days = "days";
					}
				temp.add(String.format("%d %s", diff, days));
			}

			temp.add(Integer.valueOf(payable.getExpensesInvoiceId()));
			datas.add(temp);
		}
		return datas;
	}
	
	public byte[] payableInvoicesSearch(Date from, Date to){
		Query query = em.createQuery("SELECT p FROM PayableExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<PayableExpensesInvoice> ls = (java.util.List<PayableExpensesInvoice>)query.getResultList();
		return objectWriter(mutiplePayableInvoices(ls));
	}

	public byte[] payableInvoicesSearch(Date from, Date to, int supplierId){
		Query query = em.createQuery("SELECT p FROM PayableExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.supplier.supplierId = ?1 AND e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, supplierId);
		query.setParameter(2, from);   
        query.setParameter(3, to);
		java.util.List<PayableExpensesInvoice> ls = (java.util.List<PayableExpensesInvoice>)query.getResultList();		
		return objectWriter(mutiplePayableInvoices(ls));
	}
	
	public byte[] payableInvoicesSearch(){
		java.util.List<PayableExpensesInvoice> ls = (java.util.List<PayableExpensesInvoice> )em.createQuery("SELECT p FROM PayableExpensesInvoice p").getResultList();
		return objectWriter(mutiplePayableInvoices(ls));
	}
	
	public byte[] payableInvoicesSearch(Date from, Date to, BigDecimal amtfrom, BigDecimal amtto){
	
		Query query = em.createQuery("SELECT p FROM PayableExpensesInvoice p WHERE p.expensesInvoiceId  IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?1 AND ?2)");
		query.setParameter(1, from);   
        query.setParameter(2, to);
		java.util.List<PayableExpensesInvoice> ls = (java.util.List<PayableExpensesInvoice>)query.getResultList();
		
		ArrayList<ArrayList<Object>> datas =  new ArrayList<ArrayList<Object>>();
		
		for(PayableExpensesInvoice payable : ls){
		
			BigDecimal postamount =  BigDecimal.ZERO;
			BigDecimal postedamount =  BigDecimal.ZERO;
			BigDecimal creditamount =  BigDecimal.ZERO;
			BigDecimal creditamountvat = BigDecimal.ZERO;
			BigDecimal payableamount =  BigDecimal.ZERO;
			BigDecimal totalpayable =  BigDecimal.ZERO;
			BigDecimal vatValue =  BigDecimal.ZERO;
			BigDecimal vat =  BigDecimal.ZERO;
			BigDecimal vatparcentage =  BigDecimal.ZERO;
			ArrayList<Object> temp = new ArrayList<Object>();
			
			//account payable total invoice amount 
			Query payablequery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
			payablequery.setParameter("jornal", payable.getJournal());
			payablequery.setParameter(1, payable.getOperationExpenses().getCodeId());
			try{
				payableamount = (BigDecimal)payablequery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query vatquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
	
			vatquery.setParameter("jornal",  payable.getJournal());
			try{
				
				vat = (BigDecimal)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			vatparcentage = getVATExclusive(payable.getJournal(), payable.getOperationExpenses().getCodeId(), 70020);
			
			if(payableamount.compareTo(new BigDecimal("0")) == 0){
				AccruedExpensesInvoice accruedinvoice = null;
				Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
				accrued.setParameter("jornal",  payable.getJournal());
				try{
					accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
				}
				catch(NoResultException ex){}
			
				Query accruedquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=?1");
				accruedquery.setParameter("jornal", accruedinvoice.getJournal());
				accruedquery.setParameter(1, accruedinvoice.getOperationExpenses().getCodeId());
				
				Query vataccruequery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
				vataccruequery.setParameter("jornal",  accruedinvoice.getJournal());
				try{
				
					vat = (BigDecimal)vataccruequery.getSingleResult();
					payableamount = (BigDecimal)accruedquery.getSingleResult();
				}
				catch(NoResultException ex){}
				
				vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
			
			}
	
			if(creditamount == null && vat == null){
			//	temp.add(payableamount.abs());
				totalpayable = payableamount.abs();
			}
			
			else if(creditamount != null && vat == null){
			//	temp.add(payableamount.abs().add(creditamount));
				totalpayable = payableamount.abs().add(creditamount);
			
			}
			
			else if(creditamount == null && vat != null){
				//temp.add(payableamount.abs().add(vat));
				totalpayable = payableamount.abs().add(vat);
			}
			else{
				//temp.add((payableamount.abs().add(vat)).add(creditamount));
				totalpayable = (payableamount.abs().add(vat)).add(creditamount);
			}
			
			
			//payment made toward expenses account payable
			Query querypr = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal.journalId IN(SELECT a.journal.journalId FROM PayableExpensesPayment a WHERE a.expensesPayableInvoice=:payables)");

			querypr.setParameter("payables" , payable);

			try{
				postamount = (BigDecimal)querypr.getSingleResult();
			}
			catch(NoResultException ex){}

			Query querycreditnote = em.createQuery("SELECT SUM(c.amount) FROM CreditNote c WHERE c.creditNoteId IN(SELECT e.creditNoteId FROM  ExpensesPayableCreditNote e WHERE e.expensesPayableInvoice=:payablecredit)");
			querycreditnote.setParameter("payablecredit" , payable);
			
			try{
				creditamount = (BigDecimal)querycreditnote.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query querycreditnotevat = em.createQuery("SELECT SUM(c.vat) FROM CreditNote c WHERE c.creditNoteId IN(SELECT e.creditNoteId FROM  ExpensesPayableCreditNote e WHERE e.expensesPayableInvoice=:payablecredit)");
			querycreditnotevat.setParameter("payablecredit" , payable);
			
			try{
				creditamountvat = (BigDecimal)querycreditnotevat.getSingleResult();
			}
			catch(NoResultException ex){}
			
			BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
			if(postamount != null){
				vatValue = (postamount.abs().multiply(vatparcentage)).divide(grossvat, DECIMALS, ROUNDING_MODE);
			}

			
			if(creditamount == null && postamount == null){
				
				//temp.add(BigDecimal.ZERO);
				postedamount = BigDecimal.ZERO;
			}
			else if(postamount == null && creditamount != null){

				//temp.add(creditamount.abs());
				postedamount = creditamount.abs();
				
			}
			else if(postamount != null && creditamount == null){

				//temp.add(postamount.abs());
				postedamount = postamount.abs();
			}
			else{

				//temp.add(creditamount.abs().add(postamount.abs()));
				postedamount = creditamount.abs().add(postamount.abs());
			}
			
			if(payableamount.compareTo(amtfrom) >= 0 && payableamount.compareTo(amtto) <= 0){
				temp.add(payable.getSupplier().getCompanyName());
				temp.add(payable.getInvoiceNumber());
				temp.add(payable.getOperationExpenses().getCodeName());
				temp.add(payable.getInvoiceDate());
			
				//account payable total invoice amount 
				temp.add(totalpayable);
				//payment made toward expenses account payable
				temp.add(postedamount);

				int daydiff = days(payable.getInvoiceDate());
				String [] strterm = payable.getSupplier().getPaymentTerm().split("\\s+");
				int term = (Integer.valueOf(strterm[0])).intValue();
				if(daydiff < term){
					temp.add("0 days");
				}
				else{
					int diff = daydiff - term;
					String days = null;
						if(diff == 1)
							days = "day";
						else{
							days = "days";
						}
					temp.add(String.format("%d %s", diff, days));
				}

				temp.add(Integer.valueOf(payable.getExpensesInvoiceId()));
				datas.add(temp);
			}
		}
		return objectWriter(datas);
		
	}
	
	
	public byte[] payableInvoicesSearch(Date from, Date to, String invoice){
		
		Query query = em.createQuery("SELECT p FROM PayableExpensesInvoice p WHERE p.invoiceNumber = ?1 AND  p.expensesInvoiceId IN(SELECT  e.expensesInvoiceId  FROM ExpensesInvoice e WHERE e.invoiceDate  BETWEEN ?2 AND ?3)");
		query.setParameter(1, invoice);
		query.setParameter(2, from);   
        query.setParameter(3, to);
		java.util.List<PayableExpensesInvoice> ls = (java.util.List<PayableExpensesInvoice>)query.getResultList();		
		return objectWriter(mutiplePayableInvoices(ls));
	}
	
	
	/***getPayableBalance(int payableId) bean method for automate remain balance on accounts payable 
		**@payable id as parameter and return byte[] array of payable balance
		**use by PayableExpensesPayment GUI
	*/
	public byte[] getPayableBalance(int payableId){
		BigDecimal payableamount =  BigDecimal.ZERO;

		PayableExpensesInvoice pay = em.find(PayableExpensesInvoice.class, payableId);
		Query queryposting = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal and p.codeId.codeId = 20010");
		queryposting.setParameter("jornal", pay.getJournal());
		
		try{
			payableamount = (BigDecimal)queryposting.getSingleResult();
		}
		catch(NoResultException ex){}
		return objectWriter(payableamount.abs());
	}
	
	/***getPayableBalance(Integer payableId) bean method to get gross payable balance 
		**@payable id as parameter and return byte[] array of payable balance
		**use by ExpensesPaymentDetails GUI
	*/
	public byte[] getPayableBalance(Integer payableId){

		BigDecimal payableamount =  BigDecimal.ZERO;

		PayableExpensesInvoice pay = em.find(PayableExpensesInvoice.class, payableId.intValue());
		
		Query queryposting = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal and p.codeId.codeId = 20010");
		queryposting.setParameter("jornal", pay.getJournal());
		
		try{
			payableamount = (BigDecimal)queryposting.getSingleResult();
		}
		catch(NoResultException ex){}
		
		return objectWriter(payableamount.abs());
	
	}
	
	/**DONE
		**method getExpensesPayableDetails(int payableId) get individual payment detail for single invoice
		**return byte array of payment details
		**use by ExpensesPayableDetails GUI 
	*/
	public byte[] getExpensesPayableDetails(int payableId){

		PayableExpensesInvoice expenses = em.find(PayableExpensesInvoice.class, payableId);
		Query creditnotequery = em.createQuery("SELECT c FROM ExpensesPayableCreditNote c WHERE c.expensesPayableInvoice = :expensesPayableInvoice");
		creditnotequery.setParameter("expensesPayableInvoice", expenses);
		java.util.List<ExpensesPayableCreditNote> cn = (java.util.List<ExpensesPayableCreditNote>)creditnotequery.getResultList();
		
		Query paymentquery = em.createQuery("SELECT p FROM PayableExpensesPayment p WHERE p.expensesPayableInvoice = :expensesPayableInvoice");
		paymentquery.setParameter("expensesPayableInvoice", expenses);
		java.util.List<PayableExpensesPayment> pay = (java.util.List<PayableExpensesPayment>)paymentquery.getResultList();
		ArrayList<ArrayList<Object>> supplierpay = new ArrayList<ArrayList<Object>>();
		BigDecimal vatparcentage = BigDecimal.ZERO;
		BigDecimal grossvat = BigDecimal.ZERO;
		
		vatparcentage = getVATExclusive(expenses.getJournal(), expenses.getOperationExpenses().getCodeId(), 70020);
		grossvat = vatparcentage.add(new BigDecimal("100"));
		if(vatparcentage.compareTo(new BigDecimal("0")) == 0){
			AccruedExpensesInvoice accruedinvoice = null;
			Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
			accrued.setParameter("jornal",  expenses.getJournal());
			try{
				accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query accruedquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = 70020");
			Posting accruedvat = null;
			accruedquery.setParameter("jornal",  accruedinvoice.getJournal());
			try{
				accruedvat = (Posting)accruedquery.getSingleResult();
			}
			catch(NoResultException ex){}
				
			vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
			grossvat = vatparcentage.add(new BigDecimal("100"));
		
		}
		
		for(PayableExpensesPayment payment : pay){
			ArrayList<Object> temp = new ArrayList<Object>();
			BigDecimal amount =  BigDecimal.ZERO;
			Journal paymentjornal = payment.getJournal();
			Query cashquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId = :codeId");				
			cashquery.setParameter("jornal", paymentjornal);
			cashquery.setParameter("codeId", payment.getPaymentType());
						
			try{
				 amount = (BigDecimal)cashquery.getSingleResult();
			}
			catch(NoResultException ex){}
			
				temp.add(payment.getPaymentDate());
				temp.add(amount.abs().multiply(new BigDecimal("100")).divide(grossvat, DECIMALS, ROUNDING_MODE));
				temp.add((vatparcentage.multiply(amount.abs())).divide(grossvat, DECIMALS, ROUNDING_MODE));
				temp.add(payment.getType());
				temp.add(payment.getNote());
				temp.add(Integer.valueOf(payment.getExpensesPaymentId()));
				supplierpay.add(temp);
			
		}
		
		for(ExpensesPayableCreditNote note : cn){
			ArrayList<Object> temp1 = new ArrayList<Object>();
			temp1.add(note.getIssueDate());	
			temp1.add(note.getAmount().subtract(note.getVat())); 	
			temp1.add(note.getVat());			
			temp1.add("Credit Note");				
			temp1.add(note.getCNnumber());	
			temp1.add(Integer.valueOf(note.getCreditNoteId()));
			supplierpay.add(temp1);			
			
		}
		return objectWriter(supplierpay);
	}
	
	/***addPayableExpensesPayment(byte[] payable) bean method for posting payment made on expenses payable  
		**@byte[] payable  as arguments
		**update payable and insert new payment
	*/
	public void addPayableExpensesPayment(byte[] payable){
		ArrayList<String> expenses = new ArrayList<String>();
		ByteArrayInputStream bais = new ByteArrayInputStream(payable);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			expenses = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		int payableid = (Integer.valueOf(expenses.get(0))).intValue();
		BigDecimal amount = new BigDecimal(expenses.get(1));
		int codePaymentType = (Integer.valueOf(expenses.get(2))).intValue();
		String info = expenses.get(4);
		String paymentdate = expenses.get(5);
		
		Journal jr = payableExpensesPaymentJornal(amount, payableid, codePaymentType, info, paymentdate);
		
		if(codePaymentType != 99999){
			PayableExpensesPayment pay =  new PayableExpensesPayment();
			pay.setType(expenses.get(3));
			pay.setNote(info);
			pay.setExpensesPayableInvoice(em.find(PayableExpensesInvoice.class, payableid));
			pay.setPaymentDate(java.sql.Date.valueOf(paymentdate));
			pay.setPaymentType(em.find(AccountCode.class, codePaymentType));
			pay.setJournal(jr);
			em.persist(pay);
		}
	
	}
	
	/***
		updateExpensesPayable(BigDecimal , BigDecimal , PayableExpensesInvoice ) bean method to make adjustment for accounts payable and expenses account
		when credit note is issued
		call from creditNoteJornal
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void updateExpensesPayable(BigDecimal vatAmount, BigDecimal amount, PayableExpensesInvoice payable){
	
		ArrayList<Posting> post = new ArrayList<Posting>();
		Journal previour = previour = payable.getJournal();
		
		//old expense posted
		Query expensesquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
		expensesquery.setParameter(1, payable.getOperationExpenses().getCodeId());
		expensesquery.setParameter("jornal",  previour);
		Posting updateExpenses = null;
		try{
			updateExpenses = (Posting)expensesquery.getSingleResult();
		}
		catch(NoResultException ex){}
		if(updateExpenses != null){
			updateExpenses.setAmount(updateExpenses.getAmount().subtract(amount.subtract(vatAmount)));   //credit note take away cn vat minus old material value
			post.add(updateExpenses);
			updateExpenses.setJournal(previour);
		}
				//a null expenses implies expenses is stock with accrued, so get the accrued expenses
		else{
			accruedToPayable(vatAmount, amount, payable, post);
			
		}
		
		//old account payable posted
		Query updatepayable = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:journal AND p.codeId.codeId=20010");
		updatepayable.setParameter("journal", previour);
		Posting payablepost = null;
		try{
			payablepost = (Posting)updatepayable.getSingleResult();
		}
		catch(NoResultException ex){}
		
		/*updateExpenses.setAmount(updateExpenses.getAmount().subtract(amount.subtract(vatAmount)));   //credit note take away cn vat minus old material value
		post.add(updateExpenses);
		updateExpenses.setJournal(previour);*/
			
		payablepost.setAmount(payablepost.getAmount().add( amount));
		post.add(payablepost);
		payablepost.setJournal(previour);
			
		previour.setPostingdrcr(post);
		em.persist(previour);	
	
	}
	
	/***
		accruedToPayable(vatAmount, amount, payable, post) bean method to assist updateExpensesPayable when accrued is changed to payable
		call from updateExpensesPayable
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void accruedToPayable(BigDecimal vatAmount, BigDecimal amount, PayableExpensesInvoice payable, ArrayList<Posting> post){
			AccruedExpensesInvoice accruedinvoice = null;
			Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
			accrued.setParameter("jornal",  payable.getJournal());
			try{
				accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
			}
			catch(NoResultException ex){}
			Journal previour = accruedinvoice.getJournal();
			Query accruedquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = ?1");
			accruedquery.setParameter(1, accruedinvoice.getOperationExpenses().getCodeId());
			accruedquery.setParameter("jornal",  previour);
			Posting updateExpenses = null;
			try{
				updateExpenses = (Posting)accruedquery.getSingleResult();
			}
			catch(NoResultException ex){}
			updateExpenses.setAmount(updateExpenses.getAmount().subtract(amount.subtract(vatAmount)));   //credit note take away cn vat minus old material value
			post.add(updateExpenses);
			updateExpenses.setJournal(previour);
			previour.setPostingdrcr(post);
			em.persist(previour);
	}
	
	/***
		creditNoteJornal(BigDecimal , String , String , PayableExpensesInvoice ) bean method to add expenses credit note
		call from payableExpensesPaymentJornal
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal creditNoteJornal(BigDecimal creditNoteAmount, String creditNoteNumber, String dateIssued, PayableExpensesInvoice payable){
			ExpensesPayableCreditNote creditnote = new ExpensesPayableCreditNote();
			BigDecimal vatparcentage =  BigDecimal.ZERO;
			BigDecimal vatcn =  BigDecimal.ZERO;
			ArrayList<Posting> post = new ArrayList<Posting>();
			Journal oldjr =  payable.getJournal();
			
			creditnote.setAmount(creditNoteAmount);
			creditnote.setCNnumber(creditNoteNumber);
			creditnote.setIssueDate(java.sql.Date.valueOf(dateIssued));
			creditnote.setExpensesPayableInvoice(payable);
			
			//old vat posting
			Query vatquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId=70020");
			Posting updatevat = null;
			vatquery.setParameter("jornal", oldjr);
			try{
				
				updatevat = (Posting)vatquery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			//calculate vat if any
			if(updatevat != null){

				vatparcentage = getVATExclusive(payable.getJournal(), payable.getOperationExpenses().getCodeId(), 70020);
				BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
				vatcn = (vatparcentage.multiply(creditNoteAmount)).divide(grossvat, DECIMALS, ROUNDING_MODE);
				
				updatevat.setAmount(updatevat.getAmount().subtract(vatcn));
				post.add(updatevat);
				updatevat.setJournal(oldjr);
			}
			//payable from accrued expenses
			else{
				AccruedExpensesInvoice accruedinvoice = null;
				Query accrued = em.createQuery("SELECT a.expensesAccruedInvoice FROM AccruedExpensesPayment a WHERE a.journal=:jornal");
				accrued.setParameter("jornal",  payable.getJournal());
				try{
					accruedinvoice = (AccruedExpensesInvoice)accrued.getSingleResult();
				}
				catch(NoResultException ex){}
			
				Query accruedquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:jornal AND p.codeId.codeId = 70020");
				Posting accruedvat = null;
				accruedquery.setParameter("jornal",  accruedinvoice.getJournal());
				try{
					accruedvat = (Posting)accruedquery.getSingleResult();
				}
				catch(NoResultException ex){}
				
				vatparcentage = getVATExclusive(accruedinvoice.getJournal(), accruedinvoice.getOperationExpenses().getCodeId(), 70020);
				BigDecimal grossvat = vatparcentage.add(new BigDecimal("100"));
				vatcn = (vatparcentage.multiply(creditNoteAmount)).divide(grossvat, DECIMALS, ROUNDING_MODE);
				
				accruedvat.setAmount(accruedvat.getAmount().subtract(vatcn));
				post.add(accruedvat);
				accruedvat.setJournal(accruedinvoice.getJournal());
			}
			//update expenses when credit note is issued
			updateExpensesPayable(vatcn, creditNoteAmount, payable);
			
			creditnote.setVat(vatcn);
			em.persist(creditnote);
			
			return null;
	}		
	
	/***payableExpensesPaymentJornal(BigDecimal amount, int payableid, int codePaymentType, String vatstr) bean method for posting accrued jornal  
		**@parameternew BigDecimal amount and int codeExpenses  as arguments
		**return Jornal for addPayableExpensesPayment bean method
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal payableExpensesPaymentJornal(BigDecimal amount, int payableid, int codePaymentType, String info, String paymentdate){
		Query querypayable = em.createQuery("SELECT p FROM PayableExpensesInvoice p WHERE p.expensesInvoiceId = ?1");
		querypayable.setParameter(1, payableid);
		
		Journal jr = new Journal();
		ArrayList<Posting> post = new ArrayList<Posting>();
		ArrayList<Posting> settlement = new ArrayList<Posting>();
		
		PayableExpensesInvoice payableinvoice = null;
		try{
			payableinvoice = (PayableExpensesInvoice)querypayable.getSingleResult();
		}
		catch(NoResultException ex){}
		
		Journal payablejr = payableinvoice.getJournal();
		
		if(codePaymentType == 99999){
			//credit note expenses account payable posting
			return creditNoteJornal(amount, info, paymentdate, payableinvoice);
		}
		
		else{
			//old posted account payable
			Query updatepayable = em.createQuery("SELECT p FROM Posting p WHERE p.journal=:journal AND p.codeId.codeId=20010");
			updatepayable.setParameter("journal", payablejr);
			Posting payablepost = null;
			try{
				payablepost = (Posting)updatepayable.getSingleResult();
			}
			catch(NoResultException ex){}
			
			//update account payable
			payablepost.setAmount(payablepost.getAmount().add(amount));
			post.add(payablepost);
			payablepost.setJournal(payablejr);
			payablejr.setPostingdrcr(post);
			AccountCode debit =  payablepost.getCodeId(); 
			jr.setCodeDR(String.valueOf(debit.getGroup().getGroupId()) + ".");
		
			//post new payment method agaist account payable
			AccountCode from = em.find(AccountCode.class, codePaymentType);
			Posting ps = new Posting();
			ps.setCodeId(from);
			ps.setAmount(amount.negate());
			settlement.add(ps);
			ps.setJournal(jr);
			jr.setPostingdrcr(settlement);
			jr.setCodeCR(String.valueOf(from.getGroup().getGroupId()) + ".");
			em.persist(jr);
			
			return jr;
		
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////End General Expenses
	
	////////////////////////////////////////////////////////////////////////////////////////////////////// Project Users 
	//SEEN
	public byte[] projectUserInfos(){
		java.util.List<ProjectUsers> ls = (java.util.List<ProjectUsers>)em.createQuery("SELECT p FROM ProjectUsers p").getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(ProjectUsers user : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(Integer.valueOf(user.getProjectUsersId()));
			temp.add(user.getUserName());
			temp.add(user.getUserPassword());
			//temp.add(user.getUserGroup());
			temp.add(Boolean.valueOf(user.getUserActive()));
			temp.add(user.getIssueDate());
			ArrayList<Object> userModel = userModelType(user);
			temp.add((ArrayList<String>)userModel.get(1));
			datas.add(temp);
		}
		return objectWriter(datas);
	}
	//SEEN
	public byte[] getSingleUserInfos(int userId){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		try{
			Query query = em.createQuery("SELECT p FROM ProjectUsers p WHERE p.projectUsersId = ?1 ");
			query.setParameter(1, userId);
			ProjectUsers user = (ProjectUsers)query.getSingleResult();
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(Integer.valueOf(user.getProjectUsersId()));
			temp.add(user.getUserName());
			temp.add(user.getUserPassword());
			//temp.add(user.getUserGroup());
			temp.add(Boolean.valueOf(user.getUserActive()));
			temp.add(user.getIssueDate());

			datas.add(temp);
		}
		catch(NoResultException ex){}
		return objectWriter(datas);
	}
	
	//SEEN
	public void updatePassword(byte[] users){
		ArrayList<Object> userInfo = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(users);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			userInfo = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		ProjectUsers user = em.find(ProjectUsers.class, ((Integer)userInfo.get(0)).intValue());
		user.setUserPassword(String.valueOf(userInfo.get(1)));
		em.persist(user);
	}
	
	public ArrayList<Object> userModelType(ProjectUsers user){
		ArrayList<Object> datas = new ArrayList<Object>();
		Query query = em.createQuery("SELECT u FROM UserModel u WHERE u.projectUser =:users");
		query.setParameter("users", user);
		UserModel model = null;
		try{
			model = (UserModel)query.getSingleResult();
		}
		catch(NoResultException ex){}
		datas.add(model.getUserModelId());
		ArrayList<ModelType> modelType = new ArrayList<ModelType>(model.getModelType());
		ArrayList<String> strTemp = new ArrayList<String>();
		for(ModelType type : modelType){
				
			strTemp.add(type.getModelStringType());
		}
		datas.add(strTemp);
		return datas;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////// End Project Users
	
	//////////////////////////////////////////////////////////////////////////////////////////////////// Manual Posting
	
	/***
		**bean method addManualPosting(byte[]) , insert manual posting datas into database 
		**takes byte[] array as argument
		**uses by adddoubleentry GUI
	*/
	
	public void addManualPosting(byte[] postings){
		ArrayList<Object> manual = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(postings);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			manual = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		ManualPosting posting = new ManualPosting();
		ArrayList<String> amount = (ArrayList<String>)manual.get(0);
		ArrayList<String> codes = (ArrayList<String>)manual.get(1);
		String type = (String)manual.get(4);
		Journal jr =  manualJornal(amount, codes, type);
		posting.setJournal(jr);
		posting.setPrincipleCode(em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue()));
		posting.setPostNote((String)manual.get(3));
		posting.setPostDate(java.sql.Date.valueOf((String)manual.get(2)));
		em.persist(posting);
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal manualJornal(ArrayList<String> amount, ArrayList<String> codes, String type){
		Journal jr = new Journal();
		ArrayList<Posting> post = new ArrayList<Posting>(); 

		BigDecimal amount1 = new BigDecimal(amount.get(0));
		BigDecimal amount2 = new BigDecimal(amount.get(1));
		
		jr.setPostManual(true);
		
		//the originator of the posting
		AccountCode from = em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue());
		Posting ps1 = new Posting();
		ps1.setCodeId(from);
		if(type.equals("Debit")){
			ps1.setAmount(amount1.add(amount2));
			jr.setCodeDR(String.valueOf(from.getGroup().getGroupId()) + ".");
		}
		else{
			ps1.setAmount(amount1.add(amount2).negate());
			jr.setCodeCR(String.valueOf(from.getGroup().getGroupId()) + ".");
		}
		//ps1.setPostManual(true);
		post.add(ps1);
		ps1.setJournal(jr);
		jr.setPostingdrcr(post);
		
		//the reciprocate posting
		AccountCode to = em.find(AccountCode.class, (Integer.valueOf(codes.get(1))).intValue());
		Posting ps2 = new Posting();
		ps2.setCodeId(to);
		if(type.equals("Debit"))
			ps2.setAmount(amount1.negate());
		else{
			ps2.setAmount(amount1);
		}
		//ps2.setPostManual(true);
		post.add(ps2);
		ps2.setJournal(jr);
		jr.setPostingdrcr(post);
		
		if(codes.get(2) != null && amount2.signum() == 1){
			AccountCode toOther = em.find(AccountCode.class, (Integer.valueOf(codes.get(2))).intValue());
			Posting ps3 = new Posting();
			ps3.setCodeId(toOther);
			if(type.equals("Debit")){
				ps3.setAmount(amount2.negate());
				jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
			}
			else{
				ps3.setAmount(amount2);
				jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
			}
			//ps3.setPostManual(true);
			post.add(ps3);
			ps3.setJournal(jr);
			jr.setPostingdrcr(post);
			
		}
		else{
			if(type.equals("Debit"))
				jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + ".");

			else{
				jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + ".");
			}
		}
		em.persist(jr);
		return jr;
	}
	
	/***
		**bean helper method manualPostingSearch(java.util.List<ManualPosting> ls) , retrieve manual posting data from database
		**takes List as argument
		**return arraylist of arralist
	*/
	public ArrayList<ArrayList<Object>>  manualPostingSearch(java.util.List<ManualPosting> ls){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(ManualPosting manual : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			AccountCode code = em.find(AccountCode.class, manual.getPrincipleCode().getCodeId());
			BigDecimal amount = BigDecimal.ZERO;
			Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId=:codes");
			queryamount.setParameter("jornal", manual.getJournal());
			queryamount.setParameter("codes", code);
			try{
				amount = (BigDecimal)queryamount.getSingleResult();
			}
			catch(NoResultException ex){}
			
			temp.add(code.getCodeId());
			temp.add(code.getCodeName());
			if(amount.signum() == -1){
				temp.add(amount.abs());
				temp.add("Credit");
			}
			else{
				temp.add(amount);
				temp.add("Debit");
			}
			temp.add(manual.getPostDate());
			temp.add(Integer.valueOf(manual.getManualPostingId()));
			datas.add(temp);
		}
		return datas;
	}
	public byte[] manualPostingSearch(){
	
		java.util.List<ManualPosting> ls = (java.util.List<ManualPosting>)em.createQuery("SELECT m FROM ManualPosting m").getResultList();
		return objectWriter(manualPostingSearch(ls));
	}
	public byte[] manualPostingSearch(String datefrom, String dateto){
		Query query = em.createQuery("SELECT m FROM ManualPosting m WHERE m.postDate BETWEEN ?1 AND ?2");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<ManualPosting> ls = (java.util.List<ManualPosting>)query.getResultList();
		return objectWriter(manualPostingSearch(ls));
	
	}
	public byte[] manualPostingSearch(String datefrom, String dateto, String accountsName){
		Query query = em.createQuery("SELECT m FROM ManualPosting m WHERE m.postDate BETWEEN ?1 AND ?2 AND m.principleCode.codeName=:name");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter("name", accountsName);
		java.util.List<ManualPosting> ls = (java.util.List<ManualPosting>)query.getResultList();
		return objectWriter(manualPostingSearch(ls));
	}
	public byte[] manualPostingSearch(String datefrom, String dateto, int accountsCode){ 
		Query query = em.createQuery("SELECT m FROM ManualPosting m WHERE m.postDate BETWEEN ?1 AND ?2 AND m.principleCode.codeId = ?3");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter(3, accountsCode);
		java.util.List<ManualPosting> ls = (java.util.List<ManualPosting>)query.getResultList();
		return objectWriter(manualPostingSearch(ls));
	}
	
	/***
		**bean method editManualPosting(int postedId) , retrieve manual posting data for single data from database
		**takes id of manual posted data
		**return byte array
	*/
	public byte[] editableManualPosting(int postedId){
	
		LinkedHashMap<String,BigDecimal> postedCodes = new LinkedHashMap<String,BigDecimal>();
		LinkedHashMap<Integer,String> codes = new LinkedHashMap<Integer,String>();
		ArrayList<Object> datas = new ArrayList<Object>();
		BigDecimal amount = BigDecimal.ZERO;
		
		Query queryPost = em.createQuery("SELECT p.codeId.codeId, p.amount FROM Posting p, ManualPosting a WHERE p.journal.journalId = a.journal.journalId AND a.manualPostingId = ?1");
		queryPost.setParameter(1, postedId);
		java.util.List<Object[]> postedCode = (java.util.List<Object[]>)queryPost.getResultList();
		
		ManualPosting manual = em.find(ManualPosting.class, postedId);
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId=:codes");
		queryamount.setParameter("jornal", manual.getJournal());
		queryamount.setParameter("codes", manual.getPrincipleCode());
		try{
			amount = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}
			
		AccountCode principle = em.find(AccountCode.class, manual.getPrincipleCode().getCodeId());

		postedCodes.put(String.valueOf(principle.getCodeId())+"."+principle.getCodeName(), amount.abs());
		
		for(Object[] i : postedCode){
			if(((Integer)i[0]).intValue() != manual.getPrincipleCode().getCodeId()){
			
				AccountCode others = em.find(AccountCode.class, ((Integer)i[0]).intValue());
				BigDecimal otherAmount = ((BigDecimal)i[1]).abs();
				postedCodes.put(String.valueOf(others.getCodeId())+"."+others.getCodeName(), otherAmount);
				
			}
		}
		datas.add(postedCodes);
		
		java.util.List<AccountCode> ls = (java.util.List<AccountCode>)em.createQuery("SELECT a FROM AccountCode a WHERE a.manualPosting=1 ORDER BY a.codeName ASC").getResultList();
		for(AccountCode code : ls){
			codes.put(Integer.valueOf(code.getCodeId()), code.getCodeName());
		}
		datas.add(codes);
		datas.add(manual.getPostNote());
		return objectWriter(datas);
	}
	
	/***
		**bean method editManualPosting(byte[]) , edit manual posted entity 
		**takes byte[] array as argument
		**uses by editdoubleentry GUI
	*/
	public void editManualPosting(byte[] posted){
		ArrayList<Object> manual = new ArrayList<Object>();
		ByteArrayInputStream bais = new ByteArrayInputStream(posted);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			manual = (ArrayList<Object>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		ManualPosting posting = em.find(ManualPosting.class, ((Integer)manual.get(5)).intValue());
		ArrayList<String> amount = (ArrayList<String>)manual.get(0);
		ArrayList<String> codes = (ArrayList<String>)manual.get(1);
		String type = (String)manual.get(4);
		Journal jr =  manualJornal(amount, codes, type, posting.getJournal().getJournalId());
		posting.setJournal(jr);
		posting.setPrincipleCode(em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue()));
		posting.setPostNote((String)manual.get(3));
		posting.setPostDate(java.sql.Date.valueOf((String)manual.get(2)));
		em.persist(posting);
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal manualJornal(ArrayList<String> amount, ArrayList<String> codes, String type, int jornalId){
		int postedSize = em.find(Journal.class, jornalId).getPostingdrcr().size();
		if(postedSize == codes.size()){
			return manualJornalUpdate1(amount, codes, type, jornalId);
		}
		
		else if(postedSize > codes.size()){
			return manualJornalUpdate2(amount, codes, type, jornalId);
		}
		
		else{
			return manualJornalUpdate3(amount, codes, type, jornalId);
		}
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal manualJornalUpdate1(ArrayList<String> amount, ArrayList<String> codes, String type, int jornalId){
		Journal jr = em.find(Journal.class, jornalId);
		ArrayList<Posting> posted = new ArrayList<Posting>(jr.getPostingdrcr());
		ArrayList<Posting> newPosting = new ArrayList<Posting>();
		BigDecimal amount1 = new BigDecimal(amount.get(0));
		BigDecimal amount2 = new BigDecimal(amount.get(1));
		
		for(int i = 0; i < posted.size(); i++){
			Posting ps = posted.get(i);
			AccountCode to = null;
			if(i == 0){
				AccountCode from = em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue());
				ps.setCodeId(from);
				if(type.equals("Debit")){
					ps.setAmount(amount1.add(amount2));
					jr.setCodeDR(String.valueOf(from.getGroup().getGroupId()) + ".");
				}
				else{
					ps.setAmount(amount1.add(amount2).negate());
					jr.setCodeCR(String.valueOf(from.getGroup().getGroupId()) + ".");
				}
			//	ps.setPostManual(true);
				newPosting.add(ps);
				ps.setJournal(jr);
				jr.setPostingdrcr(newPosting);
			}
			
			else if(i == 1){
				to = em.find(AccountCode.class, (Integer.valueOf(codes.get(1))).intValue());
				ps.setCodeId(to);
				if(type.equals("Debit")){
					ps.setAmount(amount1.negate());
					jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + ".");
				}
				else{
					ps.setAmount(amount1);
					jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + ".");
				}
				//ps.setPostManual(true);
				newPosting.add(ps);
				ps.setJournal(jr);
				jr.setPostingdrcr(newPosting);
			}
			
			else if(i == 2){
				AccountCode toOther = em.find(AccountCode.class, (Integer.valueOf(codes.get(2))).intValue());
				to = em.find(AccountCode.class, (Integer.valueOf(codes.get(1))).intValue());
				ps.setCodeId(toOther);
				if(type.equals("Debit")){
					ps.setAmount(amount2.negate());
					jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
				}
				else{
					ps.setAmount(amount2);
					jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
				}
				//ps.setPostManual(true);
				newPosting.add(ps);
				ps.setJournal(jr);
				jr.setPostingdrcr(newPosting);
			}
		}
		em.persist(jr);
		return jr;
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal manualJornalUpdate2(ArrayList<String> amount, ArrayList<String> codes, String type, int jornalId){
		Journal jr = em.find(Journal.class, jornalId);
		ArrayList<Posting> posted = new ArrayList<Posting>(jr.getPostingdrcr());
		ArrayList<Posting> newPosting = new ArrayList<Posting>();
		BigDecimal amount1 = new BigDecimal(amount.get(0));
		BigDecimal amount2 = new BigDecimal(amount.get(1));
		
		for(int i = 0; i < posted.size(); i++){
			Posting ps = posted.get(i);
			if(i == 0){
				AccountCode from = em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue());
				ps.setCodeId(from);
				if(type.equals("Debit")){
					ps.setAmount(amount1.add(amount2));
					jr.setCodeDR(String.valueOf(from.getGroup().getGroupId()) + ".");
				}
				else{
					ps.setAmount(amount1.add(amount2).negate());
					jr.setCodeCR(String.valueOf(from.getGroup().getGroupId()) + ".");
				}
			//	ps.setPostManual(true);
				newPosting.add(ps);
				ps.setJournal(jr);
				jr.setPostingdrcr(newPosting);
			}
			
			else if(i == 1){
				AccountCode to = em.find(AccountCode.class, (Integer.valueOf(codes.get(1))).intValue());
				ps.setCodeId(to);
				if(type.equals("Debit")){
					ps.setAmount(amount1.negate());
					jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + ".");
				}
				else{
					ps.setAmount(amount1);
					jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + ".");
				}
			//	ps.setPostManual(true);
				newPosting.add(ps);
				ps.setJournal(jr);
				jr.setPostingdrcr(newPosting);
			}
			
			else if(i == 2){
				em.remove(ps);
			}
		}
		em.persist(jr);
		return jr;
	}
	
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal manualJornalUpdate3(ArrayList<String> amount, ArrayList<String> codes, String type, int jornalId){
		Journal jr = em.find(Journal.class, jornalId);
		ArrayList<Posting> posted = new ArrayList<Posting>(jr.getPostingdrcr());
		ArrayList<Posting> newPosting = new ArrayList<Posting>();
		BigDecimal amount1 = new BigDecimal(amount.get(0));
		BigDecimal amount2 = new BigDecimal(amount.get(1));

		//princinple edit posting
		Posting ps = posted.get(0);
		AccountCode from = em.find(AccountCode.class, (Integer.valueOf(codes.get(0))).intValue());
		ps.setCodeId(from);
		if(type.equals("Debit")){
			ps.setAmount(amount1.add(amount2));
			jr.setCodeDR(String.valueOf(from.getGroup().getGroupId()) + ".");
		}
		else{
			ps.setAmount(amount1.add(amount2).negate());
			jr.setCodeCR(String.valueOf(from.getGroup().getGroupId()) + ".");
		}
	//	ps.setPostManual(true);
		newPosting.add(ps);
		ps.setJournal(jr);
		jr.setPostingdrcr(newPosting);
		
		//secoundary edit posting 1
		Posting ps1 = posted.get(1);
		AccountCode to = em.find(AccountCode.class, (Integer.valueOf(codes.get(1))).intValue());
		ps1.setCodeId(to);
		if(type.equals("Debit"))
			ps1.setAmount(amount1.negate());
		else{
			ps1.setAmount(amount1);
		}
		//ps1.setPostManual(true);
		newPosting.add(ps1);
		ps1.setJournal(jr);
		jr.setPostingdrcr(newPosting);
	
		//secoundary edit posting 2
		AccountCode toOther = em.find(AccountCode.class, (Integer.valueOf(codes.get(2))).intValue());
		Posting nps = new Posting();
		nps.setCodeId(toOther);
		if(type.equals("Debit")){
			nps.setAmount(amount2.negate());
			jr.setCodeCR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
		}
		else{
			nps.setAmount(amount2);
			jr.setCodeDR(String.valueOf(to.getGroup().getGroupId()) + "."+String.valueOf(toOther.getGroup().getGroupId()));
		}
	//	nps.setPostManual(true);
		newPosting.add(nps);
		nps.setJournal(jr);
		jr.setPostingdrcr(newPosting);

		em.persist(jr);
		return jr;
	}
	
	public byte[] manualPostingDetails(int manualDetail){
	
		ManualPosting manual = em.find(ManualPosting.class, manualDetail);
		LinkedHashMap<String,BigDecimal> postedCodes = new LinkedHashMap<String,BigDecimal>();
		ArrayList<Object> datas = new ArrayList<Object>();
		BigDecimal amount = BigDecimal.ZERO;
		
		Journal jr = em.find(Journal.class, manual.getJournal().getJournalId());
		ArrayList<Posting> posted = new ArrayList<Posting>(jr.getPostingdrcr());
		Query queryamount = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal=:jornal AND p.codeId=:codes");
		queryamount.setParameter("jornal", manual.getJournal());
		queryamount.setParameter("codes", manual.getPrincipleCode());
		try{
			amount = (BigDecimal)queryamount.getSingleResult();
		}
		catch(NoResultException ex){}
			
		AccountCode principle = em.find(AccountCode.class, manual.getPrincipleCode().getCodeId());

		postedCodes.put(String.valueOf(principle.getCodeId())+"."+principle.getCodeName(), amount.abs());
		if(amount.signum() == -1){
			datas.add("Credit");
		}
		else{
			datas.add("Debit");
		}
		datas.add(manual.getPostNote());
		datas.add(Integer.valueOf(manual.getPrincipleCode().getCodeId()));
		datas.add(manual.getPostDate());
		for(Posting p : posted){
			if(p.getCodeId().getCodeId() != manual.getPrincipleCode().getCodeId()){
			
				AccountCode others = em.find(AccountCode.class, p.getCodeId().getCodeId());
				BigDecimal otherAmount = p.getAmount().abs();
				postedCodes.put(String.valueOf(others.getCodeId())+"."+others.getCodeName(), otherAmount);
				
			}
		}
		datas.add(postedCodes);
		datas.add(Integer.valueOf(manual.getManualPostingId()));
		return objectWriter(datas);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////// Manual Posting End
	
	///////////////////////////////////////////////////////////////////////////////////////////////////// Fixed Assets
	/***
		**bean method addAssets() add fixed asset to database 
		**takes array of byte of fixed asset details
		**uses by AddAssets GUI
	*/
	public void addAssets(byte[] details){
		ArrayList<String> assetDetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(details);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);

		try{
			assetDetails = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		AccountCode assetCode = em.find(AccountCode.class, (Integer.valueOf(assetDetails.get(0))).intValue());
		String description = assetDetails.get(1);
		String serialNumber = assetDetails.get(2);
		String location = assetDetails.get(3);
		AccountCode transactionCode = em.find(AccountCode.class, (Integer.valueOf(assetDetails.get(4))).intValue());
		BigDecimal amount = new BigDecimal(assetDetails.get(5));
		String vat = assetDetails.get(6);
		Supplier assetSupplier = em.find(Supplier.class, (Integer.valueOf(assetDetails.get(7))).intValue());
		String invoiceNumber = assetDetails.get(8);
		Supplier assetInsurance = em.find(Supplier.class, (Integer.valueOf(assetDetails.get(9))).intValue());
		java.sql.Date aquisationDate = java.sql.Date.valueOf(assetDetails.get(10));
		String lifeSpan = assetDetails.get(11);
		BigDecimal amountResidual = new BigDecimal(assetDetails.get(12));
		String depType = assetDetails.get(13);
		String depRate = assetDetails.get(14);
		Asset asset =  new Asset();
		Journal jornal = assetBoughtJornal(assetCode, transactionCode, amount, vat, aquisationDate);
		asset.setLocation(location);
		asset.setSerialNumber(serialNumber);
		asset.setDescription(description);
		asset.setAssetStatus("Active");
		asset.setAquisationDate(aquisationDate);
		asset.setInvoiceNumber(invoiceNumber);
		asset.setAssetCode(assetCode);
		asset.setSupplierAsset(assetSupplier);
		asset.setSupplierInsurance(assetInsurance);
		asset.setJournal(jornal);
		Depreciation depreciation = assetDeprecitaionInfo(asset, amountResidual, lifeSpan, depType, depRate);
		em.persist(asset);
		addDepreciation(depreciation);//calculate depreciation on asset on demand
	}
	
	/***
		**assetBoughtJornal helper bean method for addAssets() add fixed asset to database 
		join addAsets method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal assetBoughtJornal(AccountCode assetCode, AccountCode transactionCode, BigDecimal amount, String vatstr, java.sql.Date aquisationDate){
		Journal jr = new Journal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		
		//find the parcentage for vat
		Posting vat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
		//credit account code use to purchase asset
		Posting tranCode = new Posting();
		tranCode.setAmount(amount.negate());
		tranCode.setCodeId(transactionCode);
		postarray.add(tranCode);
		tranCode.setJournal(jr);
		
		//debit asset account code minus vat if any
		Posting assCode = new Posting();
		assCode .setAmount(amount.subtract(vatAmount));
		assCode .setCodeId(assetCode);
		postarray.add(assCode );
		assCode.setJournal(jr);
		
		//debit vat account code if any
		if(vat != null){
			AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
			vat.setAmount(vatAmount);
			vat.setCodeId(vatcodeDR);
			postarray.add(vat);
			vat.setJournal(jr);
			jr.setCodeDR(String.valueOf(assetCode.getGroup().getGroupId()) + "."+String.valueOf(vatcodeDR.getGroup().getGroupId()));
			jr.setCodeCR(String.valueOf(transactionCode.getGroup().getGroupId()));
		}
		
		else{
			jr.setCodeDR(String.valueOf(assetCode.getGroup().getGroupId()));
			jr.setCodeCR(String.valueOf(transactionCode.getGroup().getGroupId()));
		}
		jr.setPostManual(false);
		jr.setPostingDate(aquisationDate);
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		
		return jr;
	}
	
	/***
		**assetDeprecitaionInfo helper bean method for addAssets() add fixed asset to database 
		join addAsets method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Depreciation assetDeprecitaionInfo(Asset asset, BigDecimal amountResidual, String lifeSpan , String depType , String depRate){
		Depreciation depreciation = new Depreciation();
		depreciation.setAsset(asset);
		depreciation.setLifeSpan(lifeSpan);
		depreciation.setResidual(amountResidual);
		depreciation.setDepType(depType);
		depreciation.setRate(depRate);
		em.persist(depreciation);
		return depreciation;
	}
	
	/***
		**bean method getAssetDetails() returns all asset 
		**return byte arraylist of arraylist object
		**uses by Asset Register view in main GUI
	*/
	public byte[] getAssetDetails(){
		java.util.List<Asset> ls = (java.util.List<Asset>)em.createQuery("SELECT a FROM Asset a").getResultList();
		return objectWriter(assetDetails(ls));
	}
	
		/***
		**bean method getAssetDetails(String datefrom, String dateto) returns all asset between date from and date to 
		**return byte arraylist of arraylist object
		**uses by Asset Register view in main GUI
	*/
	public byte[] getAssetDetails(String datefrom, String dateto){
		Query query = em.createQuery("SELECT a FROM Asset a WHERE a.aquisationDate BETWEEN ?1 AND ?2");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Asset> ls = (java.util.List<Asset>)query.getResultList();
		return objectWriter(assetDetails(ls));
	}
	
	/***
		**bean method getAssetDetails(String datefrom, String dateto, int account) returns all asset with specific account number
		**return byte arraylist of arraylist object
		**uses by Asset Register view in main GUI
	*/
	public byte[] getAssetDetails(String datefrom, String dateto, int account){
		Query query = em.createQuery("SELECT a FROM Asset a WHERE a.aquisationDate BETWEEN ?1 AND ?2 AND a.assetCode = :account");
		query.setParameter("account", em.find(AccountCode.class, account));
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Asset> ls = (java.util.List<Asset>)query.getResultList();
		return objectWriter(assetDetails(ls));
	}
	
		/***
		**bean method getAssetDetails(String datefrom, String dateto, String description) returns asset with this decription
		**return byte arraylist of arraylist object
		**uses by Asset Register view in main GUI
	*/
	public byte[] getAssetDetails(String datefrom, String dateto, String description){
		Query query = em.createQuery("SELECT a FROM Asset a WHERE a.aquisationDate BETWEEN ?1 AND ?2 AND a.description = ?3");
		query.setParameter(3, description);
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Asset> ls = (java.util.List<Asset>)query.getResultList();
		return objectWriter(assetDetails(ls));
	}
	
	/***
		**bean method helper getAssetDetails() returns all asset 
		**return byte arraylist of arraylist object
		**uses by Asset Register view in main GUI
	*/
		public ArrayList<ArrayList<Object>> assetDetails(java.util.List<Asset> ls){

		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Asset asset : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			BigDecimal amount = BigDecimal.ZERO;
			BigDecimal depAmount = BigDecimal.ZERO;
			temp.add(asset.getAssetCode().getCodeName());
			temp.add(asset.getDescription());
			//temp.add(asset.getSupplierAsset().getCompanyName());
			Query amtquery = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal = :jornal AND p.codeId != :code");
			amtquery.setParameter("jornal", asset.getJournal());
			amtquery.setParameter("code", asset.getAssetCode());
			try{
				amount = (BigDecimal)amtquery.getSingleResult();
			}
			catch(NoResultException ex){}
			
			Query depquery = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
			depquery.setParameter("ass", asset);
			Depreciation depreciation = null;
			try{
				depreciation = (Depreciation)depquery.getSingleResult();
			}
			catch(NoResultException ex){}
		
			addDepreciation(depreciation);//calculate depreciation on asset on demand

			temp.add(amount.abs());
			temp.add(getProcessedDepreciation(depreciation));
			temp.add(asset.getAquisationDate());
			temp.add(asset.getAssetStatus());
			temp.add(Integer.valueOf(asset.getAssetId()));
			temp.add(String.valueOf(asset.getAssetCode().getCodeId()));
			datas.add(temp);
		}
		return datas;
	}
	
	/***
		**bean method helper getDepreciation(Depreciation depreciatio) 
		**return Bigdeciaml dpreciation amout
		**uses by any bean method that need depreciation amount information
	*/
	public BigDecimal getProcessedDepreciation(Depreciation depreciation){
		BigDecimal depAmount = BigDecimal.ZERO;
		Query queryProcess = em.createQuery("SELECT e FROM ProcessedDepreciation e WHERE e.depreciation = :depreciated");
		queryProcess.setParameter("depreciated", depreciation);
		java.util.List<ProcessedDepreciation> p =  (java.util.List<ProcessedDepreciation>)queryProcess.getResultList();		
		for(ProcessedDepreciation process : p){
			Query queryAmt = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal");
			queryAmt.setParameter("jornal", process.getJournal());
			try{
				depAmount = depAmount.add((BigDecimal)queryAmt.getSingleResult());
				
			}
			catch(NoResultException ex){}
		}
		return depAmount;
	}
	
	/***
		**bean method helper getAssetSingleDetails(int assetId) returns single asset  details
		**return byte arraylist object
		**uses by Fixed Asset details view in main GUI
	*/
	public byte[] getAssetSingleDetails(int assetId){
		Asset asset = em.find(Asset.class, assetId);
		ArrayList<Object> details = new ArrayList<Object>();
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal vatAmount = BigDecimal.ZERO;
		details.add(asset.getDescription());
		details.add(Integer.valueOf(asset.getAssetId()));
		details.add(asset.getSerialNumber());
		details.add(asset.getLocation());
		Query assetPayment = em.createQuery("SELECT p.codeId FROM Posting p WHERE p.journal = :jornal AND p.amount < 0");
		assetPayment.setParameter("jornal", asset.getJournal());
		AccountCode paymentCode = null;
		try{
			paymentCode = (AccountCode)assetPayment.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(paymentCode.getCodeName());
		details.add(asset.getInvoiceNumber());
		Query amtquery = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal = :jornal AND p.codeId != :code");
		amtquery.setParameter("jornal", asset.getJournal());
		amtquery.setParameter("code", asset.getAssetCode());
		try{
			amount = (BigDecimal)amtquery.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(amount.abs());
		
		Query vatAmtquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal AND p.codeId.codeId = 70020");
		vatAmtquery.setParameter("jornal", asset.getJournal());
		try{
			vatAmount = (BigDecimal)vatAmtquery.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(vatAmount);
		Supplier supplier = asset.getSupplierAsset();
		details.add(supplier.getCompanyName()+"."+String.valueOf(supplier.getSupplierId()));
		Supplier supplierIns = asset.getSupplierInsurance();
		details.add(supplierIns.getCompanyName()+"."+String.valueOf(supplierIns.getSupplierId()));
		details.add(asset.getAquisationDate());
		Query queryDep = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
		queryDep.setParameter("ass", asset);
		Depreciation dep = null;
		try{
			dep = (Depreciation)queryDep.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(dep.getLifeSpan());
		details.add(dep.getResidual());
		details.add(dep.getDepType());
		details.add(dep.getRate());
		
		return objectWriter(details);
	}
	
	/***
		**bean method helper getEditAssetDetails(int assetId) returns single asset  details
		**return byte arraylist object
		**uses by Edit Asset GUI
	*/
	public byte[] getEditAssetDetails(int assetId){
		Asset asset = em.find(Asset.class, assetId);
		ArrayList<Object> details = new ArrayList<Object>();
		BigDecimal amount = BigDecimal.ZERO;
		//BigDecimal vatAmount = BigDecimal.ZERO;
		details.add(asset.getAssetCode().getCodeName());
		details.add(asset.getDescription());
		details.add(asset.getSerialNumber());
		details.add(asset.getLocation());
		
		Query assetPayment = em.createQuery("SELECT p.codeId, p.amount FROM Posting p WHERE p.journal = :jornal AND p.amount < 0");
		assetPayment.setParameter("jornal", asset.getJournal());
		Object[] paymentCode = null;
		try{
			paymentCode = (Object[])assetPayment.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(((AccountCode)paymentCode[0]).getCodeName());
		details.add(((BigDecimal)paymentCode[1]).abs());

		Query amtquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal AND p.codeId = :code");
		amtquery.setParameter("jornal", asset.getJournal());
		amtquery.setParameter("code", asset.getAssetCode());
		try{
			amount = (BigDecimal)amtquery.getSingleResult();
		}
		catch(NoResultException ex){}
		
		
		Query vatAmtquery = em.createQuery("SELECT p FROM Posting p WHERE p.journal = :jornal AND p.codeId.codeId = 70020");
		vatAmtquery.setParameter("jornal", asset.getJournal());
		Posting vatPosting = null;
		try{
			vatPosting = (Posting)vatAmtquery.getSingleResult();
		}
		catch(NoResultException ex){}

		if(vatPosting != null){
			details.add(parcentage(amount, vatPosting.getAmount())+"%");
		}
		else{
			details.add("None");
		}
	
		details.add(asset.getSupplierAsset().getCompanyName());
		details.add(asset.getInvoiceNumber());
		details.add(asset.getSupplierInsurance().getCompanyName());
		details.add(asset.getAquisationDate());
		Query queryDep = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
		queryDep.setParameter("ass", asset);
		Depreciation dep = null;
		try{
			dep = (Depreciation)queryDep.getSingleResult();
		}
		catch(NoResultException ex){}
		details.add(dep.getLifeSpan());
		details.add(dep.getResidual());
		details.add(dep.getDepType());
		details.add(dep.getRate());
		details.add(Integer.valueOf(asset.getAssetCode().getCodeId()));
		details.add(Integer.valueOf(asset.getSupplierAsset().getSupplierId()));
		details.add(Integer.valueOf(asset.getSupplierInsurance().getSupplierId()));
		return objectWriter(details);
	}
	
	public void editAsset(byte[] details, byte[] assetId){ 
		ArrayList<String> assetDetails = null;
		Integer asstsId = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(details);
		ByteArrayInputStream baisId = new ByteArrayInputStream(assetId);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			ObjectInputStream oisId = new ObjectInputStream(baisId);
		try{
			assetDetails = (ArrayList<String>)ois.readObject();
			asstsId = (Integer)oisId.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		Asset asset = em.find(Asset.class, asstsId.intValue());
		AccountCode assetCode = em.find(AccountCode.class, (Integer.valueOf(assetDetails.get(0))).intValue());
		String description = assetDetails.get(1);
		String serialNumber = assetDetails.get(2);
		String location = assetDetails.get(3);
		AccountCode transactionCode = em.find(AccountCode.class, (Integer.valueOf(assetDetails.get(4))).intValue());
		BigDecimal amount = new BigDecimal(assetDetails.get(5));
		String vat = assetDetails.get(6);
		Supplier assetSupplier = em.find(Supplier.class, (Integer.valueOf(assetDetails.get(7))).intValue());
		String invoiceNumber = assetDetails.get(8);
		Supplier assetInsurance = em.find(Supplier.class, (Integer.valueOf(assetDetails.get(9))).intValue());
		java.sql.Date aquisationDate = java.sql.Date.valueOf(assetDetails.get(10));
		String lifeSpan = assetDetails.get(11);
		BigDecimal amountResidual = new BigDecimal(assetDetails.get(12));
		String depType = assetDetails.get(13);
		String depRate = assetDetails.get(14);
		Journal jornal = assetUpdateJornal(asset.getJournal(), assetCode, transactionCode, amount, vat, aquisationDate);
		asset.setLocation(location);
		asset.setSerialNumber(serialNumber);
		asset.setDescription(description);
		asset.setAssetStatus("Active");
		asset.setAquisationDate(aquisationDate);
		asset.setInvoiceNumber(invoiceNumber);
		asset.setAssetCode(assetCode);
		asset.setSupplierAsset(assetSupplier);
		asset.setSupplierInsurance(assetInsurance);
		asset.setJournal(jornal);
		assetUpdateDeprecitaionInfo(asset, amountResidual, lifeSpan, depType, depRate);
		em.persist(asset);
	
	}
	
	/***
		**assetUpdateJornal helper bean method for editAsset() add fixed asset to database 
		join editAset method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal assetUpdateJornal(Journal jr, AccountCode assetCode, AccountCode transactionCode, BigDecimal amount, String vatstr, java.sql.Date aquisationDate){
	
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		ArrayList<Posting> postedarray = new ArrayList<Posting>(jr.getPostingdrcr());
		//find the parcentage for vat
		Posting newVat = null;
		Posting oldVat = null;
		BigDecimal vatAmount =  BigDecimal.ZERO;
		if(!vatstr.equals("None")){
			newVat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (amount.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
		//credit account code use to purchase asset
		Posting tranCode = postedarray.get(0);
		tranCode.setAmount(amount.negate());
		tranCode.setCodeId(transactionCode);
		postarray.add(tranCode);
		tranCode.setJournal(jr);
		
		//debit asset account code minus vat if any
		Posting assCode = postedarray.get(1);
		assCode .setAmount(amount.subtract(vatAmount));
		assCode .setCodeId(assetCode);
		postarray.add(assCode );
		assCode.setJournal(jr);
		
		//debit vat account code if any
		if(postedarray.size() == 3 && newVat != null){
			oldVat = postedarray.get(2);
			AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
			oldVat.setAmount(vatAmount);
			oldVat.setCodeId(vatcodeDR);
			postarray.add(oldVat);
			oldVat.setJournal(jr);
			jr.setCodeDR(String.valueOf(assetCode.getGroup().getGroupId()) + "."+String.valueOf(vatcodeDR.getGroup().getGroupId()));
			jr.setCodeCR(String.valueOf(transactionCode.getGroup().getGroupId()));
		}
		else if(postedarray.size() == 2 && newVat != null){
		
			AccountCode vatcodeDR = em.find(AccountCode.class, 70020);
			newVat.setAmount(vatAmount);
			newVat.setCodeId(vatcodeDR);
			postarray.add(newVat);
			newVat.setJournal(jr);
			jr.setCodeDR(String.valueOf(assetCode.getGroup().getGroupId()) + "."+String.valueOf(vatcodeDR.getGroup().getGroupId()));
			jr.setCodeCR(String.valueOf(transactionCode.getGroup().getGroupId()));
		}
		else{
			jr.setCodeDR(String.valueOf(assetCode.getGroup().getGroupId()));
			jr.setCodeCR(String.valueOf(transactionCode.getGroup().getGroupId()));
		}
		jr.setPostManual(false);
		jr.setPostingDate(aquisationDate);
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		
		return jr;
	}
	
	/***
		**assetUpdateDeprecitaionInfo helper bean method for editAsset() add fixed asset to database 
		join editAset method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void assetUpdateDeprecitaionInfo(Asset asset, BigDecimal amountResidual, String lifeSpan , String depType , String depRate){
		Query query = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
		query.setParameter("ass", asset);
		Depreciation depreciation = null;
		try{
			depreciation = (Depreciation)query.getSingleResult();
		}
		catch(NoResultException ex){}
		depreciation.setAsset(asset);
		depreciation.setLifeSpan(lifeSpan);
		depreciation.setResidual(amountResidual);
		depreciation.setDepType(depType);
		depreciation.setRate(depRate);
		em.persist(depreciation);
	}
	
	/***
		**getProcessedDepreciationDetails bean method to get processed depreciation for particuler asset in database 
		return byte array 
		use by asset details view GUI
	*/
	public byte[] getProcessedDepreciationDetails(int assetId){
		Asset asset = em.find(Asset.class, assetId);

		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		Query queryDep = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
		queryDep.setParameter("ass", asset);
		
		Depreciation depreciation = null;
		try{
			depreciation = (Depreciation)queryDep.getSingleResult();
		}
		catch(NoResultException ex){}
		
		String lifeSpan[] = depreciation.getLifeSpan().split("\\s+");
		int lifeSpanInt = (Integer.valueOf(lifeSpan[0])).intValue();

		int counter = 1; //count number of depreciation already processed
		int lifeSpanUsed = lifeSpanInt - 1;
		
		Query query = em.createQuery("SELECT p FROM ProcessedDepreciation p WHERE p.depreciation = :depreciated");
		query.setParameter("depreciated", depreciation);
		java.util.List<ProcessedDepreciation> p =  (java.util.List<ProcessedDepreciation>)query.getResultList();

		for(ProcessedDepreciation process : p){
			ArrayList<Object> temp = new ArrayList<Object>();
			BigDecimal amount = BigDecimal.ZERO;
			Journal jornal =  process.getJournal();
			Query queryAmt = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal");
			queryAmt.setParameter("jornal", jornal);
			try{
				amount = (BigDecimal)queryAmt.getSingleResult();
			}
			catch(NoResultException ex){}
			temp.add(jornal.getPostingDate());
			temp.add(amount);
			if(counter % 12 == 0){
				lifeSpanUsed--;
			}
			temp.add(String.format("%s %s %s","Year", String.valueOf(lifeSpanInt - lifeSpanUsed), "Depreciation"));
			temp.add(String.format("%s %s %s %s","Month", String.valueOf(counter), "of", String.valueOf(lifeSpanInt * 12)));
			temp.add(Integer.valueOf(process.getProcessedDepreciationId()));
			datas.add(temp);
			counter++;
		}
		return objectWriter(datas);
	}
	
	/***
		**getNetBookValue bean method get net book value of asset in database 
		return byte array 
		use by asset details view GUI
	*/
	
	public byte[] getNetBookValue(int assetId){
		Asset asset = em.find(Asset.class, assetId);
		BigDecimal amount = BigDecimal.ZERO;
		Query amtquery = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal = :jornal AND p.codeId != :code");
		amtquery.setParameter("jornal", asset.getJournal());
		amtquery.setParameter("code", asset.getAssetCode());
		try{
			amount = (BigDecimal)amtquery.getSingleResult();
		}
		catch(NoResultException ex){}
		Query depquery = em.createQuery("SELECT d FROM Depreciation d WHERE d.asset = :ass");
		depquery.setParameter("ass", asset);
		Depreciation depreciation = null;
		try{
			depreciation = (Depreciation)depquery.getSingleResult();
		}
		catch(NoResultException ex){}
		return objectWriter(amount.abs().subtract(getProcessedDepreciation(depreciation)));
	}
	
	/***
		**assetDepreciationJornal helper bean method for addDepreciation() add depreciation on fixed asset to database 
		call by getProcessedDepreciationDetails method
	*/
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void addDepreciation(Depreciation depreciation){
	
		Query query = em.createQuery("SELECT p FROM ProcessedDepreciation p WHERE p.depreciation = :depreciated");
		query.setParameter("depreciated", depreciation);
		java.util.List<ProcessedDepreciation> p =  (java.util.List<ProcessedDepreciation>)query.getResultList();
		
		Asset asset = depreciation.getAsset();
		
		String lifeSpan[] = depreciation.getLifeSpan().split("\\s+");
		int totalMouth = (Integer.valueOf(lifeSpan[0])).intValue() * 12;
		ArrayList<java.sql.Date> range = dataRange(asset.getAquisationDate(), totalMouth);
		
		for(int i = p.isEmpty() ? 0 : p.size() + 1 ; 
				i < range.size(); i++){
		ProcessedDepreciation process = new ProcessedDepreciation();
		BigDecimal depAmount = BigDecimal.ZERO;

		if(depreciation.getDepType().equals("Reducing Balance")){
			String rate = depreciation.getRate();
			BigDecimal amount = BigDecimal.ZERO;
			Query amtquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal AND p.codeId = :code");
			amtquery.setParameter("jornal", asset.getJournal());
			amtquery.setParameter("code", asset.getAssetCode());
			try{
				amount = (BigDecimal)amtquery.getSingleResult();
			}
			catch(NoResultException ex){}
			depAmount = (new BigDecimal(rate).multiply(amount)).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE);
		}
		
		else{
			depAmount = new BigDecimal(depreciation.getRate());
		}
		Journal jornal = assetDepreciationJornal(asset, depAmount, range.get(i));
		process.setJournal(jornal);
		process.setDepreciation(depreciation);
		em.persist(process);
		}
	}
	
	/***
		**assetDepreciationJornal helper bean method for addDepreciation() add depreciation on fixed asset to database 
		join addDepreciation method transaction 
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	
	public Journal assetDepreciationJornal(Asset asset, BigDecimal amount, java.sql.Date postingDate){
		Journal jr = new Journal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();

		String assetCodeGroup = updateAssetCost(asset, amount);
		Posting depreciation = new Posting();
		AccountCode depre = em.find(AccountCode.class, 60090);
		depreciation.setAmount(amount);
		depreciation.setCodeId(depre);
		postarray.add(depreciation);
		depreciation.setJournal(jr);
		jr.setPostManual(false);
		jr.setPostingDate(postingDate);
		jr.setCodeDR(String.valueOf(depre.getGroup().getGroupId()));
		jr.setCodeCR(assetCodeGroup);
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		return jr;
	}
	
	/***
		**updateAssetCost helper bean method for assetDepreciationJornal() and addAssetDisposal() update fixed asset cost when depreciation applied 
		join assetDepreciationJornal method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	
	public String updateAssetCost(Asset asset, BigDecimal amount){
		Journal jr = asset.getJournal();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		Query query = em.createQuery("SELECT p FROM Posting p WHERE p.journal = :jornal AND p.codeId = :code");
		query.setParameter("jornal", jr);
		query.setParameter("code", asset.getAssetCode());
		Posting assetCost = null;
		try{
			assetCost = (Posting)query.getSingleResult();
		}
		catch(NoResultException ex){}
		assetCost.setAmount(assetCost.getAmount().subtract(amount));
		return String.valueOf(assetCost.getCodeId().getGroup().getGroupId());
	}
	
	/***
		**bean method addAssetDisposal(int assetId) add asset that is disposed of to database
		**takes byte array of asset details
		**uses by Asset disposal GUI
	*/
	public void addAssetDisposal(byte[] details){
		ArrayList<String> assetDetails = null;
		
		ByteArrayInputStream bais = new ByteArrayInputStream(details);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
		try{
			assetDetails = (ArrayList<String>)ois.readObject();

		} finally {
			ois.close();
		}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		Journal jr = new Journal();
		AssetDisposal disposalAsset = new AssetDisposal();
		
		int assetId = (Integer.valueOf(assetDetails.get(0))).intValue();
		java.sql.Date postingDate = java.sql.Date.valueOf(assetDetails.get(1));
		String disposal = assetDetails.get(2);
		BigDecimal amount = new BigDecimal(assetDetails.get(3));
		String vat = assetDetails.get(4);
		if(disposal.equals("Cash")){
			jr = assetCash(amount, assetId, postingDate, vat);
		}
		
		else{
		
			jr = assetScrap(assetId, postingDate);
		}
		Asset asset = em.find(Asset.class, assetId);
		updateAssetStatus(asset);
		disposalAsset.setAsset(asset);
		disposalAsset.setJournal(jr);
		disposalAsset.setDisposalDate(postingDate);
		disposalAsset.setDisposalType(disposal);
		em.persist(disposalAsset);
	}
	
	/***
		**assetCash helper bean method for addAssetDisposal() update fixed asset cost when depreciation applied 
		join assetDepreciationJornal method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal assetCash(BigDecimal cashAmountInc, int assetId, java.sql.Date postingDate, String vatstr){
		Journal jr = new Journal();
		Posting cashSales = new Posting();
		Posting vat = null;
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		Asset asset = em.find(Asset.class, assetId);
		BigDecimal amount = BigDecimal.ZERO;	
		BigDecimal vatAmount =  BigDecimal.ZERO;
		
		Query amtquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal AND p.codeId = :code");
		amtquery.setParameter("jornal", asset.getJournal());
		amtquery.setParameter("code", asset.getAssetCode());
		try{
			amount = (BigDecimal)amtquery.getSingleResult();
		}
		catch(NoResultException ex){}
		
		String update = updateAssetCost(asset, amount);
		AccountCode cashCode = em.find(AccountCode.class, 10010);//debit cash with sales amount
		
		//vat calculation if any
		if(!vatstr.equals("None")){
			vat = new Posting();
			String vats[] = vatstr.split("%");
			BigDecimal vatValue = new BigDecimal(vats[0]);
			if(vatValue.compareTo(new BigDecimal("0")) > 0){
				vatAmount = (cashAmountInc.multiply(vatValue)).divide(new BigDecimal("100").add(vatValue), DECIMALS, ROUNDING_MODE);
			}
		}
		
		BigDecimal cashAmount = cashAmountInc.subtract(vatAmount);
		
		if(cashAmount.compareTo(amount) > 0){//asset sold at profit
			BigDecimal profit = cashAmount.subtract(amount);
			Posting otherIncome = new Posting();

			cashSales.setAmount(cashAmountInc);
			cashSales.setCodeId(cashCode);
			postarray.add(cashSales);
			cashSales.setJournal(jr);
			
			AccountCode otherCode = em.find(AccountCode.class, 40530);//credit other income
			otherIncome.setAmount(profit.negate());
			otherIncome.setCodeId(otherCode);
			postarray.add(otherIncome);
			otherIncome.setJournal(jr);
			
			if(vat != null){
				AccountCode vatcodeCR = em.find(AccountCode.class, 70020);
				vat.setAmount(vatAmount.negate());
				vat.setCodeId(vatcodeCR);
				postarray.add(vat);
				vat.setJournal(jr);
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId()));
				jr.setCodeCR(update+"."+String.valueOf(otherCode.getGroup().getGroupId())+
							"."+String.valueOf(vatcodeCR.getGroup().getGroupId()));
			}
			else{
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId()));
				jr.setCodeCR(update+"."+String.valueOf(otherCode.getGroup().getGroupId()));
			}
			jr.setPostManual(false);
			jr.setPostingDate(postingDate);
			jr.setPostingdrcr(postarray);
		//	return jr;
		}
		
		if(cashAmount.compareTo(amount) < 0){
			BigDecimal loss = amount.subtract(cashAmount);
			Posting lossIncome = new Posting();

			cashSales.setAmount(cashAmountInc);
			cashSales.setCodeId(cashCode);
			postarray.add(cashSales);
			cashSales.setJournal(jr);
			
			AccountCode lossCode = em.find(AccountCode.class, 60100);//debit loss on sale of asset
			lossIncome.setAmount(loss);
			lossIncome.setCodeId(lossCode);
			postarray.add(lossIncome);
			lossIncome.setJournal(jr);
			
			if(vat != null){
				AccountCode vatcodeCR = em.find(AccountCode.class, 70020);
				vat.setAmount(vatAmount.negate());
				vat.setCodeId(vatcodeCR);
				postarray.add(vat);
				vat.setJournal(jr);
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId())+"."+String.valueOf(lossCode.getGroup().getGroupId()));
				jr.setCodeCR(update+"."+String.valueOf(vatcodeCR.getGroup().getGroupId()));
			}
			else{
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId())+"."+String.valueOf(lossCode.getGroup().getGroupId()));
				jr.setCodeCR(update);
			}
			
			jr.setPostManual(false);
			jr.setPostingDate(postingDate);
			jr.setPostingdrcr(postarray);
	
		}
		
		else{
			
			cashSales.setAmount(cashAmountInc);
			cashSales.setCodeId(cashCode);
			postarray.add(cashSales);
			cashSales.setJournal(jr);
			
			if(vat != null){
				AccountCode vatcodeCR = em.find(AccountCode.class, 70020);
				vat.setAmount(vatAmount.negate());
				vat.setCodeId(vatcodeCR);
				postarray.add(vat);
				vat.setJournal(jr);
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId()));
				jr.setCodeCR(update+"."+String.valueOf(vatcodeCR.getGroup().getGroupId()));
			}
			else{
				jr.setCodeDR(String.valueOf(cashCode.getGroup().getGroupId()));
				jr.setCodeCR(update);
			}
			
			jr.setPostManual(false);
			jr.setPostingDate(postingDate);
			jr.setPostingdrcr(postarray);
			
		}
		em.persist(jr);
		return jr;
	}
	
	/***
		**assetScrap helper bean method for addAssetDisposal() update fixed asset cost when depreciation applied 
		join assetDepreciationJornal method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public Journal assetScrap(int assetId, java.sql.Date postingDate){
		Journal jr = new Journal();
		Posting cashSales = new Posting();
		Posting lossIncome = new Posting();
		ArrayList<Posting> postarray = new ArrayList<Posting>();
		Asset asset = em.find(Asset.class, assetId);
		BigDecimal amount = BigDecimal.ZERO;
		
		Query amtquery = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :jornal AND p.codeId = :code");
		amtquery.setParameter("jornal", asset.getJournal());
		amtquery.setParameter("code", asset.getAssetCode());
		try{
			amount = (BigDecimal)amtquery.getSingleResult();
		}
		catch(NoResultException ex){}
				
		String update = updateAssetCost(asset, amount);
		
		AccountCode lossCode = em.find(AccountCode.class, 60100);//debit loss on sale of asset
		lossIncome.setAmount(amount);
		lossIncome.setCodeId(lossCode);
		postarray.add(lossIncome);
		lossIncome.setJournal(jr);
			
		jr.setPostManual(false);
		jr.setPostingDate(postingDate);
		jr.setCodeDR(String.valueOf(lossCode.getGroup().getGroupId()));
		jr.setCodeCR(update);
		jr.setPostingdrcr(postarray);
		em.persist(jr);
		return jr;
	}
	
	/***
		**assetScrap helper bean method for addAssetDisposal() update fixed asset cost when depreciation applied 
		join assetDepreciationJornal method transaction
	*/
	@TransactionAttribute(value=TransactionAttributeType.MANDATORY)
	public void updateAssetStatus(Asset asset){
		asset.setAssetStatus("Disposed");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////// Fixed Assets End 
	
	////////////////////////////////////////////////////////////////////////////////////////////////// ACCOUNTS CODES
	
	/***
		**bean method getOperationalExpensesCodes() returns all Operational Expenses accounts code 
		**return byte arraylist of arraylist object
		**uses by GeneralExpenses view GUI
	*/
	public byte[] getOperationalExpensesCodes(){
		java.util.List<AccountCode> ls = (java.util.List<AccountCode>)em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId=60000").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccountCode code : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(Integer.valueOf(code.getCodeId()));
			temp.add(code.getCodeName());
			temp.add(code.getAddDate());
			datas.add(temp);
		}
		return objectWriter(datas);
	}
	
	/***
		**bean method getAssetsCodes() returns all asset accounts code 
		**return byte arraylist of arraylist object
		**uses by GeneralExpenses view GUI
	*/
	public byte[] getAssetsCodes(){
		java.util.List<AccountCode> ls = (java.util.List<AccountCode>)em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId=10500").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccountCode code : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(Integer.valueOf(code.getCodeId()));
			temp.add(code. getCodeName());
			temp.add(code.getAddDate());
			datas.add(temp);
		}
		return objectWriter(datas);
	
	
	}
 /**SEEN
		**bean method getOperationalExpensesCodes() returns all Operational Expenses accounts code 
		**return byte arraylist of arraylist object
		**uses by GeneralExpenses view GUI
	*/
	public byte[] getManualPostingCodes(){
		java.util.List<AccountCode> ls = (java.util.List<AccountCode>)em.createQuery("SELECT a FROM AccountCode a WHERE a.manualPosting=1 ORDER BY a.codeName ASC").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccountCode code : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(Integer.valueOf(code.getCodeId()));
			temp.add(code. getCodeName());
			temp.add(code.getAddDate());
			datas.add(temp);
		}
		return objectWriter(datas);
	}
	
	
	/*SEEN
		**bean method to add new account code 
		**this method is called by AddCodeDetails class
		**uses Utilities class to read byte[] array
	*/
	

	public void addAccountsCode(byte[] accountcode){
		ArrayList<String> accdetails = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(accountcode);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
		try{
			accdetails = (ArrayList<String>)ois.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}  
       AccountCode account = new AccountCode();
	   AccountsGroup group = em.find(AccountsGroup.class, (Integer.valueOf((String)accdetails.get(0))).intValue());
	   account.setGroup(group);
	   account.setCodeId((new Integer((String)accdetails.get(1))).intValue());
	   account.setCodeName((String)accdetails.get(2));
	   account.setAddDate(java.sql.Date.valueOf((String)accdetails.get(3)));
	   account.setActive((String)accdetails.get(4));
	   account.setManualPosting((Boolean.valueOf((String)accdetails.get(5))).booleanValue());
	   em.persist(account); 
	}
	
	/*SEEN
		**update account code 
		**this method is called by EditAccoutnsCode class
		**uses Utilities class to read byte[] array getAccountsDetails
	*/
	public void updateAccountsCode(byte[] editcode){
		ArrayList<String> editaccountcode = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(editcode);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
		try{
			editaccountcode = (ArrayList<String>)ois.readObject();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createQuery("UPDATE AccountCode a SET a.group = ?1, a.codeName = :codenames, a.addDate = :adate, a.active = :aactive, a.manualPosting = :manuPosting WHERE a.codeId = ?3 ");
		query.setParameter(3, new Integer((String)editaccountcode.get(0)).intValue());
		AccountsGroup g = em.find(AccountsGroup.class, (Integer.valueOf((String)editaccountcode.get(1))).intValue());
		query.setParameter(1, g);
		query.setParameter("codenames", (String)editaccountcode.get(2));
		query.setParameter("adate", java.sql.Date.valueOf((String)editaccountcode.get(3)));
		query.setParameter("aactive", (String)editaccountcode.get(4));
		query.setParameter("manuPosting", (Boolean.valueOf((String)editaccountcode.get(5))).booleanValue());
		int update = query.executeUpdate();
	}
		
	public void deleteProject(byte[] project){
		int projectid = 0;
		ByteArrayInputStream bais = new ByteArrayInputStream(project);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
		try{
			projectid = ((Integer)ois.readObject()).intValue();
		} finally {
			ois.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createQuery("DELETE FROM Project p WHERE p.projectId = ?1 ");
		query.setParameter(1, projectid);
		int deleted = query.executeUpdate();
		
	}

	 public byte[] getIncomingId(){
		java.util.List<Integer> ls = (java.util.List<Integer>)em.createQuery("SELECT i.incomingId FROM Incoming i").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }

	}
	
	
     public byte[] geneProtoolId(){
		java.util.List<Integer> ls = (java.util.List<Integer>)em.createQuery("SELECT p.projectId FROM Project p").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }

	}
	
	/**
		**method get PaymentIn Id, use to check if invoive already generated for a project
		**associate with addpaymentin user interface
		**return byte of Integer  #it should be single result set
	*/
	 public byte[] getPaymentInfo(int projectid){
		Query query = em.createQuery("SELECT p FROM PaymentIn p WHERE p.project.projectId=?1");
		query.setParameter(1, projectid); 
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)query.getResultList();
		String payment = null;
		for(PaymentIn pay : ls){	
			payment = pay.getInvoice();
		}
		try {
           ByteArrayOutputStream buffer = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(buffer);
           oos.writeObject(payment);
           oos.close();
           return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
           throw new RuntimeException("error writing to byte-array!");
        }

	}
	
	///////////////////////////////////////////////////////////////////////////////////////accounting code beans
	/*SEEN / COME BACK
		**method HashTableMaterialPosting
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable 
	*/
	public byte[] HashTableMaterialPosting(){
		Hashtable<String, Integer> accnamecode = new Hashtable<String, Integer>();
		Query querybank = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=10010 and a.active = 'YES'");
		Query querycreditcard = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=20020 and a.active = 'YES'");
		AccountCode bank = null;
		AccountCode creditCard = null;
		try{
			bank = (AccountCode)querybank.getSingleResult();
			creditCard = (AccountCode)querycreditcard.getSingleResult();
		}
		catch(NoResultException ex){}
		accnamecode.put(bank.getCodeName(), bank.getCodeId());
		accnamecode.put(creditCard.getCodeName(), creditCard.getCodeId());
		return objectWriter(accnamecode);
	}
	
	public byte[] HashTableAccruedPosting(){
		Hashtable<String, Integer> accnamecode = new Hashtable<String, Integer>();
		Query querybank = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=10010 and a.active = 'YES'");
		Query querycreditcard = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=20020 and a.active = 'YES'");
		Query querypayable = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId=20010 and a.active = 'YES'");
		AccountCode bank = null;
		AccountCode creditCard = null;
		AccountCode accountsPayable = null;
		try{
			bank = (AccountCode)querybank.getSingleResult();
			creditCard = (AccountCode)querycreditcard.getSingleResult();
			accountsPayable = (AccountCode)querypayable.getSingleResult();
		}
		catch(NoResultException ex){}
		accnamecode.put(bank.getCodeName(), bank.getCodeId());
		accnamecode.put(creditCard.getCodeName(), creditCard.getCodeId());
		accnamecode.put(accountsPayable.getCodeName(), accountsPayable.getCodeId());
		return objectWriter(accnamecode);
	}
	
	/*SEEN / COME BACK
		**method get revenue account code plus its name
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable 
	*/
	public byte[] getHashTableProjectCost(){
		Hashtable<Integer, String> accnamecode = new Hashtable<Integer, String>();
		Query query = em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId=50000 and a.active = 'YES'");
		java.util.List<AccountCode> ls = (java.util.List<AccountCode>)query.getResultList();
		for(AccountCode acc : ls){	
			accnamecode.put(acc.getCodeId(), acc.getCodeName());
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(accnamecode);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/* SEEN / COME BACK
		**method get revenue account code plus its name
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable 
	*/
	public byte[] getHashTableAccountCode(){
		Hashtable<String, Integer> accnamecode = new Hashtable<String, Integer>();
		Query query = em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId=40000 and a.active = 'YES'");
		java.util.List<AccountCode> ls =  (java.util.List<AccountCode>)query.getResultList();
		for(AccountCode acc : ls){	
			accnamecode.put(acc.getCodeName(), acc.getCodeId());
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(accnamecode);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/**
		method genericAccount(int accountCode) get account code plus account name
		provide cover change in account name before it is used
		return byte[] array of concated code and name
		
	*/
	
	public byte[] genericAccount(int accountCode){
		AccountCode code = em.find(AccountCode.class, accountCode);
		return objectWriter(String.valueOf(code.getCodeId())+"."+code.getCodeName());
	}
	
	/**
		method getHashMapPaymentType() get account code plus account name for payment type
		provide cover change in account name before it is used
		return byte[] array of hashmap 
		
	*/
	
	public byte[] getHashMapPaymentType(){
		Map<String, Integer> linkHashMap = new LinkedHashMap<String, Integer>();
		AccountCode bankCode = em.find(AccountCode.class, 10010);
		AccountCode cashCode = em.find(AccountCode.class, 10040);
		AccountCode badDebtCode = em.find(AccountCode.class, 60110);
		linkHashMap.put(bankCode.getCodeName(), Integer.valueOf(bankCode.getCodeId()));
		linkHashMap.put(cashCode.getCodeName(), Integer.valueOf(cashCode.getCodeId()));
		linkHashMap.put(badDebtCode.getCodeName(), Integer.valueOf(badDebtCode.getCodeId()));
		return objectWriter(linkHashMap);
	}
	
	/**
		method getHashMapPaymentOutType() get account code plus account name for payment type
		provide cover change in account name before it is used
		return byte[] array of hashmap 
		
	*/
	
	public byte[] getHashMapPaymentOutType(){
		Map<String, Integer> linkHashMap = new LinkedHashMap<String, Integer>();
		AccountCode bankCode = em.find(AccountCode.class, 10010);
		AccountCode cashCode = em.find(AccountCode.class, 10040);
		AccountCode creditCard = em.find(AccountCode.class, 20020);
		linkHashMap.put(bankCode.getCodeName(), Integer.valueOf(bankCode.getCodeId()));
		linkHashMap.put(cashCode.getCodeName(), Integer.valueOf(cashCode.getCodeId()));
		linkHashMap.put(creditCard.getCodeName(), Integer.valueOf(creditCard.getCodeId()));
		return objectWriter(linkHashMap);
	}
	
	/*SEEN / COME BACK
		**method get assets account code plus its name addAccountsCode
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable 
	*/
	public byte[] getHashTableAccountCodeAssets(){
		Hashtable<String, Integer> accnamecode = new Hashtable<String, Integer>();
		Query query = em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId=10000 and a.active = 'YES'");
		java.util.List<AccountCode> ls =  (java.util.List<AccountCode>)query.getResultList();
		for(AccountCode acc : ls){	
			accnamecode.put(acc.getCodeName(), acc.getCodeId());
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(accnamecode);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	
	/*SEEN
		**method get accounts group code plus its name
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable getStatus
	*/
	public byte[] getHashTableAccountsGroup(){
		Hashtable<String, Integer> accgroupnamecode = new Hashtable<String, Integer>();
		java.util.List<AccountsGroup> ls =  (java.util.List<AccountsGroup>)em.createQuery("SELECT g FROM AccountsGroup g ORDER BY g.groupId ASC").getResultList();	
		for(AccountsGroup acc : ls){	
			accgroupnamecode.put(acc.getCategory(), acc.getGroupId());
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(accgroupnamecode);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*SEEN
		**method get paymentIn ID and project ID from paymentIn entity
		**uses hashtable to store key(project ID) and value(paymentIn ID)
		**return byte of hashtable
	*/
	public byte[] getHashTablePaymentInId(){
		Hashtable<Integer, Integer> payment = new Hashtable<Integer, Integer>();
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)em.createQuery("SELECT p FROM PaymentIn p").getResultList();	
		for(PaymentIn pay : ls){	
			payment.put(pay.getProject().getProjectId(), pay.getpaymentInId());
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(payment);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	//SEEN
	 public byte[] genePaymentId(){
		java.util.List<Integer> ls = (java.util.List<Integer>)em.createQuery("SELECT p.paymentInId FROM PaymentIn p").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	/* DONE
		**method returns total quote byte for add payment in (invoice)
		**it takes project id as argument
		**return total qoute as anew BigDecimal
	*/
	public byte[] getQuoteTotals(int projectId){
		BigDecimal total = BigDecimal.ZERO;
		Query query = em.createQuery("SELECT q FROM Quote q WHERE q.project.projectId=?1");
		query.setParameter(1, projectId); 
		Quote quote = (Quote)query.getSingleResult();			
		ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
		for(Quoteline qline : quoteline){
			total = total.add(qline.getUnitPrice().multiply(qline.getQty()));
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(total);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	/*SEEN
		**method returns values from quote byte for adjusting invoice
		**it takes project id as argument
		**return total qoute as a double
	*/
	public byte[] getQuoteValues(int projectId){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		Query query = em.createQuery("SELECT q FROM Quote q WHERE q.project.projectId=?1");
		query.setParameter(1, projectId); 
		Quote quote = (Quote)query.getSingleResult();			
		ArrayList<Quoteline> quoteline= new ArrayList<Quoteline>(quote.getQuotelines());
		for(Quoteline qline : quoteline){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(qline.getDescription());
			temp.add(qline.getQty());
			temp.add(qline.getUnitPrice());
			datas.add(temp);
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	//get group id for adding account code gui SEEN
	public byte[] getGroupId(){
		java.util.List<Integer> ls =  (java.util.List<Integer>)em.createQuery("SELECT g.groupId FROM AccountsGroup g").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	/*SEEN
		**method get accounts group code plus its name
		**uses hashtable to store key(account name) and value(accounts code)
		**return byte of hashtable
	*/
	public byte[] getPaymentInIds(){
		java.util.List<Integer> ls =  (java.util.List<Integer>)em.createQuery("SELECT p.paymentInId FROM PaymentIn p").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	
		//get group name for adding account code gui SEEN
	public byte[] getGroupName(){
		java.util.List<String> ls =  (java.util.List<String>)em.createQuery("SELECT g.category FROM AccountsGroup g").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	//bean method to generate accounts code id SEEN
	public byte[] geneCodeId(){
		java.util.List<Integer> ls =  (java.util.List<Integer>)em.createQuery("SELECT a.codeId FROM AccountCode a").getResultList();	
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	//bean method to generate a specific accounts code id  SEEN
	public byte[] getAccountsDetails(int accountsCode){
		Query query = em.createQuery("SELECT a FROM AccountCode a WHERE a.codeId = ?1");
		query.setParameter(1, accountsCode);
		java.util.List<Object> ls= new ArrayList<Object>();
		AccountCode acccode = (AccountCode)query.getSingleResult();
		ls.add(acccode.getCodeId());
		AccountsGroup g = acccode.getGroup();
		ls.add(g.getCategory());
		//ls.add(acccode.getAccountsCode());
		ls.add(acccode.getCodeName());
		ls.add(acccode.getAddDate());
		ls.add(acccode.getActive());
		ls.add(acccode.getManualPosting());
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	//select all accountcode plus accountgroup id and name SEEN
	public byte[] getAccountsCodeDetails(){
		java.util.List<AccountsGroup> ls =  (java.util.List<AccountsGroup>)em.createQuery("SELECT a FROM AccountsGroup a ORDER BY a.category ASC").getResultList(); //GROUP BY a.group.groupId ASC
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(AccountsGroup groups : ls){
			int groupid = groups.getGroupId();
			ArrayList<Object> grouptemp = new ArrayList<Object>();
			grouptemp.add(Integer.valueOf(groupid));
			grouptemp.add(groups.getCategory());
			grouptemp.add(groups.getAddDate());
			grouptemp.add(Boolean.valueOf(true));
			//datas.add(grouptemp);			
			Query query = em.createQuery("SELECT a FROM AccountCode a WHERE a.group.groupId = ?1 ORDER BY a.codeId ASC");
			query.setParameter(1, groupid);
			 java.util.List<AccountCode> lscode = (java.util.List<AccountCode>)query.getResultList(); 
			 for(AccountCode codes : lscode){
				ArrayList<Object> codetemp = new ArrayList<Object>();
				codetemp.add(Integer.valueOf(codes.getCodeId()));
				codetemp.add(codes.getCodeName());
				codetemp.add(groups.getCategory());
				codetemp.add(codes.getAddDate());
				if(codes.getActive().equals("YES")){
					codetemp.add(Boolean.valueOf(true));
				}
				else{
					codetemp.add(Boolean.valueOf(false));
				}
				codetemp.add(codes.getManualPosting());
				
				datas.add(codetemp);
			 }
	
				/*Vector<Object> linetemp = new Vector<Object>();
				linetemp.addElement(new String(""));
				linetemp.addElement(new String(""));
				linetemp.addElement(new String(""));
				linetemp.addElement(new String(""));getOperationalExpensesCodes
				datas.add(linetemp);*/
		}
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
		//bean method to generate accounts code SEEN
	public byte[] geneAccountsCode(int group){
		//java.util.List<Integer> ls = em.createQuery("SELECT a.accountsCode FROM AccountCode a WHERE a.status=?1").setParameter(1, classification).getResultList();
		 Query query	= em.createQuery("SELECT a.codeId FROM AccountCode a WHERE  a.group.groupId  = ?1");
		// String status = "AccountStatus";
		 //query.setParameter("astatu", AccountStatus.valueOf(classification).values());
		 query.setParameter(1, group);
		 java.util.List<Integer> ls = (java.util.List<Integer>)query.getResultList();
		   try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	
	}
	
	//method use by projectview search to filter search using project title SEEN
	public byte[] getProjectByTitle(String title){
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.projectName = ?1");
		query.setParameter(1, title);    
		
		ArrayList<Object> temp = new ArrayList<Object>();
					
		try{
			Project pr = (Project)query.getSingleResult();
			temp.add(pr.getProjectName());
			temp.add(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
			temp.add(getQuoteTotal(pr.getProjectId()));
			temp.add(pr.getStartDate());
			temp.add(((ProjectStatus)pr.getStatus()).toString());
			temp.add(pr.getPaymentTerm()); //part of paymentIn adjustment
			temp.add(new Integer(pr.getProjectId()));

			}
			catch(NoResultException e){}
		 try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
		//method use by projectview search to filter search using client name SEEN
	public byte[] getProjectByName(String name){
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.clientName = ?1");
		query.setParameter(1, name);    
		ArrayList<Object> temp = new ArrayList<Object>();
					
		try{
			Project pr = (Project)query.getSingleResult();
			temp.add(pr.getProjectName());
			temp.add(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
			temp.add(getQuoteTotal(pr.getProjectId()));
			temp.add(pr.getStartDate());
			temp.add(((ProjectStatus)pr.getStatus()).toString());
			temp.add(pr.getPaymentTerm()); //part of paymentIn adjustment
			temp.add(new Integer(pr.getProjectId()));

			}
			catch(NoResultException e){}
		 try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(temp);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	//method use by projectview search to filter search using dates SEEN
	public byte[] geneProjectDate(Date from, Date to){
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.startDate BETWEEN ?1 AND ?2");
		query.setParameter(1, from);   
        query.setParameter(2, to); 
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		Vector<Vector<Object>> datas = new Vector<Vector<Object>>();
		for(Project pr : ls){
			Vector<Object> temp = new Vector<Object>();
			temp.addElement(pr.getProjectName());
			temp.addElement(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
			temp.addElement(getQuoteTotal(pr.getProjectId()));
			temp.addElement(pr.getStartDate());
			temp.addElement(((ProjectStatus)pr.getStatus()).toString());
			temp.addElement(pr.getPaymentTerm());// part of paymentIn adjustment
			temp.addElement(new Integer(pr.getProjectId()));
			datas.add(temp);
		}
		 try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
		//method use by projectview search to filter search using quoted amount SEEN
	public byte[] geneProjectAmount(BigDecimal from, BigDecimal to){
		Query query = em.createQuery("SELECT p FROM Project p ");
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		Vector<Vector<Object>> datas = new Vector<Vector<Object>>();
		for(Project pr : ls){

			Vector<Object> temp = new Vector<Object>();
			if(getQuoteTotal(pr.getProjectId()).compareTo(from) >= 0 && getQuoteTotal(pr.getProjectId()).compareTo(to) <= 0){
				temp.add(pr.getProjectName());
				temp.add(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
				temp.add(getQuoteTotal(pr.getProjectId()));
				temp.add(pr.getStartDate());
				temp.add(((ProjectStatus)pr.getStatus()).toString());
				temp.add(pr.getPaymentTerm()); //part of paymentIn adjustment
				temp.add(new Integer(pr.getProjectId()));
				datas.add(temp);
			}
		}
		 try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
		
		
	/**SEEN
		**method ProjectInfos() use by List Of Project GUI in supplier
		**use to get project info for project view interface
		**return byte of arralist of arraylist
	*/
	public byte[] projectInfos(){
		java.util.List<Project> ls = (java.util.List<Project>)em.createQuery("SELECT p FROM Project p").getResultList();	
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Project pr : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pr.getProjectName());
			temp.add(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
			temp.add(getQuoteTotal(pr.getProjectId()));
			temp.add(pr.getStartDate());
			temp.add(((ProjectStatus)pr.getStatus()).toString());
			temp.add(pr.getPaymentTerm()); //part of paymentIn adjustment
			temp.add(new Integer(pr.getProjectId()));
			datas.add(temp);
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
		
	}
		
	/*SEEN
		**method getProjectData() 
		**use to get project info for project view interface
		**return byte of vector of vector
	*/
	public byte[] getProjectData(){
		java.util.List<Project> ls = (java.util.List<Project>)em.createQuery("SELECT p FROM Project p").getResultList();	
		Vector<Vector<Object>> datas = new Vector<Vector<Object>>();
		for(Project pr : ls){
			Vector<Object> temp = new Vector<Object>();
			temp.addElement(pr.getProjectName());
			temp.addElement(pr.getCustomer().getBillingAddress().getBillingContact().getContactName());
			temp.addElement(getQuoteTotal(pr.getProjectId()));
			temp.addElement(pr.getStartDate());
			temp.addElement(((ProjectStatus)pr.getStatus()).toString());
			temp.addElement(pr.getPaymentTerm()); //part of paymentIn adjustment
			temp.addElement(new Integer(pr.getProjectId()));
			datas.add(temp);
		}
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(datas);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
		
	}
	
	/**
		**method getPaymentData() 
		**use to get payment info for incoming view interface
		**return byte of ArrayList of ArrayList
	*/
	public byte[] getPaymentData(){
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)em.createQuery("SELECT p FROM PaymentIn p").getResultList();
		return objectWriter(getPaymentDatas(ls));
	}
	
	/**
		**method getPaymentData(String datefrom, String dateto) 
		**use to get payment info for incoming view interface
		**return byte of ArrayList of ArrayList
	*/
	public byte[] getPaymentData(String datefrom, String dateto){
		Query query = em.createQuery("SELECT p FROM PaymentIn p WHERE p.invoiceDate BETWEEN ?1 AND ?2");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)query.getResultList();
		return objectWriter(getPaymentDatas(ls));
	}
	
	/**
		**method getPaymentData(String datefrom, String dateto, int projectId) 
		**use to get payment info for incoming view interface
		**return byte of ArrayList of ArrayList
	*/
	public byte[] getPaymentData(String datefrom, String dateto, int projectId){
		Query query = em.createQuery("SELECT p FROM PaymentIn p WHERE p.invoiceDate BETWEEN ?1 AND ?2 AND p.project=:projects");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter("projects", em.find(Project.class, projectId));
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)query.getResultList();
		return objectWriter(getPaymentDatas(ls));
	}
	
	/**
		**method getPaymentData(String datefrom, String dateto, String invoiceNumber) 
		**use to get payment info for incoming view interface
		**return byte of ArrayList of ArrayList
	*/
	public byte[] getPaymentData(String datefrom, String dateto, String invoiceNumber){
		Query query = em.createQuery("SELECT p FROM PaymentIn p WHERE p.invoiceDate BETWEEN ?1 AND ?2 AND p.invoice= ?3");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter(3, invoiceNumber);
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)query.getResultList();
		return objectWriter(getPaymentDatas(ls));
	}
	
	/**
		**method getPaymentData(String datefrom, String dateto, BigDecimal amtfrom, BigDecimal amtto) 
		**use to get payment info for incoming view interface
		**return byte of ArrayList of ArrayList
	*/
	public byte[] getPaymentData(String datefrom, String dateto, BigDecimal amtfrom, BigDecimal amtto){
		Query query = em.createQuery("SELECT p FROM PaymentIn p WHERE p.invoiceDate BETWEEN ?1 AND ?2");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<PaymentIn> ls = (java.util.List<PaymentIn>)query.getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(PaymentIn pr : ls){
			BigDecimal amount = BigDecimal.ZERO;
			Query queryAmt = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal = :jornal AND p.codeId.codeId != 10020");
			queryAmt.setParameter("jornal", pr.getJournal());
			try{
				amount = ((BigDecimal)queryAmt.getSingleResult()).abs();
				
			}
			catch(NoResultException e){}
			if(amount.compareTo(amtfrom) >= 0 && amount.compareTo(amtto) <= 0){
				ArrayList<Object> temp = new ArrayList<Object>();
				//temp.add(new Integer(pr.getpaymentInId()));
				temp.add(pr.getProject().getProjectName());
				temp.add(pr.getInvoice());
				temp.add(pr.getInvoiceDate());
				temp.add(amount);
				temp.add(totalIncoming(pr));
				temp.add(pr.getPaymentTerm());
				int daydiff = days(pr.getInvoiceDate());
				String [] strterm = pr.getPaymentTerm().split("\\s+");
				int term = (Integer.valueOf(strterm[0])).intValue();
				if(daydiff < term){
					temp.add(new String("0 days"));
				}
				else{
					int diff = daydiff - term;
					String days = null;
						if(diff == 1)
							days = "day";
						else{
							days = "days";
						}
					temp.add(String.format("%d %s", diff, days));
				}
				temp.add(pr.getpaymentInId());
				datas.add(temp);
			}
		}
		return objectWriter(datas);
	}
		
	/**SEEN
		**method getPaymentDatas() 
		**bean helper method for getting payment in information
		**return ArrayList of ArrayList
	*/
	private ArrayList<ArrayList<Object>> getPaymentDatas(java.util.List<PaymentIn> ls){
			
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(PaymentIn pr : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			//temp.add(new Integer(pr.getpaymentInId()));
			temp.add(pr.getProject().getProjectName());
			temp.add(pr.getInvoice());
			temp.add(pr.getInvoiceDate());
			Query query = em.createQuery("SELECT SUM(p.amount) FROM Posting p WHERE p.journal = :jornal AND p.codeId.codeId != 10020");
			query.setParameter("jornal", pr.getJournal());
			try{
				BigDecimal amount = (BigDecimal)query.getSingleResult();
				temp.add(amount.abs());
			}
			catch(NoResultException e){}

			temp.add(totalIncoming(pr));
			temp.add(pr.getPaymentTerm());
			int daydiff = days(pr.getInvoiceDate());
			String [] strterm = pr.getPaymentTerm().split("\\s+");
			int term = (Integer.valueOf(strterm[0])).intValue();
			if(daydiff < term){
				temp.add(new String("0 days"));
			}
			else{
				int diff = daydiff - term;
				String days = null;
					if(diff == 1)
						days = "day";
					else{
							days = "days";
					}
				temp.add(String.format("%d %s", diff, days));
			}
			temp.add(pr.getpaymentInId());
			datas.add(temp);
		}
		return datas;
	}
	
	public byte[] getProjectDetails(String datefrom, String dateto){ 
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.startDate BETWEEN ?1 AND ?2 ORDER BY p.startDate DESC");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		return objectWriter(getProjectDatas(ls));
	}
	public byte[] getProjectDetails(String datefrom, String dateto, BigDecimal amtfrom, BigDecimal amtto){
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.startDate BETWEEN ?1 AND ?2 ORDER BY p.startDate DESC");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Project pr : ls){
			BigDecimal amount = getQuoteTotal(pr.getProjectId());
			if(amount.compareTo(amtfrom) >= 0 && amount.compareTo(amtto) <= 0){
				ArrayList<Object> temp = new ArrayList<Object>();
				temp.add(pr.getProjectName());
				temp.add(pr.getCustomer().getCompanyName());
				temp.add(amount);
				temp.add(pr.getStartDate());
				temp.add(((ProjectStatus)pr.getStatus()).toString());
				//temp.add(pr.getPaymentTerm());
				temp.add(getPamentIn(pr) == null ? "No" : "Yes");
				temp.add(Integer.valueOf(pr.getProjectId()));
				datas.add(temp);
			}
		}
		return objectWriter(datas);
	
	}
	public byte[] getProjectDetailTitle(String datefrom, String dateto, String tiltle){ 
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.startDate BETWEEN ?1 AND ?2 AND p.projectName = :projectTitle ORDER BY p.startDate DESC");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter("projectTitle", tiltle);
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		return objectWriter(getProjectDatas(ls));
	}
	public byte[] getProjectDetailClient(String datefrom, String dateto, String name){ 
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.startDate BETWEEN ?1 AND ?2 AND p.customer.clientName = :contact ORDER BY p.startDate DESC");
		query.setParameter(1, java.sql.Date.valueOf(datefrom));
		query.setParameter(2, java.sql.Date.valueOf(dateto));
		query.setParameter("contact", name);
		java.util.List<Project> ls = (java.util.List<Project>)query.getResultList();
		return objectWriter(getProjectDatas(ls));
	}
	
	private PaymentIn getPamentIn(Project project){
			Query query_inv = em.createQuery("SELECT p FROM PaymentIn p WHERE p.project = :projects");
			query_inv.setParameter("projects", project);
			try{
				return (PaymentIn)query_inv.getSingleResult();
			}
			catch(NoResultException e){return null;}
	}
	/**SEEN
		**method getProjectDatas(java.util.List<PaymentIn> ls)
		**bean helper method for getting payment in information
		**return ArrayList of ArrayList IN
	*/
	private ArrayList<ArrayList<Object>> getProjectDatas(java.util.List<Project> ls){
		ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		for(Project pr : ls){
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(pr.getProjectName());
			temp.add(pr.getCustomer().getCompanyName());
			temp.add(getQuoteTotal(pr.getProjectId()));
			temp.add(pr.getStartDate());
			temp.add(((ProjectStatus)pr.getStatus()).toString());
			//temp.add(pr.getPaymentTerm());
			temp.add(getPamentIn(pr) == null ? "No" : "Yes");
			temp.add(Integer.valueOf(pr.getProjectId()));
			datas.add(temp);
		}
		return datas;
	}	
	
	/**SEEN
		**method projectFinancialActivities(java.util.List<PaymentIn> ls)
		**bean helper method for getting payment in information getPaymentData
		**return ArrayList of ArrayList 
	*/
	public byte[] projectFinancialActivities(int projectId){
		Project project = em.find(Project.class, projectId);
		BigDecimal materialTotal = BigDecimal.ZERO;
		BigDecimal contractorTotal = BigDecimal.ZERO;
		BigDecimal projectTotal = BigDecimal.ZERO;
		ArrayList<BigDecimal> datas = new ArrayList<BigDecimal>();
		
		//total received from customer up todate
		PaymentIn customerInvoice = null;
		Query querycust = em.createQuery("SELECT p FROM PaymentIn p WHERE p.project = :projects");
		querycust.setParameter("projects", project);
		try{
			customerInvoice = (PaymentIn)querycust.getSingleResult();
		}
		catch(NoResultException e){}
		if(customerInvoice != null){
			ArrayList<Incoming> custInvoice = new ArrayList<Incoming>(customerInvoice.getIncoming());
			for(Incoming income : custInvoice){
				Query queryReceived = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :journals AND p.codeId.codeId = 10010 OR p.codeId.codeId = 10040");
				queryReceived.setParameter("journals", income.getJournal());
				projectTotal = projectTotal.add((BigDecimal)queryReceived.getSingleResult());
			}
		}
		datas.add(projectTotal);
		
		//total paid on material for a particuler project
		SupplierInvoice supplierInvoice = null;
		Query querysup = em.createQuery("SELECT s FROM SupplierInvoice s WHERE s.project = :projects");
		querysup.setParameter("projects", project);
		try{
			supplierInvoice = (SupplierInvoice)querysup.getSingleResult();
		}
		catch(NoResultException e){}
		if(supplierInvoice != null){
			ArrayList<SupplierPayment> supplierInvoices = new ArrayList<SupplierPayment>(supplierInvoice.getSupplierPayment());
			for(SupplierPayment supplierPayment : supplierInvoices){
				Query querySupplier = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :journals AND p.codeId.codeId = 10010 OR p.codeId.codeId = 10040 OR p.codeId.codeId = 20020");
				querySupplier.setParameter("journals", supplierPayment.getJournal());
				materialTotal = materialTotal.add(((BigDecimal)querySupplier.getSingleResult()).abs());
			}
		}
		datas.add(materialTotal);
		
		//total paid on sub contractor for a particuler project
		SubContractorInvoice contractorInvoice = null;
		Query querycon = em.createQuery("SELECT s FROM SubContractorInvoice s WHERE s.project = :projects");
		querycon.setParameter("projects", project);
		try{
			contractorInvoice = (SubContractorInvoice)querycon.getSingleResult();
		}
		catch(NoResultException e){}
		if(contractorInvoice != null){
			ArrayList<SubContractorPayment> contractorInvoices = new ArrayList<SubContractorPayment>(contractorInvoice.getSubContractorPayment());
			for(SubContractorPayment contractorPayment : contractorInvoices){
				Query queryContractor = em.createQuery("SELECT p.amount FROM Posting p WHERE p.journal = :journals AND p.codeId.codeId = 10010 OR p.codeId.codeId = 10040");
				queryContractor.setParameter("journals", contractorPayment.getJournal());
				contractorTotal = contractorTotal.add(((BigDecimal)queryContractor.getSingleResult()).abs());
			}
		}
		datas.add(contractorTotal);
		
		datas.add(projectTotal.subtract(materialTotal.add(contractorTotal)));
		
		return objectWriter(datas);
	}
	
	// SEEN
	public byte[] getProjectDetails(int projectId){
		//Project pr = findProject(projectId);
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.projectId = ?1");
		query.setParameter(1, projectId); 
		Project pr = (Project)query.getSingleResult();
		Customer customer = pr.getCustomer();
		java.util.List<Object> ls= new ArrayList<Object>();
		ls.add(new Integer(pr.getProjectId()));
		ls.add(pr.getProjectName());
		ls.add(customer.getBillingAddress().getBillingContact().getContactName());
		ls.add(pr.getSiteAddress().getAddresses());
		ls.add(customer.getBillingAddress().getBillingContact().getClientTele());
		ls.add(customer.getBillingAddress().getBillingContact().getClientEmail());
		ls.add(getQuoteTotal(pr.getProjectId()));
		//ls.add(pr.getEstimateCost());
		ls.add(pr.getStartDate());
		//ls.add(pr.getPaymentTerm());
		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(ls);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
}