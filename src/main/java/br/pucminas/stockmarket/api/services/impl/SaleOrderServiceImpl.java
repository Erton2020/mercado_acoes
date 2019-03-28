package br.pucminas.stockmarket.api.services.impl;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.services.CompanyService;
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
	private CompanyService companyService;
	private StockService stockService;
	
	public SaleOrderServiceImpl(RabbitTemplate p_rabbitTemplate, EmailTemplateUtil p_emailTemplateUtil,
			EmailSenderUtil p_emailSenderUtil) 
	{
		this.rabbitTemplate = p_rabbitTemplate;
		this.emailTemplateUtil= p_emailTemplateUtil;
		this.emailSenderUtil = p_emailSenderUtil;
	}
	
	@Override
	public void sendMessageSalesStock(SaleOrderDTO saleOrderDTO, Stock stock)
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_DEAD_SALE_ORDER, saleOrderDTO);
		
		sendEmailSSaleOrderSolicitation(saleOrderDTO, stock);
	}

	private void sendEmailSSaleOrderSolicitation(SaleOrderDTO saleOrderDTO, Stock stock) 
	{
		Optional<Investor> investor = investorService.findInvestorById(saleOrderDTO.getInvestorId());
		
		Double currentStockValue = stockService.calculateStockCurrentValue(stock.getHistoricalStockPrices(), CalculationTypeEnum.BUY);
		if(investor.isPresent())
		{
			String bodyEmail = emailTemplateUtil.saleOrderSolicitationInvestorEmailBody(investor.get().getName(), stock.getDescription(), stock.getStockType().toString(), stock.getCompany().getName(), saleOrderDTO.getAmount(), currentStockValue);			
			String subject = "Solicitação de compra de ações da empresa " + stock.getCompany().getName();
			
			emailSenderUtil.sendEmail(investor.get().getEmail(),subject, bodyEmail);
		}
		
	}
}
