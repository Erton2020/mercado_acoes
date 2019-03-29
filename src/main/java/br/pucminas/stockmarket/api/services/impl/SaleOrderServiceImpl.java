package br.pucminas.stockmarket.api.services.impl;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.services.InvestorService;
import br.pucminas.stockmarket.api.services.SaleOrderService;
import br.pucminas.stockmarket.api.services.StockService;
import br.pucminas.stockmarket.api.utils.EmailSenderUtil;
import br.pucminas.stockmarket.api.utils.EmailTemplateUtil;

@Service
public class SaleOrderServiceImpl implements SaleOrderService
{

	private final RabbitTemplate rabbitTemplate;
	private EmailTemplateUtil emailTemplateUtil;
	private EmailSenderUtil emailSenderUtil;
	private InvestorService investorService;
	private StockService stockService;
	
	public SaleOrderServiceImpl(RabbitTemplate p_rabbitTemplate, EmailTemplateUtil p_emailTemplateUtil,
			EmailSenderUtil p_emailSenderUtil, InvestorService p_investorService,  StockService p_stockService) 
	{
		this.rabbitTemplate = p_rabbitTemplate;
		this.emailTemplateUtil= p_emailTemplateUtil;
		this.emailSenderUtil = p_emailSenderUtil;
		this.investorService = p_investorService;
		this.stockService = p_stockService;
	}
	
	@Override
	public void sendMessageSaleStock(SaleOrderDTO saleOrderDTO, Stock stock)
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_DEAD_SALE_ORDER, saleOrderDTO);
		
		sendEmailSaleOrderSolicitation(saleOrderDTO, stock);
	}

	private void sendEmailSaleOrderSolicitation(SaleOrderDTO saleOrderDTO, Stock stock) 
	{
		Optional<Investor> investor = investorService.findInvestorById(saleOrderDTO.getInvestorId());
		
		Double currentStockValue = stockService.calculateStockCurrentValue(stock.getHistoricalStockPrices(), CalculationTypeEnum.SELL);
		if(investor.isPresent())
		{
			String bodyInvestorEmail = emailTemplateUtil.saleOrderSolicitationInvestorEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), saleOrderDTO.getAmount(), currentStockValue);			
			String subjectInvestorEmail = "Solicitação de venda de ações da empresa " + stock.getCompany().getName();
			emailSenderUtil.sendEmail(investor.get().getEmail(),subjectInvestorEmail, bodyInvestorEmail);
			
			String bodyComapanyEmail = emailTemplateUtil.saleOrderSolicitationCompanyEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), saleOrderDTO.getAmount(), currentStockValue);						
			String subjectCompanyEmail = "Solicitação de recompra de "+ saleOrderDTO.getAmount() + " ações " + stock.getDescription();
			emailSenderUtil.sendEmail(stock.getCompany().getEmail(),subjectCompanyEmail, bodyComapanyEmail);
		}
	}
}
