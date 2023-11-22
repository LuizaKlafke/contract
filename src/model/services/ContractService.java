package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		for (int i = 0; i < months; i++) {
			
			double basicQuota = contract.getTotalValue() / months;
			double interestFee = basicQuota + onlinePaymentService.interest(basicQuota, i+1);
			Double amount = interestFee + onlinePaymentService.paymentFee(interestFee);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(contract.getDate());
			cal.add(Calendar.MONTH, i+1);
			Date dueDate = cal.getTime();
			
			contract.addInstallment(new Installment(dueDate, amount));
			
		}
	}

}
