@Grab('org.springframework.boot:spring-boot-starter-websocket:1.0.0.RC3')
@Grab('org.springframework:spring-messaging:4.0.1.RELEASE')

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;


@Configuration
@EnableScheduling
@ComponentScan(
		excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, value = Configuration.class)
)
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {


	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4).maxPoolSize(10);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue/", "/topic/");
		registry.setApplicationDestinationPrefixes("/app");
	}

}