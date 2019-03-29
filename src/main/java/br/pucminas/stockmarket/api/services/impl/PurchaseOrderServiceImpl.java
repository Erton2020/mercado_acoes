package br.pucminas.stockmarket.api.services.impl;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.entities.PurchaseOrder;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.repositories.PurchaseOrderRepository;
import br.pucminas.stockmarket.api.services.InvestorService;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;
import br.pucminas.stockmarket.api.services.StockService;
import br.pucminas.stockmarket.api.utils.EmailSenderUtil;
import br.pucminas.stockmarket.api.utils.EmailTemplateUtil;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService
{

	RabbitTemplate rabbitTemplate;
	EmailTemplateUtil emailTemplateUtil;
	EmailSenderUtil emailSenderUtil;
	InvestorService investorService;
	StockService stockService;
	PurchaseOrderRepository purchaseOrderRepository;
	
	public PurchaseOrderServiceImpl(RabbitTemplate p_rabbitTemplate, EmailTemplateUtil p_emailTemplateUtil,
			EmailSenderUtil p_emailSenderUtil, InvestorService p_investorService,  StockService p_stockService,
			PurchaseOrderRepository p_purchaseOrderRepository) 
	{
		this.rabbitTemplate = p_rabbitTemplate;
		this.emailTemplateUtil= p_emailTemplateUtil;
		this.emailSenderUtil = p_emailSenderUtil;
		this.investorService = p_investorService;
		this.stockService = p_stockService;
		this.purchaseOrderRepository= p_purchaseOrderRepository;
	}
	
	@Override
	public void sendMessagePurchaseStock(PurchaseOrderDTO purchaseOrderDTO, Stock stock) 
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_PURCHASE_ORDER, purchaseOrderDTO);
		
		sendEmailPuchaseOrderSolicitation(purchaseOrderDTO, stock);
	}
	
	
	private void sendEmailPuchaseOrderSolicitation(PurchaseOrderDTO purchaseOrderDTO, Stock stock) 
	{
		Optional<Investor> investor = investorService.findInvestorById(purchaseOrderDTO.getInvestorId());
		
		Double currentStockValue = stockService.calculateStockCurrentValue(stock.getHistoricalStockPrices(), CalculationTypeEnum.BUY);
		if(investor.isPresent())
		{
			String bodyInvestorEmail = emailTemplateUtil.purchaseOrderSolicitationInvestorEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), purchaseOrderDTO.getAmount(), currentStockValue);			
			String subjectInvestorEmail = "Solicitação de compra de ações da empresa " + stock.getCompany().getName();
			emailSenderUtil.sendEmail(investor.get().getEmail(),subjectInvestorEmail, bodyInvestorEmail);
			
			String bodyComapanyEmail = emailTemplateUtil.purchaseOrderSolicitationCompanyEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), purchaseOrderDTO.getAmount(), currentStockValue);						
			String subjectCompanyEmail = "Solicitação de venda de "+ purchaseOrderDTO.getAmount() + " ações " + stock.getDescription();
			emailSenderUtil.sendEmail(stock.getCompany().getEmail(),subjectCompanyEmail, bodyComapanyEmail);
		}
	}
	
	private void sendEmailPuchaseOrderConfirmation(PurchaseOrder purchaseOrder) 
	{	
/*		String bodyInvestorEmail = emailTemplateUtil.purchaseOrderSolicitationInvestorEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), purchaseOrderDTO.getAmount(), currentStockValue);			
		String subjectInvestorEmail = "Solicitação de compra de ações da empresa " + stock.getCompany().getName();
		emailSenderUtil.sendEmail(investor.get().getEmail(),subjectInvestorEmail, bodyInvestorEmail);
		
		String bodyComapanyEmail = emailTemplateUtil.purchaseOrderSolicitationCompanyEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), purchaseOrderDTO.getAmount(), currentStockValue);						
		String subjectCompanyEmail = "Solicitação de venda de "+ purchaseOrderDTO.getAmount() + " ações " + stock.getDescription();
		emailSenderUtil.sendEmail(stock.getCompany().getEmail(),subjectCompanyEmail, bodyComapanyEmail);	*/
	}

	@Override
	public PurchaseOrder insert(PurchaseOrder purchaseOrder) 
	{
		return purchaseOrderRepository.save(purchaseOrder);
	}
}
