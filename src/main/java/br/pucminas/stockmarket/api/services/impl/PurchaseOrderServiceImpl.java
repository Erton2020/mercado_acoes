package br.pucminas.stockmarket.api.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;
import br.pucminas.stockmarket.api.utils.EmailSenderUtil;
import br.pucminas.stockmarket.api.utils.EmailTemplateUtil;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService
{

	private final RabbitTemplate rabbitTemplate;
	private EmailTemplateUtil emailTemplateUtil;
	private EmailSenderUtil emailSenderUtil;
	
	public PurchaseOrderServiceImpl(RabbitTemplate p_rabbitTemplate, EmailTemplateUtil p_emailTemplateUtil,
			EmailSenderUtil p_emailSenderUtil) 
	{
		this.rabbitTemplate = p_rabbitTemplate;
		this.emailTemplateUtil= p_emailTemplateUtil;
		this.emailSenderUtil = p_emailSenderUtil;
	}
	
	@Override
	public void sendMessagePurchaseStock(PurchaseOrderDTO purchaseOrderDTO) 
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_PURCHASE_ORDER, purchaseOrderDTO);
	}
}
