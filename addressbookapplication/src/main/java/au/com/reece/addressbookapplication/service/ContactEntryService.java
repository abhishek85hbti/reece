package au.com.reece.addressbookapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import au.com.reece.addressbookapplication.serviceUtil.Status;

@Service
public interface ContactEntryService<T> {
	
	public List<T> getAll();
	
	public List<T> getAll(String param);
	
	public Status add(T e, String param);
	
	public Status delete(Integer param);
}
