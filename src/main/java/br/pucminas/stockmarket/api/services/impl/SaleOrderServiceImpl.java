package br.pucminas.stockmarket.api.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.services.SaleOrderService;

@Service
public class SaleOrderServiceImpl implements SaleOrderService
{

	private final RabbitTemplate rabbitTemplate;
	
	public SaleOrderServiceImpl(RabbitTemplate p_rabbitTemplate) 
	{
		this.rabbitTemplate = p_rabbitTemplate;
	}
	
	@Override
	public void sendMessageSalesStock(SaleOrderDTO saleOrderDTO)
	{
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_DEAD_SALE_ORDER, saleOrderDTO);
	}
}
