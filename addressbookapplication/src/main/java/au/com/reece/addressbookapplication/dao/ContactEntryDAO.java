package au.com.reece.addressbookapplication.dao;

import java.util.List;
import java.util.Optional;

public interface ContactEntryDAO<T> {
	List<T> getAll();
	List<T> getContactEntryByPhoneNumber(Integer num, String str);
	Optional<T> getContactEntryByPhoneNumber(Integer phNumber);
	Integer deleteByPhoneNumber(Integer num);
	Optional<T> upsert(T e);
}
