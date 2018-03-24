package com.armandorvila.poc.accounts.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.armandorvila.poc.accounts.domain.AccountTransaction;
import com.armandorvila.poc.accounts.exception.ServiceNotFoundExeption;

import reactor.core.publisher.Flux;

@Service
public class TransactionsService {
	
	static final String TRANSACTIONS_SERVICE_PATH = "/transactions?limit={limit}&offset={offset}";
	static final String TRANSACTIONS_SERVICE_ID = "transactions-service";

	private LoadBalancerClient loadBalancer;
	private WebClient webClient;

	public TransactionsService(LoadBalancerClient loadBalancer, WebClient webClient) {
		this.loadBalancer = loadBalancer;
		this.webClient = webClient;
	}

	public Flux<AccountTransaction> getAccountTransactions(String accountId, Integer limit, Integer offset) {
		final String uri = getServiceUrl() + TRANSACTIONS_SERVICE_PATH;
		
		return webClient.get()
				.uri(uri, limit, offset)
				.retrieve()
				.bodyToFlux(AccountTransaction.class);
	}
	
	private String getServiceUrl() {
		ServiceInstance instance = loadBalancer.choose(TRANSACTIONS_SERVICE_ID);

		if (instance == null) {
			throw new ServiceNotFoundExeption(
					String.format("Unable to find instance for service %s", TRANSACTIONS_SERVICE_ID));
		}
		return instance.getUri().toString();
	}
}
