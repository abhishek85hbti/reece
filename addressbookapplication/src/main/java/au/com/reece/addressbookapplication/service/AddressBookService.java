package au.com.reece.addressbookapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import au.com.reece.addressbookapplication.serviceUtil.Status;

@Service
public interface AddressBookService<T> {
	
	public List<T> getAll();
	
	public Status add(T e);
	
	public Status add(String str);
	
	public Status delete(String param);
}
          