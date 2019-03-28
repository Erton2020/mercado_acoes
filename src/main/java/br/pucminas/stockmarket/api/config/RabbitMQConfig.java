package br.pucminas.stockmarket.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

	public static final String QUEUE_PURCHASE_ORDER = "purchase-order-queue";
	public static final String QUEUE_SALE_ORDER = "sale-order-queue";
    public static final String EXCHANGE_PURCHASE_ORDER = "purchase-order-exchange";
    public static final String EXCHANGE_SALE_ORDER = "sale-order-exchange";
    public static final String QUEUE_DEAD_PURCHASE_ORDER = "dead-purchase_order-queue";
    public static final String QUEUE_DEAD_SALE_ORDER = "dead-salse-order-queue";
 
    @Bean
    Queue purchaseOrderQueue() {
    	return QueueBuilder.durable(QUEUE_PURCHASE_ORDER)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_PURCHASE_ORDER)
                .withArgument("x-message-ttl", 15000)
                .build();
    }
    
    @Bean
    Queue saleOrderQueue() {
    	return QueueBuilder.durable(QUEUE_SALE_ORDER)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_SALE_ORDER)
                .withArgument("x-message-ttl", 15000)
                .build();
    }
 
    @Bean
    Queue deadPurchaseOrderQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_PURCHASE_ORDER).build();
    }
    
    @Bean
    Queue deadSaleOrderQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_SALE_ORDER).build();
    }
 
    @Bean
    Exchange purchaseOrder() {
        return ExchangeBuilder.topicExchange(EXCHANGE_PURCHASE_ORDER).build();
    }
    
    @Bean
    Exchange saleOrderExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_SALE_ORDER).build();
    }
 
    @Bean
    Binding purchaseOrderBinding(Queue messagesQueue, TopicExchange messagesExchange) 
    {
        return BindingBuilder.bind(messagesQueue).to(messagesExchange).with(QUEUE_PURCHASE_ORDER);
    }
    
    @Bean
    Binding saleOrderbinding(Queue messagesQueue, TopicExchange messagesExchange) 
    {
        return BindingBuilder.bind(messagesQueue).to(messagesExchange).with(QUEUE_SALE_ORDER);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
 
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar register) {
        register.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
 
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
 
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
