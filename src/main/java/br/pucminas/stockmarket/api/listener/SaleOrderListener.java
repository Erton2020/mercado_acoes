package br.pucminas.stockmarket.api.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;

@Component
public class SaleOrderListener 
{
	static final Logger logger = LoggerFactory.getLogger(SaleOrderListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SALE_ORDER)
    public void processSaleOrder(SaleOrderDTO saleOrderDTO)
    {
        logger.info("Processando ordem de compra!");
    }
}
