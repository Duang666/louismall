package com.louisblogs.louismall.seckill.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：luqi
 * @date ：2021/6/28 21:57
 * @description创建RabbitMQ 队列 交换机
 * 运行之前，一定要小心，否则要删除队列/交换机重新运行 麻烦！
 *
 * 解决消息丢失(最怕)
 *  1 做好消息确认机制（publisher，consumer【手动ack】）
 *  2 每一个发送的消息都在数据库做好记录。定期将失败的消息再次发送一次
 * 解决消息重复
 *  1 幂等性
 *  2 防重表
 *  3 RabbitMQ自带redelivered (做法过于暴力)
 * 解决消息积压
 *  1 增加更多的消费者
 *  2 上线专门的队列消费服务，取出来，记录到数据库，离线慢慢处理
 */
//开启RabbitMQ消息队列 不监听消息可以不加
//@EnableRabbit
@Configuration
public class MyRabbitMQConfig {

	@Bean
	public MessageConverter messageConverter() {

		return new Jackson2JsonMessageConverter();
	}
}