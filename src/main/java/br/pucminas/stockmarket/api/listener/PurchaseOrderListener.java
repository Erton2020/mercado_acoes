package br.pucminas.stockmarket.api.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;

@Component
public class PurchaseOrderListener 
{	
	static final Logger logger = LoggerFactory.getLogger(PurchaseOrderListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PURCHASE_ORDER)
    public void processPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
    {
        logger.info("Processando ordem de compra!");
    }
}
