package br.pucminas.stockmarket.api.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService
{

	private final RabbitTemplate rabbitTemplate;
	
	public PurchaseOrderServiceImpl(RabbitTemplate p_rabbitTemplate)
	{
		this.rabbitTemplate = p_rabbitTemplate;
	}
	
	@Override
	public void sendMessagePurchaseStock(PurchaseOrderDTO purchaseOrderDTO) 
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_PURCHASE_ORDER, purchaseOrderDTO);
	}
}
