package com.deloitte.shk.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.deloitte.shk.entity.Company;
import com.deloitte.shk.entity.Dipnot;
import com.deloitte.shk.entity.Kullanici;
import com.deloitte.shk.entity.TrafikStat;
import com.deloitte.shk.enums.Tablo;
import com.deloitte.shk.generic.GenericBean;
import com.deloitte.shk.generic.GenericService;
import com.deloitte.shk.qualifier.CurrentUser;
import com.deloitte.shk.services.TrafikStatService;
/**
 * @author yusufertekin
 *
 */
@Named
@SessionScoped
public class TrafikStatBean extends GenericBean<TrafikStat,Long> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject TrafikStatService trafikStatService;
	@Inject @CurrentUser Kullanici currentUser;
	
	
	@Override
	public GenericService<TrafikStat,Long> getEntityService() {
		// TODO Auto-generated method stub
		return trafikStatService;
	}

	
	public void sorgula()
	{
		if(currentUser.getCompany() != null && currentUser.getCompany().getCompanyId() != null)
		{
			try {
				getInstance().setCompany((Company)currentUser.getCompany().clone());
				setCompany((Company)currentUser.getCompany().clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getInstance().setDonem(getSelectedDonem());
		Dipnot tmp = trafikStatService.findDipnotByDonemAndCompany(getInstance().getDonem(), getInstance().getCompany(), Tablo.TRAFIKSTAT.getValue());
		if(tmp != null)
		{
			setDipnot(tmp);
		}
		else
		{
			setDipnot(new Dipnot());
		}
		TrafikStat trafikStat = trafikStatService.findByDonemAndCompany(getInstance().getDonem(), getInstance().getCompany());
		if(trafikStat != null)
		{
			setInstance(trafikStat);
		}
		else
		{
			newInstance();
		}
	}
	public String initPage() {
		// TODO Auto-generated method stub
		newInstance();
		getInstance().setDonem(getDonemList().get(0));
		setSelectedDonem(getDonemList().get(0));
		sorgula();
		return "/xhtml/user/trafikStat.xhtml?faces-redirect=true";
	}


}
