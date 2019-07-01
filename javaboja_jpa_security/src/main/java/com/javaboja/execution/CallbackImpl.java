package com.javaboja.execution;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Getter
@Setter
@Slf4j
public class CallbackImpl implements Callback{

	private Response response;
	private IOException e;
	public final CompletableFuture<Response> future = new CompletableFuture<>(); 

	@Override
	public void onFailure(Call call, IOException e) {
		// TODO Auto-generated method stub
		log.info("실패 : "+e.getMessage());
		future.completeExceptionally(e);
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		// TODO Auto-generated method stub
		log.info("성공 : "+response.body().string());
		future.complete(response);
	}

}
