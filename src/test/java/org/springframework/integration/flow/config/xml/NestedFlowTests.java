package org.springframework.integration.flow.config.xml;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class NestedFlowTests {
  @Autowired
  MessageChannel inputChannel;
  
  @Autowired
  SubscribableChannel outputChannel;
  
  @Test 
  @DirtiesContext
  public void testNestedFlowOneExecution() {
	  final AtomicInteger count = new AtomicInteger();
	  outputChannel.subscribe(new MessageHandler() {

			public void handleMessage(Message<?> message) throws MessagingException {
				count.getAndIncrement();
				
			}});
	  inputChannel.send(new GenericMessage<String>("hello"));
	  assertEquals(1,count.get());
  }
  
  @Test
  @DirtiesContext
  public void testNestedFlowMultipleExecutions() {
	  final AtomicInteger count = new AtomicInteger();
	  
	  outputChannel.subscribe(new MessageHandler() {

		public void handleMessage(Message<?> message) throws MessagingException {
			count.getAndIncrement();
			
		}});
	  
	  
	  for (int i=0; i<2; i++) {
		  inputChannel.send(new GenericMessage<String>("hello"));
	  }
	  assertEquals(2,count.get());
  }
  
}
