package com.queenzend.marketingplatformandroid.WebServices;

public interface TaskNotifier<T>{


	public void onProgressChange(final int progressCompleted);


	public void onError(final Throwable response);


	public void onSuccess(final T response);

}
